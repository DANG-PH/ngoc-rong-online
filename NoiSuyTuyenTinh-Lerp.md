# Nội suy tuyến tính (Lerp) trong Game Multiplayer Real-Time

> Tài liệu ghi lại quá trình thiết kế và implement hệ thống đồng bộ vị trí player từ server xuống client trong game Dragon Boy multiplayer, từ giải pháp đơn giản đến giải pháp production-grade với snapshot interpolation buffer và hiệu ứng teleport.

## Mục lục

1. [Bài toán đặt ra](#1-bài-toán-đặt-ra)
2. [Các hướng giải quyết](#2-các-hướng-giải-quyết)
3. [So sánh chi tiết Cách 1 (Simple Lerp) vs Cách 2 (Snapshot Interpolation Buffer)](#3-so-sánh-chi-tiết)
4. [Tại sao chọn cách mới](#4-tại-sao-chọn-cách-mới)
5. [Flow chi tiết của cách mới](#5-flow-chi-tiết-của-cách-mới)
6. [Deque là gì và tại sao dùng nó](#6-deque-là-gì-và-tại-sao-dùng-nó)
7. [Vấn đề render delay 150ms](#7-vấn-đề-render-delay-150ms)
8. [Vì sao KHÔNG dùng extrapolation](#8-vì-sao-không-dùng-extrapolation)
9. [Xử lý các trường hợp đặc biệt](#9-xử-lý-các-trường-hợp-đặc-biệt)
10. [Bài toán "Idle then Move" và cách giải](#10-bài-toán-idle-then-move-và-cách-giải)
11. [Tips và best practices cho dev](#11-tips-và-best-practices-cho-dev)
12. [Roadmap mở rộng](#12-roadmap-mở-rộng)

---

## 1. Bài toán đặt ra

### 1.1. Bối cảnh

Trong game Dragon Boy multiplayer, mỗi player đang điều khiển trên client của họ sẽ gửi vị trí lên server. Server tổng hợp vị trí của tất cả player trong cùng map, rồi broadcast (gửi lại) cho tất cả client trong map đó.

Server tick mỗi **50ms** một lần (tức 20 packet/giây mỗi player). Mỗi packet chứa:

```
PlayerSync packet:
- userId
- x, y (vị trí)
- dir (hướng nhìn: 1 hoặc -1)
- trangthai (DUNG_YEN / DI_CHUYEN / BAY_NGANG / ROI / ...)
- dau, than, chan (skin id)
- timeChoHienBay, lechDauX, lechDauY, ... (các offset render)
- frameVanBay, dangMangVanBay, tenVanBay (vân bay)
- rong, cao (kích thước hitbox)
- avatar
```

### 1.2. Thử thách

Mạng Internet **không hoàn hảo**:

- **Jitter**: packet đến không đều. Server gửi đều mỗi 50ms nhưng client nhận lúc 30ms, lúc 80ms, lúc 200ms.
- **Packet loss**: 1-5% packet bị mất hoàn toàn, không bao giờ đến.
- **Burst delay**: lag spike có thể delay 500ms-1s rồi dồn nhiều packet đến cùng lúc.
- **Reordering**: hiếm nhưng có — packet B đến trước packet A.

Nếu render thẳng vị trí từ packet → **player giật cục**, trải nghiệm tệ.

### 1.3. Mục tiêu

- Player khác di chuyển **mượt mà** trên client của mình, bất chấp jitter và packet loss nhẹ
- Khi player thực sự teleport (skill, đổi map): có hiệu ứng visual rõ ràng
- **An toàn với physics** — không lọt qua đất, qua tường khi mạng tệ
- Code dễ bảo trì, không over-engineer
- Latency hiển thị chấp nhận được (≤ 200ms)

---

## 2. Các hướng giải quyết

Có 4 hướng chính để xử lý đồng bộ vị trí trong game multiplayer:

### 2.1. Set thẳng vị trí (Naive)

```java
ps.x = packet.x;
ps.y = packet.y;
```

**Đánh giá:** Tệ nhất. Player giật cục mỗi khi nhận packet. Chỉ dùng được trong LAN với latency cực thấp.

### 2.2. Simple Lerp (Cách 1 — version cũ)

Lưu vị trí "đích" từ server vào `serverX/serverY`, mỗi frame lerp tới đó:

```java
ps.serverX = packet.x;
ps.serverY = packet.y;

// Trong update:
x += (serverX - x) * lerpSpeed * delta;
y += (serverY - y) * lerpSpeed * delta;
```

**Đánh giá:** Tốt hơn naive nhưng vẫn có jitter và "trượt" theo lerpSpeed cố định.

### 2.3. Snapshot Interpolation Buffer (Cách 2 — version mới)

Lưu nhiều snapshot vào buffer, render trễ ~150ms để luôn có 2 snapshot bao quanh thời điểm render → lerp giữa 2 snapshot có sẵn.

**Đây là cách của Source Engine (CS, Dota), Overwatch, hầu hết game online chuyên nghiệp.**

### 2.4. Client-side Prediction + Server Reconciliation (cho local player)

Client tự predict vị trí của chính mình (không chờ server), khi server reply thì so sánh và rollback nếu sai. Cách này dành cho **local player** (player đang điều khiển), không áp dụng cho remote player.

> Trong tài liệu này tập trung vào Cách 2 — interpolation cho **remote players**.

---

## 3. So sánh chi tiết

### 3.1. Simple Lerp (Cách cũ)

```java
public float serverX, serverY;

// Khi nhận packet:
ps.serverX = x;
ps.serverY = y;
ps.dau = dau;  // discrete fields set thẳng
// ...

// Mỗi frame:
float lerpSpeed = 12f;
x += (serverX - x) * lerpSpeed * delta;
y += (serverY - y) * lerpSpeed * delta;

if (dist > 200f) {  // teleport
    x = serverX;
    y = serverY;
}
```

**Ưu điểm:**

- Đơn giản, ít tốn RAM
- Latency thấp (bám sát server gần như ngay lập tức)
- Dễ debug

**Nhược điểm:**

- **Jitter rất nặng khi mạng không ổn định.** Mỗi lần packet đến, `serverX/serverY` nhảy → player giật.
- **Mất packet → player đứng yên đột ngột** rồi nhảy vọt khi packet sau đến.
- **Tốc độ player không đều** vì `lerpSpeed` cố định nhưng khoảng cách giữa các snapshot thay đổi theo network.
- Player luôn bị "trễ" một khoảng so với server (asymptotic), không bao giờ đuổi kịp khi đang di chuyển liên tục.

### 3.2. Snapshot Interpolation Buffer (Cách mới)

```java
private final Deque<PlayerSnapshot> snapshots = new ArrayDeque<>();
private static final long RENDER_DELAY_MS = 150;
private static final long BUFFER_MAX_AGE_MS = 1000;

// Khi nhận packet — chỉ push vào buffer, KHÔNG ghi trực tiếp:
public void applyServerSync(...) {
    PlayerSnapshot snap = new PlayerSnapshot();
    snap.time = System.currentTimeMillis();
    snap.x = x; snap.y = y; ...
    snapshots.addLast(snap);
    latestSnap = snap;
    
    // Cleanup snapshot quá cũ, giữ ít nhất 1
    long cutoff = snap.time - BUFFER_MAX_AGE_MS;
    while (snapshots.size() > 1 && snapshots.peekFirst().time < cutoff) {
        snapshots.pollFirst();
    }
}

// Mỗi frame trong capNhat() → interpolateFromBuffer(delta):
//   1. Tìm 2 snapshot bao quanh renderTime (= now - 150ms)
//   2. Lerp giữa chúng
//   3. Nếu không có → fallback (hold last position hoặc snap teleport)
```

**Ưu điểm:**

- **Mượt bất chấp jitter network.** Render luôn nội suy giữa 2 snapshot đã có sẵn.
- **Chống mất packet 1-2 gói.** Buffer 150ms = 3 server ticks dự phòng.
- **Tốc độ chuyển động đều, đúng tốc độ server gửi.**
- **An toàn với physics** — không bao giờ render player ở vị trí mà server chưa xác nhận.
- **Chuẩn industry** — dùng được trên mạng 4G, wifi yếu.

**Nhược điểm:**

- Latency hiển thị +150ms (player thấy người khác trễ hơn 150ms so với thực tế)
- Code phức tạp hơn
- Tốn RAM (buffer 3-5 snapshot mỗi player)

### 3.3. Bảng so sánh nhanh

| Tiêu chí | Simple Lerp | Snapshot Buffer |
|---|---|---|
| Mượt mà khi jitter | Kém | Rất tốt |
| Chống mất packet | Không | Có (1-2 packet) |
| An toàn với physics | OK | Rất tốt |
| Latency hiển thị | Thấp (~50ms) | Cao hơn (~150-200ms) |
| Code phức tạp | Đơn giản | Trung bình |
| RAM | Thấp | Trung bình |
| Phù hợp với | Prototype, LAN | Production, Internet |

---

## 4. Tại sao chọn cách mới

Quyết định dựa trên 4 lý do chính:

**1. Đặc thù game cho phép.** Dragon Boy là game di chuyển 2D ngang đơn giản, không phải FPS competitive cần aim chính xác. 150ms latency hiển thị **không ảnh hưởng gameplay** — combat/skill (biến khỉ, huýt sao, bom) đều có animation dài hơn 150ms nên người chơi không cảm nhận được.

**2. Mạng người chơi đa dạng.** Người chơi Việt Nam dùng nhiều loại mạng (FPT, Viettel, 4G mobile) với chất lượng khác nhau. Simple Lerp chỉ tốt với mạng ổn định; Snapshot Buffer tốt với mọi loại mạng.

**3. Trade-off chấp nhận được.** "Mượt mà" > "150ms latency" trong loại game này. Người chơi sẽ không nhận ra 150ms delay (mắt thường khó phân biệt dưới 200ms) nhưng sẽ nhận ra ngay khi player giật.

**4. Đầu tư 1 lần, dùng lâu dài.** Code phức tạp hơn nhưng chỉ cần viết 1 lần, sau này không phải sửa khi mạng người chơi tệ. Simple Lerp thì cứ phải tinker tham số `lerpSpeed` mãi.

---

## 5. Flow chi tiết của cách mới

### 5.1. Sơ đồ tổng quan

```
┌─────────────────┐                      ┌──────────────────┐
│  Server tick    │  packet (50ms/lần)   │  Client          │
│  ─────────────  │ ──────────────────►  │  ────────────    │
│  - Tổng hợp     │                      │  WebSocket       │
│    vị trí       │                      │  handler         │
└─────────────────┘                      └────────┬─────────┘
                                                  │
                                                  ▼
                                  ┌──────────────────────────────┐
                                  │  applyServerSync()           │
                                  │  ─ Flush buffer nếu gap>500ms│
                                  │  ─ Push anchor snapshot      │
                                  │  ─ Push PlayerSnapshot mới   │
                                  │  ─ Cleanup snapshot quá cũ   │
                                  └──────────────┬───────────────┘
                                                 │
                                                 ▼
                                       ┌────────────────────┐
                                       │  Buffer (Deque)    │
                                       │  [s1, s2, s3, s4]  │
                                       └─────────┬──────────┘
                                                 │
                                                 ▼
                              ┌───────────────────────────────────┐
                              │  Mỗi frame trong capNhat():       │
                              │  interpolateFromBuffer(delta)     │
                              │  ─ renderTime = now - 150ms       │
                              │  ─ Tìm prev/next bao quanh        │
                              │    renderTime                     │
                              │  ─ Lerp x, y giữa 2 snapshot      │
                              │  ─ Apply discrete fields          │
                              └─────────────────┬─────────────────┘
                                                │
                                                ▼
                                       ┌──────────────────┐
                                       │  ve() — render   │
                                       │  player lên màn  │
                                       └──────────────────┘
```

### 5.2. Bước 1 — Nhận packet và push vào buffer

```java
public void applyServerSync(float x, float y, ..., String avatar) {
    long now = System.currentTimeMillis();

    // Nếu gap > 500ms → flush và tạo anchor (xem Section 10)
    if (latestSnap != null && (now - latestSnap.time) > 500) {
        snapshots.clear();
        PlayerSnapshot anchor = new PlayerSnapshot();
        anchor.time = now - RENDER_DELAY_MS;
        anchor.x = this.x;
        anchor.y = this.y;
        // ... copy discrete fields từ latestSnap
        snapshots.addLast(anchor);
    }

    PlayerSnapshot snap = new PlayerSnapshot();
    snap.time = now;
    snap.x = x;
    snap.y = y;
    // ... copy hết field vào snap
    
    snapshots.addLast(snap);
    latestSnap = snap;
    
    // Cleanup snapshot cũ (> 1 giây), giữ ít nhất 1
    long cutoff = snap.time - BUFFER_MAX_AGE_MS;
    while (snapshots.size() > 1 && snapshots.peekFirst().time < cutoff) {
        snapshots.pollFirst();
    }
    
    // Init lần đầu (tránh draw vị trí (0,0))
    if (this.dau == null || this.dau.isEmpty()) {
        this.x = x;
        this.y = y;
        // ...
    }
}
```

**Quan trọng:** Hàm này **KHÔNG ghi trực tiếp** vào field hiển thị (`this.x`, `this.y`). Chỉ push vào buffer. `interpolateFromBuffer()` sẽ ghi sau.

### 5.3. Bước 2 — Mỗi frame, tìm 2 snapshot bao quanh renderTime

```java
long renderTime = System.currentTimeMillis() - RENDER_DELAY_MS;  // now - 150ms

PlayerSnapshot prev = null, next = null;
PlayerSnapshot last = null;

for (Iterator<PlayerSnapshot> it = snapshots.iterator(); it.hasNext(); ) {
    PlayerSnapshot s = it.next();
    if (last != null && last.time <= renderTime && s.time >= renderTime) {
        prev = last;
        next = s;
        break;
    }
    last = s;
}
```

Buffer thường chỉ 3-5 phần tử nên loop O(n) không vấn đề.

### 5.4. Bước 3 — Lerp giữa prev và next (HAPPY PATH)

```java
if (prev != null && next != null) {
    long span = next.time - prev.time;
    float t = span > 0 ? (renderTime - prev.time) / (float) span : 1f;
    if (t < 0f) t = 0f;
    if (t > 1f) t = 1f;
    
    x = prev.x + (next.x - prev.x) * t;
    y = prev.y + (next.y - prev.y) * t;
    
    applyDiscreteFromSnapshot(next);  // string, int, bool — không lerp
}
```

`t` là tỉ lệ thời gian từ `prev` tới `renderTime` so với khoảng `prev → next`. Ví dụ:

- `prev.time = 100ms, next.time = 150ms, renderTime = 130ms`
- `span = 50ms, t = (130 - 100) / 50 = 0.6`
- `x = prev.x + (next.x - prev.x) * 0.6` → vị trí 60% đường từ prev sang next

### 5.5. Bước 4 — Fallback khi không có prev/next bao quanh

Có 2 sub-case:

#### Case A — Khoảng cách quá xa (teleport / lag spike)

```java
if (dist > 60f) {
    // Lưu vị trí cũ TRƯỚC khi snap
    x_truoc_dash = x;
    y_truoc_dash = y;
    flip_truoc_dash = (dir == -1);
    
    x = latestSnap.x;
    y = latestSnap.y;
    
    timeTeleport = TIME_TELE_PORT_MAX;  // trigger hiệu ứng fade
}
```

#### Case B — Khoảng cách gần (mất 1-2 packet) → hold last position

```java
else if (dist < 0.5f) {
    // Đúng vị trí rồi, không cần làm gì
    x = latestSnap.x;
    y = latestSnap.y;
} else {
    // Lerp nhẹ về snapshot mới nhất, KHÔNG extrapolate
    // Nếu dist lớn (gần ngưỡng tele) → kích thêm hiệu ứng
    if (dist > 40f && timeTeleport <= 0) {
        x_truoc_dash = x;
        y_truoc_dash = y;
        flip_truoc_dash = (dir == -1);
        timeTeleport = TIME_TELE_PORT_MAX;
    }
    float lerpSpeed = 10f;
    x += dx * lerpSpeed * delta;
    y += dy * lerpSpeed * delta;
}
```

> **Note:** Phiên bản trước có Case extrapolation (đoán vị trí dựa vào velocity). Đã **gỡ bỏ** vì gây bug player rơi xuyên đất / đâm xuyên tường khi mất packet. Chi tiết tại [Section 8](#8-vì-sao-không-dùng-extrapolation).

### 5.6. Bước 5 — Hiệu ứng teleport (fade in/out)

Trong `ve()`:

```java
if (timeTeleport > 0) {
    timeTeleport -= Gdx.graphics.getDeltaTime();
    
    float alpha;
    if (timeTeleport > TIME_TELE_PORT_MAX/2) {
        // Nửa đầu: fade in (alpha 0 → 1)
        alpha = (TIME_TELE_PORT_MAX - timeTeleport) / (TIME_TELE_PORT_MAX/2);
    } else {
        // Nửa sau: fade out (alpha 1 → 0)
        alpha = timeTeleport / (TIME_TELE_PORT_MAX/2);
    }
    
    batch.setColor(1f, 1f, 1f, alpha);
    
    // Vẽ "ghost" của player ở vị trí cũ với alpha fade
    batch.draw(chan_tele, ...);
    batch.draw(than_tele, ...);
    batch.draw(dau_tele, ...);
    
    batch.setColor(1f, 1f, 1f, 1f);  // reset
}
```

Hiệu ứng kéo dài 0.2s — 0.1s fade in, 0.1s fade out.

---

## 6. Deque là gì và tại sao dùng nó

### 6.1. Deque là gì

`Deque` (Double-Ended Queue, đọc là "deck") là cấu trúc dữ liệu cho phép thêm/xóa từ **cả hai đầu** với độ phức tạp **O(1)**.

```
┌─────────────────────────────────────────┐
│  Deque                                   │
│  ┌────┬────┬────┬────┬────┐             │
│  │ s1 │ s2 │ s3 │ s4 │ s5 │             │
│  └────┴────┴────┴────┴────┘             │
│   ▲                       ▲              │
│  first                  last             │
│                                          │
│  - addFirst() / addLast()                │
│  - pollFirst() / pollLast()              │
│  - peekFirst() / peekLast()              │
└─────────────────────────────────────────┘
```

So với:
- **Queue** (hàng đợi): chỉ FIFO (vào đầu này, ra đầu kia)
- **Stack** (ngăn xếp): chỉ LIFO (vào ra cùng đầu)
- **List**: thêm/xóa giữa O(n)

### 6.2. Tại sao dùng Deque cho buffer snapshot

Buffer snapshot có 2 thao tác chính:

1. **Thêm snapshot mới vào cuối** khi nhận packet → cần `addLast()` O(1)
2. **Xóa snapshot cũ ở đầu** khi cleanup → cần `pollFirst()` O(1)

Ngoài ra:

3. **Lấy snapshot mới nhất** → cần `peekLast()` O(1)
4. **Lấy snapshot cũ nhất để check time** (cleanup) → cần `peekFirst()` O(1)
5. **Iterate forward** (tìm prev/next bao quanh renderTime) → cần `iterator()`

`Deque` thỏa mãn tất cả. `LinkedList` cũng được nhưng `ArrayDeque` (implementation của Deque) **nhanh hơn** vì dùng circular array thay vì linked nodes.

### 6.3. Các syntax Deque thường dùng

```java
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

Deque<PlayerSnapshot> snapshots = new ArrayDeque<>();

// === THÊM ===
snapshots.addLast(snap);    // thêm vào cuối (như push của queue)
snapshots.addFirst(snap);   // thêm vào đầu

// === LẤY (không xóa) ===
PlayerSnapshot first = snapshots.peekFirst();  // lấy đầu, null nếu rỗng
PlayerSnapshot last = snapshots.peekLast();    // lấy cuối, null nếu rỗng

// === XÓA ===
PlayerSnapshot popped = snapshots.pollFirst();  // xóa đầu và trả về
PlayerSnapshot popped2 = snapshots.pollLast();  // xóa cuối và trả về

// === KIỂM TRA ===
boolean empty = snapshots.isEmpty();
int size = snapshots.size();

// === ITERATE ===
for (Iterator<PlayerSnapshot> it = snapshots.iterator(); it.hasNext(); ) {
    PlayerSnapshot s = it.next();
    if (someCondition) break;
}

// === CLEANUP ===
while (snapshots.size() > 1 && snapshots.peekFirst().time < cutoff) {
    snapshots.pollFirst();
}
```

### 6.4. Lưu ý khi dùng Deque

- **`peekXxx()` trả null nếu rỗng** — phải check `isEmpty()` trước
- **`getXxx()` throw exception nếu rỗng** — dùng cẩn thận
- **`ArrayDeque` không thread-safe** — nếu nhiều thread cùng truy cập phải dùng `ConcurrentLinkedDeque` hoặc đồng bộ thủ công

---

## 7. Vấn đề render delay 150ms

### 7.1. Tại sao cần delay?

Nếu render ngay lập tức theo packet mới nhất, sẽ không có dữ liệu "tương lai" để lerp tới. Khi packet sau đến, vị trí nhảy đột ngột → giật.

Bằng cách render trễ 150ms (3 server tick), client luôn có ít nhất 2 snapshot bao quanh thời điểm render → lerp giữa chúng → mượt.

### 7.2. Ví dụ cụ thể: Không có delay

Server gửi packet mỗi 50ms với vị trí player chạy đều:

```
Time:  0ms  50ms  100ms  150ms  200ms
Pos:   100  150   200    250    300
```

**Nếu render ngay (không delay):**

```
Client time:  10ms   60ms   115ms   160ms   220ms
Packet đến:   S(100) S(150) S(200)  S(250)  S(300)

Render mỗi frame (60fps = 16.67ms):
  10ms: x = 100 (chỉ có S0)
  26ms: x = 100 (vẫn chưa có gì mới)
  43ms: x = 100
  60ms: x = 150 ← NHẢY 50px đột ngột!
  76ms: x = 150
  ...
```

**Player giật cục.**

### 7.3. Ví dụ cụ thể: Có delay 150ms

```
Server time:  0ms   50ms   100ms  150ms  200ms  250ms
Server pos:   100   150    200    250    300    350

Client nhận:  10ms  60ms   115ms  160ms  220ms  260ms
              S0    S1     S2     S3     S4     S5

Render time (now - 150ms):
  At now=160ms → renderTime = 10ms → lerp giữa S0 và S1
  At now=170ms → renderTime = 20ms → lerp giữa S0 và S1 (t=0.2)
  ...
  At now=210ms → renderTime = 60ms → lerp giữa S1 và S2 (t≈0)
  At now=260ms → renderTime = 110ms → lerp giữa S2 và S3 (t≈0.2)
```

Mọi frame đều có 2 snapshot bao quanh → lerp mượt.

### 7.4. Ví dụ cụ thể: Khi mất packet

Mất S2 (packet 100ms):

```
Server time:  0ms   50ms   100ms  150ms  200ms
Server pos:   100   150    200    250    300

Client nhận:  10ms  60ms   ❌    160ms  220ms
              S0    S1            S3     S4
```

**Không có delay (Simple Lerp):**

```
Time 60-160ms: client kẹt ở S1.x = 150 (đứng yên 100ms)
Time 160ms: nhận S3 → nhảy từ 150 → 250 (nhảy 100px!)
```

**Có delay 150ms (Snapshot Buffer):**

```
At now=210ms → renderTime = 60ms
  → S1(60ms) và S3(160ms) → t = (60-60)/100 = 0 → x = 150

At now=260ms → renderTime = 110ms
  → S1(60ms) và S3(160ms) → t = (110-60)/100 = 0.5 → x = 200 (đúng!)

At now=310ms → renderTime = 160ms
  → S3 và S4 → t = 0 → x = 250
```

**Player vẫn chạy mượt — không ai biết đã mất packet S2!**

### 7.5. Tại sao chọn 150ms?

- **Server tick 50ms × 3 = 150ms** → đủ buffer cho 2-3 packet liên tiếp bị mất
- **< 200ms** → mắt thường khó nhận ra delay
- **> 100ms** → đủ chống jitter typical của Internet (50-100ms variance)

---

## 8. Vì sao KHÔNG dùng extrapolation

### 8.1. Extrapolation là gì

Khi mất packet, thay vì giữ nguyên vị trí, client **đoán** player đang đi đâu dựa vào velocity của 2 snapshot cuối:

```java
float vx = (lastSnap.x - prevLastSnap.x) / dt;
float vy = (lastSnap.y - prevLastSnap.y) / dt;
float targetX = lastSnap.x + vx * extrapMs;
float targetY = lastSnap.y + vy * extrapMs;
```

### 8.2. Vì sao gây bug nghiêm trọng

**Bug 1: Player rơi xuyên đất**

```
Server: player đang rơi, sắp chạm đất tại y=100
        gửi packet: y=120, y=110, y=100 (chạm đất, dừng)

Client: nhận y=120, y=110 thì mất packet
        velocity vy = -10 → extrapolate xuống tiếp
        render y=90, y=80, y=70 (XUYÊN ĐẤT!)
        
        Khi packet y=100 đến → player snap NGƯỢC từ y=70 lên y=100
```

**Bug 2: Player đâm xuyên tường**

Server biết tường nên dừng player, nhưng client extrapolate tiếp → player lọt qua tường rồi nảy ngược.

### 8.3. Lý do gốc

**Client không biết về collision của map.** Server có map data, client chỉ render. Extrapolation = client tự suy diễn → suy diễn sai khi có tường/sàn.

### 8.4. Khi nào extrapolation phù hợp

| Loại game | Có collision? | Dùng extrapolation? |
|---|---|---|
| FPS top-down | Tường đơn giản | OK với cap ngắn |
| Racing | Đường định sẵn | OK |
| Space shooter | Không có | OK |
| Platformer (Mario-like) | Có sàn, tường | **KHÔNG** |
| MMO 2D (Dragon Boy) | Có sàn, tường | **KHÔNG** |

### 8.5. Bài học

> **Đừng để client đoán những gì chỉ server biết.**

Hold last position đơn giản, an toàn, đủ tốt — không bị bug "rơi xuyên đất" hay "đâm xuyên tường".

---

## 9. Xử lý các trường hợp đặc biệt

### 9.1. Player vừa join map

Buffer chưa có snapshot → render mặc định ở (0,0)?

**Giải pháp:** Init field hiển thị lần đầu trong `applyServerSync`:

```java
if (this.dau == null || this.dau.isEmpty()) {
    this.x = x;
    this.y = y;
    // ...
}
```

### 9.2. Player teleport thật (skill, đổi map)

Khoảng cách giữa snapshot mới và vị trí hiện tại > 60px → snap về vị trí mới + hiệu ứng fade.

### 9.3. Race condition: render trước khi nhận packet đầu tiên

Buffer rỗng → `interpolateFromBuffer()` return ngay, giữ nguyên vị trí cũ.

### 9.4. Memory leak nếu player AFK mãi

Buffer cleanup tự động giữ tối đa ~20 snapshot (1 giây × 20 packet/giây), luôn giữ ít nhất 1.

---

## 10. Bài toán "Idle then Move" và cách giải

### 10.1. Bài toán là gì

Đây là bài toán **thực sự gây giật** trong Dragon Boy, được phát hiện qua quá trình debug thực tế.

**Scenario:**

```
A đứng yên 3 giây
→ Server không gửi packet (đúng behavior — không có gì thay đổi)
→ Buffer của B tiếp tục nhận cleanup theo BUFFER_MAX_AGE_MS
→ A bắt đầu di chuyển / nhảy
→ Packet đầu tiên đến B
→ B bị giật
```

### 10.2. Tại sao lại gặp bài toán này

**Server hoàn toàn đúng** — không broadcast khi không có gì thay đổi là correct behavior, tiết kiệm bandwidth.

Vấn đề nằm ở client:

```java
// Version cũ — cleanup xóa hết kể cả snapshot cuối
while (!snapshots.isEmpty() && snapshots.peekFirst().time < cutoff) {
    snapshots.pollFirst();
}
```

Sau 1 giây idle → toàn bộ snapshot bị xóa → buffer rỗng. Khi A di chuyển, packet đầu tiên đến:

```
Buffer: [snap_mới @ t=now]   ← chỉ có 1 snapshot
renderTime = now - 150ms
snap_mới.time = now > renderTime
→ rơi vào fallback: x = latestSnap.x  ← snap về vị trí mới ngay lập tức
→ GIẬT
```

**Fix đầu tiên (chưa đủ):** giữ lại 1 snapshot cuối cùng.

```java
while (snapshots.size() > 1 && snapshots.peekFirst().time < cutoff) {
    snapshots.pollFirst();
}
```

Nhưng vẫn giật vì lý do khác:

```
Buffer sau 3s idle: [snap_cũ @ t=0ms]   ← 1 snapshot, đúng vị trí
A di chuyển → packet đầu đến @ t=3000ms
Buffer: [snap_cũ @ t=0ms, snap_mới @ t=3000ms]

renderTime = now - 150ms = 2850ms
prev = snap_cũ (t=0ms), next = snap_mới (t=3000ms)
span = 3000ms
t = (2850 - 0) / 3000 = 0.95  ← nhảy 95% khoảng cách ngay frame đầu!
```

→ Vẫn giật dù vào happy path.

### 10.3. Root cause thật sự

Khi `span` giữa 2 snapshot quá lớn (vì gap dài), `t` bắt đầu gần 1.0 thay vì 0 → **nhảy gần như toàn bộ khoảng cách ngay frame đầu tiên**, không lerp gì cả.

### 10.4. Fix cuối cùng: flush + anchor

Khi nhận packet mới sau gap > 500ms, tạo một **anchor snapshot** tại vị trí render hiện tại với timestamp `now - RENDER_DELAY_MS`:

```java
if (latestSnap != null && (now - latestSnap.time) > 500) {
    snapshots.clear();

    PlayerSnapshot anchor = new PlayerSnapshot();
    anchor.time = now - RENDER_DELAY_MS;  // = now - 150ms
    anchor.x = this.x;  // vị trí render HIỆN TẠI
    anchor.y = this.y;
    // ... copy discrete fields từ latestSnap
    snapshots.addLast(anchor);
}
// ... push snap mới như bình thường (snap.time = now)
```

**Tại sao anchor.time = now - RENDER_DELAY_MS?**

```
anchor.time = now - 150ms
snap.time   = now
renderTime  = now - 150ms

→ anchor.time == renderTime
→ happy path tìm được: prev=anchor, next=snap
→ t = (renderTime - anchor.time) / (snap.time - anchor.time)
     = (0) / (150ms)
     = 0

→ x = anchor.x + (snap.x - anchor.x) * 0  =  anchor.x  (vị trí hiện tại)
```

Frame tiếp theo `t` tăng dần từ 0 → 1 trong 150ms → lerp mượt từ vị trí hiện tại sang vị trí mới.

### 10.5. Tại sao chọn 500ms làm ngưỡng flush?

- **Server tick 50ms** → gap bình thường là 50ms
- **Jitter mạng** có thể làm gap lên 100-200ms mà không phải "idle"
- **500ms = 10 tick missed** → chắc chắn là idle thật, không phải jitter tạm thời
- Nếu dùng 300ms → có thể flush nhầm khi mạng tệ thoáng qua
- Nếu dùng 1000ms → idle quá lâu mới flush, vẫn giật

### 10.6. Tóm tắt chuỗi fix

| Version | Vấn đề | Fix |
|---|---|---|
| v1 | Snapshot bị xóa hết → buffer rỗng khi idle | Giữ ít nhất 1 snapshot (`size > 1`) |
| v2 | `span` khổng lồ → `t` gần 1.0 → nhảy vọt | Flush + anchor khi gap > 500ms |
| v3 (hiện tại) | **Ổn định, mượt** | — |

---

## 11. Tips và best practices cho dev

### 11.1. Về architecture

**Tách biệt receiving và rendering.** `applyServerSync()` chỉ push vào buffer, không động vào field hiển thị. `interpolateFromBuffer()` mới ghi field.

**Dùng snapshot object thay vì truyền raw fields.** Tạo class `PlayerSnapshot` chứa hết state tại một thời điểm. Dễ thêm field, dễ debug.

**Server là nguồn duy nhất của sự thật.** Đừng để client đoán vị trí (extrapolate) trong game có collision.

### 11.2. Về performance

**Dùng `ArrayDeque` thay vì `LinkedList`.** Nhanh hơn 2-3x cho thao tác push/pop ở 2 đầu.

**Đừng tạo object trong hot loop.** Trong `interpolateFromBuffer()` chạy 60 lần/giây × N players, đừng `new PlayerSnapshot()` — chỉ tạo trong `applyServerSync()`.

### 11.3. Về testing

**Test với latency giả lập.** Dùng tool như `clumsy` (Windows) hoặc `tc` (Linux) để giả lập 200ms latency, 5% packet loss.

**Test case "idle then move" đặc biệt.** Đứng yên 5 giây, rồi di chuyển — đây là case hay bị bỏ qua khi test.

**Log buffer state khi debug:** `snapshots.size()`, `latestSnap.time - now`, `dist` — nhìn vào log sẽ biết case nào đang xảy ra.

### 11.4. Về phân biệt continuous vs discrete fields

- **Continuous (lerp được):** x, y, hp, mana
- **Discrete (không lerp):** trangthai, dir, dau, than, chan, tenVanBay

Nhầm 2 loại → bug khó debug. Ví dụ lerp `dir` (1 hoặc -1) sẽ ra 0.5 — vô nghĩa.

---

## 12. Roadmap mở rộng

### 12.1. Server gửi `serverTime` + clock sync

Hiện tại dùng `System.currentTimeMillis()` của client. Cải tiến: server gửi kèm `serverTime`, client dùng server time cho buffer. Cần clock sync lúc connect.

### 12.2. Adaptive render delay

Đo jitter realtime, tự điều chỉnh delay:
- Mạng tốt: 100ms
- Mạng trung bình: 150ms
- Mạng tệ: 200ms

### 12.3. Server gửi flag `isTeleport`

Phân biệt teleport thật vs lag spike → hiện hiệu ứng đúng case.

### 12.4. Lag compensation cho combat

Khi player A bắn skill vào B, server "rewind" vị trí của B về thời điểm A nhìn thấy (~150ms trước) để xử lý hit chính xác. Cần thiết khi có PvP yêu cầu hit chính xác.

### 12.5. Delta compression

Chỉ gửi field nào thay đổi thay vì full state mỗi packet. Giảm bandwidth 50-80%. Cần thiết khi map > 50 player.

---

## Kết luận

Snapshot Interpolation Buffer là giải pháp **chuẩn industry** cho game multiplayer real-time. Bài học quan trọng rút ra trong quá trình implement Dragon Boy:

1. **Server không broadcast khi idle là đúng** — đừng fix server, fix client.
2. **Không dùng extrapolation trong game có physics** — hold last position an toàn hơn nhiều.
3. **Bài toán "Idle then Move"** là edge case hay bị bỏ qua — cần flush buffer + anchor khi gap > 500ms để tránh `span` khổng lồ làm `t` nhảy vọt.
4. **`anchor.time = now - RENDER_DELAY_MS`** không phải magic number — đây là công thức đảm bảo `t = 0` ở frame đầu tiên sau khi idle, lerp mượt từ vị trí hiện tại sang vị trí mới.

Implement đúng = đầu tư 1 lần, dùng được nhiều năm. Người chơi sẽ không bao giờ biết đến code này, và đó là dấu hiệu tốt — họ chỉ thấy game **mượt**.

> *"The best networking code is the one players never notice."*

---

**Phiên bản:** 1.2
**Ngày cập nhật:** 2026-04-30
**Thay đổi v1.2:** Thêm Section 10 "Bài toán Idle then Move" — giải thích root cause, chuỗi fix, và lý do chọn các con số (500ms, `now - RENDER_DELAY_MS`). Cập nhật flow diagram và code snippets theo implementation hiện tại.
**Thay đổi v1.1:** Bỏ extrapolation (gây bug xuyên đất/tường), thay bằng hold last position.
**Áp dụng cho:** Dragon Boy multiplayer client (libGDX + Java)
