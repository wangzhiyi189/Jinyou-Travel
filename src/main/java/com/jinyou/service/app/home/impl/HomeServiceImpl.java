package com.jinyou.service.app.home.impl;

import com.jinyou.mapper.app.home.HomeMapper;
import com.jinyou.pojo.admin.home.Banner;
import com.jinyou.pojo.admin.home.TopBanner;
import com.jinyou.service.app.home.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeMapper homeMapper;
    @Override
    public List<Banner> list() {
        return homeMapper.list();
    }

    @Override
    public List<TopBanner> listTop() {
        return homeMapper.listTop();
    }
}
