package com.jinyou.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jinyou.mapper.StationMapper;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Station;
import com.jinyou.service.StationService;
import com.jinyou.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationMapper stationMapper;
    public PageBean<Station> list(Integer pageNum, Integer pageSize, String stationName, Integer status) {
        PageBean<Station> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Station> stationList = (List<Station>) stationMapper.list(userId, stationName, status);
        Page<Station> page = (Page<Station>) stationList;
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());

        return pb;
    }

    @Override
    public void add(Station station) {
        station.setUpdateTime(LocalDateTime.now());
        station.setCreateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        station.setCreateUser(userId);
        stationMapper.add(station);
    }

    @Override
    public void update(Station station) {
        station.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        station.setCreateUser(userId);
        System.out.println("修改站点：" + station);
        stationMapper.update(station);
    }

    @Override
    public void delete(Integer id) {
        stationMapper.delete(id);
    }

    @Override
    public void updateStatus(Integer stationId, Integer status) {
        stationMapper.updateStatus(stationId,status);
    }
}
