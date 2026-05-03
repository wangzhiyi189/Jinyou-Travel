package com.jinyou.DTO.app.order;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("order条件查询")
public class OrderQueryDTO {
    // ========== 分页参数 ==========
    @ApiModelProperty("页码，默认1")
    private Integer pageNum = 1;

    @ApiModelProperty("每页条数，默认10")
    private Integer pageSize = 10;

    @ApiModelProperty("订单状态 0待支付 1已支付 2已出票 3已取消 4已退票 5已出行")
    private Integer status;
}
