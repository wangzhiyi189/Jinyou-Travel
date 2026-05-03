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
    private String msg; // 提示信息
    private T data; // 响应数据

    // 【关键修复】静态方法泛型<E>必须和类泛型<T>严格对应，显式声明泛型
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(400, msg, null);
    }

    public static <T> Result<T> success(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> fail(String msg, T data) {
        return new Result<>(500, msg, data);
    }
}