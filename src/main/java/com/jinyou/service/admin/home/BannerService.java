package com.jinyou.service.admin.home;

import com.jinyou.DTO.admin.home.BannerQueryDTO;
import com.jinyou.pojo.admin.home.Banner;
import com.jinyou.pojo.PageBean;

public interface BannerService {
    //    条件分页列表查询banner图
    PageBean<Banner> list(BannerQueryDTO query);

    // 添加banner
    void add(Banner banner);
    // 修改banner信息
    void update(Banner banner);
    // 删除banner信息
    void delete(Long id);
    // 修改banner状态
    void updateStatus(Long bannerId, Integer status);
}
