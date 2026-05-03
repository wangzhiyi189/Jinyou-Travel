package com.jinyou.service.admin.operation.impl;

import com.jinyou.DTO.admin.operation.StationQueryDTO;
import com.jinyou.mapper.admin.operation.StationMapper;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.admin.operation.Station;
import com.jinyou.service.admin.operation.StationService;
import com.jinyou.utils.EntityFillUtil;
import com.jinyou.utils.PageBeanUtil;
import com.jinyou.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationMapper stationMapper;
    public PageBean<Station> list(StationQueryDTO query) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        return PageBeanUtil.toPageBean(query.getPageNum(), query.getPageSize(),
                () -> stationMapper.list(userId, query)
        );
    }

    @Override
    public void add(Station station) {
        EntityFillUtil.fillCreateFields(station);
        stationMapper.add(station);
    }

    @Override
    public void update(Station station) {
        EntityFillUtil.fillUpdateFields(station);
        stationMapper.update(station);
    }

    @Override
    public void delete(Long id) {
        stationMapper.delete(id);
    }

    @Override
    public void updateStatus(Long stationId, Integer status) {
        stationMapper.updateStatus(stationId,status);
    }

    @Override
    public List<Station> listAll() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        return stationMapper.listAll(userId);
    }

    @Override
    public List<Station> listByStationIds(Integer[] stationIds) {
        return stationMapper.listByStationIds(stationIds);
    }

    @Override
    public Station getDetail(Long stationId) {
        return stationMapper.getDetail(stationId);
    }
}
