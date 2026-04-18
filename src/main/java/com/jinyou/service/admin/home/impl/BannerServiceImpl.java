package com.jinyou.service.admin.home.impl;

import com.jinyou.DTO.admin.home.BannerQueryDTO;
import com.jinyou.mapper.admin.home.BannerMapper;
import com.jinyou.pojo.admin.home.Banner;
import com.jinyou.pojo.PageBean;
import com.jinyou.service.admin.home.BannerService;
import com.jinyou.utils.EntityFillUtil;
import com.jinyou.utils.PageBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public PageBean<Banner> list(BannerQueryDTO query) {
        return PageBeanUtil.toPageBeanWithUser(query.getPageNum(), query.getPageSize(),
                userId -> bannerMapper.list(userId, query)
        );
    }
    @Override
    public void add(Banner banner) {
        EntityFillUtil.fillCreateFields(banner);
        bannerMapper.add(banner);
    }

    @Override
    public void update(Banner banner) {
        EntityFillUtil.fillUpdateFields(banner);
        bannerMapper.update(banner);
    }

    @Override
    public void delete(Long id) {
        bannerMapper.delete(id);
    }

    @Override
    public void updateStatus(Long bannerId, Integer status) {
        bannerMapper.updateStatus(bannerId,status);
    }
}
