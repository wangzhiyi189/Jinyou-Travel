package com.jinyou.service.admin.ticket;

import com.jinyou.DTO.admin.ticket.ScheduleTemplateQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.admin.ticket.ScheduleTemplate;

public interface ScheduleTemplateService {
    PageBean<ScheduleTemplate> list(ScheduleTemplateQueryDTO query);
    // 添加模板
    void add(ScheduleTemplate scheduleTemplate);
}
