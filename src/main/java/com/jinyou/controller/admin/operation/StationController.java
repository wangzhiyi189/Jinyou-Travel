package com.jinyou.controller.admin.operation;

import com.jinyou.DTO.admin.operation.StationQueryDTO;
import com.jinyou.pojo.admin.operation.Station;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.service.admin.operation.StationService;
import com.jinyou.vo.admin.StationVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/station")
@Validated
public class StationController {
    @Autowired
    private StationService stationService;
    @GetMapping
    public Result<PageBean<Station>> list(StationQueryDTO query){
        PageBean<Station> pb = stationService.list(query);
        return Result.success(pb);
    }
    @PostMapping("add")
    public Result<String> add(@RequestBody @Validated Station station){
        stationService.add(station);
        return Result.success("添加成功");
    }
    @PutMapping("update")
    public Result<String> update(@RequestBody @Validated Station station){
        stationService.update(station);
        return Result.success("修改成功");
    }
    @DeleteMapping("delete/{id}")
    public Result<String> delete(@PathVariable Long id){
        stationService.delete(id);
        return Result.success("删除成功");
    }
    @PutMapping("update/status")
    public Result<String> updateStatus(@RequestBody Map<String,Integer> params){
        Long stationId = Long.valueOf(params.get("stationId"));
        if(stationId == null){
            return Result.error("站点ID不能为空");
        }
        Integer status = params.get("status");
        stationService.updateStatus(stationId,status);
        return Result.success("修改成功");
    }

    @GetMapping("/optionList")
    public Result<List<StationVO>> optionList() {
        // 1. 查询所有线路（不分页，给下拉框用）
        List<Station> stationList = stationService.listAll();
        List<StationVO> voList = stationList.stream().map(station -> {
            StationVO vo = new StationVO();
            vo.setStationId(station.getStationId());
            vo.setStationName(station.getStationName());
            return vo;
        }).collect(Collectors.toList());
        return Result.success(voList);
    }
//    根据stationId数组查询站点
    @GetMapping("/listByStationIds")
    public Result<List<StationVO>> listByStationIds(@Param("stationIds[]") Integer[] stationIds){
        List<Station> stationList = stationService.listByStationIds(stationIds);
        List<StationVO> voList = stationList.stream().map(station -> {
            StationVO vo = new StationVO();
            vo.setStationId(station.getStationId());
            vo.setStationName(station.getStationName());
            vo.setLng(station.getLng());
            vo.setLat(station.getLat());
            vo.setCity(station.getCity());
            vo.setAddress(station.getAddress());
            return vo;
        }).collect(Collectors.toList());
        return Result.success(voList);
    }
}
