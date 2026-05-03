package com.jinyou.DTO.app.order;

import lombok.Data;

@Data
//查站点
public class StationSimpleDTO {
    private Long stationId;
    private String stationName;

    // 全参构造器，方便转换
    public StationSimpleDTO(Long stationId, String stationName) {
        this.stationId = stationId;
        this.stationName = stationName;
    }
}
