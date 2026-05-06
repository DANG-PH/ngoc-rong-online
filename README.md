<p align="center">
  <img src="https://i.pinimg.com/originals/fd/91/b1/fd91b1715061efc79dbb6678aea0f9b9.gif" width="220" alt="Ngọc Rồng Online">
</p>

<h2 align="center">Ngọc Rồng Online – Java Multiplayer Game</h2>

<p align="center">
  Dự án cá nhân mô phỏng <strong>Ngọc Rồng Online</strong>,<br>
  xây dựng bằng <strong>Java (LibGDX)</strong> cho client và <strong>NestJS & Golang</strong> cho server.
</p>

<p align="center">
  <img src="https://i.pinimg.com/originals/fd/91/b1/fd91b1715061efc79dbb6678aea0f9b9.gif" width="220" alt="Ngọc Rồng Online">
</p>

<h2 align="center">Ngọc Rồng Online – Java Multiplayer Game</h2>

<p align="center">
  Dự án cá nhân tái hiện <strong>Ngọc Rồng Online</strong> – tựa game MMORPG hành động gắn liền với tuổi thơ của hàng triệu game thủ Việt,<br>
  lấy cảm hứng từ bộ truyện tranh huyền thoại <strong>Dragon Ball (7 Viên Ngọc Rồng)</strong> của tác giả Akira Toriyama.
</p>

---

## 🐉 Về Ngọc Rồng Online

Ngọc Rồng Online là tựa game nhập vai hành động trực tuyến (MMORPG) nơi người chơi hóa thân thành chiến binh của một trong **3 hành tinh**: Trái Đất, Namếc, hoặc Xayda — mỗi tộc sở hữu bộ kỹ năng và phong cách chiến đấu hoàn toàn riêng biệt.

Người chơi cùng nhau khám phá thế giới Dragon Ball quen thuộc: gặp lại Bunma, Quy Lão Kame, Piccolo, Broly... tham gia **Đại Hội Võ Thuật**, **đánh doanh trại Độc Nhãn**, và **săn lùng 7 viên Ngọc Rồng** để thực hiện điều ước. Hệ thống chiến đấu thời gian thực cho phép biến hình (Super Saiyan), kết hợp kỹ năng KI, và chiến đấu theo nhóm hoặc PvP 1v1 — giữ nguyên tinh thần shonen action của nguyên tác.

---

## 🖥️ Công nghệ & Lý do lựa chọn

### Client — Java + LibGDX

**LibGDX** là framework game Java đa nền tảng (PC, Android, iOS, Web), hiệu năng cao nhờ render bằng OpenGL. Phù hợp để xây dựng game 2D action với vòng lặp game chính xác, xử lý input mượt mà và hỗ trợ tileMap — đúng kiến trúc của Ngọc Rồng Online nguyên bản.

### Server — NestJS (Node.js)

**NestJS** đảm nhận phần API gateway và game logic (REST + WebSocket). Kiến trúc module hóa rõ ràng, decorator-based giúp dễ scale từng service riêng lẻ. Hỗ trợ WebSocket tích hợp sẵn — lý tưởng cho việc đồng bộ trạng thái người chơi theo thời gian thực.

### Server — Golang

**Golang** đảm nhận các service đòi hỏi hiệu năng cao: xử lý combat, tính toán damage, quản lý vị trí thực thể. Goroutine nhẹ hơn thread hệ điều hành nhiều lần, phù hợp khi cần xử lý hàng nghìn kết nối đồng thời với độ trễ thấp — yêu cầu sống còn của game multiplayer real-time.

---

<p align="center">
  💥 Cảm ơn bạn đã quan tâm đến dự án Ngọc Rồng Online 💥
</p>

<p align="center">
  <a href="https://github.com/DANG-PH">👉 Xem thêm các dự án khác trên GitHub</a>
</p>
