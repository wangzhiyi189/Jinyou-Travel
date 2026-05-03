package com.jinyou.controller.admin.ticket;

import com.jinyou.DTO.admin.ticket.ScheduleTemplateQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.pojo.admin.ticket.ScheduleTemplate;
import com.jinyou.service.admin.ticket.ScheduleTemplateService;
import com.jinyou.vo.admin.ScheduleTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduleTemplate")
@Validated
public class ScheduleTemplateController {

    @Autowired
    private ScheduleTemplateService scheduleTemplateService;
    // 返回列表
    @GetMapping("/list")
    public Result<PageBean<ScheduleTemplate>> list(ScheduleTemplateQueryDTO query){
        PageBean<ScheduleTemplate> pb = scheduleTemplateService.list(query);
        return Result.success(pb);
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody @Validated ScheduleTemplate ScheduleTemplate){
//
        System.out.println(ScheduleTemplate);
        scheduleTemplateService.add(ScheduleTemplate);
        return Result.success("添加成功");
    }
}
