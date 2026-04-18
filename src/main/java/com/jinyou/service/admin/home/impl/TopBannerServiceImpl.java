package com.jinyou.service.admin.home.impl;

import com.jinyou.DTO.admin.home.TopBannerQueryDTO;
import com.jinyou.mapper.admin.home.TopBannerMapper;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.admin.home.TopBanner;
import com.jinyou.service.admin.home.TopBannerService;
import com.jinyou.utils.EntityFillUtil;
import com.jinyou.utils.PageBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopBannerServiceImpl implements TopBannerService {
    @Autowired
    TopBannerMapper topBannerMapper;

    @Override
    public PageBean<TopBanner> list(TopBannerQueryDTO query) {
        return PageBeanUtil.toPageBeanWithUser(query.getPageNum(), query.getPageSize(),
                userId -> topBannerMapper.list(userId, query)
        );
    }

    @Override
    public void add(TopBanner topBanner) {
        EntityFillUtil.fillCreateFields(topBanner);
        topBannerMapper.add(topBanner);
    }

    @Override
    public void updateStatus(Long topBannerId, Integer status) {
        topBannerMapper.updateStatus(topBannerId,status);
    }

    @Override
    public void delete(Long id) {
        topBannerMapper.delete(id);
    }

    @Override
    public void update(TopBanner topBanner) {
        EntityFillUtil.fillUpdateFields(topBanner);
        topBannerMapper.update(topBanner);
    }


}
