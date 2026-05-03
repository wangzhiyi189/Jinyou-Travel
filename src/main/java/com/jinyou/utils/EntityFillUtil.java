package com.jinyou.utils;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 实体类公共字段自动填充工具类
 * 用于统一处理 createTime、updateTime、createUser 等公共字段
 */
public class EntityFillUtil {

    /**
     * 填充【新增】时的公共字段
     * @param entity 任意实体对象（通过反射赋值，兼容所有实体）
     */
    public static void fillCreateFields(Object entity) {
        // 1. 填充时间
        LocalDateTime now = LocalDateTime.now();
        setFieldValue(entity, "createTime", now);
        setFieldValue(entity, "updateTime", now);

        // 2. 填充创建人ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        setFieldValue(entity, "createUser", userId);
    }

    /**
     * 填充【更新】时的公共字段
     * @param entity 任意实体对象
     */
    public static void fillUpdateFields(Object entity) {
        // 1. 填充更新时间
        setFieldValue(entity, "updateTime", LocalDateTime.now());

        // 2. （可选）填充更新人ID，若需要可放开
         Map<String, Object> map = ThreadLocalUtil.get();
         Long userId = (Long) map.get("id");
         setFieldValue(entity, "createUser", userId);
    }

    /**
     * 反射工具：给对象的指定字段赋值
     */
    private static void setFieldValue(Object entity, String fieldName, Object value) {
        try {
            // 获取字段对象
            java.lang.reflect.Field field = entity.getClass().getDeclaredField(fieldName);
            // 打破访问权限
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            Object finalValue = value;

            if (value instanceof Long) {
                Long longVal = (Long) value;
                if (fieldType == Integer.class || fieldType == int.class) {
                    // 数据库实体是 Integer → 自动转成 Integer
                    finalValue = longVal.intValue();
                }
            }
            // 赋值
            field.set(entity, finalValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 字段不存在时静默跳过（兼容不同实体的字段差异）
            System.err.printf("字段 %s 不存在，跳过填充%n", fieldName);
        }
    }
}