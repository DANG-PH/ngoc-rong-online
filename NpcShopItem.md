# Game Data Service — Tài liệu kỹ thuật Client

## Mục lục

1. [Tổng quan](#1-tổng-quan)
2. [Flow hoàn chỉnh](#2-flow-hoàn-chỉnh)
    - [Login → Prefetch](#21-login--prefetch)
    - [Vào map lần đầu](#22-vào-map-lần-đầu)
    - [Vào map lần 2+](#23-vào-map-lần-2)
    - [Mở shop NPC lần đầu](#24-mở-shop-npc-lần-đầu)
    - [Mở shop NPC lần 2+](#25-mở-shop-npc-lần-2)
3. [Các file thêm mới](#3-các-file-thêm-mới)
    - [NpcServerData](#31-npcserverdatajava)
    - [MapDataCache](#32-mapdatacachejava)
    - [MapIdHelper](#33-mapidhelperjava)
    - [ShopItemServerData](#34-shopitemserverdatajava)
    - [ShopCache](#35-shopcachejava)
4. [Các file đã sửa](#4-các-file-đã-sửa)
    - [ApiService](#41-apiservicejava)
    - [MapCoBan](#42-mapcobanjava)
    - [MapDoiHoaCuc / MapLangAru / MapNhaGohan](#43-mapxxx-subclass)
    - [ManHinhXxx — constructor](#44-manhinhxxx--constructor)
    - [ManHinhXxx — show](#45-manhinhxxx--show)
    - [ManHinhXxx — render](#46-manhinhxxx--render)
    - [ItemThuongXuLi](#47-itemthuongxulijava)
    - [ItemGia](#48-itemgiajava)
    - [admin_thanhle](#49-admin_thanhlejs)
5. [Lưu ý và best practices](#5-lưu-ý-và-best-practices)

---

## 1. Tổng quan

Mục tiêu: toàn bộ data game (NPC spawn, shop item) được load từ server thay vì hardcode client. Admin thay đổi data không cần release client mới. Client dùng lazy loading + in-memory cache để đảm bảo 0ms delay với người chơi ở mọi trạng thái.

**Nguyên tắc chung:**
- Data thay đổi → server quản lý
- Logic và presentation → client quản lý
- I/O luôn chạy trong thread riêng, không block GL thread
- Cache xuyên suốt session — gọi API 1 lần duy nhất mỗi resource

---

## 2. Flow hoàn chỉnh

### 2.1 Login → Prefetch

```
verifyOTP() / getProfile() thành công
    │
    ├─ biết user.mapHienTai
    ├─ MapIdHelper.layMapId(mapHienTai) → mapId
    └─ ApiService.layNpcCuaMap(mapId, data -> {
            MapDataCache.luu(mapId, data)  ← chạy nền, không block
       })

[Menu hiện ngay, user đọc UI]

[API trả về ~200-500ms sau, cache đã có sẵn]
```

Người chơi không chờ thêm giây nào. Prefetch chạy song song hoàn toàn.

---

### 2.2 Vào map lần đầu

```
User bấm nút → ManHinhSplash constructor
                    → Supplier.get() → new ManHinhXxx()
                                          → map.khoiTao(() -> {
                                                cache hit? → apDungDuLieuServer()
                                                cache miss? → gọi API nền
                                            })
                    ↓
               Splash hiện ~1 giây
                    ↓
               thoiGian > 1f → game.setScreen(manHinhDaTao)
                    ↓
               show() → render() frame 1 → NPC có sẵn ✅
```

Splash che toàn bộ thời gian chờ. Người chơi không thấy gì bất thường.

---

### 2.3 Vào map lần 2+

```
User chuyển map → new ManHinhXxx(thongtin)
    │
    ├─ thongtin.mapSau != null
    ├─ map = thongtin.mapSau  ← object cũ, NPC đã có sẵn
    └─ danhSachNpc = map.LayDanhSachNpc()  ← lấy thẳng, 0ms
```

Siêu mượt — không gọi API, không đụng cache, lấy thẳng object cũ.

---

### 2.4 Mở shop NPC lần đầu

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
       API trả về
            │
       apDungDuLieuShop() → rebuild danhSachItem theo tab
       render() → shop hiện item ✅
```

---

### 2.5 Mở shop NPC lần 2+

```
User click admin_thanhle → new admin_thanhle()
    │
    ├─ ShopCache.daCo(3) == true
    └─ apDungDuLieuShop(ShopCache.lay(3))  ← 0ms
            │
       danhSachItem có ngay
       render() → shop hiện item ngay lập tức ✅
```

---

## 3. Các file thêm mới

### 3.1 NpcServerData.java

```
package: com.dang.dragonboy.network.DTO
```

DTO ánh xạ 1-1 với JSON response từ `GET /game-data/map/npcs`. Chỉ chứa data, không có logic.

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

**Tại sao tách DTO riêng thay vì dùng class `Npc`:** `Npc` là game object có texture, animation, state — không nên trộn lẫn với raw server data. Thay đổi API response không ảnh hưởng game logic.

---

### 3.2 MapDataCache.java

```
package: com.dang.dragonboy.xu_ly_map
```

Singleton cache — lưu NPC data theo `mapId`.

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

---

### 3.3 MapIdHelper.java

```
package: com.dang.dragonboy.xu_ly_map
```

Mapping tên map (server string) → mapId (server int). Dùng cho prefetch sau login.

```java
public class MapIdHelper {
    public static int layMapId(String tenMap) {
        switch (tenMap) {
            case "Nhà Gôhan":    return 1;
            case "Làng Aru":     return 2;
            case "Đồi Hoa Cúc":  return 3;
            default: return 0;
        }
    }
}
```

**Quan trọng:** ID phải khớp với DB server. Sai ID → prefetch fetch sai map → cache miss mãi mãi. Thêm map mới phải cập nhật file này đồng thời với server.

---

### 3.4 ShopItemServerData.java

```
package: com.dang.dragonboy.network.DTO
```

DTO ánh xạ 1-1 với JSON response từ `GET /game-data/npc-shop`.

```java
public class ShopItemServerData {
    public int id;
    public String tenItem;
    public long gia;        // đã parse từ proto int64
    public String loaiTien; // "VANG" | "NGOC"
    public String tab;      // "AO_QUAN" | "PHU_KIEN" | "DAC_BIET"
    public boolean is_active;
}
```

---

### 3.5 ShopCache.java

```
package: com.dang.dragonboy.xu_ly_map.npc
```

Singleton cache — lưu shop data theo `npcBaseId`. Giống `MapDataCache`.

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

---

## 4. Các file đã sửa

### 4.1 ApiService.java

Thêm 2 method:

**`layNpcCuaMap`** — fetch NPC spawn theo map:

```java
public static void layNpcCuaMap(int mapId, Consumer<List<NpcServerData>> onHoanThanh) {
    new Thread(() -> {
        // HTTP GET /game-data/map/npcs?map_id={mapId}
        // parse JSON → List<NpcServerData>
        onHoanThanh.accept(danhSach); // không wrap postRunnable
    }).start();
}
```

**`layShopCuaNpc`** — fetch shop item theo NPC:

```java
public static void layShopCuaNpc(int npcBaseId, Consumer<List<ShopItemServerData>> onHoanThanh) {
    new Thread(() -> {
        // HTTP GET /game-data/npc-shop?npc_base_id={npcBaseId}
        // parse JSON → List<ShopItemServerData>
        // gia cần parse proto int64:
        JsonElement giaEl = obj.get("gia");
        item.gia = giaEl.isJsonObject()
            ? ApiService.parseProtoLong(giaEl.getAsJsonObject())
            : giaEl.getAsLong();

        onHoanThanh.accept(danhSach); // không wrap postRunnable
    }).start();
}
```

**Thêm vào `getProfile()`** — prefetch NPC map hiện tại ngay sau login:

```java
// Cuối getProfile(), sau khi đã có user.mapHienTai
int mapId = MapIdHelper.layMapId(user.mapHienTai);
if (mapId != 0) {
    ApiService.layNpcCuaMap(mapId, data -> {
        MapDataCache.getInstance().luu(mapId, data);
    });
}
```

**Tại sao callback không wrap `postRunnable`:** Callback chỉ lưu vào HashMap — không đụng GL object. Chỗ nào cần GL thread thì caller tự wrap `Gdx.app.postRunnable()`.

---

### 4.2 MapCoBan.java

Thêm `mapId` field và 2 method:

```java
protected int mapId; // subclass gán trong constructor, mặc định 0 = không có server data

public void khoiTao(Runnable onHoanThanh) {
    taiDuLieuMap(); // hitbox local, sync, xong ngay

    if (mapId == 0) { onHoanThanh.run(); return; }

    MapDataCache cache = MapDataCache.getInstance();
    if (cache.daCo(mapId)) {
        apDungDuLieuServer(cache.lay(mapId));
        onHoanThanh.run();
        return;
    }

    ApiService.layNpcCuaMap(mapId, danhSach -> {
        cache.luu(mapId, danhSach);
        apDungDuLieuServer(danhSach);
        onHoanThanh.run();
    });
}

private void apDungDuLieuServer(List<NpcServerData> danhSach) {
    for (NpcServerData npc : danhSach) {
        if (!npc.is_active) continue;
        LoaiNPC loai = LoaiNPC.valueOf(npc.loai_npc);
        themNpc(npc.ten_npc, loai, npc.x, npc.y);
    }
}
```

**`khoiTao()` thay thế hoàn toàn `taiDuLieuMap()`** khi gọi từ bên ngoài. Caller không cần biết bên trong có cache hay API hay local.

---

### 4.3 MapXxx subclass

Mỗi map subclass chỉ cần thêm `mapId` trong constructor, xóa `themNpc` loại NGUOI (server lo):

```java
public class MapLangAru extends MapCoBan {
    public MapLangAru() {
        this.mapId = 2; // khớp với DB
    }

    @Override
    public void taiDuLieuMap() {
        // Chỉ còn hitbox
        danhSachDat.add(new HitboxDat(-70, -38, 2540, 175+38));
        // Không còn themNpc NGUOI — server lo
    }
}
```

NPC loại đặc biệt (CAYDAU, RUONGDO, DUIGA) vẫn `themNpc()` trong `taiDuLieuMap()` như bình thường.

---

### 4.4 ManHinhXxx — constructor

Gọi `khoiTao()` thay vì `taiDuLieuMap()`. Callback setup NPC luôn trong constructor:

```java
// Nhánh thongtin != null, mapSau == null:
map = new MapLangAru();
map.khoiTao(() -> {
    this.danhSachNpc = map.LayDanhSachNpc();
    this.npcTaiAnhMap = map.getNpcTaiAnhMap();
    for (Npc npc : danhSachNpc) {
        npc.setNpcTaiAnh(npcTaiAnhMap.get(npc.getTen()));
        npc.setNpcOffset(map.getNpcOffset(npc.getTen()));
        npc.setNhanVat(nhanVat); // nhanVat đã gán trước khoiTao() → an toàn
    }
});

// Nhánh thongtin != null, mapSau != null:
map = thongtin.mapSau;
this.danhSachNpc = map.LayDanhSachNpc(); // object cũ, lấy thẳng
this.npcTaiAnhMap = map.getNpcTaiAnhMap();
for (Npc npc : danhSachNpc) {
    npc.setNpcTaiAnh(npcTaiAnhMap.get(npc.getTen()));
    npc.setNpcOffset(map.getNpcOffset(npc.getTen()));
    npc.setNhanVat(nhanVat);
}
```

**Tại sao gọi trong constructor thay vì `show()`:**

```
Constructor trong splash → API chạy nền trong splash (~1 giây)
show() → API đã xong → NPC có sẵn ngay frame đầu

Nếu gọi trong show():
show() sau splash → API bắt đầu lúc này → NPC pop ra sau ~300ms
```

---

### 4.5 ManHinhXxx — show()

Xóa toàn bộ khối setup NPC — constructor đã lo:

```java
// Chỉ giữ lại:
nhanVat.setDanhSachDat(map.LayDanhSachDat());
nhanVat.setGioiHanToaDo(map.getChieuRongMap(), map.getChieuCaoMap(), 5, 0);
// Không còn đụng NPC ở đây
```

---

### 4.6 ManHinhXxx — render()

Guard null cho `danhSachNpc` — vì API có thể chưa trả về khi render frame đầu:

```java
if (danhSachNpc != null) {
    for (int i = 0; i < danhSachNpc.size(); i++) {
        danhSachNpc.get(i).checkClick(nhanVat.x_check_npc, nhanVat.y_check_npc);
        danhSachNpc.get(i).ve(batch, thoiGianTichLuy);
    }
    map.capNhatNpc();
}

boolean duocVeDiemCanDen = true;
if (danhSachNpc != null) {
    for (Npc npc : danhSachNpc) {
        if (npc.dangClickNpc) { duocVeDiemCanDen = false; }
    }
}
```

---

### 4.7 ItemThuongXuLi.java

Thêm `TEN_TO_INFO` và `taoItemTuTen()` — mapping ngược từ tên → Item object:

```java
private record ItemInfo(String id, LoaiItem loai, String hanhTinh) {}

private static final Map<String, ItemInfo> TEN_TO_INFO = new HashMap<>();

static {
    TEN_TO_INFO.put("Áo võ kame",           new ItemInfo("set_cam",  LoaiItem.AO,           "traidat"));
    TEN_TO_INFO.put("Quần võ kame",          new ItemInfo("set_cam",  LoaiItem.QUAN,         "traidat"));
    TEN_TO_INFO.put("Găng võ kame",          new ItemInfo("set_cam",  LoaiItem.GANG,         "traidat"));
    TEN_TO_INFO.put("Giày võ kame",          new ItemInfo("set_cam",  LoaiItem.GIAY,         "traidat"));
    TEN_TO_INFO.put("Rada cấp 1",            new ItemInfo("rada1",    LoaiItem.RADA,         "all"));
    TEN_TO_INFO.put("Bông tai Porata",       new ItemInfo("bongtaic1",LoaiItem.BONGTAI,      "all"));
    TEN_TO_INFO.put("Giáp luyện tập cấp 1", new ItemInfo("glt_c1",   LoaiItem.GIAPLUYENTAP, "all"));
}

public static Item taoItemTuTen(String tenItem) {
    ItemInfo info = TEN_TO_INFO.get(tenItem);
    if (info == null) {
        Gdx.app.error("ItemThuongXuLi", "Không tìm thấy item: " + tenItem);
        return null;
    }
    // Item đặc biệt xử lý riêng constructor
    // Item thường gọi taoItemThuong()
}
```

**Thêm item mới:** 1 dòng vào `TEN_TO_INFO` + 1 record vào DB — không đụng gì khác.

---

### 4.8 ItemGia.java

Bỏ hoàn toàn hardcode `bangGia`. Đọc thẳng từ `ShopCache`:

```java
public class ItemGia {
    private static final int NPC_BASE_ID = 3;

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

**Xóa hoàn toàn:** static block `bangGia`, `themGia()`, nested `Map<LoaiTien, Long>`, vòng for `keySet()` không ổn định.

---

### 4.9 admin_thanhle.java

Bỏ `themItemVaoDanhSach...()` hardcode. Constructor fetch từ server:

```java
private static final int NPC_BASE_ID = 3;

public admin_thanhle(...) {
    super(...);
    ShopCache cache = ShopCache.getInstance();
    if (cache.daCo(NPC_BASE_ID)) {
        apDungDuLieuShop(cache.lay(NPC_BASE_ID));
    } else {
        ApiService.layShopCuaNpc(NPC_BASE_ID, data -> {
            cache.luu(NPC_BASE_ID, data);
            Gdx.app.postRunnable(() -> apDungDuLieuShop(data));
        });
    }
}

private void apDungDuLieuShop(List<ShopItemServerData> data) {
    danhSachItemAoQuan.clear();
    danhSachItemPhuKien.clear();
    danhSachItemDacBiet.clear();
    for (ShopItemServerData s : data) {
        if (!s.is_active) continue;
        Item item = ItemThuongXuLi.taoItemTuTen(s.tenItem);
        if (item == null) continue;
        switch (s.tab) {
            case "AO_QUAN"  -> danhSachItemAoQuan.add(item);
            case "PHU_KIEN" -> danhSachItemPhuKien.add(item);
            case "DAC_BIET" -> danhSachItemDacBiet.add(item);
        }
    }
}
```

**Xóa hoàn toàn:** `themItemVaoDanhSachAoQuan()`, `themItemVaoDanhSachPhuKien()`, `themItemVaoDanhSachDacBiet()`.

---

## 5. Lưu ý và best practices

### ID phải khớp với DB

```java
// MapIdHelper
case "Làng Aru": return 2;

// MapLangAru
this.mapId = 2;

// admin_thanhle
private static final int NPC_BASE_ID = 3;
```

Sai ID → cache sai key → miss cache mãi mãi → double fetch mỗi lần vào. Thêm map/NPC mới phải update đồng thời client và server.

---

### Thread safety

`danhSachNpc` và `danhSachItem` được gán trong GL thread (nhờ `postRunnable`) và đọc trong `render()` cũng trên GL thread → không có race condition. Callback của `layNpcCuaMap` và `layShopCuaNpc` không wrap `postRunnable` vì chỉ lưu HashMap — caller tự wrap khi cần đụng GL object.

---

### Graceful degradation

API lỗi → NPC/shop không hiện, map vẫn chơi được (hitbox vẫn có). Tốt hơn crash. Người chơi ra vào lại map/NPC sẽ retry tự động vì cache chưa được luu.

---

### UX — tại sao không cảm thấy lag

| Thời điểm | Người chơi thấy | Thực tế |
|---|---|---|
| Login | Menu hiện ngay | NPC prefetch nền |
| Bấm vào game | Splash hiện ngay | Constructor + API chạy nền |
| Vào map lần đầu | NPC có sẵn | Cache hit từ prefetch |
| Vào map lần 2+ | NPC có sẵn | Object map cũ / cache hit |
| Mở shop lần đầu | Shop có thể rỗng ~200ms | API đang fetch |
| Mở shop lần 2+ | Item có sẵn ngay | Cache hit |

Worst case duy nhất: mạng cực chậm khiến API chưa xong khi splash kết thúc → NPC pop ra sau vào map. Không phải lỗi thiết kế, là infrastructure issue.

---

### Thêm map mới

1. Tạo `MapXxx extends MapCoBan`, set `this.mapId = N`
2. Thêm `case "Tên Map": return N` vào `MapIdHelper`
3. Thêm record vào DB — không đụng gì khác

---

### Thêm NPC shop mới (NPC khác admin_thanhle)

1. Tạo class NPC mới tương tự `admin_thanhle`, set `NPC_BASE_ID` riêng
2. Thêm item vào DB qua API admin
3. Thêm mapping vào `TEN_TO_INFO` nếu có item mới
4. Không đụng `ShopCache`, `ApiService`, `ItemGia`

---

### Thêm item mới vào shop hiện có

1. Thêm 1 dòng vào `TEN_TO_INFO` trong `ItemThuongXuLi`
2. POST `/game-data/npc-shop` với data item mới
3. Không release client mới

---

### Cache invalidation nếu cần

```java
// MapDataCache / ShopCache
public void xoaCache(int id) { cache.remove(id); }
public void xoaToanBoCache() { cache.clear(); }
```

Gọi sau khi nhận WebSocket event update từ server nếu muốn live reload.

---

### Parse proto int64

Server dùng protobuf `int64` → JSON serialize thành `{ low, high, unsigned }`. Luôn dùng `parseProtoLong()` có sẵn trong `ApiService`:

```java
JsonElement giaEl = obj.get("gia");
item.gia = giaEl.isJsonObject()
    ? ApiService.parseProtoLong(giaEl.getAsJsonObject())
    : giaEl.getAsLong();
```

Không dùng `.getAsLong()` thẳng trên proto int64 field — số lớn sẽ bị sai âm thầm.
