package com.jinyou.DTO.admin.home;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Banner查询条件DTO
 */
@Data
@ApiModel("Banner查询条件")
public class BannerQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // ========== 分页参数 ==========
    @ApiModelProperty("页码，默认1")
    private Integer pageNum = 1;

    @ApiModelProperty("每页条数，默认10")
    private Integer pageSize = 10;

    // ========== 现有搜索条件 ==========
    @ApiModelProperty("标题，模糊匹配")
    private String title;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("链接")
    private String linkUrl;
}