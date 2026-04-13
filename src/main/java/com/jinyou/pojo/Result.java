package com.jinyou.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 统一响应结果（Spring Boot 4.x 兼容规范版）
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code; // 业务状态码 200-成功 1-失败
    private String message; // 提示信息
    private T data; // 响应数据

    // 【关键修复】静态方法泛型<E>必须和类泛型<T>严格对应，显式声明泛型
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(400, message, null);
    }

    public static <T> Result<T> success(Integer code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }
}