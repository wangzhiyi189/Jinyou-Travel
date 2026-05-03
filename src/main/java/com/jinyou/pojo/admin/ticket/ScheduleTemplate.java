package com.jinyou.pojo.admin.ticket;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jinyou.vo.admin.ScheduleTemplateVO;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 车次模板表 POJO
 * 支持 6种发车模式 + 自动生成开关
 */
@Data
@TableName("bus_schedule_template")
public class ScheduleTemplate {

    /**
     * 模板主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long templateId;

    /**
     * 车次名称
     */
    private String scheduleName;

    /**
     * 关联线路ID
     */
    private Integer lineId;

    /**
     * 模板发车时分 如 08:00:00
     */
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime departTime;

    /**
     * 模板到达时分 如 06:30:00
     */
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime arriveTime;

    /**
     * 运营基准日期
     */
    private LocalDate runStartDate;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 车型
     */
    private String vehicleType;

    /**
     * 标准票价
     */
    private java.math.BigDecimal price;

    /**
     * 总座位数
     */
    private Integer seatTotal;

    /**
     * 是否自动生成 1=是 0=否
     */
    private Integer autoCreate;

    /**
     * 发车模式
     * 1=指定日期 2=每日发车 3=间隔N天 4=每周固定 5=常驻滚动 6=节假日
     */
    private Integer runMode;

    /**
     * 指定发车日期 逗号分隔
     */
    private String assignDates;

    /**
     * 间隔天数 1=每天 2=隔天
     */
    private Integer intervalDay;

    /**
     * 周几发车 1=周一 ... 7=周日 逗号分隔
     */
    private String weekDays;

    /**
     * 常驻保留天数 7/8/9
     */
    private Integer keepDays;

    /**
     * 是否推荐 1=是 0=否
     */
    private Integer isRecommend;

    /**
     * 状态 1=启用 0=停用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人ID
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime updateTime;

    // ======================== 新增线路/站点字段（完全按你的需求） ========================
    private String startCity;         // 出发城市
    private String endCity;           // 到达城市
    private List<ScheduleTemplateVO.StationInfo> stationList; // 完整站点列表（按线路顺序，含始发/途经/终点）

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