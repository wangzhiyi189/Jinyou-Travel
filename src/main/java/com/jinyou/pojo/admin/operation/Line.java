package com.jinyou.pojo.admin.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
// 线路
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Line {
    private Long lineId;//路线ID
    private Integer createUser;//创建人ID
    @NonNull
    private String lineName;//路线名 北京→石家庄
    @NonNull
    private String stationList;//站点顺序数组，逗号分隔
    @NonNull
    private String startCity;//出发城市
    @NonNull
    private String endCity;//到达城市
    private Integer status;//状态 0-禁用 1-启用
    private LocalDateTime createTime;//创建时间
    private String remark;//备注
}
