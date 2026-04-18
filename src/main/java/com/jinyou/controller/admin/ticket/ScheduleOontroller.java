package com.jinyou.controller.admin.ticket;

import com.jinyou.DTO.admin.ticket.ScheduleQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.pojo.admin.ticket.Schedule;
import com.jinyou.service.admin.ticket.ScheduleService;
import com.jinyou.vo.admin.ScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/schedule")
@Validated
public class ScheduleOontroller {
    @Autowired
    private ScheduleService scheduleService;
    @GetMapping
    public Result<PageBean<Schedule>> list(ScheduleQueryDTO query){
        PageBean<Schedule> pb = scheduleService.list(query);
        return Result.success(pb);
    }
    // 新增：返回带站点信息的VO接口
    @GetMapping("/vo")
    public Result<PageBean<ScheduleVO>> listVO(ScheduleQueryDTO query){
        PageBean<ScheduleVO> pb = scheduleService.listVO(query);
        return Result.success(pb);
    }
    @PostMapping("add")
    public Result<String> add(@RequestBody @Validated Schedule schedule){
        scheduleService.add(schedule);
        return Result.success("添加成功");
    }
    @PutMapping("update")
    public Result<String> update(@RequestBody @Validated Schedule schedule){
        scheduleService.update(schedule);
        return Result.success("修改成功");
    }

    @DeleteMapping("delete/{id}")
    public Result<String> delete(@PathVariable Long id){
        scheduleService.delete(id);
        return Result.success("删除成功");
    }

//    修改状态
    @PutMapping("update/status")
    public Result<String> updateStatus(@RequestBody Map<String,Integer> params){
        Long scheduleId = Long.valueOf(params.get("scheduleId"));
        if(scheduleId == null){
            return Result.error("线路ID不能为空");
        }
        Integer status = params.get("status");
        scheduleService.updateStatus(scheduleId,status);
        return Result.success("修改成功");
    }

//    修改是否推荐
    @PutMapping("update/isRecommend")
    public Result<String> updateIsRecommend(@RequestBody Map<String,Integer> params){
        Long scheduleId = Long.valueOf(params.get("scheduleId"));
        if(scheduleId == null){
            return Result.error("线路ID不能为空");
        }
        Integer isRecommend = params.get("isRecommend");
        scheduleService.updateIsRecommend(scheduleId,isRecommend);
        return Result.success("修改成功");
    }
}
