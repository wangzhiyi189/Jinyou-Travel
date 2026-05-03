package com.jinyou.utils;

public class PhoneUtil {
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() != 11) {
            return phone;
        }
        // 核心：只保留前3位 + 后4位，中间变成****
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
}
