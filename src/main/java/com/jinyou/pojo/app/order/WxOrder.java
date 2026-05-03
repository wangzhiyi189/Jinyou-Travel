package com.jinyou.pojo.app.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jinyou.DTO.app.order.StationSimpleDTO;
import com.jinyou.pojo.admin.operation.Station;
import com.jinyou.utils.serializer.PhoneMaskSerializer;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 微信小程序订单实体
 * 对应数据库表：wx_order
 */
@Data
@TableName("wx_order")
public class WxOrder {

    // 主键ID
    private Long wxOrderId;

    // 订单编号（唯一，不可重复）
    private String orderNo;

    // 下单用户ID（小程序登录用户ID）
    private Long createUser;

    // ==================== 班次信息 ====================
    // 班次ID
    private Long scheduleId;
    // 出发站名称
    private String startStation;
    // 到达站名称
    private String endStation;
    // 发车时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime departTime;
    // 到达时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime arriveTime;
    // ==================== 上车点信息 ====================
    // 上车点ID（前端传）
    private Long boardPointId;
    // 上车点名称（快照，防止后台删除/改名后订单显示异常）
    private String boardPointName;

    // ==================== 票价信息 ====================
    // 购票数量
    private Integer ticketCount;
    // 车票单价（后端从班次表查询，不相信前端）
    private BigDecimal unitPrice;
    // 订单总金额（单价 × 票数，后端计算）
    private BigDecimal totalAmount;

    // ==================== 乘客信息（防删除方案） ====================
    // 乘客ID集合，逗号分隔 如：1,2,3
    private String passengerIds;
    // 乘客信息快照JSON（下单时的乘客信息，永久保存，删除乘客不影响订单）
    private String passengerSnapshot;

    // ==================== 联系人信息 ====================
    // 联系人手机号（接收通知）
    @JsonSerialize(using = PhoneMaskSerializer.class)
    private String contactPhone;
    // 订单备注（选填）
    private String remark;

    // ==================== 订单状态 ====================
    // 订单状态：0待支付 1已支付 2已出票 3已取消 4已退票
    private Integer orderStatus;
    // 支付状态：0未支付 1已支付
    private Integer payStatus;
    // 支付时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime payTime;
    // 订单过期时间（30分钟未支付自动取消）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime expireTime;

    // ==================== 系统时间 ====================
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime createTime;
    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime updateTime;
    private String stationList;       // 数据库JSON：[1,2,3]
    private List<StationSimpleDTO> stationInfo;
    private String plateNumber;   // 对应 plate_number
    private String vehicleType;
}