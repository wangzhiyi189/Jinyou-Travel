package com.jinyou.DTO.admin.ticket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单条件查询")
public class OrderAdminQueryDTO {
    // ========== 分页参数 ==========
    @ApiModelProperty("页码，默认1")
    private Integer pageNum = 1;

    @ApiModelProperty("每页条数，默认10")
    private Integer pageSize = 10;

    // ========== 现有搜索条件 ==========
    @ApiModelProperty("订单编号")
    private String orderNo;
    @ApiModelProperty("联系人手机号")
    private String contactPhone;
    @ApiModelProperty("订单状态：0待支付 1已支付 2已出票 3已取消 4已退票")
    private Integer orderStatus;
}
