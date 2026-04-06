package com.jinyou.service.impl;

import com.jinyou.mapper.BannerMapper;
import com.jinyou.pojo.Banner;
import com.jinyou.service.BannerService;
import com.jinyou.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BannerServicelmpl implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;
    @Override
    public List<Banner> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return bannerMapper.list(userId);
    }
}
