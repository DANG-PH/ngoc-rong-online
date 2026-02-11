package com.dang.dragonboy.websocket;

import com.dang.dragonboy.nhan_vat.NhanVat;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;
import java.util.*;

public class GameSocket {

    public static Socket socket;
    private static boolean eventsRegistered = false;

    public static void connect(String token) {
        if (isConnected()) return;

        try {
            IO.Options opts = new IO.Options();
            opts.transports = new String[]{"websocket"};

            // ✅ HEADER ĐÚNG KIỂU
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("Authorization", Collections.singletonList("Bearer " + token));
            opts.extraHeaders = headers;

            socket = IO.socket("http://localhost:3000/ws-game", opts);

            socket.on(Socket.EVENT_CONNECT, args -> {
                System.out.println("WS CONNECTED");
                if (!eventsRegistered) {
                    registerGameEvents();
                    eventsRegistered = true;
                }
            });

            socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
                System.out.println("WS CONNECT ERROR: " + args[0]);
            });

            socket.on(Socket.EVENT_DISCONNECT, args -> {
                System.out.println("WS DISCONNECTED");
            });

            socket.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerGameEvents() {
        socket.on("mapSnapshot", args -> {
            System.out.println("===== mapSnapshot =====");
            System.out.println("args.length = " + args.length);

            for (int i = 0; i < args.length; i++) {
                System.out.println("args[" + i + "] class = " + args[i].getClass());
                System.out.println("args[" + i + "] value = " + args[i]);
            }

            WorldState.onMapSnapshot(args);
        });
        socket.on("playerSpawn", args -> {
            System.out.println("===== playerSpawn =====");
            System.out.println(args[0]);
            WorldState.onPlayerSpawn(args);
        });
        socket.on("playerDespawn", args -> {
            System.out.println("===== playerDespawn =====");
            System.out.println(args[0]);
            WorldState.onPlayerDespawn(args);
        });
        socket.on("playerSync", WorldState::onPlayerSync);
        socket.on("playerChat", WorldState::onPlayerChat);
    }

    public static boolean isConnected() {
        return socket != null && socket.connected();
    }

    public static void guiSetMap(String oldMap, String newMap, NhanVat nhanVat) {
        try {
            JSONObject data = new JSONObject();
            data.put("oldMap", oldMap);
            data.put("map", newMap);
            data.put("x", nhanVat.getX());
            data.put("y", nhanVat.getY());
            data.put("trangthai", nhanVat.getTrangThai().name());
            data.put("dir", nhanVat.getFlipX() ? -1 : 1);
            data.put("dau", nhanVat.dauPath);
            data.put("than", nhanVat.thanPath);
            data.put("chan", nhanVat.chanPath);
            data.put("lechDauX", nhanVat.lechDauX);
            data.put("lechDauY", nhanVat.lechDauY);
            data.put("lechThanX", nhanVat.lechThanX);
            data.put("lechThanY", nhanVat.lechThanY);
            data.put("lechChanX", nhanVat.lechChanX);
            data.put("lechChanY", nhanVat.lechChanY);
            data.put("timeChoHienBay", nhanVat.timeChoHienBay);
            data.put("frameVanBay", nhanVat.frameVanBay);
            data.put("dangMangVanBay", nhanVat.dangMangVanBay);
            data.put("tenVanBay", nhanVat.tenVanBay);
            data.put("rong", nhanVat.rong);
            data.put("cao", nhanVat.cao);

            socket.emit("setMap", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void guiPlayerMove(NhanVat nhanVat) throws Exception {
        JSONObject data = new JSONObject();
        data.put("x", nhanVat.getX());
        data.put("y", nhanVat.getY());
        data.put("trangthai", nhanVat.getTrangThai().name());
        data.put("dir", nhanVat.getFlipX() ? -1 : 1);
        data.put("dau", nhanVat.dauPath);
        data.put("than", nhanVat.thanPath);
        data.put("chan", nhanVat.chanPath);
        data.put("lechDauX", nhanVat.lechDauX);
        data.put("lechDauY", nhanVat.lechDauY);
        data.put("lechThanX", nhanVat.lechThanX);
        data.put("lechThanY", nhanVat.lechThanY);
        data.put("lechChanX", nhanVat.lechChanX);
        data.put("lechChanY", nhanVat.lechChanY);
        data.put("timeChoHienBay", nhanVat.timeChoHienBay);
        data.put("frameVanBay", nhanVat.frameVanBay);
        data.put("dangMangVanBay", nhanVat.dangMangVanBay);
        data.put("tenVanBay", nhanVat.tenVanBay);
        data.put("rong", nhanVat.rong);
        data.put("cao", nhanVat.cao);

        socket.emit("player-move", data);
    }

    public static void guiChat(String tinNhan) throws Exception {
        JSONObject data = new JSONObject();
        data.put("message", tinNhan);

        socket.emit("player-chat", data);
    }
}
