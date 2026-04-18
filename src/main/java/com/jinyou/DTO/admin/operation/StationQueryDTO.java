package com.jinyou.DTO.admin.operation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Station查询条件")
public class StationQueryDTO {
    private static final long serialVersionUID = 1L;

    // ========== 分页参数 ==========
    @ApiModelProperty("页码，默认1")
    private Integer pageNum = 1;

    @ApiModelProperty("每页条数，默认10")
    private Integer pageSize = 10;

    // ========== 现有搜索条件 ==========
    @ApiModelProperty("标题，模糊匹配")
    private String stationName;

    @ApiModelProperty("状态")
    private Integer status;

}
