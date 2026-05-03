package com.jinyou.mapper.admin.ticket;

import com.jinyou.DTO.admin.ticket.ScheduleTemplateQueryDTO;
import com.jinyou.pojo.admin.ticket.ScheduleTemplate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleTemplateMapper {
    List<ScheduleTemplate> list(Long userId, ScheduleTemplateQueryDTO query);
    // 添加
    void add(ScheduleTemplate scheduleTemplate);
}
