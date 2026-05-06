package com.dang.dragonboy.he_thong;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = AppConfig.class.getResourceAsStream("/secrets.properties")) {
            if (in == null) {
                throw new RuntimeException("Không tìm thấy secrets.properties");
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi đọc secrets.properties", e);
        }
    }

    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Thiếu config: " + key);
        }
        return value;
    }
}
