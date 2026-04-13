package com.jinyou.service;

import com.jinyou.pojo.Station;
import com.jinyou.pojo.PageBean;

public interface StationService {
    PageBean<Station> list(Integer pageNum, Integer pageSize, String stationName, Integer status);
    void add(Station station);

    void update(Station station);

    void delete(Integer id);

    void updateStatus(Integer stationId, Integer status);
}
