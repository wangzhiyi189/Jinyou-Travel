package com.jinyou.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jinyou.mapper.BannerMapper;
import com.jinyou.pojo.Banner;
import com.jinyou.pojo.PageBean;
import com.jinyou.service.BannerService;
import com.jinyou.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public PageBean<Banner> list(Integer pageNum, Integer pageSize, String title, Integer status) {
        // 1. 创建分页结果对象
        PageBean<Banner> pb = new PageBean<>();
        // 2. 开启 PageHelper 分页
        PageHelper.startPage(pageNum, pageSize);
        // 3. 获取当前登录用户ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        // 4. 查询数据
        List<Banner> bannerList = (List<Banner>) bannerMapper.list(userId, title, status);
        // 5. 强转 —— 这里必须是 PageHelper 的 Page！
        Page<Banner> page = (Page<Banner>) bannerList;
        // 6. 封装结果
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());
        return pb;
    }
    @Override
    public void add(Banner banner) {
        banner.setUpdateTime(LocalDateTime.now());
        banner.setCreateTime(LocalDateTime.now());

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        banner.setCreateUser(userId);
        System.out.println("添加轮播图：" + banner);
        bannerMapper.add(banner);
    }

    @Override
    public void update(Banner banner) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        banner.setCreateUser(userId);
        banner.setUpdateTime(LocalDateTime.now());
        bannerMapper.update(banner);
    }

    @Override
    public void delete(Integer id) {
        bannerMapper.delete(id);
    }

    @Override
    public void updateStatus(Integer bannerId, Integer status) {
        bannerMapper.updateStatus(bannerId,status);
    }
}
