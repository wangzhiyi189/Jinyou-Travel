package com.jinyou.pojo.admin.ticket;
// 车次
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Long scheduleId;// 车次ID
    private Long createUser;// 创建人ID
    @NonNull
    private String scheduleName;// 车次名称
    @NonNull
    private Long lineId;// 线路列表
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime departTime;// 发车时间（日期+时间 如 2026-04-17 08:00:00）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime arriveTime;// 到达时间
    private String duration;// 运行时长 例：3h20m
    private String plateNumber;// 车牌号
    private String vehicleType;// 车型：大巴/中巴/卧铺/商务车
    @NonNull
    private BigDecimal price;// 票价
    @NonNull
    private Integer seatTotal;// 总座位数
    @NonNull
    private Integer seatRemaining;// 剩余座位
    private Integer status;// 状态 0-禁用 1-启用
    private Integer isRecommend;// 是否推荐 0-否 1-是
//    private Integer sort;// 排序号 数字越小越靠前
    private LocalDateTime createTime;// 创建时间
    private LocalDateTime updateTime;// 更新时间
    private String remark;// 备注
}
