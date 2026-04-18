# NPC Async Loading — Tài liệu kỹ thuật

## Mục lục

1. [Tổng quan kiến trúc](#1-tổng-quan-kiến-trúc)
2. [Các class và hàm](#2-các-class-và-hàm)
    - [NpcServerData](#21-npcserverdata)
    - [MapDataCache](#22-mapdatacache)
    - [ApiService.layNpcCuaMap](#23-apiservicelaynpccuamap)
    - [MapCoBan.khoiTao](#24-mapcobankhoitao)
    - [MapCoBan.apDungDuLieuServer](#25-mapcobanapduligdulieuserver)
3. [Giải thích Runnable và Consumer](#3-giải-thích-runnable-và-consumer)
4. [Tại sao gọi khoiTao() trong constructor thay vì show()](#4-tại-sao-gọi-khoitao-trong-constructor-thay-vì-show)
5. [Flow hoàn chỉnh](#5-flow-hoàn-chỉnh)
6. [Lưu ý và best practices](#6-lưu-ý-và-best-practices)
7. [So sánh: Data ở Client vs Data ở Server](#7-so-sánh-data-ở-client-vs-data-ở-server)

---

## 1. Tổng quan kiến trúc

Mục tiêu: load NPC từ server **một lần duy nhất**, cache lại, không block GL thread, không gây pop-in khi vào map.

```
Server API
    │
    ▼
ApiService.layNpcCuaMap()   ← gọi HTTP trong thread riêng
    │
    ▼
MapDataCache                ← lưu kết quả, lần sau khỏi gọi API
    │
    ▼
MapCoBan.apDungDuLieuServer() ← themNpc() cho từng NPC
    │
    ▼
Gdx.app.postRunnable()      ← trả về GL thread an toàn
    │
    ▼
callback (onHoanThanh)      ← ManHinhXxx nhận data, setup NPC xong
```

---

## 2. Các class và hàm

### 2.1 NpcServerData

```java
public class NpcServerData {
    public int id;
    public String ten_npc;
    public String loai_npc;
    public float x;
    public float y;
    public boolean is_active;
}
```

**Tác dụng:** DTO (Data Transfer Object) — ánh xạ 1-1 với JSON trả về từ API. Chỉ chứa data, không có logic.

**Lý do dùng DTO riêng thay vì dùng thẳng class `Npc`:** `Npc` là game object có texture, animation, state — không nên trộn lẫn với raw server data. Tách ra giúp dễ thay đổi API response mà không ảnh hưởng game logic.

---

### 2.2 MapDataCache

```java
public class MapDataCache {
    private static final MapDataCache INSTANCE = new MapDataCache();
    private final Map<Integer, List<NpcServerData>> cache = new HashMap<>();

    public static MapDataCache getInstance() { return INSTANCE; }
    public boolean daCo(int mapId) { return cache.containsKey(mapId); }
    public List<NpcServerData> lay(int mapId) { return cache.get(mapId); }
    public void luu(int mapId, List<NpcServerData> data) { cache.put(mapId, data); }
}
```

**Tác dụng:** Singleton cache — lưu NPC data theo `mapId`. Lần đầu vào map gọi API, lần sau lấy thẳng từ bộ nhớ.

**Tại sao Singleton:** Cache phải tồn tại xuyên suốt session, dùng chung cho mọi màn hình. Static field đảm bảo chỉ có 1 instance duy nhất.

**Trade-off cần biết:** Cache này không tự invalidate. Nếu admin cập nhật NPC trên server trong lúc người chơi đang chơi, người chơi sẽ không thấy thay đổi cho đến khi restart game. Với game kiểu này là chấp nhận được.

---

### 2.3 ApiService.layNpcCuaMap

```java
public static void layNpcCuaMap(int mapId, Consumer<List<NpcServerData>> onHoanThanh)
```

**Tham số:**
- `mapId` — ID của map cần lấy NPC
- `onHoanThanh` — callback sẽ được gọi khi API trả về xong, nhận vào `List<NpcServerData>`

**Tác dụng:** Gọi HTTP GET đến `api.ngocrongdark.com/game-data/map/npcs?map_id={mapId}` trong một thread riêng. Khi xong, wrap callback vào `Gdx.app.postRunnable()` để đảm bảo chạy trên GL thread.

**Tại sao cần thread riêng:** HTTP call là blocking I/O — nếu gọi trên GL thread sẽ đóng băng toàn bộ game trong lúc chờ server. LibGDX render loop chạy trên GL thread, không được block.

**Tại sao cần `Gdx.app.postRunnable()`:** LibGDX không thread-safe. Mọi thao tác với game object (Texture, Sprite, danh sách NPC...) phải chạy trên GL thread. `postRunnable` đưa callback vào queue, GL thread sẽ chạy nó ở đầu frame tiếp theo.

---

### 2.4 MapCoBan.khoiTao

```java
public void khoiTao(Runnable onHoanThanh)
```

**Tham số:**
- `onHoanThanh` — đoạn code sẽ chạy khi map đã load xong hoàn toàn (hitbox + NPC)

**Tác dụng:** Điều phối toàn bộ quá trình khởi tạo map theo thứ tự:

1. Gọi `taiDuLieuMap()` — load hitbox local (sync, xong ngay)
2. Kiểm tra `mapId == 0` → không có server data, chạy callback luôn
3. Kiểm tra cache → có rồi thì dùng cache, chạy callback
4. Không có cache → gọi API → nhận data → lưu cache → chạy callback

**Lý do tồn tại:** Tách biệt "khi nào map sẵn sàng" ra khỏi "map sẵn sàng như thế nào". Caller chỉ cần `map.khoiTao(() -> { ... })` mà không cần biết bên trong có cache hay API hay local.

---

### 2.5 MapCoBan.apDungDuLieuServer

```java
private void apDungDuLieuServer(List<NpcServerData> danhSach)
```

**Tác dụng:** Duyệt qua danh sách NPC từ server, lọc `is_active == false`, convert `loai_npc` string thành enum `LoaiNPC`, rồi gọi `themNpc()` cho từng cái.

**Tại sao private:** Đây là implementation detail của `MapCoBan`. Subclass không cần và không nên gọi trực tiếp.

---

## 3. Giải thích Runnable và Consumer

### Runnable

```java
Runnable onHoanThanh = () -> {
    gameScreen.setMap(map);
};

onHoanThanh.run(); // gọi nó
```

- Interface có sẵn trong Java
- Đại diện cho **một đoạn code không nhận tham số, không return gì**
- Dùng khi chỉ cần biết "xong chưa", không cần data

```
() -> { ... }
↑
không có tham số
```

### Consumer\<T\>

```java
Consumer<List<NpcServerData>> onHoanThanh = danhSach -> {
    apDungDuLieuServer(danhSach);
};

onHoanThanh.accept(danhSach); // gọi nó, truyền data vào
```

- Interface có sẵn trong Java (`java.util.function`)
- Đại diện cho **một hàm nhận vào 1 tham số kiểu T, không return gì**
- Dùng khi cần truyền data từ nơi gọi về caller

```
danhSach -> { ... }
↑
tham số được truyền vào khi .accept() được gọi
```

### So sánh

| | Runnable | Consumer\<T\> |
|---|---|---|
| Tham số | Không | Có (kiểu T) |
| Return | Không | Không |
| Cách gọi | `.run()` | `.accept(value)` |
| Dùng khi | Chỉ cần biết "xong" | Cần nhận data về |

### Tại sao không dùng return thay vì callback

```java
// ❌ Không làm được — hàm return trước khi thread chạy xong
public static List<NpcServerData> layNpcCuaMap(int mapId) {
    new Thread(() -> {
        // chạy sau... nhưng hàm đã return null rồi
    }).start();
    return null; // luôn null
}

// ✅ Callback — khi thread xong mới gọi, lúc đó có data thật
public static void layNpcCuaMap(int mapId, Consumer<List<NpcServerData>> onHoanThanh) {
    new Thread(() -> {
        List<NpcServerData> data = goiApi();
        onHoanThanh.accept(data); // lúc này mới có data
    }).start();
}
```

---

## 4. Tại sao gọi khoiTao() trong constructor thay vì show()

### Vấn đề với show()

LibGDX lifecycle của một Screen:

```
new ManHinhXxx()     ← constructor
new ManHinhSplash()  ← splash bắt đầu hiện
    splash chạy ~1-2 giây
game.setScreen()     ← show() được gọi ← render() bắt đầu
```

Nếu gọi `khoiTao()` trong `show()`:

```
Constructor        Splash [==========] 2 giây        show() → khoiTao() → API call
                                                                              ↓ 300ms
                                                                         NPC pop ra ← người chơi thấy
```

Người chơi vào map rồi mới thấy NPC bật ra sau ~300ms.

### Giải pháp: gọi trong constructor

```
Constructor → khoiTao() → API call (chạy nền)
     ↓
Splash [==========] 2 giây    ← API trả về trong lúc này, NPC data đã có
     ↓
show() → danhSachNpc đã sẵn sàng → setup ngay
     ↓
render() frame 1 → NPC hiện ngay, không delay
```

Splash vô tình trở thành "loading screen" miễn phí — không cần thêm logic gì.

### Điều kiện để pattern này hoạt động

`nhanVat` phải được gán **trước** khi gọi `khoiTao()` trong constructor. Nhìn vào code:

```java
// Nhánh if (thongtin != null):
nhanVat = thongtin.nhanVat; // ← gán trước
hud = thongtin.hud;
camManager = thongtin.camManager;
map = new MapLangAru();
map.khoiTao(() -> {          // ← gọi sau, callback dùng nhanVat an toàn
    npc.setNhanVat(nhanVat);
});

// Nhánh else:
nhanVat = haidang;           // ← gán trước
// ...
map.khoiTao(() -> {          // ← gọi sau
    npc.setNhanVat(nhanVat);
});
```

Cả 2 nhánh đều gán `nhanVat` trước → callback an toàn.

---

## 5. Flow hoàn chỉnh

### Lần đầu vào map (không có cache)

```
new ManHinhLangAru()
    │
    ├─ nhanVat = thongtin.nhanVat
    ├─ map = new MapLangAru()
    └─ map.khoiTao(() -> { setup NPC })
            │
            ├─ taiDuLieuMap() → hitbox xong
            ├─ mapId != 0, cache chưa có
            └─ ApiService.layNpcCuaMap(mapId, data -> {
                    cache.luu(mapId, data)
                    apDungDuLieuServer(data)
                    Gdx.app.postRunnable(() -> {
                        // GL thread
                        danhSachNpc = map.LayDanhSachNpc()
                        npc.setNhanVat(nhanVat)
                        onHoanThanh.run()  ← callback của ManHinhLangAru
                    })
               })

[Splash hiện ~2 giây, API trả về trong lúc này]

show() → danhSachNpc đã có → không cần làm gì thêm
render() → NPC hiện ngay từ frame đầu
```

### Lần 2 trở đi (có cache)

```
new ManHinhLangAru()
    └─ map.khoiTao(() -> { ... })
            ├─ taiDuLieuMap()
            ├─ cache.daCo(mapId) == true
            ├─ apDungDuLieuServer(cache.lay(mapId))  ← không gọi API
            └─ onHoanThanh.run()  ← ngay lập tức
```

---

## 6. Lưu ý và best practices

### Thread safety

`danhSachNpc` được gán trong GL thread (nhờ `postRunnable`) và được đọc trong `render()` cũng trên GL thread → không có race condition. Nếu bao giờ cần gán từ thread khác, phải dùng `volatile` hoặc `AtomicReference`.

### Subclass chỉ cần khai báo mapId

```java
public class MapDoiHoaCuc extends MapCoBan {
    public MapDoiHoaCuc() {
        this.mapId = 3; // khai báo 1 dòng
    }

    @Override
    public void taiDuLieuMap() {
        // chỉ hitbox, NPC do server lo
        danhSachDat.add(new HitboxDat(...));
    }
}
```

Map không có server data đặt `mapId = 0` (default), `khoiTao()` sẽ bỏ qua API.

### NPC local vẫn dùng themNpc() như cũ

NPC đặc biệt (cây đậu, rương đồ, đùi gà...) server chưa quản lý thì vẫn `themNpc()` trong `taiDuLieuMap()` như bình thường — không bị ảnh hưởng gì.

```java
@Override
public void taiDuLieuMap() {
    danhSachDat.add(new HitboxDat(0, 0, 1420, 175));
    // NPC local vẫn dùng bình thường
    themNpc("dau_traidat_1", LoaiNPC.CAYDAU, 600, 192);
    themNpc("ruong_do", LoaiNPC.RUONGDO, 120, 190);
    // NPC loại NGUOI không cần khai báo ở đây nữa — server lo
}
```

### Xử lý lỗi API

Hiện tại nếu API lỗi thì NPC không hiện, map vẫn chơi được (hitbox vẫn có). Đây là graceful degradation — tốt hơn crash. Nếu muốn báo lỗi cho người chơi, thêm `Consumer<Exception> onLoi` vào `layNpcCuaMap`.

### Cache invalidation

Cache hiện tại tồn tại suốt session. Nếu sau này cần reload (ví dụ admin update NPC live), thêm method:

```java
public void xoaCache(int mapId) {
    cache.remove(mapId);
}

public void xoaToanBoCache() {
    cache.clear();
}
```

### Không dùng Gdx.net

Code dùng `HttpURLConnection` trong thread thủ công thay vì `Gdx.net`. Lý do: `Gdx.net` callback không đảm bảo về GL thread trên mọi platform (Android vs Desktop behavior khác nhau). Cách thủ công + `postRunnable` explicit rõ ràng và nhất quán hơn.

---

## 7. So sánh: Data ở Client vs Data ở Server

### 7.1 Tổng quan

| Tiêu chí | Cách cũ (hardcode client) | Cách mới (server + cache) |
|---|---|---|
| Startup time | 0ms | 200–500ms lần đầu, 0ms lần sau |
| Render performance | Như nhau | Như nhau |
| User thấy lag? | Không | Không (splash che) |
| Worst case | Không có | NPC pop ra nếu mạng > 2 giây |
| Deploy NPC mới | Rebuild + release client | Gọi API admin, có ngay |
| Sửa tọa độ NPC | Rebuild + release client | Gọi API admin, có ngay |
| Bật/tắt NPC | Không làm được | `is_active = false` |
| Thêm map mới | Viết code + release | Thêm DB + khai báo mapId |

---

### 7.2 Hiệu năng và Latency

**Lần đầu vào map:**

```
Cách cũ:   Constructor → 0ms   → map ready
Cách mới:  Constructor → API call (200–500ms nền) → map ready

Người chơi cảm nhận: như nhau — splash che hết
```

**Lần 2+ vào map:**

```
Cách cũ:   Constructor → 0ms   → map ready
Cách mới:  Constructor → cache hit (0ms) → map ready

Người chơi cảm nhận: như nhau, thậm chí cách mới nhanh hơn vì
không cần đọc và parse file hardcode nếu map phức tạp
```

**Trong render() mỗi frame:**

```
Cách cũ:   danhSachNpc đã có → vẽ bình thường
Cách mới:  danhSachNpc đã có → vẽ bình thường

Hoàn toàn như nhau — 0 overhead sau khi load xong
```

**Kết luận:** Cách mới không làm chậm game trong runtime. Chi phí duy nhất là HTTP call lần đầu, và nó chạy hoàn toàn trong background.

---

### 7.3 UX — Người chơi thấy gì

**Happy path (mạng bình thường ~200ms):**

```
Cách cũ:  bấm nút → splash → vào map → NPC có sẵn ✅
Cách mới: bấm nút → splash → vào map → NPC có sẵn ✅

Hoàn toàn giống nhau từ góc nhìn người chơi
```

**Worst case (mạng chậm >2 giây):**

```
Cách cũ:  không có worst case — data local luôn có sẵn
Cách mới: bấm nút → splash → vào map → NPC pop ra sau ~1 giây

→ Game không crash, nhân vật vẫn đi được, chỉ NPC hiện muộn
→ Đây là graceful degradation, không phải bug
```

**Tại sao production game chọn lag cuối thay vì lag đầu:**

```
Lag đầu (constructor trên GL thread):
  bấm nút → màn hình đứng im 500ms → user nghĩ game treo → trải nghiệm tệ

Lag cuối (NPC pop ra):
  bấm nút → splash hiện ngay → vào map → NPC hiện sau → user chấp nhận được
```

Feedback visual ngay lập tức quan trọng hơn data đầy đủ ngay lập tức. Đây là nguyên tắc UX cơ bản của mọi game production.

---

### 7.4 Trade-off Admin có thể customize

Đây là lợi ích lớn nhất của cách mới — **không cần release client mới** để thay đổi NPC.

**Những gì admin làm được ngay lập tức qua API:**

```
✅ Thêm NPC mới vào map
   POST /game-data/npc-spawn
   → Người chơi restart game thấy ngay (hoặc xóa cache)

✅ Sửa tọa độ NPC
   PATCH /game-data/npc-spawn
   → Không cần đụng 1 dòng Java nào

✅ Bật/tắt NPC tạm thời
   PATCH /game-data/npc-spawn { is_active: false }
   → Event game, bảo trì, seasonal content

✅ Thêm map mới hoàn toàn
   POST /game-data/map
   → Client chỉ cần khai báo mapId, còn lại server lo

✅ Xóa NPC
   DELETE /game-data/npc-spawn
   → Không để lại artifact trong code
```

**Những gì vẫn cần release client:**

```
❌ Thêm loại NPC hoàn toàn mới (LoaiNPC enum chưa có)
❌ Thêm animation, texture mới cho NPC
❌ Thay đổi hitbox map
❌ Logic tương tác NPC mới
```

Tóm lại: **data thay đổi → server lo, logic thay đổi → client mới**. Đây là đường ranh giới đúng đắn.

---

### 7.5 Tại sao trade-off này đúng đắn

Cách cũ (hardcode) phù hợp khi:
- Team nhỏ, 1 dev làm hết
- Game không update thường xuyên
- Không có admin panel

Cách mới (server) phù hợp khi:
- Có nhiều map, nhiều NPC cần quản lý
- Admin cần điều chỉnh game mà không phụ thuộc dev
- Muốn làm event, seasonal content linh hoạt
- Game đang scale lên

Với ngocrongdark hiện tại — có admin panel, có nhiều map, cần thêm NPC linh hoạt — cách mới là lựa chọn đúng. Chi phí bỏ ra (200–500ms API lần đầu, bị che bởi splash) nhỏ hơn nhiều so với lợi ích (deploy không cần release client).
