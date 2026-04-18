package com.jinyou.pojo.admin.home;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
// 轮播图
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    private Long bannerId;//主键ID
    private Integer createUser;//创建人ID
    @NonNull
    private String title;// 轮播图标题
    @NonNull
    private String imageUrl;//图片地址
    @NonNull
    private Integer linkType;//跳转类型 0-不跳转 1-小程序页面 2-外部链接
    private String linkUrl;//跳转地址
    @NonNull
    private Integer sort;//排序号 数字越小越靠前
    private Integer status;//状态 0-禁用 1-启用
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    private String remark; // 备注

}
