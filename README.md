<p align="center">
  <img src="https://i.pinimg.com/originals/fd/91/b1/fd91b1715061efc79dbb6678aea0f9b9.gif" width="220" alt="Ngọc Rồng Online">
</p>

<h1 align="center">Ngọc Rồng Online</h1>

<p align="center">
  <em>Java Multiplayer MMORPG · Microservice Architecture · Real-time Combat</em>
</p>

<p align="center">
  <a href="https://www.java.com/"><img src="https://img.shields.io/badge/Java-17+-ED8B00?style=flat&logo=openjdk&logoColor=white" alt="Java"/></a>
  <a href="https://libgdx.com/"><img src="https://img.shields.io/badge/LibGDX-1.12+-E74C3C?style=flat&logo=libgdx&logoColor=white" alt="LibGDX"/></a>
  <a href="https://gradle.org/"><img src="https://img.shields.io/badge/Gradle-8+-02303A?style=flat&logo=gradle&logoColor=white" alt="Gradle"/></a>
  <a href="https://nestjs.com/"><img src="https://img.shields.io/badge/NestJS-10+-E0234E?style=flat&logo=nestjs&logoColor=white" alt="NestJS"/></a>
  <a href="https://golang.org/"><img src="https://img.shields.io/badge/Go-1.22+-00ADD8?style=flat&logo=go&logoColor=white" alt="Go"/></a>
  <a href="https://www.typescriptlang.org/"><img src="https://img.shields.io/badge/TypeScript-5+-3178C6?style=flat&logo=typescript&logoColor=white" alt="TypeScript"/></a>
</p>

<p align="center">
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License"/></a>
  <a href="https://github.com/DANG-PH/MICROSERVICE_GAME_SERVICE_GO/stargazers"><img src="https://img.shields.io/github/stars/DANG-PH/MICROSERVICE_GAME_SERVICE_GO?style=flat&color=yellow" alt="Stars"/></a>
  <a href="CONTRIBUTING.md"><img src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg" alt="PRs Welcome"/></a>
  <a href="https://goreportcard.com/report/github.com/DANG-PH/MICROSERVICE_GAME_SERVICE_GO"><img src="https://goreportcard.com/badge/github.com/DANG-PH/MICROSERVICE_GAME_SERVICE_GO?v=2" alt="Go Report Card"/></a>
</p>

<p align="center">
  Dự án cá nhân tái hiện <strong>Ngọc Rồng Online</strong> – tựa game MMORPG gắn liền với tuổi thơ của hàng triệu game thủ Việt,<br>
  lấy cảm hứng từ bộ truyện tranh huyền thoại <strong>Dragon Ball (7 Viên Ngọc Rồng)</strong> của tác giả Akira Toriyama.
</p>

<p align="center">
  <strong>🎮 Chơi ngay: <a href="https://ngocrongdark.com">ngocrongdark.com</a></strong>
</p>

<p align="center">
  <a href="#-về-dự-án">Giới thiệu</a> ·
  <a href="#-tính-năng-hiện-có">Tính năng</a> ·
  <a href="#-công-nghệ--lý-do-lựa-chọn">Công nghệ</a> ·
  <a href="#-yêu-cầu-hệ-thống">Yêu cầu</a> ·
  <a href="#-hướng-dẫn-cài-đặt">Cài đặt</a> ·
  <a href="#-đóng-góp">Đóng góp</a>
</p>

---

## 🐉 Về dự án

Sau khoảng **1 năm phát triển**, mình đã ra mắt phiên bản playable đầu tiên của game đa người chơi lấy cảm hứng từ Dragon Ball, hiện đang chạy trên **PC (Windows)**. Dự án tập trung vào việc xây dựng **hệ thống multiplayer thời gian thực** và **kiến trúc backend từ đầu**, không sử dụng game engine có sẵn cho phần logic server.

Đây không chỉ là một bản tái hiện về mặt gameplay mà còn là **bài thực hành kiến trúc microservice** với 3 stack công nghệ khác nhau (Java/LibGDX, NestJS, Golang), mô phỏng cách các tựa game online thương mại được xây dựng trong thực tế. Game vẫn đang trong quá trình hoàn thiện — sửa lỗi, tối ưu hiệu năng và bổ sung tính năng mới — và mình rất mong nhận được góp ý từ cộng đồng.

---

## ✨ Tính năng hiện có

### 🌐 Real-time Multiplayer
- **Đồng bộ thời gian thực**: người chơi nhìn thấy nhau, tương tác và quan sát hành động (di chuyển, kỹ năng) trên cùng bản đồ
- **Hệ thống giao dịch (trade)** giữa người chơi với xác thực 2 phía
- **NPC Shop** mua bán vật phẩm
- **Cường hóa item** qua NPC
- **Mini-game** trong thế giới game

### 🎒 Game Systems
- **Inventory persistence** — hành trang được lưu trữ liên tục, đồng bộ giữa client và server
- **Hệ thống đệ tử** và cơ chế **fusion** (hợp thể)
- **Triệu hồi rồng thần** sau khi thu thập đủ 7 viên ngọc rồng
- **Vòng quay may mắn** với **pity system** (đảm bảo nhận thưởng sau số lần quay nhất định)
- **Hệ thống Giftcode** — phát mã quà tặng cho người chơi

### 🛠️ Web Platform & Admin Tools
- **Dashboard quản lý người chơi** — xem, tìm kiếm, thao tác trên tài khoản
- **Inventory tracking** — theo dõi vật phẩm của từng người chơi
- **Thao tác thủ công vàng/vật phẩm** — admin có thể cộng/trừ tài nguyên khi cần (xử lý khiếu nại, sự kiện)
- **Hệ thống tin tức (news)** — đăng tải thông báo trong game và trên web
- **Tích hợp chatbot** — hỗ trợ người chơi tự động

### 💳 Payment Integration
- **Tích hợp PayOS** cho nạp tiền online
- **Giao tài nguyên tự động và nhất quán** vào game ngay sau khi thanh toán thành công
- Đảm bảo **idempotency** — không bị giao trùng hoặc thiếu khi có lỗi mạng

### ⚙️ Backend & Infrastructure
- **Server chạy 24/7**, đã deploy lên môi trường thật
- **Tách biệt môi trường** dev và production
- **Hỗ trợ nhiều người chơi đồng thời**

---

## 🖥️ Công nghệ & Lý do lựa chọn

### 🎮 Client — Java + LibGDX

**LibGDX** là framework game Java đa nền tảng (PC, Android, iOS, Web), hiệu năng cao nhờ render trực tiếp bằng OpenGL. Phù hợp để xây dựng game 2D action với vòng lặp game chính xác, xử lý input mượt mà và hỗ trợ tileMap, sprite animation. LibGDX có cộng đồng lớn, tài liệu phong phú, và cho phép build cùng một codebase ra nhiều nền tảng mà không phải viết lại logic.

Việc chọn Java thay vì Unity (C#) hay Godot (GDScript) cũng có lý do thực tế: Java có hệ sinh thái networking trưởng thành (OkHttp, WebSocket native), dễ tích hợp với nhiều backend khác nhau, và quan trọng nhất — đây là ngôn ngữ mình muốn rèn luyện sâu hơn qua một dự án thực tế.

### 🔥 API Gateway — NestJS (Node.js + TypeScript)

**NestJS** đảm nhận phần API Gateway và các service không yêu cầu hiệu năng cực cao: authentication (JWT, OAuth Google), quản lý user/profile, item inventory, payment webhook (PayOS), admin dashboard. Kiến trúc module hóa rõ ràng theo phong cách Angular, decorator-based giúp code dễ đọc, dễ test.

TypeScript mang lại type-safety và DTO validation tự động (qua `class-validator`), giảm đáng kể bug runtime so với JavaScript thuần. Hệ sinh thái npm phong phú cũng giúp tích hợp nhanh với MySQL, JWT, OAuth providers, và payment gateway mà không phải viết lại từ đầu.

### ⚡ Game Service — Golang

**Golang** đảm nhận các service đòi hỏi hiệu năng cao và độ trễ thấp: đồng bộ vị trí người chơi, broadcast event đến nhiều client cùng lúc, xử lý real-time interaction trên bản đồ. Goroutine nhẹ hơn thread hệ điều hành hàng chục lần (vài KB so với vài MB), phù hợp khi cần xử lý hàng nghìn kết nối đồng thời mà vẫn giữ độ trễ thấp.

Channel của Go khiến việc đồng bộ giữa các goroutine trở nên rõ ràng và an toàn hơn so với mutex truyền thống. Compile ra binary đơn lẻ, deploy cực nhanh, RAM chiếm dụng thấp — rất phù hợp để chạy trên VPS giá hợp lý mà vẫn phục vụ được nhiều người chơi đồng thời.

---

## 🏗️ Kiến trúc hệ thống

```
┌─────────────────────────────────────────┐
│   Client (LibGDX + Java)                │
│   - Render, Input, Animation            │
│   - WebSocket Client                    │
└──────────────┬──────────────────────────┘
               │ HTTPS / WSS
               ▼
┌─────────────────────────────────────────┐
│   API Gateway (NestJS)                  │
│   - Auth (JWT, Google OAuth)            │
│   - User, Item, Inventory CRUD          │
│   - Payment Webhook (PayOS)             │
│   - Admin Dashboard, News, Chatbot      │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│   Game Service (Golang)                 │
│   - Real-time Position Sync             │
│   - Event Broadcast                     │
│   - Multiplayer Interaction             │
└──────────────┬──────────────────────────┘
               │
               ▼
       ┌───────────────┐
       │    MySQL      │
       └───────────────┘
```

---

## 📋 Yêu cầu hệ thống

Để chạy được phần **client** (LibGDX), máy bạn cần có:

| Công cụ | Phiên bản tối thiểu | Ghi chú |
|---------|---------------------|---------|
| **JDK** | 17+ | Khuyến nghị Temurin / OpenJDK |
| **Gradle** | 8.0+ | Hoặc dùng wrapper `./gradlew` đã có sẵn |
| **Git** | Bất kỳ | Để clone repo |

> 💡 Nếu chỉ muốn **trải nghiệm game** mà không cần build, hãy vào trực tiếp [ngocrongdark.com](https://ngocrongdark.com) để chơi.

---

## 🚀 Hướng dẫn cài đặt

### 1. Clone repository

```bash
git clone https://github.com/DANG-PH/dragonboy-libgdx-client.git
cd dragonboy-libgdx-client
```

### 2. Cấu hình `secrets.properties`

Dự án **không commit** file chứa secret thật lên Git. Bạn cần tự tạo file `secrets.properties` từ file mẫu đã có sẵn trong repo:

```bash
# Linux / macOS
cp src/main/resources/secrets.properties.example src/main/resources/secrets.properties

# Windows (PowerShell)
Copy-Item src\main\resources\secrets.properties.example src\main\resources\secrets.properties
```

Sau đó mở file `src/main/resources/secrets.properties` và điền thông tin của bạn:

```properties
# Google OAuth (lấy từ Google Cloud Console)
google.client.id=YOUR_CLIENT_ID.apps.googleusercontent.com
google.client.secret=YOUR_CLIENT_SECRET

# Backend URL
api.base.url=https://api.dangpham.id.vn
ws.url=wss://api.dangpham.id.vn/ws
```

> 🔐 **Cách lấy Google OAuth credentials:**
> 1. Vào [Google Cloud Console](https://console.cloud.google.com/)
> 2. Tạo project mới → **APIs & Services** → **Credentials**
> 3. Nhấn **Create Credentials** → **OAuth client ID**
> 4. Chọn loại **Desktop app**
> 5. Copy **Client ID** và **Client Secret** vào file trên

> ⚠️ **Tuyệt đối không commit file `secrets.properties` lên Git.** File đã có trong `.gitignore` sẵn — đừng xóa dòng đó.

### 3. Build và chạy client

Dự án dùng **Gradle wrapper** — bạn không cần cài Gradle riêng, chỉ cần JDK 17+.

```bash
# Linux / macOS
./gradlew lwjgl3:run

# Windows
gradlew.bat lwjgl3:run
```

Lần đầu Gradle sẽ tải các dependency (LibGDX, OkHttp, Jackson...) — quá trình này mất **2–5 phút** tùy mạng. Các lần sau sẽ chạy gần như ngay lập tức nhờ cache.

Để build ra file `.jar` chạy được:

```bash
./gradlew lwjgl3:jar
# File output: lwjgl3/build/libs/dragonboy-1.0.0.jar
```

Chạy file jar:

```bash
java -jar lwjgl3/build/libs/dragonboy-1.0.0.jar
```

### 4. Khắc phục lỗi thường gặp

<details>
<summary><b>🔴 "Cannot resolve symbol 'load'" hoặc "Không tìm thấy secrets.properties"</b></summary>

File `secrets.properties` chưa tồn tại hoặc đặt sai chỗ. Kiểm tra:
- File phải nằm ở `src/main/resources/secrets.properties`
- Đã rebuild project chưa? Chạy `./gradlew clean build`
</details>

<details>
<summary><b>🔴 "Could not find or load main class"</b></summary>

JDK chưa được cài hoặc `JAVA_HOME` chưa set đúng. Kiểm tra:
```bash
java -version    # phải >= 17
echo $JAVA_HOME  # phải trỏ đến thư mục JDK
```
</details>

<details>
<summary><b>🔴 LibGDX báo lỗi OpenGL / GLFW khi chạy</b></summary>

- **Linux**: cài thêm `libgl1-mesa-dev` và `libglfw3`
- **macOS Apple Silicon (M1/M2/M3)**: chạy với flag `-XstartOnFirstThread`:
```bash
  ./gradlew lwjgl3:run --args="-XstartOnFirstThread"
```
</details>

<details>
<summary><b>🔴 Không kết nối được server</b></summary>

- Kiểm tra `api.base.url` trong `secrets.properties` đúng chưa
- Server có đang chạy không? Thử `curl https://api.dangpham.id.vn/health`
- Firewall có chặn port không?
</details>

---

## 🤝 Đóng góp

Game vẫn đang trong giai đoạn hoàn thiện và mình rất mong nhận được góp ý từ cộng đồng. Mọi đóng góp đều được chào đón!

1. **Fork** repository này
2. Tạo branch mới: `git checkout -b feature/ten-tinh-nang`
3. Commit theo chuẩn [Conventional Commits](https://www.conventionalcommits.org/): `feat: them he thong cuong hoa`
4. Push lên fork và mở **Pull Request**

Trước khi làm tính năng lớn, vui lòng mở **Issue** để thảo luận trước — tránh trùng lặp công sức.

Bạn cũng có thể đóng góp bằng cách **báo bug** hoặc **đề xuất tính năng** qua tab Issues — mọi feedback đều quý giá với mình.

---

## 📜 License

Dự án phát hành dưới giấy phép [MIT License](LICENSE) — bạn được tự do sử dụng, chỉnh sửa, phân phối với điều kiện giữ lại copyright notice. **Lưu ý**: dự án này là **fan-made**, không liên quan đến Hiker Games hay bất kỳ chủ sở hữu thương mại nào của Ngọc Rồng Online. Chỉ dùng cho mục đích **học tập và nghiên cứu**.

---

<p align="center">
  💥 Cảm ơn bạn đã quan tâm đến dự án Ngọc Rồng Online 💥
</p>

<p align="center">
  Nếu thấy hữu ích, hãy cho repo một ⭐ để ủng hộ mình nhé!
</p>

<p align="center">
  🎮 <a href="https://ngocrongdark.com">Chơi ngay tại ngocrongdark.com</a>
</p>

<p align="center">
  <a href="https://github.com/DANG-PH">👉 Xem thêm các dự án khác trên GitHub</a>
</p>
