package com.jinyou.controller;

import com.jinyou.pojo.Station;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/station")
@Validated
public class StationController {
    @Autowired
    private StationService stationService;
    @GetMapping
    public Result<PageBean<Station>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String station_name,
            @RequestParam(required = false) Integer status
    ){
        PageBean<Station> pb = stationService.list(pageNum,pageSize, station_name,status);
        System.out.println("获取站点列表：" + pb);
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
    public Result<String> delete(@PathVariable Integer id){
        stationService.delete(id);
        return Result.success("删除成功");
    }
    @PutMapping("update/status")
    public Result<String> updateStatus(@RequestBody Map<String,Integer> params){
        Integer stationId = params.get("stationId");
        if(stationId == null){
            return Result.error("站点ID不能为空");
        }
        Integer status = params.get("status");
        stationService.updateStatus(stationId,status);
        return Result.success("修改成功");
    }
}
