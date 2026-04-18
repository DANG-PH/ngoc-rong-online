# NPC Shop Item — Tài liệu kỹ thuật Client

## Mục lục

1. [Tổng quan](#1-tổng-quan)
2. [So sánh: Logic cũ vs Logic mới](#2-so-sánh-logic-cũ-vs-logic-mới)
    - [Flow logic cũ](#21-flow-logic-cũ)
    - [Flow logic mới](#22-flow-logic-mới)
    - [Vấn đề của cách cũ](#23-vấn-đề-của-cách-cũ)
3. [Flow hoàn chỉnh](#3-flow-hoàn-chỉnh)
    - [Mở shop lần đầu](#31-mở-shop-lần-đầu)
    - [Mở shop lần 2+](#32-mở-shop-lần-2)
4. [Bảng các hàm](#4-bảng-các-hàm)
5. [Các file thêm mới](#5-các-file-thêm-mới)
    - [ShopItemServerData](#51-shopitemserverdatajava)
    - [ShopCache](#52-shopcachejava)
6. [Các file đã sửa](#6-các-file-đã-sửa)
    - [ApiService](#61-apiservicejava)
    - [ItemThuongXuLi](#62-itemthuongxulijava)
    - [ItemGia](#63-itemgiajava)
    - [admin_thanhle](#64-admin_thanhlejs)
7. [Lưu ý và best practices](#7-lưu-ý-và-best-practices)

---

## 1. Tổng quan

Mục tiêu: danh sách item trong shop NPC được load từ server thay vì hardcode client. Admin thêm/sửa/xóa/bật tắt item mà không cần release client mới.

```
Server API GET /game-data/npc-shop?npc_base_id={id}
    │
    ▼
ApiService.layShopCuaNpc()   ← HTTP GET trong thread riêng
    │
    ▼
ShopCache                    ← lưu theo npcBaseId, lần sau không gọi API
    │
    ▼
apDungDuLieuShop()           ← clear + rebuild danhSachItem theo tab
    │
    ▼
ItemThuongXuLi.taoItemTuTen() ← mapping tenItem (server) → Item object (client)
    │
    ▼
danhSachItemAoQuan / PhuKien / DacBiet  ← sẵn sàng để render
```

---

## 2. So sánh: Logic cũ vs Logic mới

### 2.1 Flow logic cũ

```
admin_thanhle constructor
    │
    ├─ themItemVaoDanhSachAoQuan()
    │       └─ ItemThuongXuLi.taoItemThuong("set_cam", AO, "traidat")  ← hardcode
    │
    ├─ themItemVaoDanhSachPhuKien()
    │       └─ ItemThuongXuLi.taoItemThuong("set_cam", GANG, "traidat") ← hardcode
    │
    └─ themItemVaoDanhSachDacBiet()
            └─ new Item("bongtaic1", "Bông tai Porata", ..., 1500000L, ...)  ← giá hardcode

render() → NPC_CUA_HANG.render_item()
    └─ veGiaItem()
            └─ ItemGia.layGiaItem(item)
                    └─ bangGia.get(item.getTenItem())  ← Map hardcode trong static block
```

Toàn bộ data nằm trong client code. Mỗi thay đổi đều cần rebuild và release.

---

### 2.2 Flow logic mới

```
admin_thanhle constructor
    │
    ├─ ShopCache.daCo(3)?
    │       YES → apDungDuLieuShop(cache)  ← 0ms
    │       NO  → ApiService.layShopCuaNpc(3, callback)
    │                   │
    │              [Thread riêng chạy nền]
    │              HTTP GET /game-data/npc-shop?npc_base_id=3
    │              parse JSON → List<ShopItemServerData>
    │              ShopCache.luu(3, data)
    │              Gdx.app.postRunnable(() -> apDungDuLieuShop(data))
    │
    └─ apDungDuLieuShop(data)
            ├─ clear 3 danh sách
            └─ loop ShopItemServerData
                    ├─ is_active check
                    ├─ ItemThuongXuLi.taoItemTuTen(s.tenItem)
                    │       └─ TEN_TO_INFO.get(tenItem) → ItemInfo
                    │               └─ taoItemThuong(id, loai, hanhTinh)
                    └─ add vào đúng tab (AO_QUAN / PHU_KIEN / DAC_BIET)

render() → NPC_CUA_HANG.render_item()
    └─ veGiaItem()
            └─ ItemGia.layGiaItem(item)
                    └─ ShopCache.lay(3) → tìm theo tenItem → data.gia  ← từ server
```

Data đến từ server. Admin thay đổi không cần động đến client.

---

### 2.3 Vấn đề của cách cũ

| Vấn đề | Cách cũ | Cách mới |
|---|---|---|
| Thêm item vào shop | Sửa code + rebuild + release | POST API, có ngay session tiếp theo |
| Xóa item khỏi shop | Sửa code + rebuild + release | `is_active = false` qua API |
| Sửa giá item | Sửa `ItemGia` + rebuild + release | PATCH API, có ngay session tiếp theo |
| Giá bị sai | Có thể — `keySet()` không ổn định | Không thể — đọc thẳng từ server data |
| NPC shop mới | Viết toàn bộ hardcode từ đầu | Copy pattern, set `NPC_BASE_ID` riêng |

---

## 3. Flow hoàn chỉnh

### 3.1 Mở shop lần đầu

```
User click admin_thanhle → new admin_thanhle()
    │
    ├─ ShopCache.daCo(3) == false
    ├─ danhSachItem rỗng (shop hiện trống ~200ms)
    └─ ApiService.layShopCuaNpc(3, data -> {
            ShopCache.luu(3, data)
            Gdx.app.postRunnable(() -> apDungDuLieuShop(data))
       })
            │
       API trả về ~200ms
            │
       apDungDuLieuShop()
            ├─ danhSachItemAoQuan.clear()
            ├─ danhSachItemPhuKien.clear()
            ├─ danhSachItemDacBiet.clear()
            └─ loop data → taoItemTuTen() → add vào đúng tab
            │
       render() → shop hiện item ✅
```

### 3.2 Mở shop lần 2+

```
User click admin_thanhle → new admin_thanhle()
    │
    ├─ ShopCache.daCo(3) == true
    └─ apDungDuLieuShop(ShopCache.lay(3))  ← 0ms, không gọi API
            │
       danhSachItem có ngay
       render() → shop hiện item ngay lập tức ✅
```

---

## 4. Bảng các hàm

| Hàm | File | Tác dụng | Gọi khi nào |
|---|---|---|---|
| `layShopCuaNpc(npcBaseId, callback)` | `ApiService` | HTTP GET shop data, parse JSON, gọi callback | Constructor `admin_thanhle` khi cache miss |
| `parseProtoLong(obj)` | `ApiService` | Convert `{low, high}` proto int64 → Java long | Trong `layShopCuaNpc` khi parse field `gia` |
| `daCo(npcBaseId)` | `ShopCache` | Kiểm tra cache có data của NPC chưa | Constructor `admin_thanhle` trước khi gọi API |
| `lay(npcBaseId)` | `ShopCache` | Lấy data từ cache | `apDungDuLieuShop` khi cache hit |
| `luu(npcBaseId, data)` | `ShopCache` | Lưu data vào cache | Callback của `layShopCuaNpc` sau khi fetch xong |
| `taoItemTuTen(tenItem)` | `ItemThuongXuLi` | Mapping tên item (server string) → `Item` object | Trong `apDungDuLieuShop` khi loop data |
| `taoItemThuong(id, loai, hanhTinh)` | `ItemThuongXuLi` | Tạo Item thường từ id + loại + hành tinh | Được gọi bởi `taoItemTuTen` cho item thường |
| `apDungDuLieuShop(data)` | `admin_thanhle` | Clear + rebuild 3 danhSachItem từ server data | Sau khi có data (cache hit hoặc API về) |
| `layGiaItem(item)` | `ItemGia` | Lấy giá của item từ ShopCache | `veGiaItem()` trong `NPC_CUA_HANG` khi render |
| `layLoaiTien(item)` | `ItemGia` | Lấy loại tiền (VANG/NGOC) từ ShopCache | `veGiaItem()` và khi user mua item |
| `timTrongCache(tenItem)` | `ItemGia` | Tìm `ShopItemServerData` theo tên trong cache | Private helper dùng nội bộ trong `ItemGia` |

---

## 5. Các file thêm mới

### 5.1 ShopItemServerData.java

```
package: com.dang.dragonboy.network.DTO
```

DTO ánh xạ 1-1 với JSON response từ API. Chỉ chứa data, không có logic.

```java
public class ShopItemServerData {
    public int id;
    public String tenItem;  // "Bông tai Porata" — key khớp với TEN_TO_INFO
    public long gia;        // đã parse từ proto int64
    public String loaiTien; // "VANG" | "NGOC"
    public String tab;      // "AO_QUAN" | "PHU_KIEN" | "DAC_BIET"
    public boolean is_active;
}
```

**Tại sao tách DTO riêng thay vì dùng class `Item`:** `Item` là game object có texture, animation, state — không nên trộn lẫn với raw server data. Thay đổi API response không ảnh hưởng game logic.

---

### 5.2 ShopCache.java

```
package: com.dang.dragonboy.xu_ly_map.npc
```

Singleton cache — lưu shop data theo `npcBaseId`. Pattern giống `MapDataCache`.

```java
public class ShopCache {
    private static final ShopCache INSTANCE = new ShopCache();
    private final Map<Integer, List<ShopItemServerData>> cache = new HashMap<>();

    public static ShopCache getInstance() { return INSTANCE; }
    public boolean daCo(int npcBaseId) { return cache.containsKey(npcBaseId); }
    public List<ShopItemServerData> lay(int npcBaseId) { return cache.get(npcBaseId); }
    public void luu(int npcBaseId, List<ShopItemServerData> data) { cache.put(npcBaseId, data); }
}
```

**Trade-off:** Cache không tự invalidate. Admin update giá trên server → người chơi không thấy cho đến khi restart game. Chấp nhận được với game kiểu này.

---

## 6. Các file đã sửa

### 6.1 ApiService.java

**Thêm method `layShopCuaNpc`:**

```java
public static void layShopCuaNpc(int npcBaseId, Consumer<List<ShopItemServerData>> onHoanThanh) {
    new Thread(() -> {
        try {
            URL url = new URL("https://api.ngocrongdark.com/game-data/npc-shop?npc_base_id=" + npcBaseId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            // ... đọc response ...

            JsonArray itemsArray = JsonParser.parseString(response.toString())
                .getAsJsonObject()
                .getAsJsonArray("items");

            List<ShopItemServerData> danhSach = new ArrayList<>();
            for (var element : itemsArray) {
                JsonObject obj = element.getAsJsonObject();
                ShopItemServerData item = new ShopItemServerData();
                item.tenItem   = obj.get("tenItem").getAsString();
                item.loaiTien  = obj.get("loaiTien").getAsString();
                item.tab       = obj.get("tab").getAsString();
                item.is_active = obj.get("is_active").getAsBoolean();

                // Parse proto int64 — gia trả về dạng { low, high, unsigned }
                JsonElement giaEl = obj.get("gia");
                item.gia = giaEl.isJsonObject()
                    ? ApiService.parseProtoLong(giaEl.getAsJsonObject())
                    : giaEl.getAsLong();

                danhSach.add(item);
            }

            onHoanThanh.accept(danhSach); // không wrap postRunnable — không đụng GL object

        } catch (Exception e) {
            Gdx.app.error("ApiService", "layShopCuaNpc exception", e);
        }
    }).start();
}
```

**Tại sao không wrap `postRunnable`:** Callback chỉ lưu vào HashMap — không đụng GL object. Chỗ nào cần GL thread thì caller tự wrap, ví dụ `apDungDuLieuShop` đụng `danhSachItem` nên caller wrap `postRunnable` trước khi gọi.

**Tại sao parse proto int64:** Server dùng protobuf `int64` cho field `gia`. Khi serialize sang JSON, protobuf không trả về number thẳng mà trả về object `{ low, high, unsigned }`. Dùng `.getAsLong()` thẳng sẽ crash. Phải dùng `parseProtoLong()` có sẵn:

```java
// Server trả về:
// "gia": { "low": 500000, "high": 0, "unsigned": false }

// Parse:
public static long parseProtoLong(JsonObject obj) {
    long low  = obj.get("low").getAsLong();
    long high = obj.get("high").getAsLong();
    return (high << 32) + (low & 0xffffffffL);
}
// → 500000L
```

---

### 6.2 ItemThuongXuLi.java

**Cũ — không có mapping ngược:**

```java
// Chỉ có taoItemThuong(id, loai, hanhTinh)
// Không có cách nào đi từ tên → Item
// admin_thanhle phải tự new Item() thủ công:

public void themItemVaoDanhSachDacBiet() {
    danhSachItemDacBiet.add(new Item(
        "bongtaic1", "Bông tai Porata", LoaiItem.BONGTAI,
        "vatpham/vatphamgame/bongtai/bongtaic1.png",
        "Sử dụng để hợp thể với đệ tử", 1,
        new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
        "all", 1500000L, null, 0, 0, 0, -1
    ));
}
// → Giá hardcode thẳng vào constructor Item
// → Thêm item mới phải sửa code + rebuild
```

**Mới — thêm `TEN_TO_INFO` và `taoItemTuTen()`:**

```java
// Thêm record để lưu thông tin mapping
private record ItemInfo(String id, LoaiItem loai, String hanhTinh) {}

// Map tên (server) → (id, loai, hanhTinh) để gọi taoItemThuong()
private static final Map<String, ItemInfo> TEN_TO_INFO = new HashMap<>();

static {
    TEN_TO_INFO.put("Áo võ kame",           new ItemInfo("set_cam",   LoaiItem.AO,           "traidat"));
    TEN_TO_INFO.put("Quần võ kame",          new ItemInfo("set_cam",   LoaiItem.QUAN,         "traidat"));
    TEN_TO_INFO.put("Găng võ kame",          new ItemInfo("set_cam",   LoaiItem.GANG,         "traidat"));
    TEN_TO_INFO.put("Giày võ kame",          new ItemInfo("set_cam",   LoaiItem.GIAY,         "traidat"));
    TEN_TO_INFO.put("Rada cấp 1",            new ItemInfo("rada1",     LoaiItem.RADA,         "all"));
    TEN_TO_INFO.put("Bông tai Porata",       new ItemInfo("bongtaic1", LoaiItem.BONGTAI,      "all"));
    TEN_TO_INFO.put("Giáp luyện tập cấp 1", new ItemInfo("glt_c1",    LoaiItem.GIAPLUYENTAP, "all"));
}

// Method mới — mapping ngược tên → Item
public static Item taoItemTuTen(String tenItem) {
    ItemInfo info = TEN_TO_INFO.get(tenItem);
    if (info == null) {
        Gdx.app.error("ItemThuongXuLi", "Không tìm thấy item: " + tenItem);
        return null; // graceful — shop bỏ qua item không biết
    }

    // Item đặc biệt có constructor riêng
    if (info.loai() == LoaiItem.BONGTAI) {
        return new Item("bongtaic1", "Bông tai Porata", LoaiItem.BONGTAI, ...);
    }
    if (info.loai() == LoaiItem.GIAPLUYENTAP) {
        return new Item("glt_c1", "Giáp luyện tập cấp 1", LoaiItem.GIAPLUYENTAP, ...);
    }

    // Item thường — tái sử dụng taoItemThuong() có sẵn
    return taoItemThuong(info.id(), info.loai(), info.hanhTinh());
}
```

**Thay đổi so với cũ:**
- Thêm `record ItemInfo` — giữ 3 thông tin cần thiết để tạo Item
- Thêm `TEN_TO_INFO` static map — lookup O(1)
- Thêm `taoItemTuTen()` — entry point từ server data vào Item object
- Giữ nguyên `taoItemThuong()` — tái sử dụng lại, không sửa

---

### 6.3 ItemGia.java

**Cũ — hardcode toàn bộ giá:**

```java
public class ItemGia {
    // Nested Map — dễ nhầm khi lookup
    private static final Map<String, Map<LoaiTien, Long>> bangGia = new HashMap<>();

    static {
        themGia("Áo võ kame",   LoaiTien.VANG, 500_000L);
        themGia("Quần võ kame", LoaiTien.VANG, 450_000L);
        // ...
    }

    public static LoaiTien layLoaiTien(Item item) {
        Map<LoaiTien, Long> gia = bangGia.get(item.getTenItem());
        for (LoaiTien loai : gia.keySet()) {
            return loai; // ← BUG TIỀM ẨN: HashMap.keySet() không đảm bảo thứ tự
        }
        return null;
    }
}
// Vấn đề:
// 1. Giá hardcode → admin muốn sửa giá phải release client mới
// 2. layLoaiTien() dùng keySet() iteration → không ổn định nếu có 2 loại tiền
// 3. Thêm item mới phải sửa 2 chỗ: ItemGia + admin_thanhle
```

**Mới — đọc từ ShopCache:**

```java
public class ItemGia {
    private static final int NPC_BASE_ID = 3; // admin_thanhle

    public static long layGiaItem(Item item) {
        ShopItemServerData data = timTrongCache(item.getTenItem());
        return data != null ? data.gia : 0L;
    }

    public static LoaiTien layLoaiTien(Item item) {
        ShopItemServerData data = timTrongCache(item.getTenItem());
        if (data == null) return LoaiTien.VANG;
        return data.loaiTien.equals("NGOC") ? LoaiTien.NGOC : LoaiTien.VANG;
    }

    private static ShopItemServerData timTrongCache(String tenItem) {
        ShopCache cache = ShopCache.getInstance();
        if (!cache.daCo(NPC_BASE_ID)) return null;
        for (ShopItemServerData s : cache.lay(NPC_BASE_ID)) {
            if (s.tenItem.equals(tenItem)) return s;
        }
        return null;
    }
}
```

**Thay đổi so với cũ:**
- Xóa hoàn toàn `bangGia`, `themGia()`, nested `Map<LoaiTien, Long>`
- Xóa vòng for `keySet()` không ổn định
- Đọc thẳng từ `ShopCache` — giá luôn đồng bộ với server
- Admin sửa giá trên server → có hiệu lực ngay session tiếp theo

---

### 6.4 admin_thanhle.java

**Cũ — hardcode toàn bộ item trong constructor:**

```java
public admin_thanhle(...) {
    super(...);
    themItemVaoDanhSachAoQuan();   // hardcode
    themItemVaoDanhSachPhuKien();  // hardcode
    themItemVaoDanhSachDacBiet();  // hardcode
}

public void themItemVaoDanhSachAoQuan() {
    danhSachItemAoQuan.add(ItemThuongXuLi.taoItemThuong("set_cam", LoaiItem.AO, "traidat"));
    danhSachItemAoQuan.add(ItemThuongXuLi.taoItemThuong("set_cam", LoaiItem.QUAN, "traidat"));
}

public void themItemVaoDanhSachDacBiet() {
    danhSachItemDacBiet.add(new Item(
        "bongtaic1", "Bông tai Porata", LoaiItem.BONGTAI,
        "vatpham/vatphamgame/bongtai/bongtaic1.png",
        "Sử dụng để hợp thể với đệ tử", 1,
        new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0},
        "all", 1500000L, null, 0, 0, 0, -1  // ← giá hardcode
    ));
}
// Vấn đề:
// Thêm item → sửa code → rebuild → release
// Xóa item → sửa code → rebuild → release
// Sửa giá  → sửa ItemGia + sửa code → rebuild → release
```

**Mới — fetch từ server, cache lại:**

```java
private static final int NPC_BASE_ID = 3;

public admin_thanhle(...) {
    super(...);

    ShopCache cache = ShopCache.getInstance();
    if (cache.daCo(NPC_BASE_ID)) {
        // Cache hit — 0ms, không gọi API
        apDungDuLieuShop(cache.lay(NPC_BASE_ID));
    } else {
        // Cache miss — fetch API nền, shop rỗng đến khi có data
        ApiService.layShopCuaNpc(NPC_BASE_ID, data -> {
            cache.luu(NPC_BASE_ID, data);
            Gdx.app.postRunnable(() -> apDungDuLieuShop(data)); // GL thread
        });
    }
}

private void apDungDuLieuShop(List<ShopItemServerData> data) {
    danhSachItemAoQuan.clear();
    danhSachItemPhuKien.clear();
    danhSachItemDacBiet.clear();

    for (ShopItemServerData s : data) {
        if (!s.is_active) continue; // admin bật tắt từ server
        Item item = ItemThuongXuLi.taoItemTuTen(s.tenItem);
        if (item == null) continue; // item không biết → bỏ qua, không crash

        switch (s.tab) {
            case "AO_QUAN"  -> danhSachItemAoQuan.add(item);
            case "PHU_KIEN" -> danhSachItemPhuKien.add(item);
            case "DAC_BIET" -> danhSachItemDacBiet.add(item);
        }
    }
}
```

**Thay đổi so với cũ:**
- Xóa `themItemVaoDanhSachAoQuan()`, `themItemVaoDanhSachPhuKien()`, `themItemVaoDanhSachDacBiet()`
- Thêm `apDungDuLieuShop()` — rebuild từ server data
- Constructor check cache trước, chỉ gọi API khi cần
- `is_active` check — admin bật tắt item không cần release client

---

## 7. Lưu ý và best practices

### NPC_BASE_ID phải khớp DB

```java
private static final int NPC_BASE_ID = 3; // admin_thanhle trong DB
```

Sai ID → fetch đúng endpoint nhưng cache sai key → miss cache mãi mãi → double fetch mỗi lần click NPC. Kiểm tra DB trước khi deploy.

### Thêm item mới vào shop

Chỉ cần 2 bước, không release client:

1. Thêm 1 dòng vào `TEN_TO_INFO` trong `ItemThuongXuLi` (nếu là item hoàn toàn mới)
2. POST `/game-data/npc-shop` với data item mới

### Thêm NPC shop mới

Tạo class tương tự `admin_thanhle` với `NPC_BASE_ID` riêng. Không đụng `ShopCache`, `ApiService`, `ItemGia`.

### Shop rỗng lần đầu click

Lần đầu click NPC khi cache chưa có → shop hiện trống ~200ms. Chấp nhận được vì animation mở shop đã che phần lớn thời gian này. Không dùng fallback hardcode vì sẽ tạo inconsistency giữa data server và data hiển thị.

### Cache invalidation

Cache tồn tại suốt session. Nếu cần reload live:

```java
ShopCache.getInstance().xoaCache(NPC_BASE_ID);
// Lần click NPC tiếp theo sẽ fetch lại từ server
```
