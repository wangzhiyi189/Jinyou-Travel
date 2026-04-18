package com.jinyou.pojo.admin.ticket;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ScheduleSearch {
    /**
     * 主键ID（自增）
     */
    private Long scheduleSearchId;

    /**
     * 车次ID，对应bus_schedule表的主键schedule_id
     */
    private Long scheduleId;

    /**
     * 线路ID
     */
    private Long lineId;


    /**
     * 出发城市
     */
    private String startCity;

    /**
     * 到达城市
     */
    private String endCity;

    /**
     * 发车时间（yyyy-MM-dd HH:mm:ss）
     */
    private LocalDateTime departTime;

    /**
     * 到达时间（yyyy-MM-dd HH:mm:ss）
     */
    private LocalDateTime arriveTime;

    private String duration;// 运行时长 例：3h20m
    /**
     * 票价
     */
    private BigDecimal price;

    /**
     * 剩余票数（余票）
     */
    private Integer seatRemaining;

    /**
     * 状态 1=正常售票 0=禁用/停运
     */
    private Integer status;
}
