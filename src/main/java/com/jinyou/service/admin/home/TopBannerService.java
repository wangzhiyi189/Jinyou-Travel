package com.jinyou.service.admin.home;

import com.jinyou.DTO.admin.home.TopBannerQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.admin.home.TopBanner;

public interface TopBannerService {
    PageBean<TopBanner> list(TopBannerQueryDTO query);

    void add(TopBanner topBanner);

    void update(TopBanner topBanner);

    void delete(Long id);

    void updateStatus(Long topBannerId, Integer status);
}
