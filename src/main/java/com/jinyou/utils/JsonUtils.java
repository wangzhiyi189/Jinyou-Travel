package com.jinyou.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// 工具类转JSON
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()); // 支持 LocalDateTime

    public static String toJson(Object obj) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("JSON 序列化失败", e);
        }
    }

    public static Integer[] toIntArray(String str) {
        if (str == null || str.length() <= 2) return new Integer[0];
        String[] s = str.replaceAll("[\\[\\]]", "").split(",");
        Integer[] arr = new Integer[s.length];
        for (int i = 0; i < s.length; i++) arr[i] = Integer.valueOf(s[i].trim());
        return arr;
    }
}