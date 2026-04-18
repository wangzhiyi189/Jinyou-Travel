package com.jinyou.vo.app;

import lombok.Data;

@Data
public class BannerVO {
    private Long bannerId;          // 轮播图ID
    private String imgUrl;    // 图片地址
    private Integer linkType;     // 跳转类型
    private String linkUrl;   // 跳转链接
}
