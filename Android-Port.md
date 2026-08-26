# Port Desktop (.exe) sang Android — Tài liệu kỹ thuật & Nhật ký triển khai

> **Cách dùng tài liệu này**: đây vừa là nhật ký kỹ thuật (đọc tuần tự để
> hiểu toàn bộ quá trình) vừa là tài liệu tra cứu. Nếu đang gặp 1 lỗi cụ
> thể → nhảy thẳng **mục 16** (bảng tra cứu). Mới join dự án, chưa build
> được lần nào → bắt đầu từ **mục 15**. Định port project libGDX khác sang
> Android/thêm 1 platform mới → đọc kỹ **mục 3, 4, 5.1, 17** (đây là phần
> tổng quát hoá được, không riêng gì project này). Chỉ quan tâm tính năng
> mobile UI mới thêm (joystick, nút bấm ảo) → **mục 8**. Tài liệu này chỉ
> nói về phía **client** (`core`/`lwjgl3`/`html`/`android`) — các thay đổi
> phía backend (NestJS, Go) được nhắc tới khi liên quan (VD mục 5, mục 9)
> nhưng nằm ở repo/service khác, không thuộc phạm vi chi tiết ở đây.

## Mục lục

1. [Bối cảnh & bài toán](#1-bối-cảnh--bài-toán)
2. [Kiến trúc dự án trước khi có Android](#2-kiến-trúc-dự-án-trước-khi-có-android)
3. [Bootstrap Android module từ đầu](#3-bootstrap-android-module-từ-đầu)
    - [3.1. Các bước tổng quát](#31-các-bước-tổng-quát)
    - [3.2. `android/build.gradle` — giải thích từng khối](#32-androidbuildgradle--giải-thích-từng-khối)
    - [3.3. `android/AndroidManifest.xml` — từng dòng](#33-androidandroidmanifestxml--từng-dòng)
    - [3.4. `AndroidLauncher.java` — entry point](#34-androidlauncherjava--entry-point)
    - [3.5. `res/` — tài nguyên Android](#35-res--tài-nguyên-android)
    - [3.6. `libs/<abi>/*.so` — thư viện native](#36-libsabiso--thư-viện-native)
    - [3.7. `proguard-rules.pro`](#37-proguard-rulespro)
    - [3.8. Chỗ khai báo ở gốc project (`settings.gradle`, `build.gradle`, `gradle.properties`)](#38-chỗ-khai-báo-ở-gốc-project-settingsgradle-buildgradle-gradleproperties)
4. [Các lỗi "giả định desktop" đã sửa trong core/](#4-các-lỗi-giả-định-desktop-đã-sửa-trong-core)
5. [Google Sign-In trên Android — khác hoàn toàn Desktop](#5-google-sign-in-trên-android--khác-hoàn-toàn-desktop)
    - [5.1. Kiến trúc trừu tượng theo platform](#51-kiến-trúc-trừu-tượng-theo-platform)
    - [5.2. `AndroidGoogleOAuth.java` — chi tiết từng bước](#52-androidgoogleoauthjava--chi-tiết-từng-bước)
    - [5.3. `GoogleOAuth2Desktop.java` — đối chiếu để thấy vì sao 2 flow khác hẳn nhau](#53-googleoauth2desktopjava--đối-chiếu-để-thấy-vì-sao-2-flow-khác-hẳn-nhau)
6. [Bug thực tế phát hiện khi test máy thật](#6-bug-thực-tế-phát-hiện-khi-test-máy-thật)
7. [Tính năng: phát hiện lỗi mạng (no-internet)](#7-tính-năng-phát-hiện-lỗi-mạng-no-internet)
8. [Tính năng: cụm nút điều khiển ảo cho mobile](#8-tính-năng-cụm-nút-điều-khiển-ảo-cho-mobile)
9. [Chẩn đoán lỗi nhạc nền + thông báo qua pet](#9-chẩn-đoán-lỗi-nhạc-nền--thông-báo-qua-pet)
10. [Nỗ lực "fit theo màn hình thật" — đã thử 2 lần, đều revert](#10-nỗ-lực-fit-theo-màn-hình-thật--đã-thử-2-lần-đều-revert)
11. [Quy trình git đã dùng trong suốt quá trình](#11-quy-trình-git-đã-dùng-trong-suốt-quá-trình)
12. [Checklist tổng quát cho dự án libGDX desktop-only muốn port Android](#12-checklist-tổng-quát-cho-dự-án-libgdx-desktop-only-muốn-port-android)
13. [Cơ chế auto-update & phân phối bản build (PC vs Android)](#13-cơ-chế-auto-update--phân-phối-bản-build-pc-vs-android)
14. [Toàn bộ lệnh build cần nhớ](#14-toàn-bộ-lệnh-build-cần-nhớ)
15. [Thiết lập môi trường dev từ đầu — checklist cho dev hoàn toàn mới](#15-thiết-lập-môi-trường-dev-từ-đầu--checklist-cho-dev-hoàn-toàn-mới)
16. [Lỗi thường gặp & cách fix nhanh — bảng tra cứu](#16-lỗi-thường-gặp--cách-fix-nhanh--bảng-tra-cứu)
17. [Kiến trúc tổng thể — sơ đồ](#17-kiến-trúc-tổng-thể--sơ-đồ)
18. [Chưa kiểm chứng / rủi ro còn tồn đọng](#18-chưa-kiểm-chứng--rủi-ro-còn-tồn-đọng)

---

## 1. Bối cảnh & bài toán

Dự án là 1 game MMORPG 2D viết bằng Java/libGDX, tên nội bộ `DragonBoy`
(package `com.dang.dragonboy`). Project được khởi tạo từ rất lâu bằng
**gdx-liftoff**, ban đầu chỉ generate 3 module: `core` (logic dùng chung),
`lwjgl3` (chạy desktop, xuất ra `.exe`/jar chạy trên Windows/Linux), `html`
(chạy web qua GWT). **Không hề có module `android` nào tồn tại trong source**
— nghĩa là không phải chỉ thiếu build config, mà toàn bộ code trong `core/`
được viết với giả định ngầm là "chỉ chạy trên desktop": toạ độ chuột/chạm
hard-code theo pixel thật của cửa sổ PC, lưu file cục bộ bằng `java.io.File`
kiểu desktop, đăng nhập Google qua trình duyệt (flow chỉ hợp lệ với OAuth
Desktop Client)...

Yêu cầu ban đầu: "làm sao để dùng java/kotlin build ra APK chơi được trên
mobile" — tức là phải làm **từ số 0**: tạo module Android, sửa lại toàn bộ
những chỗ code trót giả định desktop, rồi lặp lại nhiều vòng fix bug thật
qua test trên điện thoại thật, và cuối cùng thêm hẳn 1 bộ UI/input mới dành
riêng cho mobile (nút bấm ảo) vì màn cảm ứng không có bàn phím vật lý.

## 2. Kiến trúc dự án trước khi có Android

```
ngoc-rong-online/
├── core/        # Toàn bộ logic game, render, network — dùng chung mọi platform
├── lwjgl3/      # Launcher cho desktop (LWJGL3 backend)
├── html/        # Launcher cho web (GWT backend)
├── assets/      # Texture, font, âm thanh — dùng chung, symlink/copy vào từng module lúc build
├── build.gradle, settings.gradle, gradle.properties   # multi-module Gradle root
```

Điểm quan trọng nhất để hiểu toàn bộ phần còn lại của tài liệu này: **mọi
toạ độ UI/HUD trong `core/` đều hard-code theo 1 "canvas ảo" cố định
1020×610** (`QuanLyCamera.VIRTUAL_WIDTH/VIRTUAL_HEIGHT`), đúng bằng kích
thước cửa sổ PC mặc định. Khi chạy trên PC, `FitViewport(1020, 610)` khớp
gần như 1:1 với cửa sổ thật nên mọi thứ trông đúng. Khi chạy trên điện
thoại (tỉ lệ màn hình hoàn toàn khác, và toạ độ chạm libGDX trả về là pixel
thật của thiết bị, không phải toạ độ ảo), toàn bộ giả định này sụp đổ nếu
không xử lý đúng.

## 3. Bootstrap Android module từ đầu

### 3.1. Các bước tổng quát

Với 1 project gdx-liftoff cũ chưa từng có Android, các bước đã làm (tổng
quát hoá, áp dụng được cho project tương tự):

1. **Cài Android SDK** qua `cmdline-tools`/`sdkmanager` — không cần cài cả
   Android Studio, chỉ cần SDK + build-tools + platform tương ứng
   `compileSdkVersion`/`targetSdkVersion` muốn dùng.
2. **Tạo module `android/` thủ công** theo đúng cấu trúc chuẩn của
   `gdx-liftoff` khi chọn platform Android lúc khởi tạo (có thể tham khảo
   file mẫu từ 1 project gdx-liftoff mới tạo có bật sẵn Android để copy
   cấu trúc, rồi chỉnh lại theo package/tên project hiện tại) — chi tiết
   từng file ở các mục 3.2-3.7 bên dưới.
3. **`settings.gradle`**: thêm `include ':android'`.
4. **Root `build.gradle`**: đảm bảo có `google()` trong `repositories` —
   bắt buộc, nếu thiếu sẽ không resolve được `com.android.tools.build:gradle`
   và nhiều dependency AndroidX (xem mục 3.8 — thật ra template gdx-liftoff
   gốc của project này ĐÃ có sẵn dòng này từ trước, phòng hờ trường hợp
   sau này thêm Android, nên không phải lúc nào cũng cần tự thêm).
5. **`gradle.properties`**: thêm `android.useAndroidX=true`.
6. **`AndroidManifest.xml`**: đặt
   `android:windowSoftInputMode="adjustResize"` trên activity chính — nếu
   không, bàn phím ảo Android sẽ **đè lên** các ô nhập liệu (đăng nhập,
   OTP, chat...) mà không tự cuộn màn hình lên.
7. **Build thử**: `./gradlew :android:assembleDebug`, cài bằng
   `adb install -r android/build/outputs/apk/debug/android-debug.apk`.

### 3.2. `android/build.gradle` — giải thích từng khối

File cấu hình build riêng cho module Android — đây là module DUY NHẤT
không dùng chung cấu hình `java-library` với `core`/`lwjgl3`/`html` (xem
mục 3.8), vì Android có hệ build riêng (Android Gradle Plugin) khác hẳn
Java thường.

```gradle
apply plugin: 'com.android.application'
```
Plugin của Android Gradle Plugin (khác `java-library` các module kia dùng)
— cho phép khai báo khối `android { ... }`, biết cách đóng gói ra `.apk`
thay vì `.jar` thường.

```gradle
android {
  namespace 'com.dang.dragonboy.android'
  compileSdkVersion 35
  buildToolsVersion "35.0.0"
```
- `namespace`: định danh package dùng để sinh code `R.java` (resource ID)
  — **khác** với `applicationId` bên dưới (namespace là khái niệm code,
  applicationId là khái niệm Play Store/cài đặt), tuy ở đây trùng nhau.
- `compileSdkVersion`/`buildToolsVersion`: phiên bản Android SDK dùng để
  BIÊN DỊCH — luôn nên để cao nhất SDK đã cài, không quyết định máy nào
  cài được app (đó là việc của `minSdkVersion`/`targetSdkVersion`).

```gradle
  sourceSets {
    main {
      manifest.srcFile 'AndroidManifest.xml'
      java.srcDirs = ['src/main/java']
      jniLibs.srcDirs = ['libs']
      res.srcDirs = ['res']
      assets.srcDirs = [rootProject.file('assets').path]
    }
  }
```
Android Gradle Plugin mặc định mong đợi cấu trúc thư mục kiểu
`src/main/AndroidManifest.xml`, `src/main/res/`... — nhưng project này đặt
`AndroidManifest.xml`/`res/`/`libs/` ngay tại gốc module `android/` (khớp
layout chuẩn gdx-liftoff hay dùng, gọn hơn). Khối `sourceSets` này khai
báo lại đường dẫn thật cho từng loại file. Dòng quan trọng nhất:
`assets.srcDirs = [rootProject.file('assets').path]` — trỏ THẲNG về thư
mục `assets/` dùng chung ở gốc repo (không copy riêng cho Android) — texture/
font/âm thanh chỉ có **1 bản duy nhất**, dùng chung cho mọi platform.

```gradle
  packagingOptions {
    exclude 'META-INF/robovm/ios/robovm.xml'
  }
```
Loại bỏ 1 file thừa từ dependency liên quan iOS (RoboVM) — 1 vài thư viện
gdx đóng gói kèm file cấu hình cho platform iOS dù project này không target
iOS, nếu không exclude có thể gây lỗi trùng file khi đóng gói.

```gradle
  defaultConfig {
    applicationId 'com.dang.dragonboy.android'
    minSdkVersion 21
    targetSdkVersion 35
    versionCode 1
    versionName "$projectVersion"
  }
```
- `applicationId`: định danh DUY NHẤT của app trên máy người dùng/Play
  Store — **không được đổi** sau khi đã phát hành, đổi là Android coi như
  1 app hoàn toàn khác (user cũ không update được, phải cài lại).
- `minSdkVersion 21` (Android 5.0 Lollipop, phát hành 2014): phiên bản
  Android CŨ NHẤT còn cài được app — chọn 21 vì đó là mức tối thiểu phổ
  biến vẫn phủ được đa số máy thật đang dùng, đồng thời vẫn tương thích
  với AndroidX/Credential Manager cần cho Google Sign-In (mục 5).
- `targetSdkVersion 35`: phiên bản Android mà app được TEST/nhắm tới — ảnh
  hưởng hành vi 1 số API hiện đại (permission model, background limits...).
  Nên luôn để gần với `compileSdkVersion` nhất có thể, Google Play cũng
  yêu cầu target SDK không quá cũ để còn được publish app mới.
- `versionCode`: số nguyên tăng dần, Android dùng để SO SÁNH bản nào mới
  hơn khi cài đè — **phải tăng mỗi lần build bản mới thật sự phát hành**,
  hiện đang cố định `1` (chưa gắn với quy trình phát hành thật, xem mục 13).
- `versionName "$projectVersion"`: chuỗi hiển thị cho người dùng thấy (VD
  "1.0.0"), lấy từ `gradle.properties` — không ảnh hưởng logic cài đè như
  `versionCode`.

```gradle
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_17
    targetCompatibility JavaVersion.VERSION_17
    coreLibraryDesugaringEnabled true
  }
```
Code viết bằng Java 17 (đồng bộ với `core`/`lwjgl3`), nhưng máy Android
**cũ** (đặc biệt dưới Android 8 API 26) có runtime thiếu 1 số API Java
hiện đại (`java.time`, streams nâng cao...). Bật
`coreLibraryDesugaringEnabled` để công cụ build tự "dịch ngược" các API
đó thành dạng tương thích máy cũ — không phải lúc nào cũng cần, nhưng bật
sẵn cho an toàn nếu code (kể cả thư viện phụ thuộc) có dùng tới.

```gradle
  buildTypes {
    release {
      minifyEnabled false
      proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }
  }
```
`minifyEnabled false`: **chưa bật** rút gọn/làm rối code (R8/ProGuard) cho
bản release — nghĩa là dù có khai `proguardFiles`, các rule đó hiện
**chưa thật sự chạy** (xem mục 3.7 để hiểu khi nào cần bật và vì sao). Ưu
điểm hiện tại: đơn giản, không lo obfuscation làm crash reflection-based
code; nhược điểm: file APK to hơn, dễ decompile hơn.

```gradle
dependencies {
  coreLibraryDesugaring 'com.android.tools:desugar_jdk_libs:2.1.4'
  implementation "com.badlogicgames.gdx-controllers:gdx-controllers-android:$gdxControllersVersion"
  implementation "com.badlogicgames.gdx:gdx-backend-android:$gdxVersion"
  implementation "androidx.credentials:credentials:1.5.0"
  implementation "androidx.credentials:credentials-play-services-auth:1.5.0"
  implementation "com.google.android.libraries.identity.googleid:googleid:1.1.1"
  natives "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi-v7a"
  ... (tương tự cho arm64-v8a, x86, x86_64, cả gdx-box2d-platform và gdx-freetype-platform)
  implementation project(':core')
}
```
- `gdx-backend-android`: implementation của libGDX CHO Android — cung cấp
  `AndroidApplication` (class `AndroidLauncher` kế thừa, mục 3.4), tự lo
  vòng đời `Activity`, tạo GL surface, forward input event vào
  `InputAdapter` của game — đây là lý do `core/` không cần biết gì về
  Android mà vẫn chạy được.
- `androidx.credentials*`/`googleid`: thư viện Credential Manager cho
  đăng nhập Google kiểu mới (mục 5) — Android **bắt buộc** dùng bộ này,
  không dùng lại được thư viện OAuth thường của Desktop.
- Khối `natives "..."` (dùng configuration tuỳ biến tên `natives`, không
  phải `implementation`): các file `.jar` này KHÔNG chứa code Java, chỉ
  chứa file `.so` (thư viện C/C++ biên dịch sẵn — phần lõi hiệu năng cao
  của libGDX: OpenGL binding, Box2D physics, FreeType font rendering).
  Mỗi kiến trúc CPU Android (`armeabi-v7a`, `arm64-v8a`, `x86`, `x86_64`)
  cần bản `.so` riêng — đây là điểm khác biệt lớn nhất so với
  `lwjgl3`/`html` (chỉ chạy trên CPU/JVM desktop, không cần multi-ABI).
  Dùng configuration `natives` riêng (không phải `implementation` thường)
  để có thể tự viết task Gradle bóc file `.so` ra khỏi các `.jar` này
  (xem `copyAndroidNatives` bên dưới) — nếu để `implementation` thường,
  Android Gradle Plugin sẽ không tự biết cách xử lý các file bên trong.

```gradle
task copyAndroidNatives {
  doFirst {
    file("libs/armeabi-v7a/").mkdirs()
    ...
    configurations.natives.files.each { jar ->
      def outputDir = null
      if (jar.name.endsWith("natives-armeabi-v7a.jar")) outputDir = file("libs/armeabi-v7a")
      ...
      if (outputDir != null) {
        copy { from zipTree(jar); into outputDir; include "*.so" }
      }
    }
  }
}
tasks.whenTaskAdded { packageTask ->
  if (packageTask.name.contains("package")) {
    packageTask.dependsOn 'copyAndroidNatives'
  }
}
```
Đây chính là bước **giải nén** các file `.jar` chứa native trong khối
`natives` ở trên, lấy đúng file `.so` bên trong, copy vào đúng thư mục
`libs/<abi>/` mà `jniLibs.srcDirs = ['libs']` ở khối `sourceSets` phía
trên đã khai là sẽ đóng gói vào APK. `tasks.whenTaskAdded {...}` móc vào
BẤT KỲ task nào có tên chứa `"package"` (VD `packageDebug`,
`packageRelease` — bước đóng gói APK cuối cùng) để đảm bảo
`copyAndroidNatives` LUÔN chạy trước, nếu không APK sẽ thiếu file `.so`
→ app crash ngay khi khởi động (`UnsatisfiedLinkError`) trên máy thật.
**Đây cũng chính là lý do thư mục `libs/<abi>/*.so` (mục 3.6) có mặt sẵn
trong source** — do task này tự sinh ra mỗi lần build, không phải file
tự tay viết.

```gradle
tasks.register('run', Exec) { ... commandLine "$adb", 'shell', 'am', 'start', ... }
```
Task tiện ích: cài xong app (qua `installDebug`) thì tự bắn lệnh `adb
shell am start` để MỞ LUÔN app trên máy/emulator đang nối — đỡ phải tự mở
tay trên màn hình điện thoại mỗi lần test.

### 3.3. `android/AndroidManifest.xml` — từng dòng

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
`INTERNET` bắt buộc cho MỌI app cần gọi API/socket (game này gọi HTTP tới
backend NestJS, WebSocket real-time, tải nhạc từ Supabase...) — Android
**mặc định chặn** mọi truy cập mạng nếu thiếu quyền này, khác iOS/Desktop
vốn không cần khai báo gì. `ACCESS_NETWORK_STATE` cho phép app kiểm tra
tình trạng kết nối hiện tại (dùng ở tính năng phát hiện lỗi mạng, mục 7).

```xml
<uses-feature android:glEsVersion="0x00020000" android:required="true" />
```
Khai báo app cần OpenGL ES **2.0 trở lên** (`0x00020000` = phiên bản
2.0 mã hex) — Google Play (nếu sau này phát hành ở đó) sẽ tự ẩn app khỏi
danh sách với máy không hỗ trợ GLES 2.0 (rất hiếm ở thời điểm hiện tại,
nhưng là chuẩn khai báo bắt buộc cho mọi app libGDX).

```xml
<application
    android:allowBackup="true"
    android:fullBackupContent="false"
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name"
    android:isGame="true"
    android:appCategory="game"
    android:theme="@style/GdxTheme">
```
- `allowBackup="true"` + `fullBackupContent="false"`: cho phép Android tự
  backup dữ liệu app (Auto Backup) khi user đổi máy, NHƯNG
  `fullBackupContent="false"` tắt việc backup **toàn bộ file** app — chỉ
  nên bật full backup có kiểm soát (VD chỉ backup file setting, KHÔNG
  backup token đăng nhập/cache nhạy cảm) — hiện đang tắt hẳn nội dung chi
  tiết, an toàn nhưng cũng đồng nghĩa user đổi máy sẽ không tự khôi phục
  được gì, phải đăng nhập lại từ đầu (chấp nhận được vì có hệ thống tài
  khoản qua backend, không phụ thuộc dữ liệu local).
- `isGame="true"` + `appCategory="game"`: metadata cho Play Store phân
  loại app (không ảnh hưởng gì nếu chưa publish lên đó).
- `theme="@style/GdxTheme"`: xem mục 3.5.

```xml
<activity
    android:name="com.dang.dragonboy.android.AndroidLauncher"
    android:exported="true"
    android:configChanges="keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize|navigation|uiMode"
    android:label="@string/app_name"
    android:screenOrientation="sensorLandscape"
    android:windowSoftInputMode="adjustResize">
```
- `exported="true"`: **bắt buộc** từ Android 12 (API 31) trở lên cho bất
  kỳ activity nào có `intent-filter` (activity chính có `MAIN`/`LAUNCHER`
  bên dưới) — thiếu dòng này app sẽ **crash ngay lúc cài đặt/build** trên
  SDK mới, đây là 1 trong những khác biệt hay gặp nhất so với hướng dẫn
  Android cũ trên mạng.
- `configChanges="..."`: đây là chi tiết **quan trọng cho 1 game real-time
  loop**. Mặc định, khi thiết bị xoay màn hình, bàn phím ảo bật/tắt, hay
  đổi kích thước cửa sổ (chia đôi màn hình...), Android sẽ **huỷ và tạo
  lại Activity từ đầu** — với 1 app thường không sao (state được lưu/khôi
  phục qua `onSaveInstanceState`), nhưng với 1 game libGDX đang giữ GL
  context, kết nối WebSocket, state nhân vật đang chơi dở... việc bị tái
  tạo Activity giữa chừng gần như chắc chắn gây crash hoặc mất kết nối.
  Khai báo đầy đủ danh sách này để Android **KHÔNG tái tạo Activity**
  trong các trường hợp đó — thay vào đó chỉ gọi `onConfigurationChanged()`
  (mà `AndroidApplication` của libGDX tự xử lý ổn thoả), giữ nguyên toàn
  bộ state đang chạy.
- `screenOrientation="sensorLandscape"`: khoá màn hình luôn nằm ngang
  (đúng thiết kế UI cố định `1020×610` — xem mục 2), nhưng vẫn cho phép
  xoay giữa 2 chiều ngang (bình thường/lộn ngược) theo cảm biến, không
  cứng nhắc chỉ 1 chiều duy nhất — thoải mái hơn cho người cầm máy theo
  hướng nào cũng được miễn là nằm ngang.
- `windowSoftInputMode="adjustResize"`: đã giải thích ở mục 3.1/4.5 — bàn
  phím ảo hiện lên sẽ làm CO LẠI vùng hiển thị app thay vì đè lên trên.

### 3.4. `AndroidLauncher.java` — entry point

```java
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PlatformBridge.googleOAuth = new AndroidGoogleOAuth(this);

        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useImmersiveMode = true;
        initialize(new Main(), configuration);
    }
}
```
Đây là **toàn bộ** phần code Android phải viết để khởi động game — vỏn
vẹn ~10 dòng thật sự có logic, minh chứng rõ nhất cho việc "gần như mọi
thứ nằm ở `core/`, module platform chỉ là lớp vỏ mỏng":
- `extends AndroidApplication`: class nền của libGDX, tự động lo việc tạo
  `Activity` chuẩn Android, tạo GL surface view, vòng lặp render, forward
  input (chạm/nút back...) vào hệ thống `InputProcessor` của libGDX mà
  `core/` (`ThaoTac.java`) đang dùng — `core/` không cần biết gì về
  `Activity`/`View`/Android lifecycle cả.
- `PlatformBridge.googleOAuth = new AndroidGoogleOAuth(this)`: đăng ký
  implementation Google Sign-In CỦA Android **TRƯỚC KHI** gọi
  `initialize(new Main(), ...)` — thứ tự này quan trọng vì `Main`/các màn
  hình bên trong có thể cần gọi `PlatformBridge.googleOAuth.login(...)`
  bất cứ lúc nào sau khi khởi tạo xong, nếu đăng ký sau sẽ có khoảng thời
  gian field này còn `null`, gọi vào sẽ `NullPointerException` (chi tiết
  kiến trúc trừu tượng này ở mục 5.1).
- `configuration.useImmersiveMode = true`: ẩn thanh trạng thái/thanh điều
  hướng hệ thống (immersive fullscreen) — chuẩn UX cho game mobile, tránh
  user vô tình chạm phải nút Home/Back hệ thống nằm sát viền màn hình.
- `initialize(new Main(), configuration)`: `Main` chính là **đúng class**
  `core/src/main/java/com/dang/dragonboy/he_thong/Main.java` mà
  `Lwjgl3Launcher` cũng dùng — không có phiên bản `Main` riêng cho
  Android, đây là điểm mấu chốt của kiến trúc multi-module libGDX.

### 3.5. `res/` — tài nguyên Android

- `res/values/strings.xml`: chỉ có 1 string `app_name` — tên hiển thị
  dưới icon app trên màn hình chính điện thoại, được `AndroidManifest.xml`
  tham chiếu qua `android:label="@string/app_name"` (mục 3.3). Cách làm
  chuẩn Android: text hiển thị luôn tách ra `strings.xml` thay vì hard-code
  trong manifest, để sau này dễ đa ngôn ngữ hoá (`values-en/strings.xml`...)
  nếu cần — hiện chỉ có 1 bản tiếng Việt/không dấu.
- `res/values/styles.xml`: định nghĩa `GdxTheme` kế thừa
  `android:Theme.NoTitleBar.Fullscreen` — bỏ thanh tiêu đề mặc định của
  Activity VÀ cho phép fullscreen, đây là theme chuẩn khuyến nghị cho mọi
  app libGDX (game tự vẽ toàn bộ UI bằng OpenGL, không cần bất kỳ
  UI-chrome nào của hệ điều hành).
- `res/mipmap-{mdpi,hdpi,xhdpi,xxhdpi,xxxhdpi}/ic_launcher.png`: icon app
  hiển thị ở màn hình chính, có **5 bản với độ phân giải khác nhau**
  (48/72/96/144/192px) — Android tự chọn đúng bản khớp mật độ điểm ảnh
  (DPI) của từng thiết bị, không cần code gì thêm, chỉ cần đặt đúng file
  vào đúng thư mục `mipmap-<density>`.

### 3.6. `libs/<abi>/*.so` — thư viện native

```
libs/armeabi-v7a/{libgdx.so, libgdx-box2d.so, libgdx-freetype.so}
libs/arm64-v8a/{...}
libs/x86_64/{...}
libs/x86/{...}
```
**Không phải file tự tay thêm** — đây là sản phẩm của task
`copyAndroidNatives` (mục 3.2), tự động bóc ra từ dependency
`gdx-platform`/`gdx-box2d-platform`/`gdx-freetype-platform` mỗi lần build.
Có 4 bộ vì Android chạy trên nhiều kiến trúc CPU khác nhau:
- `armeabi-v7a`: ARM 32-bit — máy Android đời cũ.
- `arm64-v8a`: ARM 64-bit — **hầu hết điện thoại Android hiện đại** dùng
  kiến trúc này.
- `x86`/`x86_64`: Intel/AMD — chủ yếu dùng cho **máy ảo/emulator** khi
  test trên máy tính, hiếm gặp trên thiết bị thật (trừ vài Chromebook).

Đóng gói đủ cả 4 giúp APK chạy được trên mọi máy/emulator mà không cần
biết trước cấu hình máy user, đánh đổi là file APK **to hơn** vì chứa dư
thừa 3/4 số kiến trúc không dùng tới trên bất kỳ máy cụ thể nào. Tối ưu
sau này (nếu cần giảm dung lượng tải về): build theo **Android App
Bundle** (`.aab`, publish qua Play Store, Google tự tách đúng ABI cho
từng máy) hoặc cấu hình `splits { abi { ... } }` để build ra nhiều APK
riêng theo từng ABI.

### 3.7. `proguard-rules.pro`

```
-keep class com.badlogic.gdx.** { *; }
-keep class com.dang.dragonboy.** { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.protobuf.** { *; }
-keep class org.java_websocket.** { *; }
-keep class io.socket.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
```
File này CHUẨN BỊ SẴN cho việc bật `minifyEnabled true` trong tương lai
(hiện đang `false`, xem mục 3.2 — nghĩa là các rule này **chưa thực sự
chạy** lúc build hiện tại, nhưng để sẵn cho lần bật minify sau này khỏi
phải dò lỗi lại từ đầu). R8/ProGuard (công cụ rút gọn + làm rối code) mặc
định sẽ **xoá/đổi tên** bất kỳ class/field nào nó thấy không có chỗ nào
gọi tới trực tiếp trong code — nhưng nhiều thư viện dùng **reflection**
(Gson đọc field qua tên lúc parse JSON, Protobuf sinh code động, các thư
viện WebSocket dùng annotation...) mà R8 không "nhìn thấy" được các lệnh
gọi kiểu đó, nên sẽ xoá nhầm — làm app **crash chỉ ở bản release có bật
minify**, chạy bình thường ở bản debug (rất khó debug nếu không biết
trước nguyên nhân). Mỗi dòng `-keep class X.** { *; }` là "giữ nguyên
nguyên vẹn, đừng đụng vào" cho đúng những thư viện/package có dùng
reflection kiểu này, `-dontwarn` để không dừng build vì cảnh báo thiếu
class không liên quan (thường do 1 số optional dependency không có mặt).

### 3.8. Chỗ khai báo ở gốc project (`settings.gradle`, `build.gradle`, `gradle.properties`)

- `settings.gradle`: `include 'lwjgl3', 'html', 'core', 'android'` — thiếu
  `'android'` trong danh sách này thì Gradle **hoàn toàn không biết** thư
  mục `android/` là 1 module, mọi lệnh `:android:...` sẽ báo lỗi "task
  not found".
- Root `build.gradle`, khối `buildscript.dependencies`:
  `classpath "com.android.tools.build:gradle:$androidPluginVersion"` —
  nạp chính Android Gradle Plugin (phiên bản khai ở `gradle.properties`,
  `androidPluginVersion=8.6.0`) để Gradle hiểu được cú pháp
  `apply plugin: 'com.android.application'` dùng trong `android/build.gradle`.
- Root `build.gradle` có 1 dòng đáng chú ý:
  `configure(subprojects - project(':android')) { apply plugin: 'java-library'; ... }`
  — cố tình **LOẠI TRỪ** `:android` khỏi khối cấu hình chung cho các
  module Java thường (`core`/`lwjgl3`/`html`), vì Android không dùng plugin
  `java-library` mà dùng `com.android.application` riêng (mục 3.2) với
  cách khai `sourceCompatibility` khác (`compileOptions` bên trong khối
  `android {}`, không phải `sourceCompatibility = 17` kiểu Java thường).
  → **Bài học hay**: nếu tạo Android module mà quên trừ nó ra khỏi 1 khối
  cấu hình `java-library` áp dụng chung như vậy, build sẽ lỗi xung đột
  plugin ngay lập tức.
- `google()` đã có sẵn trong cả `buildscript.repositories` VÀ
  `subprojects.repositories` của root `build.gradle` **từ trước khi có
  module Android** — dấu hiệu cho thấy template gdx-liftoff gốc của
  project này vốn đã "chừa sẵn chỗ" phòng khi thêm Android sau, nên bước
  này trong thực tế **không cần tự thêm mới**, chỉ cần xác nhận lại là đủ.
- `gradle.properties`: `android.useAndroidX=true` — bắt buộc vì mọi thư
  viện Android hiện đại (bao gồm Credential Manager ở mục 5) đều dựa trên
  package `androidx.*`, thiếu dòng này build sẽ báo lỗi ngay khi resolve
  dependency AndroidX.

### Vòng lặp dev nhanh hơn — không cần build APK mỗi lần

- `./gradlew :lwjgl3:run` để chạy desktop — libGDX backend desktop coi
  **click chuột là 1 touch event**, nên phần lớn logic input/UI mobile có
  thể prototype và test ngay trên desktop trước, chỉ cần build APK để xác
  nhận lần cuối trên thiết bị thật.
- `./gradlew :android:installDebug` cài trực tiếp qua `adb` nếu có máy nối
  USB — nhanh hơn nhiều so với build APK rồi upload lên cloud storage để
  tải về máy.
- Lấy SHA-1 fingerprint của debug keystore (cần cho Google Sign-In, xem
  mục 5) bằng `keytool -list -v -keystore ~/.android/debug.keystore`.

## 4. Các lỗi "giả định desktop" đã sửa trong core/

Đây là phần tốn công nhất — không phải thêm code Android, mà là audit lại
`core/` để tìm những chỗ VÔ TÌNH chỉ đúng trên desktop.

### 4.1. Toạ độ chạm/chuột — sai lệch hoàn toàn trên màn hình khác tỉ lệ PC

`ThaoTac.java` (input handler chính, implements `InputAdapter`) nhận
`touchDown/touchDragged/touchUp(screenX, screenY, ...)` — libGDX trả về
**pixel thật của thiết bị**, nhưng toàn bộ hit-test bên dưới lại so sánh
với các hằng số theo hệ toạ độ ảo 1020×610. Trên PC, 2 hệ này gần như trùng
nhau (viewport ~1:1) nên bug không lộ ra; trên điện thoại độ phân giải
1080×2400 chẳng hạn, lệch hoàn toàn.

**Fix**: mọi nơi nhận toạ độ chạm phải `unproject` qua viewport trước khi
so khớp với hằng số ảo:
```java
Vector2 diem = camera.uiViewport.unproject(new Vector2(screenX, screenY));
int vx = (int) diem.x;
int vy = (int) diem.y;
```
Lưu ý có **2 viewport khác nhau** trong game này: `uiViewport` (cho toạ độ
HUD/click) và `viewport` (world — cho toạ độ bản đồ/NPC), phải dùng đúng
loại tương ứng, không được lẫn.

### 4.2. Lưu trữ cục bộ

`LocalStorage.java` (lưu token đăng nhập, danh sách nhạc đã tải...) trước
đó dùng thẳng `java.io.File` với đường dẫn kiểu `System.getProperty("user.home")`
— hoạt động trên desktop nhưng **Android không cho phép** truy cập
filesystem kiểu này ngoài sandbox riêng của app. Thay bằng
`Gdx.files.local(path)` — libGDX tự map đúng theo platform: desktop trỏ về
thư mục làm việc, Android trỏ về `context.getFilesDir()` (bộ nhớ riêng của
app, ghi được, không cần xin quyền storage).

### 4.3. `glScissor` không đi qua ma trận chiếu

`glScissor` (dùng để clip vùng vẽ, ví dụ khung chat/khung cuộn item) nhận
toạ độ **pixel thật của framebuffer**, khác hẳn với `batch.draw()`/
`shapeRenderer` vốn đi qua ma trận chiếu (`setProjectionMatrix`). Gọi thẳng
`Gdx.gl.glScissor(x, y, w, h)` với số toạ độ ảo sẽ ra sai hoàn toàn trên
màn hình có scale khác 1:1.

**Fix**: thêm 1 helper dùng chung `QuanLyCamera.scissor(vx, vy, vw, vh)`,
tự quy đổi từ toạ độ ảo sang pixel thật theo scale hiện tại của
`uiViewport`, rồi mới gọi `glScissor` — áp dụng lại ở ~9 file đang dùng
scissor trực tiếp trước đó.

### 4.4. Chữ FreeType bị mờ trên điện thoại

Font sinh ra từ `FreeTypeFontGenerator` mặc định dùng
`TextureFilter.Nearest` — ổn khi vẽ ở đúng scale 1:1 (PC), nhưng khi
canvas ảo bị phóng to lên để vừa màn hình điện thoại (`viewport` scale >
1x), `Nearest` làm chữ vỡ nét/răng cưa rõ rệt. Đổi `minFilter`/`magFilter`
sang `TextureFilter.Linear` khi generate font — chữ mượt hẳn trên di động
mà không ảnh hưởng gì trên PC.

### 4.5. Bàn phím ảo Android — Enter phải thoát được vòng lặp

Những ô nhập liệu (giftcode, chat...) khi bấm Enter trên bàn phím ảo
Android gửi ký tự `'\n'` (không giống hành vi phím Enter vật lý trên PC) —
nếu không xử lý riêng, người dùng bị kẹt không thoát được bàn phím ảo.
Cộng với `windowSoftInputMode="adjustResize"` ở mục 3, xử lý `'\n'`/`'\r'`
trong `keyTyped()` như 1 hành động "Gửi/Enter" tường minh cho từng ô nhập.

## 5. Google Sign-In trên Android — khác hoàn toàn Desktop

Đây là phần **kiến trúc phải thiết kế lại**, không chỉ sửa lỗi vặt.

- **Desktop**: flow cũ là mở trình duyệt cho user login Google, redirect về
  `localhost` để lấy code — chỉ hợp lệ với OAuth Client Type **Desktop**.
- **Android**: thử dùng lại đúng flow browser-redirect với 1 OAuth Client
  Type **Android** (package name + SHA-1, không có client secret) — Google
  **chặn thẳng** với `Error 400: invalid_request`, vì chính sách Google
  không cho phép kiểu flow này với Android Client Type.
- **Giải pháp đúng**: dùng `androidx.credentials` **Credential Manager**
  (`CredentialManager`, `GetCredentialRequest`,
  `GetSignInWithGoogleOption`, `GoogleIdTokenCredential`) — đây là API
  chính thức Google khuyến nghị cho Android hiện tại.
- **Điểm dễ nhầm nhất**: `GetSignInWithGoogleOption` yêu cầu 1
  `serverClientId` — tưởng phải là Android Client ID, nhưng **thực ra phải
  là Web Client ID** (1 OAuth Client Type Web riêng, tạo thêm chỉ để dùng
  làm audience). Đây là quy ước bắt buộc từ phía Google, token trả về sẽ
  có `audience` là Web Client ID chứ không phải Android Client ID.
- **Backend phải sửa theo**: vì desktop và Android gửi token có audience
  KHÁC NHAU (Desktop Client ID vs Web Client ID) nhưng cùng gửi
  `x-platform: game`, hàm verify token phía backend (NestJS +
  `google-auth-library`) phải chấp nhận **mảng nhiều audience hợp lệ**
  thay vì 1 string cố định:
  ```ts
  audience: [process.env.GOOGLE_GAME_CLIENT_ID, process.env.GOOGLE_GAME_CLIENT_ID_ANDROID]
  ```

### 5.1. Kiến trúc trừu tượng theo platform

3 file nhỏ trong `core/` tạo thành 1 mẫu thiết kế kiểu **Strategy/Bridge
pattern** — `core/` định nghĩa "cần làm gì" qua interface, còn "làm như
thế nào" do từng platform tự quyết, đăng ký vào lúc khởi động:

```java
// core/.../network/GoogleLoginCallback.java — kết quả trả về, không quan tâm platform nào gọi
public interface GoogleLoginCallback {
    void onSuccess(String idToken);
    void onFailure(String error);
}

// core/.../network/GoogleOAuthProvider.java — "hợp đồng" mà MỌI platform phải tuân theo
public interface GoogleOAuthProvider {
    void login(GoogleLoginCallback callback);
}

// core/.../he_thong/PlatformBridge.java — "hộp thư" để launcher nhét implementation vào
public class PlatformBridge {
    public static GoogleOAuthProvider googleOAuth;
}
```

Luồng hoạt động:
1. Lúc khởi động, MỖI launcher tự gán 1 implementation cụ thể của
   `GoogleOAuthProvider` vào `PlatformBridge.googleOAuth`:
   - `Lwjgl3Launcher` (desktop) → `PlatformBridge.googleOAuth = new GoogleOAuth2Desktop();`
   - `AndroidLauncher` (mục 3.4) → `PlatformBridge.googleOAuth = new AndroidGoogleOAuth(this);`
2. Bất kỳ đâu trong `core/` (VD màn hình đăng nhập) cần login Google chỉ
   gọi `PlatformBridge.googleOAuth.login(callback)` — **không hề biết** và
   **không cần biết** đang chạy trên platform nào, không có `if
   (Gdx.app.getType() == Android) { ... } else { ... }` rải rác khắp nơi.
3. Muốn thêm platform thứ 3 (VD iOS sau này) chỉ cần viết 1 class mới
   implement `GoogleOAuthProvider`, gán vào `PlatformBridge.googleOAuth`
   ở launcher iOS tương ứng — không sửa 1 dòng nào trong `core/`.

Đây là mẫu hình chung nên áp dụng cho **bất kỳ chức năng nào chỉ platform
cụ thể mới làm được** (chia sẻ file, thông báo push, mua hàng trong app...)
khi port 1 project libGDX sang nhiều platform — không riêng gì Google
Sign-In.

### 5.2. `AndroidGoogleOAuth.java` — chi tiết từng bước

```java
public class AndroidGoogleOAuth implements GoogleOAuthProvider {
    private final Activity activity;
    private final CredentialManager credentialManager;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public AndroidGoogleOAuth(Activity activity) {
        this.activity = activity;
        this.credentialManager = CredentialManager.create(activity);
    }

    @Override
    public void login(GoogleLoginCallback callback) {
        String webClientId = AppConfig.get("google.client.id.web"); // PHẢI là Client ID loại "Web", không phải "Android"

        String nonce = UUID.randomUUID().toString().replace("-", "");
        GetSignInWithGoogleOption option = new GetSignInWithGoogleOption.Builder(webClientId)
            .setNonce(nonce)
            .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build();

        credentialManager.getCredentialAsync(activity, request, new CancellationSignal(), executor,
            new CredentialManagerCallback<>() {
                @Override public void onResult(GetCredentialResponse response) {
                    String idToken = trichIdToken(response);
                    Gdx.app.postRunnable(() -> callback.onSuccess(idToken));
                }
                @Override public void onError(GetCredentialException e) {
                    Gdx.app.postRunnable(() -> callback.onFailure(e.getMessage()));
                }
            });
    }
}
```
- `CredentialManager.create(activity)`: API hệ thống của Android (từ thư
  viện `androidx.credentials`) — không phải tự implement OAuth thủ công
  như Desktop, mà **giao hẳn** việc xác thực cho Google Play Services xử
  lý — SDK này tự biết app nào đang gọi nó qua **package name + chữ ký ký
  APK** (không cần redirect URI, không cần mở trình duyệt riêng, không có
  bước "dán code thủ công" nào cả).
- `nonce`: chuỗi ngẫu nhiên gửi kèm request, Google sẽ nhét lại y hệt vào
  trong `id_token` trả về — dùng để chống **replay attack** (kẻ tấn công
  bắt được 1 `id_token` cũ rồi gửi lại giả làm request mới); phía server
  verify token nên kiểm tra nonce khớp (hiện dự án còn để `TODO` phần này
  bên Desktop, xem mục 5.3 — nên bổ sung cho cả 2 platform).
- `getCredentialAsync(...)`: gọi **bất đồng bộ**, kết quả trả về qua
  callback trên **thread riêng của Credential Manager**, không phải thread
  chính/GL thread của libGDX — đây là lý do phải bọc `callback.onSuccess/
  onFailure` bên trong `Gdx.app.postRunnable(() -> ...)`: mọi thao tác
  đụng tới object của libGDX (đổi màn hình, vẽ UI...) **bắt buộc** phải
  chạy trên GL thread, gọi trực tiếp từ thread khác sẽ gây lỗi khó debug
  (đôi khi im lặng không hiện gì, đôi khi crash ngẫu nhiên).
- `trichIdToken(response)`: `CredentialManager` trả về 1 `Credential`
  dạng tổng quát (`CustomCredential`) — phải kiểm tra đúng loại
  (`GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL`) rồi mới parse
  ra được chuỗi `idToken` thật sự để gửi lên backend verify.

### 5.3. `GoogleOAuth2Desktop.java` — đối chiếu để thấy vì sao 2 flow khác hẳn nhau

Ngược lại hoàn toàn với Android, Desktop **phải tự làm mọi bước** vì
không có sẵn SDK hệ thống nào lo hộ:

1. Mở **trình duyệt hệ thống thật** (`Desktop.getDesktop().browse(uri)`,
   có fallback gọi lệnh OS `cmd start`/`open`/`xdg-open` nếu API chuẩn
   không dùng được) tới URL đăng nhập Google
   (`https://accounts.google.com/o/oauth2/v2/auth?...`).
2. Tự mở **1 `ServerSocket` cục bộ ở port ngẫu nhiên** (`new ServerSocket(0)`)
   làm `redirect_uri` (`http://127.0.0.1:<port>/callback`) — Google sau khi
   user đăng nhập xong sẽ redirect trình duyệt tới đúng URL cục bộ này kèm
   theo `?code=...`.
3. `serverSocket.accept()` **block (chặn) luồng hiện tại** chờ đúng 1 kết
   nối tới (chính là request redirect từ trình duyệt) — có `setSoTimeout(180000)`
   (3 phút) để không treo vĩnh viễn nếu user bỏ dở không đăng nhập.
4. Tự đọc thô request HTTP đến (`BufferedReader` đọc từng dòng, tự parse
   query string bằng tay — không dùng thư viện HTTP server nào), lấy ra
   `code`.
5. Trả về 1 trang HTML đơn giản báo thành công/lỗi cho trình duyệt hiển
   thị (để user biết có thể đóng tab), rồi đóng socket.
6. **Đổi `code` lấy `id_token` thật** bằng 1 request POST server-to-server
   riêng tới `https://oauth2.googleapis.com/token`, kèm `client_secret` —
   Desktop OAuth Client Type **được phép** dùng `client_secret` (khác
   Android/mobile — SDK Google coi app desktop là "được tin tưởng" hơn để
   giữ bí mật này, dù về lý thuyết vẫn có thể bị decompile lấy ra, đây là
   rủi ro Google ghi nhận và chấp nhận cho loại Client này).

**So sánh trực quan**:

| | Desktop (`GoogleOAuth2Desktop`) | Android (`AndroidGoogleOAuth`) |
|---|---|---|
| Cách xác thực app | `client_secret` gửi kèm request | Package name + chữ ký APK, do hệ thống tự xác minh |
| Nơi hiện UI đăng nhập | Trình duyệt hệ thống (tab mới) | Google Play Services (dialog hệ thống, không rời app) |
| Cách nhận kết quả | Tự mở server cục bộ chờ redirect | Callback bất đồng bộ từ SDK |
| Cần networking tự viết | Có (ServerSocket, parse HTTP thô) | Không (SDK lo hết) |
| OAuth Client Type dùng | Desktop | Web (dù chạy trên máy Android — xem lưu ý audience ở đầu mục 5) |

## 6. Bug thực tế phát hiện khi test máy thật

Nhiều bug **chỉ lộ ra khi cầm điện thoại thật bấm**, không thấy được khi
test bằng chuột trên desktop dù cùng 1 codebase:

- **Popup item lệch vị trí thật đang hiển thị**: popup bị clamp lại trong
  màn hình (không cho tràn ra ngoài) lúc vẽ, nhưng phần xử lý click vẫn
  dùng toạ độ TRƯỚC KHI clamp → bấm đúng chỗ nhìn thấy trên màn hình lại
  không trúng. Fix: ghi lại toạ độ/kích thước ĐÃ clamp vào field dùng
  chung, để phần click đọc đúng giá trị cuối cùng đã vẽ ra — chú ý reset
  biến tích luỹ kích thước về 0 mỗi frame, nếu không sẽ bị cộng dồn tăng
  vô hạn qua từng frame (bug tự gây ra rồi tự fix ngay trong cùng 1 lượt
  sửa).
- **Crash khi trang bị/tháo đồ cho đệ tử (pet)**: code copy-paste ở ~10 vị
  trí đọc nhầm `duLieuNguoiChoi.getHanhTrangDangMac()` (đồ của người chơi)
  thay vì `duLieuNguoiChoi.deTu.getHanhTrangDangMac()` (đồ của pet) — bug
  logic thuần tuý, không liên quan gì Android, nhưng chỉ bị phát hiện vì
  user test kỹ trên mobile.
- **Tự sửa nhầm rồi phải revert**: từng thêm bounds-check
  (`Math.max/min`) vào logic click hành trang vì tưởng đó là nguyên nhân
  crash — sau khi user xác nhận logic click gốc vẫn đúng, phải revert lại
  y hệt bản gốc, vì nguyên nhân crash thật sự nằm ở mục trên (đọc nhầm
  inventory của pet).
- **`ExtendViewport` thử lần đầu**: xem chi tiết mục 10.

## 7. Tính năng: phát hiện lỗi mạng (no-internet)

Vấn đề: `ApiService.login()`, `register()`, `verifyOTP()`, `getProfile()`,
`loginWithGoogle()` đều trả `null`/`false` giống hệt nhau cho **2 tình
huống khác nhau**: sai tài khoản/mật khẩu (server từ chối hợp lệ) và mất
kết nối mạng (không gọi được tới server) — người dùng không phân biệt
được, tưởng nhầm mật khẩu trong khi thực ra điện thoại không có mạng.

**Giải pháp** (`ApiService.java`):
```java
public static volatile boolean loiMangGanNhat = false;

private static boolean laLoiMangInternet(Exception e) {
    return e instanceof java.net.UnknownHostException
        || e instanceof java.net.ConnectException
        || e instanceof java.net.SocketTimeoutException
        || e instanceof java.net.NoRouteToHostException;
}
```
Reset `loiMangGanNhat = false` ở đầu mỗi hàm gọi API, set lại trong khối
`catch` dựa theo loại exception. `ManHinhDoiTaiKhoan.java` (màn đăng
nhập/đăng ký) kiểm tra cờ này để hiện đúng thông báo "Mất kết nối mạng,
vui lòng kiểm tra Internet rồi thử lại." thay vì thông báo sai chung
chung. `Main.java` cũng dùng cờ này để phân biệt "chưa có mạng lúc mở app
nên chưa kiểm tra được token" với "token thật sự hết hạn" trong log khởi
động (không hiện UI được ở bước này vì chưa vào màn hình nào).

## 8. Tính năng: cụm nút điều khiển ảo cho mobile

Màn cảm ứng không có phím mũi tên/số 1-5 như PC, nên cần 1 bộ UI thao tác
hoàn toàn mới cho mobile, bật/tắt được qua setting (mặc định bật trên
Android, tắt trên PC — vẫn bật thử được trên PC để dev/test).

### 8.1. Joystick ảo (tự vẽ, không cần asset)

Không có sẵn hình ảnh cho phần di chuyển, nên vẽ trực tiếp bằng
`ShapeRenderer`: 1 vòng tròn ngoài cố định (viền, hơi mờ) + 1 vòng tròn
trong di chuyển theo ngón tay kéo, kẹp trong bán kính vòng ngoài.

- **Toạ độ**: `JOYSTICK_CX/CY` là vị trí góc trái dưới, tuyệt đối từ gốc
  (0,0) — không phụ thuộc kích thước màn hình vì gốc luôn cố định ở góc.
- **Theo dõi bằng `pointer id`** (không phải chỉ 1 boolean) — bắt buộc để
  không lẫn với ngón tay khác đang thao tác thứ khác cùng lúc (kéo
  camera, kéo item trong hành trang...). Khi 1 ngón tay chạm trong vòng
  ngoài, lưu `joystickPointer = pointer`; `touchDragged`/`touchUp` chỉ xử
  lý joystick khi đúng `pointer == joystickPointer`.
- **Suy hướng di chuyển từ độ lệch tâm**: kéo ngang → set
  `phimTraiDangGiu`/`phimPhaiDangGiu` (tái dùng nguyên cơ chế giữ-phím rời
  rạc có sẵn từ bản PC, không viết lại logic di chuyển), kéo lên vượt
  ngưỡng → nhảy. Nhả tay thì tự thả hết các hướng đang giữ.
- **Phải loại trừ khỏi kéo camera**: mặc định MỌI lượt chạm đều bắt đầu 1
  phiên "kéo camera tiềm năng" (chỉ thực sự pan nếu di chuyển đủ xa) — nếu
  không loại trừ riêng vùng joystick, kéo joystick sẽ đồng thời pan camera
  theo, phải kiểm tra "có đang chạm trong vùng joystick không" trước khi
  gọi `camera.batDauKeoCamera()`.
- **Bug đã gặp — "bấm nút phải bấm 2 lần mới ăn"**: nếu vì lý do gì đó
  (app bị gián đoạn giữa lúc đang kéo...) mà sự kiện `touchUp` của lần
  giữ joystick trước bị mất, biến `joystickPointer` bị "kẹt" không reset
  về -1. Lần chạm tiếp theo tái sử dụng đúng pointer đó sẽ bị nhầm là vẫn
  thuộc về joystick cũ, bị nuốt mất ở `touchUp`/`touchDragged` — nhưng
  cũng chính lần đó lại tự dọn state, nên lần bấm THỨ HAI mới hoạt động
  bình thường. **Fix**: tự dọn dẹp ngay ở đầu `touchDown` — 1 pointer
  không thể có 2 lần `touchDown` liên tiếp mà thiếu `touchUp` ở giữa, nên
  thấy `touchDown` mới trên đúng pointer đang giữ joystick nghĩa là phiên
  cũ chắc chắn đã kết thúc ngoài ý muốn, dọn state ngay tại đó thay vì đợi
  `touchUp`.

### 8.2. Nút tấn công / đổi mục tiêu / đậu thần — bố cục hình tam giác

Layout không phải hàng ngang mà là tam giác: **tấn công** làm tâm (to
nhất, gần sát mép phải), **đổi mục tiêu** phía trên và lệch phải 1 chút,
**đậu thần** (thu nhỏ từ 75 xuống 55 khi bật cụm nút, nhường chỗ) nằm bên
trái và thấp hơn tấn công 1 chút. Toạ độ dùng chung 1 nguồn duy nhất
(field `public static final` trong `VeHUD`) cho cả chỗ vẽ (`VeHUD`) lẫn
chỗ bắt chạm (`HUDClickHandler`, `ThaoTac`) — bài học từ bug ở mục 6: vẽ
và bắt-chạm PHẢI cùng đọc 1 nguồn, không được duplicate công thức.

**Hiệu ứng bấm "sống động"**: chỉ đổi texture sang bản `_click` khi bấm
KHÔNG đủ — nếu spam bấm liên tục, ảnh cứ đứng yên ở trạng thái click trông
như bị đơ (không có animation nào phản hồi việc bấm dồn dập). Thêm hiệu
ứng thu nhỏ ngay khi bấm rồi phóng to dần về đúng kích thước gốc trong lúc
timer đếm ngược (tâm nút giữ nguyên, bù trừ offset X/Y theo phần chênh
lệch kích thước) — mỗi lần spam bấm timer reset lại từ đầu nên nút luôn
"nảy" theo từng lượt bấm.

**Đổi mục tiêu NPC theo khoảng cách — bug tinh vi**: lúc đầu chỉ set thẳng
`npc.dangClickNpc = true` cho NPC được chọn — KHÔNG có tác dụng gì cả!
Nguyên nhân: mỗi frame, màn hình map tự gọi lại
`checkClick(nhanVat.x_check_npc, nhanVat.y_check_npc)` cho từng NPC để
XÁC NHẬN LẠI ai đang được chọn, dựa theo **điểm chạm world lần cuối**
(chỉ cập nhật khi tap-để-di-chuyển trên mặt đất). Set thẳng flag mà không
đồng bộ điểm chạm này thì ngay frame kế tiếp, vòng lặp `checkClick()` tự
reset flag về `false` vì điểm chạm cũ không nằm trên NPC vừa chọn — chọn
xong bị xoá gần như ngay lập tức. **Fix đúng**: khi chọn 1 NPC mới, cũng
đồng bộ luôn `nhanVat.x_check_npc`/`y_check_npc` về đúng toạ độ NPC đó, để
vòng lặp mỗi frame tự xác nhận lại đúng NPC này thay vì xoá chọn. Việc
sắp xếp theo khoảng cách gần→xa dùng **bản sao** của danh sách NPC gốc của
map (không sort tại chỗ) — tránh làm xáo trộn thứ tự danh sách gốc mà các
hệ thống khác (vẽ, va chạm) có thể đang phụ thuộc vào.

**Tấn công tự động đi tới/tương tác NPC đang chọn**: nếu đang có NPC được
chọn, bấm tấn công sẽ tự đi tới chỗ NPC đó (tái dùng đúng
`nhanVat.setToaDoMucTieu()` — cùng cơ chế click-to-move bằng chuột/chạm)
nếu còn xa, hoặc mở luôn popup/tương tác nếu đã đủ gần — tái dùng thẳng
`Npc.thucHienHanhDongNpc()` có sẵn (đã tự có ngưỡng khoảng cách 60 và xử
lý đúng theo từng loại NPC: người mở popup nói chuyện, cây đậu mở khung
đậu thần, rương đồ/đùi gà tương tự) thay vì viết lại logic tương tác từ
đầu.

### 8.3. Thanh kỹ năng & setting bật/tắt

- Thanh kỹ năng dời ra giữa màn hình theo chiều ngang khi bật cụm nút
  mobile (tránh đè lên joystick bên trái), số thứ tự 1-5 trên từng ô kỹ
  năng chỉ hiện trên PC (đó là phím tắt bàn phím — vô nghĩa trên mobile vì
  không bấm phím được).
- Setting bật/tắt cụm nút nằm trong popup nhân vật → tab "Chức năng" →
  mục con "Tài khoản" (chung chỗ với "Bật/Tắt FPS", "Bật/Tắt hiện chỉ
  số"...) — thêm bằng cách nối thêm 1 dòng vào mảng label có sẵn, thêm 1
  `case` mới vào switch xử lý click tương ứng theo index, và tăng tổng số
  ô lên 1 ở phần tính hit-test theo index (dùng chung 1 cách tính vị trí
  theo scroll offset có sẵn cho cả danh sách).

## 9. Chẩn đoán lỗi nhạc nền + thông báo qua pet

**Triệu chứng**: nhạc nền nghe được trên PC nhưng im re trên Android.

**Cách chẩn đoán** (quy trình có thể tái dùng cho lỗi "chạy trên PC nhưng
lỗi trên mobile" bất kỳ khi nghi ngờ do cache):
1. Đọc code tải nhạc (`MusicManager.java`) — thấy file được cache vĩnh
   viễn vào `Gdx.files.local("app/nhacnen/")`, chỉ tải lại nếu file CHƯA
   tồn tại. Trên PC, thư mục này nằm cạnh project (dùng lại qua nhiều lần
   chạy dev); trên Android là bộ nhớ private của app, **trống hoàn toàn**
   khi mới cài.
2. Kiểm tra thư mục cache thật trên máy dev (`app/nhacnen/`) — thấy 6 file
   mp3 đã có sẵn từ **rất lâu trước đó** → nghi ngờ PC chỉ đang phát từ
   cache cũ, chưa chắc mạng/server hiện tại còn hoạt động.
3. **Test trực tiếp** file URL thật (lấy từ response JSON của API danh
   sách nhạc) bằng `curl` — không cần chạy cả app: nhận HTTP 530, body
   `error code: 1016` → đây là mã lỗi CỦA CLOUDFLARE, nghĩa là request đã
   tới được Cloudflare edge (DNS công khai của domain vẫn resolve ra IP
   Cloudflare bình thường) nhưng Cloudflare không kết nối được tới origin
   nó được cấu hình để forward tới — chưa từng chạm tới origin thật.
4. Xoá thử cache trên PC (`rm app/nhacnen/*.mp3`) — xác nhận PC cũng lỗi y
   hệt khi không còn cache, chứng minh đây không phải bug riêng của
   client/Android.
5. Nguyên nhân gốc: project Supabase (nơi lưu file nhạc qua Storage, có 1
   domain Cloudflare proxy đứng trước) bị **tự động pause do không hoạt
   động** — đây là hành vi chuẩn của gói Free Supabase (không phải hết
   hạn free/bị tính phí), tự pause sau khoảng thời gian không có traffic.
   Bấm "Resume project" trên dashboard Supabase là xong, không cần đổi gì
   ở Cloudflare/DNS.

**Cải thiện code kèm theo**: trước đó lỗi tải/load nhạc chỉ
`Gdx.app.error(...)` ra console — không thấy được gì khi đang cầm máy
thật. Thêm 1 danh sách `loiTaiNhac` trong `MusicManager` ghi lại tên các
bài lỗi, và hiện qua thông báo pet (`setTinNhanPet(...)`) ngay lúc vào
game nếu danh sách này không rỗng — lần sau gặp lại sự cố tương tự (server
lỗi, hết dung lượng, mạng chập chờn...) sẽ thấy ngay trong game trên cả PC
lẫn Android, không cần đoán mò qua log nữa.

## 10. Nỗ lực "fit theo màn hình thật" — đã thử 2 lần, đều revert

Vấn đề còn tồn đọng, **chưa giải quyết**: game hiện dùng `FitViewport`
(canvas ảo cố định 1020×610, letterbox viền đen 2 bên trên màn hình tỉ lệ
khác PC) cho cả world lẫn UI — an toàn nhưng không "đẹp" bằng các game
mobile khác luôn lấp đầy màn hình thật.

### Lần thử thứ nhất (giữa quá trình port Android)

Đổi world camera sang `ExtendViewport` (lấp đầy màn hình rộng) nhưng
**giữ nguyên UI camera là `FitViewport`** cố định 1020×610. Kết quả: nền
bản đồ chạm đúng mép màn hình thật, nhưng toàn bộ popup/nút bấm vẫn đứng
yên ở khung 1020×610 cũ → trông **không đồng nhất** (nền chạm mép, UI thì
lơ lửng giữa màn hình), user báo "quá nhiều lỗi", revert lại `FitViewport`
đồng nhất cho cả 2 camera.

### Lần thử thứ hai (cuối quá trình, có kế hoạch kỹ hơn)

Rút kinh nghiệm lần 1: chỉ đổi ExtendViewport riêng cho world/UI là sai vì
2 camera "lệch pha" nhau — lần này lên kế hoạch đổi **nhất quán cả 2**
cùng lúc, trên 1 nhánh git riêng (`feature/mobile-fit-viewport`, tách từ
`master`, không merge tự động):

- Cả `viewport` (world) và `uiViewport` (UI) đều đổi từ `FitViewport` sang
  `ExtendViewport(1020, 610, camera)` — 1020×610 lúc này chuyển nghĩa
  thành kích thước **sàn tối thiểu**, không còn là canvas cố định.
- `resize()` **bắt buộc** gọi `viewport.update(width, height, true)` —
  tham số `centerCamera=true` (bản 3 tham số, không phải bản 2 tham số
  mặc định) — nếu thiếu, sau khi world được nới rộng, camera vẫn đứng ở
  tâm CŨ thay vì tâm MỚI, làm toạ độ `(0,0)` không còn là góc dưới-trái
  thật của màn hình nữa — phá vỡ đúng tiền đề mà mọi công thức neo-mép
  đang dựa vào.
- Về nguyên lý: với `ExtendViewport` + `centerCamera=true`, toạ độ `(0,0)`
  LUÔN là góc dưới-trái thật của màn hình, và
  `(worldWidth, worldHeight)` — **giá trị ĐANG nới rộng thực tế, đọc qua
  `viewport.getWorldWidth()/getWorldHeight()`, không phải hằng số cố
  định** — là góc trên-phải thật. Suy ra: phần tử neo bên trái/dưới (x=0
  hoặc y=0, hoặc offset nhỏ cố định từ đó) **không cần sửa gì** (đây là lý
  do joystick — neo tuyệt đối từ gốc — không cần đổi công thức); chỉ
  những phần tử neo bên phải/trên (`x = someWidth - offset`) mới cần đổi
  từ đọc hằng số `VIRTUAL_WIDTH`/`VIRTUAL_HEIGHT` sang đọc
  `uiViewport.getWorldWidth()/getHeight()` hiện tại.
- Các hằng số vị trí kiểu `public static final float NUT_ATTACK_X = ...`
  (neo theo mép phải) **không thể tiếp tục là hằng số** vì giá trị không
  còn cố định lúc compile — phải chuyển thành hàm `static` nhận chiều
  rộng hiện tại làm tham số, vẫn giữ đúng nguyên tắc "1 nguồn chung" cho
  cả chỗ vẽ lẫn chỗ bắt chạm, chỉ đổi từ đọc field sang gọi hàm.
- Helper `QuanLyCamera.scissor()` cũng phải sửa vì nó chia tỉ lệ theo
  hằng số `VIRTUAL_WIDTH` cố định — sẽ sai ngay khi world được nới rộng.
- Đã build thành công (`core`, `lwjgl3`, `android:assembleDebug` đều pass)
  trên nhánh riêng, nhưng **không tự verify được bằng mắt** trên 1 cửa sổ
  chạy thật vì cần đăng nhập tài khoản thật (không muốn tự động hoá thao
  tác trên tài khoản người dùng); có 1 lối tắt dev có sẵn trong code để
  bỏ qua đăng nhập (`Main.java`, dòng comment sẵn
  `new ManHinhNhaGohan(this,"admin","traidat","Goku",null)`) nhưng đã cũ,
  hiện bị crash (`NullPointerException` vì thiếu
  `State_Management.getUserResponse()`) — không sửa thêm để tránh dựng
  scaffolding không cần thiết quanh hệ thống tài khoản.
- Sau khi review, user quyết định **"không ổn", revert lại toàn bộ**.
  Nhánh không có commit nào riêng (toàn bộ chỉ là working-tree diff), nên
  revert chỉ đơn giản là discard diff rồi quay lại `master`, xoá nhánh.

**Bài học cho lần thử tiếp theo** (nếu làm lại):
- Hướng kỹ thuật (ExtendViewport nhất quán 2 camera + neo-mép động +
  `centerCamera=true`) đã compile/build sạch, về lý thuyết là đúng hướng
  — vấn đề nằm ở chỗ **chưa verify được bằng mắt trên thiết bị thật**
  trước khi đánh giá. Lần sau nên ưu tiên build APK sớm và test ngay trên
  máy thật (hoặc dựng lại đúng lối tắt dev bỏ qua đăng nhập cho gọn) thay
  vì chỉ dựa vào compile pass.
- Nên làm theo từng phần nhỏ hơn nữa: bắt đầu từ 1-2 phần tử dễ thấy nhất
  (VD: chỉ riêng thanh HP hoặc chỉ riêng nút chat) để user duyệt trước khi
  áp dụng cho toàn bộ cụm HUD gameplay, thay vì làm hết Phase 1 (7 phần
  tử) rồi mới cho xem.
- Cân nhắc kỹ trường hợp map nhỏ (nhà trong nhà — Gohan, Broly): nếu màn
  hình rất rộng khiến viewport mở rộng vượt quá cả kích thước map, logic
  giới hạn camera (`updateMainCamera`) có thể bị đảo ngược khoảng kẹp toạ
  độ — chưa kiểm chứng được trên thiết bị thật.

## 11. Quy trình git đã dùng trong suốt quá trình

- **Không bao giờ** `git add -A`/`git add .` — luôn stage từng file rõ
  ràng theo tên, review kỹ `git status`/`git diff` trước khi commit, tránh
  dính file nhạy cảm không cố ý.
- **Luôn loại trừ** khỏi mọi commit: `app/app.pid` (file runtime, tự sinh
  lúc chạy bản đóng gói), `.vscode/` (config IDE cá nhân),
  `DragonBoy/user_data.json` (chứa access token đăng nhập thật, không
  được commit).
- **Tách commit theo từng mối quan tâm logic riêng biệt** khi khối lượng
  thay đổi lớn/đa dạng — ví dụ 1 lượt làm việc có thể tách thành: 1 commit
  "thêm module Android", 1 commit "trừu tượng hoá Google Sign-In theo
  platform", 1 commit "sửa UI/input/camera cho Android" — dùng
  `git apply --cached <patch chỉ chứa đúng phần hunk cần>` khi 1 file có
  nhiều thay đổi thuộc các mối quan tâm khác nhau cần tách ra commit riêng
  mà không muốn dùng `git add -p` tương tác.
- **Việc rủi ro/thử nghiệm lớn** (như nỗ lực ExtendViewport lần 2 ở mục
  10) làm trên **nhánh riêng tách từ `master`**, không tự merge — nếu
  quyết định không dùng, chỉ cần discard/xoá nhánh mà không ảnh hưởng gì
  tới `master`.
- Chỉ commit khi được yêu cầu rõ ràng; chỉ push khi được yêu cầu rõ ràng.

## 12. Checklist tổng quát cho dự án libGDX desktop-only muốn port Android

Áp dụng được cho bất kỳ project libGDX/gdx-liftoff nào đang chỉ chạy
desktop, muốn thêm Android:

- [ ] Tạo module `android/` (manifest, launcher, build.gradle,
      proguard-rules, res/), thêm `include ':android'` vào
      `settings.gradle`, thêm `google()` repo + `android.useAndroidX=true`.
- [ ] `windowSoftInputMode="adjustResize"` trong manifest.
- [ ] Audit toàn bộ input handler: mọi toạ độ chạm/chuột phải `unproject`
      qua đúng viewport trước khi so khớp với toạ độ ảo/UI.
- [ ] Audit toàn bộ chỗ lưu file cục bộ: chuyển từ `java.io.File`/đường
      dẫn kiểu desktop sang `Gdx.files.local(...)`.
- [ ] Audit mọi chỗ gọi `Gdx.gl.glScissor` trực tiếp — phải tự quy đổi từ
      toạ độ ảo sang pixel thật theo scale viewport hiện tại.
- [ ] Bật `TextureFilter.Linear` cho font FreeType generate ra (chữ sẽ mờ
      trên mobile nếu giữ mặc định `Nearest`).
- [ ] Nếu có OAuth/Google Sign-In: thiết kế lại theo interface trừu tượng
      per-platform ngay từ đầu — Android **không dùng chung được** flow
      browser-redirect của desktop, phải qua Credential Manager, và cần 1
      Web Client ID riêng làm `serverClientId`. Backend phải chấp nhận
      nhiều audience hợp lệ nếu 1 header `x-platform` dùng chung cho cả 2.
- [ ] Nếu có tải asset/nhạc từ mạng về cache local: kiểm tra kỹ hành vi
      "cache cũ che giấu lỗi server hiện tại" — dev machine chạy lâu ngày
      dễ có cache cũ làm lỗi server không lộ ra khi test trên PC.
- [ ] Test thật trên thiết bị Android — nhiều bug (lệch toạ độ popup, quên
      xử lý multi-touch, bàn phím ảo che input...) **chỉ lộ ra khi cầm máy
      thật bấm**, không thấy được qua test bằng chuột trên desktop dù
      cùng 1 codebase.
- [ ] Nếu muốn "fit thật" theo tỉ lệ màn hình thay vì letterbox viền đen:
      đổi ExtendViewport **đồng thời và nhất quán** cho MỌI camera đang
      dùng (world lẫn UI) trong cùng 1 lần, không đổi riêng lẻ từng cái —
      xem chi tiết cạm bẫy & công thức đúng ở mục 10.

## 13. Cơ chế auto-update & phân phối bản build (PC vs Android)

### 13.1. Cấu trúc repo — có 2 repo GitHub khác nhau, không phải 1

- **`github.com/DANG-PH/ngoc-rong-online`** — repo SOURCE (repo hiện tại,
  toàn bộ code `core/lwjgl3/html/android` + tài liệu này). Đây là nơi
  commit/push code, KHÔNG phải nơi user tải game về chơi.
- **`github.com/DANG-PH/NgocRongOnline`** — repo PHÂN PHỐI công khai, có
  trang **Releases** chứa file cài đặt để user tải lần đầu (link trong
  `README.md`, badge "⬇ TẢI VỀ").
- **`raw.githubusercontent.com/DANG-PH/NRO_ONLINE/master/version.json`** —
  1 repo/nhánh khác nữa (tên khác hẳn `NgocRongOnline`, không phải chỉ
  khác hoa/thường) chuyên host file `version.json` mà client tự động dò
  mỗi lần khởi động để biết có bản mới hay không
  (`ManHinhKhoiDong.checkUpdate()`). Cấu trúc file:
  ```json
  { "version": "1.0.x", "jar": "<URL tải file .jar mới, thường là link GitHub Releases asset>" }
  ```
- **Kết luận quan trọng**: `ngoc-rong-online` (repo code) và
  `NgocRongOnline`/`NRO_ONLINE` (repo phân phối) là **2 hệ thống tách
  biệt** — push code lên repo source **không** tự động cập nhật gì cho
  người chơi cả. Phải build ra artifact rồi **thủ công** (hoặc CI/CD, xem
  mục 13.4) đăng lên repo phân phối + cập nhật `version.json` thì user mới
  nhận được bản mới.

### 13.2. PC/Windows — ĐÃ có auto-update, hoạt động thế nào

Code nằm ở `core/src/main/java/com/dang/dragonboy/giao_dien_ngoai/ManHinhKhoiDong.java`
(màn hình splash/loading đầu tiên khi mở game):

1. **`checkUpdate()`** (chạy trên thread riêng, không block UI): đọc phiên
   bản đang cài từ `app/version.txt` (nếu file không tồn tại → coi là
   `"dev"`, tức đang chạy qua Gradle chứ không phải bản đóng gói, thì bỏ
   qua toàn bộ check update). So sánh với `version` trong `version.json`
   tải từ GitHub.
2. Nếu khác nhau (và không phải `"dev"`) → `needUpdate = true` → hiện
   progress bar, tải file `.jar` mới từ URL trong `version.json` về
   `app/nro_new.jar` (tự follow redirect vì GitHub Releases trả 302).
3. **`startNewJar()`**: sinh ra 1 script cài đặt tạm thời
   (`app/updater.bat` trên Windows, `app/updater.sh` trên Linux/macOS),
   rồi chạy script đó bằng `ProcessBuilder` và tự thoát app hiện tại
   (`Gdx.app.exit()`). Script này:
   - Đọc PID của tiến trình game đang chạy từ **`app/app.pid`** (chính là
     file được `Main.java`'s `savePid()` ghi ra lúc `create()` — CHỈ ghi
     khi thư mục `app/` tồn tại, tức đang chạy bản đóng gói, không ghi gì
     khi chạy dev qua Gradle) và **kill đúng tiến trình đó**.
   - Xoá jar cũ, đổi tên `nro_new.jar` thành tên jar chính thức
     (`NgocRongOnline-1.0.0.jar`), ghi version mới vào `app/version.txt`.
   - Khởi động lại game bằng **JDK đóng gói sẵn cùng app**:
     `runtime/bin/javaw -jar app/NgocRongOnline-1.0.0.jar` (Windows) /
     `runtime/bin/java -jar app/NgocRongOnline-1.0.0.jar` (Linux/macOS) —
     KHÔNG dùng Java cài trên máy user, dùng JDK bundle riêng (xem mục
     13.3 — đây chính là thứ `construo` đóng gói).
   - Tự xoá chính nó (`del "%~f0"` / `rm -- "$0"`) sau khi xong.

**Vì sao chỉ cần push đúng file `.jar` (không cần build lại cả gói .exe)
cho mỗi lần update thường**: cơ chế trên chỉ TẢI VÀ THAY THẾ đúng 1 file
`.jar` bên trong 1 bản cài đã có sẵn `runtime/` (JDK) + cấu trúc thư mục
`app/` từ trước — nó không tải lại JDK hay file `.exe` gốc. Chỉ cần build
lại `.jar` mới, upload lên GitHub Releases (repo phân phối), sửa
`version.json` trỏ đúng version + URL jar mới, là toàn bộ user cũ tự động
nhận được update ở lần mở game kế tiếp.

### 13.3. Android — CHƯA có cơ chế tương tự, và KHÔNG THỂ giống hệt PC

Đã kiểm tra kỹ code (`AndroidLauncher.java` và toàn bộ package
`giao_dien_ngoai`) — **hiện tại phía Android không có bất kỳ đoạn code
check-update/tải-update nào**. Nếu chỉ cập nhật `version.json` + jar mới
cho PC, user Android **sẽ không biết** có bản mới, cho tới khi admin báo
thủ công.

**Vì sao không thể copy y hệt cơ chế PC sang Android**: cơ chế PC hoạt
động được vì nó tự kill tiến trình, tự ghi đè file jar, tự khởi động lại —
tất cả đều là thao tác filesystem/process bình thường trên desktop.
Android **không cho phép** 1 app tự thay thế APK của chính nó theo kiểu
"âm thầm" như vậy, vì lý do bảo mật của hệ điều hành:
- Không có quyền ghi đè trực tiếp lên APK đã cài (khác `app/` folder tự do
  ghi trên desktop).
- Cài 1 APK mới (kể cả cùng package name, bản cập nhật) **bắt buộc** phải
  đi qua Android Package Installer — luôn hiện dialog xác nhận cho user
  bấm "Cài đặt", không thể tự động hoá hoàn toàn nếu không phải app hệ
  thống hoặc có quyền cài đặt đặc biệt (`REQUEST_INSTALL_PACKAGES`, và kể
  cả có quyền đó vẫn hiện dialog xác nhận, chỉ là bỏ qua được bước chọn
  file trong Package Installer).
- Ngoài Play Store (game này không phân phối qua Play Store), không có
  kênh "âm thầm tự cập nhật" chính thức nào khác.

**Hướng thực tế nhất cho Android** (chưa triển khai, cần xác nhận trước
khi làm — xem mục 13.4):
1. Thêm 1 field mới vào `version.json` dùng chung, ví dụ `"apk": "<URL
   file .apk mới>"`, để không cần tạo file version riêng cho Android.
2. Trong `ManHinhKhoiDong` (hoặc màn tương đương), kiểm tra
   `Gdx.app.getType() == ApplicationType.Android` — nếu đúng và phát hiện
   version khác, thay vì tự tải-và-kill-tiến-trình như PC, chỉ **hiện
   thông báo + nút "Tải bản mới"**, bấm vào thì mở trình duyệt hoặc dùng
   `DownloadManager` tải file `.apk` về rồi bắn `Intent`
   (`ACTION_VIEW`/`ACTION_INSTALL_PACKAGE` qua `FileProvider`) để Android
   tự mở màn hình cài đặt — user vẫn phải tự bấm xác nhận cài, nhưng đỡ
   phải tự tìm link tải thủ công.
3. Cần khai báo thêm quyền `REQUEST_INSTALL_PACKAGES` trong
   `AndroidManifest.xml` và cấu hình 1 `FileProvider` nếu muốn tự trigger
   được màn hình cài đặt thay vì chỉ mở trình duyệt để user tự tải.

**Một vấn đề khác cần xử lý trước khi phân phối APK thật sự** (không liên
quan auto-update, nhưng bắt buộc phải có): `android/build.gradle` hiện
**chưa có `signingConfig`** nào cho `buildTypes.release` — nghĩa là
`./gradlew :android:assembleRelease` sẽ build ra 1 file APK **KHÔNG ký**,
không cài đặt được trên máy user thật (chỉ APK **debug** — build bằng
`assembleDebug`, tự ký bằng debug keystore của máy dev — mới cài được,
nhưng debug keystore đổi theo từng máy dev nên **không nên dùng để phân
phối lâu dài**: nếu đổi máy dev/xoá debug keystore, các bản build sau ký
bằng key khác sẽ bị Android coi là app khác, user cũ **không update
được**, phải gỡ cài lại từ đầu). Cần tạo **1 release keystore riêng, lưu
giữ cẩn thận vĩnh viễn** (mất là không thể phát hành bản update tương
thích với các bản đã cài trước đó nữa), rồi khai báo `signingConfig` trỏ
tới keystore đó trong `android/build.gradle`.

### 13.4. Đề xuất CI/CD — CHƯA triển khai, cần bạn xác nhận trước

Hiện tại quy trình phát hành là: build thủ công → upload thủ công 2 file
(`.jar`/gói `.exe` cho PC, `.apk` cho Android) lên repo phân phối → sửa
`version.json` thủ công. Có thể tự động hoá bằng **GitHub Actions**: mỗi
khi push tag (VD `v1.0.1`) lên repo source, 1 workflow tự build cả 2
platform rồi tự đăng lên repo phân phối.

**Vì sao chưa tự làm luôn**: việc này cần bạn tự tay chuẩn bị 2 thứ mà tôi
không thể tự tạo thay được:
1. **Android release keystore** (file `.jks`/`.keystore` + mật khẩu) — tự
   tạo bằng `keytool -genkeypair ...`, PHẢI tự lưu giữ an toàn, không đưa
   vào git. Đây là khoá ký production thật, mất là mất luôn khả năng phát
   hành update tương thích.
2. **Quyền push lên repo phân phối** (`NgocRongOnline`/`NRO_ONLINE`) từ
   phía GitHub Actions của repo source — cần tạo 1 GitHub Personal Access
   Token (hoặc deploy key) có quyền ghi vào repo đó, add làm **GitHub
   Actions Secret** trong repo source (`Settings → Secrets and
   variables → Actions`).

Khi có đủ 2 thứ trên, workflow (`.github/workflows/release.yml`) đại khái
sẽ chạy các bước: checkout → setup JDK 17 → `./gradlew :lwjgl3:jar
:lwjgl3:packageWinX64 :android:bundleRelease` (build cả 3 artifact) → ký
APK bằng keystore lấy từ secret → tính lại `version.json` theo tag vừa
push → dùng `gh` CLI hoặc `softprops/action-gh-release` để tạo GitHub
Release + upload file trên repo phân phối → commit `version.json` mới lên
nhánh `master` của repo phân phối.

**Nếu muốn tôi triển khai luôn**: cần bạn xác nhận đồng ý, sau đó tôi cần
bạn tự tạo keystore + PAT rồi add vào Secrets (tôi không tự tạo/nhìn thấy
được các giá trị bí mật này), tôi sẽ viết sẵn file workflow YAML đầy đủ
dựa theo đúng tên secret bạn đặt.

## 14. Toàn bộ lệnh build cần nhớ

Chạy từ thư mục gốc repo (`ngoc-rong-online/`), dùng `./gradlew` (Linux/macOS)
hoặc `gradlew.bat` (Windows).

### Kiểm tra compile nhanh (không build ra file chạy được)

```bash
./gradlew :core:compileJava           # chỉ check core/ có lỗi compile không
./gradlew :lwjgl3:compileJava         # check cả phần desktop-specific
./gradlew :core:compileJava :lwjgl3:compileJava :android:assembleDebug   # check toàn bộ, kể cả android
```

### Chạy thử ngay trên desktop (không cần build APK)

```bash
./gradlew :lwjgl3:run
```
Chuột trên desktop được libGDX coi như 1 touch event — dùng để test nhanh
logic input/UI mobile trước khi build APK xác nhận lần cuối.

### Android — build & cài APK

```bash
./gradlew :android:assembleDebug
# → android/build/outputs/apk/debug/android-debug.apk (ký bằng debug keystore, cài được ngay để tự test)

./gradlew :android:installDebug
# build + cài thẳng qua adb nếu có máy nối USB (bật USB debugging) — nhanh hơn build rồi copy file thủ công

./gradlew :android:assembleRelease
# → APK bản release nhưng CHƯA KÝ (chưa có signingConfig, xem mục 13.3) — KHÔNG cài được trên máy user
# thật cho tới khi cấu hình signingConfig trỏ tới 1 release keystore

adb install -r android/build/outputs/apk/debug/android-debug.apk
# cài thủ công 1 file APK cụ thể qua adb (máy đã nối USB + bật USB debugging)

keytool -list -v -keystore ~/.android/debug.keystore -storepass android -alias androiddebugkey
# lấy SHA-1 fingerprint của debug keystore — cần khi đăng ký OAuth Client Type "Android" cho Google Sign-In
```

### PC/Windows — build jar (dùng cho auto-update) & gói cài đặt đầy đủ

```bash
./gradlew :lwjgl3:jar
# → lwjgl3/build/libs/NgocRongOnline-1.0.0.jar (chạy được bằng `java -jar ...`)
# Đây là file cần upload lên repo phân phối + trỏ trong version.json mỗi lần chỉ muốn update
# nhẹ cho user ĐÃ CÀI SẴN — xem cơ chế auto-update ở mục 13.2.

./gradlew :lwjgl3:jarWin      # bản jar chỉ chứa native lib Windows (nhẹ hơn ~6MB)
./gradlew :lwjgl3:jarLinux    # bản jar chỉ chứa native lib Linux (nhẹ hơn ~5MB)
./gradlew :lwjgl3:jarMac      # bản jar chỉ chứa native lib macOS (nhẹ hơn ~7MB)

./gradlew :lwjgl3:packageWinX64
# → gói cài đặt ĐẦY ĐỦ cho Windows: bundle sẵn JDK 17 (tự tải về lúc build) + jar + launcher .exe native,
# KHÔNG cần máy user có sẵn Java. Đây là file dùng cho user tải LẦN ĐẦU (link trên README/trang Releases),
# không phải file dùng cho auto-update định kỳ.
./gradlew :lwjgl3:packageLinuxX64
./gradlew :lwjgl3:packageMacM1     # macOS Apple Silicon (arm64)
./gradlew :lwjgl3:packageMacX64    # macOS Intel
./gradlew :lwjgl3:buildMacAppBundleMacM1   # đóng gói dạng .app bundle cho macOS (Apple Silicon)
./gradlew :lwjgl3:buildMacAppBundleMacX64  # đóng gói dạng .app bundle cho macOS (Intel)
```

### Dọn build cache khi nghi ngờ build bị dính cache cũ

```bash
./gradlew clean
```

### Ghi chú version

`projectVersion` (hiện là `1.0.0`) khai báo trong `gradle.properties` ở
root — quyết định tên file jar xuất ra (`NgocRongOnline-1.0.0.jar`) và
`versionName` của APK. **Đổi giá trị này TRƯỚC khi build** mỗi lần phát
hành bản mới, đồng bộ với giá trị `"version"` sẽ ghi vào `version.json`
trên repo phân phối — nếu quên đổi, client cũ sẽ không thấy có update
(so sánh version trùng nhau) dù đã build/upload jar mới.

## 15. Thiết lập môi trường dev từ đầu — checklist cho dev hoàn toàn mới

Thứ tự làm — bỏ qua bước 2 gần như chắc chắn sẽ bị crash ngay khi chạy dù
compile vẫn qua, đây là cái bẫy đầu tiên ai mới vào project cũng dễ dính:

1. **Cài JDK 17** (project cố định `sourceCompatibility`/`targetCompatibility`
   = 17 ở mọi module) + Android SDK nếu định build/chạy Android (mục 3.1).
   Với Android SDK, cách gọn nhất không cần cả Android Studio:
   ```bash
   # tải "Command line tools only" từ trang Android Studio, giải nén, rồi:
   sdkmanager --sdk_root=$ANDROID_HOME "platform-tools" "platforms;android-35" "build-tools;35.0.0"
   ```
   Tạo file `local.properties` ở gốc repo (KHÔNG commit, đã gitignore) trỏ
   đúng SDK vừa cài:
   ```properties
   sdk.dir=/duong/dan/toi/Android/Sdk
   ```

2. **BẮT BUỘC tạo `core/src/main/resources/secrets.properties`** — file
   này KHÔNG được commit (đã gitignore, chỉ có bản mẫu
   `secrets.properties.example` nằm cùng thư mục được commit). Copy file
   mẫu rồi điền giá trị thật:
   ```bash
   cp core/src/main/resources/secrets.properties.example \
      core/src/main/resources/secrets.properties
   ```
   **Vì sao bắt buộc phải làm NGAY trước khi chạy bất kỳ thứ gì**: class
   `core/.../he_thong/AppConfig.java` đọc file này trong 1 khối
   `static { ... }` — tức là chạy **ngay lần đầu tiên bất kỳ code nào
   chạm tới class `AppConfig`** (mà rất nhiều class khác, VD
   `ApiService`, gọi `AppConfig.get(...)` làm khởi tạo field `static
   final` ngay từ đầu). Thiếu file này, code **vẫn compile bình thường**
   (đây không phải lỗi biên dịch) nhưng app sẽ **crash ngay khi vừa mở**
   với `RuntimeException("Không tìm thấy secrets.properties")` — dễ làm
   dev mới hoang mang vì build "thành công" mà chạy lại lỗi ngay lập tức.

   Nội dung cần điền (xem file `.example` để có comment giải thích đầy đủ
   từng dòng):
   ```properties
   google.client.id=YOUR_CLIENT_ID           # OAuth Client Type "Desktop", cho GoogleOAuth2Desktop
   google.client.secret=YOUR_CLIENT_SECRET   # secret đi kèm client id Desktop ở trên
   google.client.id.web=YOUR_WEB_CLIENT_ID   # OAuth Client Type "Web application", cho AndroidGoogleOAuth (mục 5.2)
   api.base.url=https://api.dangpham.id.vn   # backend NestJS — có sẵn server public dùng thử, không bắt buộc tự dựng
   ws.nestjs.url=https://ws.dangpham.id.vn/ws-game
   ws.go.url=wss://ws-go.dangpham.id.vn/ws-game
   ```
   Nếu **chỉ muốn build/chạy game để xem UI**, không cần đăng nhập Google
   thật, vẫn có thể điền giá trị placeholder cho 2 dòng `google.client.*`
   (chỉ tính năng đăng nhập Google sẽ lỗi khi bấm thử, phần còn lại của
   game hoạt động bình thường) — nhưng **không được để trống file hoặc
   xoá dòng nào**, thiếu key nào `AppConfig.get(key)` gọi tới key đó sẽ
   ném `RuntimeException("Thiếu config: " + key)` ngay lúc đó.

3. **Nếu muốn Google Sign-In hoạt động thật** (không bắt buộc cho các bước
   dev khác): vào Google Cloud Console tạo **3 OAuth Client ID** (dễ nhầm
   lẫn nhất trong cả project):
   - 1 Client Type **"Desktop app"** → lấy `client_id`/`client_secret`
     điền vào `google.client.id`/`google.client.secret`.
   - 1 Client Type **"Web application"** → lấy `client_id` điền vào
     `google.client.id.web` (dùng cho **cả Android lẫn desktop đọc để
     làm audience**, xem mục 5).
   - 1 Client Type **"Android"** → điền package name
     (`com.dang.dragonboy.android`, khớp `applicationId` mục 3.2) + SHA-1
     fingerprint (lệnh lấy SHA-1 ở mục 14) — **client này không cần điền
     giá trị ID vào bất kỳ đâu trong code**, chỉ cần TỒN TẠI trên Cloud
     Console để Google Play Services nhận diện & cho phép app xác thực
     qua Credential Manager (mục 5.2).

4. **Build thử để xác nhận môi trường đã đúng**:
   ```bash
   ./gradlew :core:compileJava :lwjgl3:compileJava
   ./gradlew :lwjgl3:run          # chạy thử desktop — nếu thiếu secrets.properties sẽ crash ngay ở đây
   ./gradlew :android:assembleDebug   # chỉ cần nếu đã cài Android SDK
   ```

## 16. Lỗi thường gặp & cách fix nhanh — bảng tra cứu

| Triệu chứng | Nguyên nhân | Cách fix | Xem chi tiết |
|---|---|---|---|
| App crash ngay khi mở: `RuntimeException: Không tìm thấy secrets.properties` | Chưa tạo `core/src/main/resources/secrets.properties` (bị gitignore, không tự có sau khi clone) | Copy từ `secrets.properties.example`, điền giá trị | Mục 15 |
| `RuntimeException: Thiếu config: <key>` | File `secrets.properties` có tồn tại nhưng thiếu 1 dòng key nào đó | Điền đủ tất cả key có trong `.example` | Mục 15 |
| App Android crash ngay khi mở: `UnsatisfiedLinkError` | Thiếu file `.so` trong `android/libs/<abi>/` — thường do build cache cũ, hoặc task `copyAndroidNatives` không chạy | `./gradlew clean :android:assembleDebug` | Mục 3.2, 3.6 |
| Build Android báo lỗi liên quan `com.android.tools.build:gradle` không resolve được | Thiếu `google()` trong `repositories` | Kiểm tra root `build.gradle` | Mục 3.8 |
| Google Sign-In trên Android báo "Error 400: invalid_request" | Đang cố dùng flow browser-redirect (kiểu Desktop) cho OAuth Client Type "Android" — Google chặn thẳng, không phải bug code | Dùng Credential Manager, xem toàn bộ mục 5 | Mục 5 |
| Backend từ chối token Google hợp lệ (audience mismatch) | Desktop và Android gửi token có `audience` khác nhau (2 Client ID khác nhau) nhưng backend chỉ nhận 1 audience cố định | Backend nhận `audience` dạng mảng nhiều giá trị hợp lệ | Mục 5 |
| Bàn phím ảo Android che mất ô nhập liệu (đăng nhập/OTP/chat) | Thiếu `android:windowSoftInputMode="adjustResize"` | Thêm vào thẻ `<activity>` trong `AndroidManifest.xml` | Mục 3.3, 4.5 |
| Bấm Enter trên bàn phím ảo không thoát/không gửi được | Chưa xử lý ký tự `'\n'`/`'\r'` trong `keyTyped()` cho ô nhập đó | Coi `'\n'`/`'\r'` như hành động "Gửi/Enter" tường minh | Mục 4.5 |
| Chạm sai vị trí trên điện thoại dù bấm đúng trên PC (test bằng chuột) | Toạ độ chạm dùng thẳng pixel thật thay vì `unproject` qua viewport trước khi so khớp toạ độ ảo | Luôn `viewport.unproject(new Vector2(screenX, screenY))` trước khi hit-test | Mục 4.1 |
| Chữ bị mờ/vỡ nét trên điện thoại (rõ trên PC) | Font FreeType dùng `TextureFilter.Nearest` mặc định, phóng to sai tỉ lệ trên màn hình khác PC | Đổi `minFilter`/`magFilter` sang `TextureFilter.Linear` lúc generate font | Mục 4.4 |
| App bị crash/mất trạng thái khi xoay máy hoặc bàn phím ảo bật/tắt | Thiếu `android:configChanges` đầy đủ trên activity → Android tự huỷ-tạo-lại Activity, làm mất GL context/kết nối WebSocket đang chạy dở | Khai đủ danh sách `configChanges` trong `AndroidManifest.xml` | Mục 3.3 |
| `glScissor`/clip vùng vẽ (khung chat, cuộn item...) sai vị trí trên điện thoại | Gọi thẳng `Gdx.gl.glScissor` với toạ độ ảo — API này nhận pixel thật, không đi qua ma trận chiếu như `batch`/`shapeRenderer` | Dùng helper `QuanLyCamera.scissor(vx, vy, vw, vh)` tự quy đổi | Mục 4.3 |
| Lưu file cục bộ (token, cache nhạc...) lỗi/không hoạt động trên Android | Dùng `java.io.File`/đường dẫn kiểu `user.home` — không hợp lệ trong sandbox Android | Dùng `Gdx.files.local(path)`, libGDX tự map đúng theo platform | Mục 4.2 |
| `./gradlew :android:assembleRelease` ra APK không cài được trên máy user thật | Chưa có `signingConfig` cho `buildTypes.release` — APK release hiện tại KHÔNG được ký | Tạo release keystore riêng, khai `signingConfig` | Mục 13.3 |
| PC nghe được nhạc nền nhưng Android thì im, không lỗi gì rõ ràng | Rất có thể KHÔNG phải bug code — PC có cache mp3 tải từ trước, Android cài mới nên phải tải lại và lộ ra lỗi server/mạng thật | Xoá thử cache PC (`app/nhacnen/*.mp3`) để xác nhận, kiểm tra trực tiếp file URL bằng `curl` | Mục 9 (nguyên văn quy trình chẩn đoán) |
| Bấm nút HUD mobile (attack/change/skill/đậu thần...) phải bấm 2 lần mới ăn, lần đầu như bị lờ đi | Biến theo dõi pointer đang giữ joystick ảo bị "kẹt" do lỡ mất 1 sự kiện `touchUp`, khiến lần chạm tiếp theo trùng pointer bị nuốt nhầm | Tự dọn state ngay ở đầu `touchDown` nếu thấy touchDown mới trên đúng pointer đang được ghi là chủ joystick | Mục 8.1 |
| Kéo joystick ảo làm camera cũng bị pan/kéo theo | Mọi `touchDown` mặc định đều bắt đầu 1 phiên "kéo camera tiềm năng", chưa loại trừ vùng joystick | Kiểm tra có đang chạm trong vùng joystick trước khi gọi `camera.batDauKeoCamera()` | Mục 8.1 |
| Đổi mục tiêu NPC qua nút bấm không có tác dụng gì (không lỗi, không hiện mũi tên chọn) | Set thẳng `npc.dangClickNpc = true` bị vòng lặp `checkClick()` chạy mỗi frame tự reset lại ngay vì không khớp điểm chạm world lần cuối | Đồng bộ `nhanVat.x_check_npc`/`y_check_npc` về đúng toạ độ NPC vừa chọn | Mục 8.2 |
| Popup item/khung nào đó hiện đúng vị trí trên màn hình nhưng bấm vào không trúng | Popup bị clamp lại lúc VẼ nhưng phần XỬ LÝ CLICK vẫn dùng toạ độ trước khi clamp | Ghi lại toạ độ/kích thước ĐÃ clamp vào field dùng chung cho cả vẽ lẫn click | Mục 6 |

## 17. Kiến trúc tổng thể — sơ đồ

```
                    ┌─────────────────────────────────────────┐
                    │                 core/                    │
                    │  (toàn bộ logic game — render, input,     │
                    │   network, HUD... dùng chung mọi platform)│
                    │                                            │
                    │  interface GoogleOAuthProvider             │
                    │  class PlatformBridge {                    │
                    │      static GoogleOAuthProvider googleOAuth │◄──┐ đăng ký lúc khởi động
                    │  }                                          │   │ (Strategy/Bridge pattern,
                    │  class Main extends Game { ... }  ──────────┼─┐ │  xem mục 5.1 — áp dụng được
                    └─────────────────────────────────────────┘ │ │  cho MỌI chức năng chỉ 1
                                     ▲        ▲        ▲          │ │  platform mới làm được)
                 implementation      │        │        │          │ │
              project(':core')       │        │        │          │ │
                    ┌────────────────┘        │        └──────────┼─┼──────────┐
                    │                          │                   │ │          │
            ┌───────┴──────┐          ┌────────┴───────┐   ┌───────┴─┴──┐      │
            │   lwjgl3/    │          │     html/      │   │  android/   │      │
            │  (Desktop)   │          │     (Web)      │   │             │      │
            │              │          │                │   │  build.gradle,     │
            │ Lwjgl3Launcher│          │  GwtLauncher   │   │  AndroidManifest,  │
            │  new Main()  │          │   new Main()   │   │  AndroidLauncher ──┘
            │              │          │                │   │  (extends AndroidApplication,
            │ PlatformBridge│          │                │   │   new Main(), mục 3.4)
            │  .googleOAuth │          │                │   │
            │  = GoogleOAuth2Desktop() │                │   │  PlatformBridge.googleOAuth
            └──────────────┘          └────────────────┘   │  = AndroidGoogleOAuth(this)
                                                              └─────────────┘

  → 4 module CHIA SẺ chung 1 class `Main` và toàn bộ logic trong core/.
  → Mỗi module platform (lwjgl3/html/android) chỉ là "vỏ khởi động" mỏng:
    tạo cửa sổ/Activity/canvas theo đúng kiểu platform đó, rồi gọi
    initialize(new Main(), config) — hoặc tương đương — và đăng ký các
    implementation platform-specific (như GoogleOAuthProvider) vào
    PlatformBridge TRƯỚC khi core/ có cơ hội cần dùng tới chúng.
  → Muốn thêm 1 platform mới (VD iOS): tạo module mới tương tự, viết
    launcher + implementation riêng, core/ không cần sửa gì.
```

## 18. Chưa kiểm chứng / rủi ro còn tồn đọng

Để tránh ảo tưởng "đã xong hết" — đây là những điểm **chưa được kiểm tra
kỹ hoặc cố tình chưa làm** trong quá trình port, người tiếp quản nên biết
trước thay vì tự khám phá ra giữa chừng:

- **Vòng đời app Android khi bị đưa xuống nền (background)**: chưa audit
  kỹ điều gì xảy ra với kết nối WebSocket (`GameSocket`/`GameSocketGo`)
  khi user chuyển app khác/tắt màn hình rồi quay lại — Android có thể tự
  ngắt kết nối mạng của app chạy nền (tuỳ chính sách tiết kiệm pin của
  từng hãng máy), và libGDX gọi `pause()`/`resume()` trên `ApplicationListener`
  ở các thời điểm này nhưng code hiện tại **chưa có xử lý riêng** cho việc
  tự reconnect socket khi resume sau khi bị OS ngắt ngầm — nên thử nghiệm
  kỹ: mở app, chuyển sang app khác vài phút, quay lại xem còn kết nối/còn
  điều khiển nhân vật được không.
- **Chưa profiling hiệu năng trên máy Android thật sự yếu**: mọi test
  trong quá trình này đều trên 1 thiết bị cụ thể của người dùng — chưa có
  số liệu FPS/bộ nhớ trên máy cấu hình thấp hơn. `minifyEnabled false`
  (mục 3.7) cũng đồng nghĩa APK hiện tại chưa được rút gọn kích thước.
- **Chưa có adaptive icon** (`res/mipmap-anydpi-v26/ic_launcher.xml` với
  layer riêng foreground/background) — chỉ mới có icon vuông kiểu cũ
  (`res/mipmap-*/ic_launcher.png`, mục 3.5). Từ Android 8.0 trở lên, thiếu
  adaptive icon không làm app lỗi gì, chỉ là icon sẽ không tự thích ứng
  hình dạng (tròn/vuông/giọt nước...) theo từng launcher của từng hãng máy
  — thuần tuý thẩm mỹ, không phải bug chức năng.
- **`html/` (bản Web/GWT) không nằm trong phạm vi port lần này**: đã thử
  compile lại (`./gradlew :html:compileJava`) và vẫn qua bình thường vì
  dùng chung `core/`, nhưng **chưa hề chạy thử/test** bản HTML này sau
  toàn bộ các thay đổi ở mục 4 (unproject toạ độ, `Gdx.files.local`,
  scissor helper...) — về lý thuyết các fix đó là platform-agnostic
  (dùng API libGDX chung, không riêng gì Android) nên nhiều khả năng vẫn
  ổn, nhưng chưa có xác nhận thực tế trên trình duyệt, đặc biệt nếu có ai
  từng mở bản Web bằng điện thoại (touch trên trình duyệt mobile) thì
  càng chưa rõ.
- **Ký release Android + APK phân phối** — đã nêu ở mục 13.3, nhắc lại vì
  đây là việc **bắt buộc phải làm trước khi đưa app cho người dùng thật**,
  không chỉ là chi tiết phụ: chưa có `signingConfig`, mọi APK hiện tại chỉ
  là bản debug.
- **Nonce của Google Sign-In chưa được verify ở phía nào rõ ràng** — cả 2
  flow (Desktop mục 5.3, Android mục 5.2) đều SINH `nonce` gửi kèm request,
  nhưng chưa xác nhận được backend có thực sự kiểm tra lại nonce này khớp
  với token trả về hay không (thấy `TODO` ngay trong comment của
  `GoogleOAuth2Desktop.java`) — nếu backend bỏ qua bước này, về lý thuyết
  có lỗ hổng replay attack nhỏ, nên kiểm tra lại phía backend.
- **Nỗ lực "fit theo màn hình thật" (mục 10) vẫn đang bỏ ngỏ** — không
  phải rủi ro kỹ thuật (đã revert sạch, không để lại code dở dang nào),
  nhưng là việc CHƯA XONG nếu mục tiêu cuối vẫn là loại bỏ viền đen 2 bên
  trên mobile — người làm tiếp nên đọc kỹ bài học ở mục 10 trước khi thử
  lại, tránh lặp lại đúng 2 lần thất bại trước.
