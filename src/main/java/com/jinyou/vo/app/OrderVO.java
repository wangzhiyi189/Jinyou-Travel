package com.jinyou.vo.app;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class OrderVO {
    @NonNull()
    private Long scheduleId;           // 班次ID
    @NonNull
    private Integer ticketCount;       // 购票数量
    @NonNull
    private Long boardPointId;         // 上车点ID
    @NonNull
    private String contactPhone;      // 联系电话
    @NonNull
    private String remark;            // 备注
    @NonNull
    private List<Long> passengerIdList; // 乘客ID列表
}
