package com.jinyou.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    /**
     * 判断发车时间是否已过当前时间
     * @param departTime 发车时间字符串，格式：yyyy-MM-dd HH:mm:ss
     * @return true=已过期 / false=未过期
     */
    public static boolean isDepartTimePassed(LocalDateTime  departTime) {
        if (departTime == null) {
            return false; // 时间为null时，按“未过期”处理
        }
        LocalDateTime now = LocalDateTime.now();
        return departTime.isBefore(now);
    }
}