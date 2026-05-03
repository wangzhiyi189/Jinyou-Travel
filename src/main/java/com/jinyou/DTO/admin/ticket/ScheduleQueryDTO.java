package com.jinyou.DTO.admin.ticket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Schedule条件查询")
public class ScheduleQueryDTO {
    private static final long serialVersionUID = 1L;

    // ========== 分页参数 ==========
    @ApiModelProperty("页码，默认1")
    private Integer pageNum = 1;

    @ApiModelProperty("每页条数，默认10")
    private Integer pageSize = 10;

    // ========== 现有搜索条件 ==========
    @ApiModelProperty("标题，模糊匹配")
    private String scheduleName;

    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("发车时间")
    private String departTime;
    @ApiModelProperty("到达时间")
    private String arriveTime;
}
