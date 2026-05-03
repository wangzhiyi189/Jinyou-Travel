package com.jinyou.service.admin.operation;

import com.jinyou.DTO.admin.operation.StationQueryDTO;
import com.jinyou.pojo.admin.operation.Station;
import com.jinyou.pojo.PageBean;

import java.util.List;

public interface StationService {


    PageBean<Station> list(StationQueryDTO query);
    void add(Station station);

    void update(Station station);

    void delete(Long id);

    void updateStatus(Long stationId, Integer status);

    List<Station> listAll();

    List<Station> listByStationIds(Integer[] stationIds);

    Station getDetail(Long stationId);
}
