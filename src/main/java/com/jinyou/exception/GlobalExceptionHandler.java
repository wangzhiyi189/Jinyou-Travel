package com.jinyou.exception;

import com.jinyou.pojo.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindException;

import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理 @RequestBody 参数校验异常（你现在用的就是这个）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String fieldName = fieldError.getField(); // 哪个字段错了
        String msg = fieldError.getDefaultMessage(); // 错误信息

        // 返回：字段 + 错误信息
        return Result.error(fieldName + "：" + msg);
    }

    /**
     * 处理单个参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String field = violation.getPropertyPath().toString();
            String msg = violation.getMessage();
            return Result.error(field + "：" + msg);
        }
        return Result.error("参数有误");
    }

    /**
     * 处理普通绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String fieldName = fieldError.getField();
        String msg = fieldError.getDefaultMessage();
        return Result.error(fieldName + "：" + msg);
    }
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "服务器异常");
    }
}
