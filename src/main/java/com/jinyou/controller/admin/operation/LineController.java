package com.jinyou.controller.admin.operation;

import com.jinyou.DTO.admin.operation.LineQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.pojo.admin.operation.Line;
import com.jinyou.service.admin.operation.LineService;
import com.jinyou.vo.admin.LineVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/line")
@Validated
public class LineController {
    @Autowired
    private LineService lineService;
    @GetMapping
    public Result <PageBean<Line>> list(LineQueryDTO query) {
        PageBean<Line> pb = lineService.list(query);
        return Result.success(pb);
    }
    @PostMapping("add")
    public Result<String> add(@RequestBody @Validated Line line) {
        lineService.add(line);
        return Result.success("添加成功");
    }

    @PutMapping("update")
    public Result<String> update(@RequestBody @Validated Line line) {
        lineService.update(line);
        return Result.success("修改成功");
    }

    @DeleteMapping("delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        lineService.delete(id);
        return Result.success("删除成功");
    }

    @PutMapping("update/status")
    public Result<String> updateStatus(@RequestBody Map<String,Integer> params) {
        Long lineId = Long.valueOf(params.get("lineId"));
        if(lineId == null){
            return Result.error("线路ID不能为空");
        }
        Integer status = params.get("status");
        lineService.updateStatus(lineId,status);
        return Result.success("修改成功");
    }

    // 查询所有线路 返回指定字段
    @GetMapping("/optionList")
    public Result<List<LineVO>> optionList() {
        // 1. 查询所有线路（不分页，给下拉框用）
        List<Line> lineList = lineService.listAll();
        List<LineVO> voList = lineList.stream().map(line -> {
            LineVO vo = new LineVO();
            vo.setLineId(line.getLineId());
            vo.setLineName(line.getLineName());
//            vo.setStationList(line.getStationList());
//            vo.setStartCity(line.getStartCity());
//            vo.setEndCity(line.getEndCity());
            return vo;
        }).collect(Collectors.toList());
        return Result.success(voList);
    }
}
