package com.jinyou.vo.admin;

import lombok.Data;

/**
 * 线路列表返回VO（只返回指定字段）
 */
@Data
public class StationVO {
    private Long stationId;     // 站点ID
    private String stationName;      // 站点名称
    private Double lng;      // 经度
    private Double lat;        // 纬度
    private String city; // 所属城市
    private String address; // 详细地址
}