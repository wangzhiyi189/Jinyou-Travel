package com.jinyou.pojo.admin.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
//站点
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    private Long stationId;//主键ID
    private Integer createUser;//创建人ID
    @NonNull
    private String stationName;// 车站名称
    @NonNull
    private String city;//所属城市
    @NonNull
    private String address;//车站详细地址
    @NonNull
    private Double lng;//经度（地图专用）
    @NonNull
    private Double lat;//纬度（地图专用）
    @NonNull
    private Integer sort;//排序号 数字越小越靠前
    private Integer status;//状态 0-禁用 1-启用
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    private String remark; // 备注
}
