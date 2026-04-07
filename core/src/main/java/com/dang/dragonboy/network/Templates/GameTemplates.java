package com.dang.dragonboy.network.Templates;

public class GameTemplates {
    public static final String LOADING_PAGE = """
    <html>
    <head>
        <meta charset="UTF-8">
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Orbitron:wght@500;700&display=swap');

            * { margin: 0; padding: 0; box-sizing: border-box; }

            body {
                background: #050505;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                font-family: 'Orbitron', sans-serif;
                color: #ffcc00;
                overflow: hidden;
            }

            .loading-wrapper {
                text-align: center;
                position: relative;
            }

            /* Hiệu ứng hào quang rực sáng phía sau */
            .aura {
                position: absolute;
                top: 50%; left: 50%;
                transform: translate(-50%, -50%);
                width: 200px; height: 200px;
                background: radial-gradient(circle, rgba(255,204,0,0.4) 0%, transparent 70%);
                border-radius: 50%;
                animation: pulse 1.5s infinite ease-in-out;
                z-index: -1;
            }

            @keyframes pulse {
                0% { transform: translate(-50%, -50%) scale(1); opacity: 0.5; }
                50% { transform: translate(-50%, -50%) scale(2); opacity: 0.8; }
                100% { transform: translate(-50%, -50%) scale(1); opacity: 0.5; }
            }

            h1 {
                font-size: 1.5rem;
                letter-spacing: 5px;
                text-shadow: 0 0 10px #ffcc00;
                margin-bottom: 20px;
                animation: blink 1s infinite;
            }

            @keyframes blink {
                50% { opacity: 0.5; }
            }

            /* Thanh Power Bar kiểu Saiyan */
            .power-meter {
                width: 300px;
                height: 8px;
                background: rgba(255,255,255,0.1);
                border: 1px solid #ffcc00;
                border-radius: 4px;
                overflow: hidden;
                position: relative;
            }

            .power-fill {
                height: 100%;
                width: 0%;
                background: linear-gradient(90deg, #ffcc00, #ff8000);
                box-shadow: 0 0 15px #ffcc00;
                animation: fillBar 2s ease-in-out forwards;
            }

            @keyframes fillBar {
                0% { width: 0%; }
                100% { width: 100%; }
            }

            .status-text {
                margin-top: 15px;
                font-size: 12px;
                color: #aaa;
                text-transform: uppercase;
            }
        </style>
    </head>
    <body>
        <div class="loading-wrapper">
            <div class="aura"></div>
            <h1>Đang xử lí...</h1>
            <div class="power-meter">
                <div class="power-fill"></div>
            </div>
            <p class="status-text">Đang đồng bộ hóa dữ liệu người chơi</p>
        </div>

        <script>
            // Xử lý redirect sau một khoảng trễ ngắn để người dùng kịp thấy hiệu ứng "phê"
            setTimeout(() => {
                const hash = window.location.hash.substring(1);
                if (hash) {
                    window.location.href = '/callback?' + hash;
                } else {
                    // Nếu không có hash, có thể xử lý lỗi hoặc redirect mặc định
                    console.log("Không tìm thấy dữ liệu xác thực.");
                }
            }, 1500); // Đợi 1.5 giây cho "ngầu"
        </script>
    </body>
    </html>
    """;

    public static final String ERROR_PAGE = """
    <html>
    <head>
        <meta charset="UTF-8">
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Cinzel:wght@700&family=Rajdhani:wght@400;600&display=swap');
            * { margin: 0; padding: 0; box-sizing: border-box; }

            body {
                font-family: 'Rajdhani', sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background: radial-gradient(ellipse at center, #2b0000 0%, #050000 70%);
                overflow: hidden;
            }

            .particles { position: fixed; width: 100%; height: 100%; pointer-events: none; }
            .particle {
                position: absolute;
                width: 3px; height: 3px;
                border-radius: 50%;
                animation: float linear infinite;
                opacity: 0;
            }
            @keyframes float {
                0%   { transform: translateY(100vh) scale(0); opacity: 0; }
                10%  { opacity: 1; }
                90%  { opacity: 1; }
                100% { transform: translateY(-10vh) scale(1.5); opacity: 0; }
            }

            .card {
                position: relative;
                background: linear-gradient(145deg, rgba(50,5,5,0.95), rgba(10,0,0,0.98));
                border: 1px solid rgba(200,30,30,0.4);
                border-radius: 20px;
                padding: 48px 56px;
                text-align: center;
                box-shadow:
                    0 0 40px rgba(200, 30, 30, 0.25),
                    0 0 80px rgba(255, 80, 0, 0.1),
                    inset 0 1px 0 rgba(255,255,255,0.05);
                animation: appear 0.6s ease-out;
            }
            @keyframes appear {
                from { opacity: 0; transform: scale(0.85) translateY(20px); }
                to   { opacity: 1; transform: scale(1) translateY(0); }
            }

            .corner { position: absolute; width: 16px; height: 16px; border-color: #cc0000; border-style: solid; }
            .corner.tl { top: 10px; left: 10px;   border-width: 2px 0 0 2px; }
            .corner.tr { top: 10px; right: 10px;  border-width: 2px 2px 0 0; }
            .corner.bl { bottom: 10px; left: 10px;  border-width: 0 0 2px 2px; }
            .corner.br { bottom: 10px; right: 10px; border-width: 0 2px 2px 0; }

            .avatar-ring { position: relative; width: 110px; height: 110px; margin: 0 auto 24px; }
            .avatar-ring::before {
                content: '';
                position: absolute;
                inset: -4px;
                border-radius: 50%;
                /* Hào quang đỏ - hồng đỏ - cam lửa của Goku God */
                background: conic-gradient(#cc0000, #ff4500, #ff6b6b, #cc0000);
                animation: spin 2s linear infinite;
            }
            @keyframes spin { to { transform: rotate(360deg); } }

            .avatar-ring img {
                position: relative;
                width: 100%; height: 100%;
                border-radius: 50%;
                object-fit: cover;
                object-position: center top;
                border: 3px solid #050000;
                z-index: 1;
            }

            .badge {
                display: inline-block;
                background: linear-gradient(90deg, #cc0000, #880000);
                color: #fff;
                font-size: 11px;
                font-weight: 600;
                letter-spacing: 3px;
                text-transform: uppercase;
                padding: 4px 14px;
                border-radius: 20px;
                margin-bottom: 16px;
            }

            h2 {
                font-family: 'Cinzel', serif;
                font-size: 26px;
                color: #fff;
                text-shadow: 0 0 15px rgba(200, 30, 30, 0.9);
                margin-bottom: 10px;
                letter-spacing: 1px;
            }

            p { color: #ff9999; font-size: 15px; letter-spacing: 1px; }

            .divider {
                width: 60px; height: 1px;
                background: linear-gradient(90deg, transparent, #cc0000, transparent);
                margin: 20px auto;
            }

            .power-bar { width: 180px; height: 4px; background: rgba(255,255,255,0.1); border-radius: 2px; margin: 16px auto 0; overflow: hidden; }
            .power-fill {
                height: 100%; width: 0%;
                background: linear-gradient(90deg, #cc0000, #ff6b6b);
                border-radius: 2px;
                animation: fill 1.2s ease-out 0.3s forwards;
                box-shadow: 0 0 8px #cc0000;
            }
            @keyframes fill { to { width: 100%; } }
        </style>
    </head>
    <body>
        <div class="particles" id="particles"></div>

        <div class="card">
            <div class="corner tl"></div>
            <div class="corner tr"></div>
            <div class="corner bl"></div>
            <div class="corner br"></div>

            <div class="avatar-ring">
                <img src="https://avatarfiles.alphacoders.com/857/85715.png" alt="goku god"/>
            </div>

            <div class="badge">Ngọc Rồng Online</div>

            <h2>Đăng Nhập Thất Bại</h2>

            <div class="divider"></div>

            <p>Đã hủy hoặc có lỗi xảy ra.</p>
            <p>Quay lại game và thử lại!</p>

            <div class="power-bar">
                <div class="power-fill"></div>
            </div>
        </div>

        <script>
            const container = document.getElementById('particles');
            for (let i = 0; i < 30; i++) {
                const p = document.createElement('div');
                p.className = 'particle';
                p.style.left = Math.random() * 100 + 'vw';
                p.style.animationDuration = (3 + Math.random() * 4) + 's';
                p.style.animationDelay = (Math.random() * 5) + 's';
                // Tông đỏ - hồng đỏ - cam lửa
                const colors = ['#cc0000','#ff4500','#ff6b6b','#ffaaaa'];
                p.style.background = colors[Math.floor(Math.random() * colors.length)];
                container.appendChild(p);
            }
        </script>
    </body>
    </html>
    """;

    public static final String SUCCESS_PAGE = """
    <html>
    <head>
        <meta charset="UTF-8">
        <style>
                @import url('https://fonts.googleapis.com/css2?family=Cinzel:wght@700&family=Rajdhani:wght@400;600&display=swap');
                * { margin: 0; padding: 0; box-sizing: border-box; }

                body {
                    font-family: 'Rajdhani', sans-serif;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                    /* Chuyển sang tông Nâu Đen - Cam Cháy */
                    background: radial-gradient(ellipse at center, #2b1a05 0%, #0a0500 70%);
                    overflow: hidden;
                }

                .particles { position: fixed; width: 100%; height: 100%; pointer-events: none; }
                .particle {
                    position: absolute;
                    width: 3px; height: 3px;
                    background: #ffcc00; /* Tia chớp vàng */
                    border-radius: 50%;
                    animation: float linear infinite;
                    opacity: 0;
                }

                @keyframes float {
                    0%   { transform: translateY(100vh) scale(0); opacity: 0; }
                    10%  { opacity: 1; }
                    90%  { opacity: 1; }
                    100% { transform: translateY(-10vh) scale(1.5); opacity: 0; }
                }

                .card {
                    position: relative;
                    /* Nền tối sâu với ánh vàng nhẹ */
                    background: linear-gradient(145deg, rgba(40,25,0,0.95), rgba(10,5,0,0.98));
                    border: 1px solid rgba(255,204,0,0.4);
                    border-radius: 20px;
                    padding: 48px 56px;
                    text-align: center;
                    box-shadow:
                        0 0 40px rgba(255, 204, 0, 0.2),
                        0 0 80px rgba(255, 128, 0, 0.1),
                        inset 0 1px 0 rgba(255,255,255,0.05);
                    animation: appear 0.6s ease-out;
                }

                @keyframes appear {
                    from { opacity: 0; transform: scale(0.85) translateY(20px); }
                    to   { opacity: 1; transform: scale(1) translateY(0); }
                }

                .corner { position: absolute; width: 16px; height: 16px; border-color: #ffcc00; border-style: solid; }
                .corner.tl { top: 10px; left: 10px;  border-width: 2px 0 0 2px; }
                .corner.tr { top: 10px; right: 10px;  border-width: 2px 2px 0 0; }
                .corner.bl { bottom: 10px; left: 10px;  border-width: 0 0 2px 2px; }
                .corner.br { bottom: 10px; right: 10px;  border-width: 0 2px 2px 0; }

                .avatar-ring { position: relative; width: 110px; height: 110px; margin: 0 auto 24px; }
                .avatar-ring::before {
                    content: '';
                    position: absolute;
                    inset: -4px;
                    border-radius: 50%;
                    /* Màu hào quang: Vàng - Cam - Cyan (mắt) */
                    background: conic-gradient(#ffcc00, #ff8000, #5eead4, #ffcc00);
                    animation: spin 2s linear infinite;
                }

                @keyframes spin { to { transform: rotate(360deg); } }

                .avatar-ring img {
                    position: relative;
                    width: 100%; height: 100%;
                    border-radius: 50%;
                    object-fit: cover;
                    border: 3px solid #0a0500;
                    z-index: 1;
                }

                .badge {
                    display: inline-block;
                    /* Màu võ phục Cam */
                    background: linear-gradient(90deg, #ff8000, #ff4500);
                    color: #fff;
                    font-size: 11px;
                    font-weight: 600;
                    letter-spacing: 3px;
                    text-transform: uppercase;
                    padding: 4px 14px;
                    border-radius: 20px;
                    margin-bottom: 16px;
                }

                h2 {
                    font-family: 'Cinzel', serif;
                    font-size: 26px;
                    color: #fff;
                    text-shadow: 0 0 15px rgba(255, 204, 0, 0.8);
                    margin-bottom: 10px;
                    letter-spacing: 1px;
                }

                p { color: #fcd34d; font-size: 15px; letter-spacing: 1px; }

                .divider {
                    width: 60px; height: 1px;
                    background: linear-gradient(90deg, transparent, #ffcc00, transparent);
                    margin: 20px auto;
                }

                .power-bar { width: 180px; height: 4px; background: rgba(255,255,255,0.1); border-radius: 2px; margin: 16px auto 0; overflow: hidden; }
                .power-fill {
                    height: 100%; width: 0%;
                    background: linear-gradient(90deg, #ffcc00, #5eead4);
                    border-radius: 2px;
                    animation: fill 1.2s ease-out 0.3s forwards;
                    box-shadow: 0 0 8px #ffcc00;
                }
                @keyframes fill { to { width: 100%; } }
            </style>
    </head>
    <body>

        <div class="particles" id="particles"></div>

        <div class="card">
            <div class="corner tl"></div>
            <div class="corner tr"></div>
            <div class="corner bl"></div>
            <div class="corner br"></div>

            <div class="avatar-ring">
                <img src="https://scontent-hkg1-1.xx.fbcdn.net/v/t39.30808-6/661003029_122163385442884727_1700703607514535178_n.webp?_nc_cat=101&ccb=1-7&_nc_sid=1d70fc&_nc_eui2=AeF-AJ72F2DG-BuzDXdqS4cniBhxjBJM0LmIGHGMEkzQuduTo0_Hg8e0Td-LjRZuravwkl_2AdrkbxzB-K9HPbde&_nc_ohc=feedU-EbqawQ7kNvwHVVUU6&_nc_oc=AdrnwSbntQuWovRK2Yh4lEYSdXOI0Z2KI9nzlU5tJXdAgEB_IS6mBjhL2LM366Hw1AdDaJsNmUWbFbZlHUnOFALG&_nc_zt=23&_nc_ht=scontent-hkg1-1.xx&_nc_gid=G5F6VZNQEBp2ogmwPBzlRw&_nc_ss=7a3a8&oh=00_Af0JyYZ313Ot-DVMQkFxjZzG4tQlUT04RW4LPHIWoFM6Vg&oe=69D9A77A" alt="avatar"/>
            </div>

            <div class="badge">Ngọc Rồng Online</div>

            <h2>Đăng Nhập Thành Công</h2>

            <div class="divider"></div>

            <p>Quay lại game và chiến thôi!</p>

            <div class="power-bar">
                <div class="power-fill"></div>
            </div>
        </div>

        <script>
            const container = document.getElementById('particles');
            for (let i = 0; i < 30; i++) {
                const p = document.createElement('div');
                p.className = 'particle';
                p.style.left = Math.random() * 100 + 'vw';
                p.style.animationDuration = (3 + Math.random() * 4) + 's';
                p.style.animationDelay = (Math.random() * 5) + 's';
                // Tông vàng, cam, trắng rực rỡ
                const colors = ['#ffcc00','#ff8000','#fff7ae','#ffffff'];
                p.style.background = colors[Math.floor(Math.random() * colors.length)];
                container.appendChild(p);
            }
        </script>
    </body>
    </html>
    """;
}
