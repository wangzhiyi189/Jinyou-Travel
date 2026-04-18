package com.jinyou.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ScheduleVO {

    // ======================== 原 Schedule 所有字段（完整复制，一个不漏） ========================
    private Long scheduleId;       // 车次ID
    private Integer createUser;       // 创建人ID
    private String scheduleName;      // 车次名称
    private Long lineId;           // 线路ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime departTime; // 发车时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime arriveTime; // 到达时间
    private String duration;          // 运行时长
    private String plateNumber;       // 车牌号
    private String vehicleType;       // 车型
    private BigDecimal price;         // 票价
    private Integer seatTotal;        // 总座位数
    private Integer seatRemaining;    // 剩余座位
    private Integer status;           // 状态 0-禁用 1-启用
    private Integer isRecommend;      // 是否推荐
    private Integer sort;             // 排序号
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private String remark;            // 备注

    // ======================== 新增线路/站点字段（完全按你的需求） ========================
    private String startCity;         // 出发城市
    private String endCity;           // 到达城市
    private List<StationInfo> stationList; // 完整站点列表（按线路顺序，含始发/途经/终点）

    // 内部静态类，用来封装站点信息（id + name，前端直接用）
    @Data
    public static class StationInfo {
        private Integer stationId;   // 站点ID
        private String stationName;  // 站点名称
        private Double lng;
        private Double lat;
        private String address;
    }
}