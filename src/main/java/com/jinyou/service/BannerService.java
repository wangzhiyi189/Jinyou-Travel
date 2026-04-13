package com.jinyou.service;

import com.jinyou.pojo.Banner;
import com.jinyou.pojo.PageBean;

import java.util.List;

public interface BannerService {
    //    条件分页列表查询banner图
    PageBean<Banner> list(Integer pageNum, Integer pageSize, String title, Integer status);

    // 添加banner
    void add(Banner banner);
    // 修改banner信息
    void update(Banner banner);
    // 删除banner信息
    void delete(Integer id);
    // 修改banner状态
    void updateStatus(Integer bannerId, Integer status);
}
