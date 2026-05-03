package com.jinyou.DTO.app.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderListDTO {
    // 主键ID
    private Long wxOrderId;

    // 订单编号（唯一，不可重复）
    private String orderNo;
    // 发车时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime departureTime;
    // 到达时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime arriveTime;
    // 购票数量
    private Integer ticketCount;
    // 车票单价（后端从班次表查询，不相信前端）
    private BigDecimal unitPrice;
    // 订单总金额（单价 × 票数，后端计算）
    private BigDecimal totalAmount;
    // 订单状态：0待支付 1已支付 2已出票 3已取消 4已退票
    private Integer orderStatus;
    // 支付状态：0未支付 1已支付
    private Integer payStatus;
//    站点列表
    private String stationList;
    // 出发站名称
    private String startStation;
    // 到达站名称
    private String endStation;
}
