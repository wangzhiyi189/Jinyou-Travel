package com.jinyou.service.admin.ticket;

import com.jinyou.DTO.admin.ticket.ScheduleQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.admin.ticket.Schedule;
import com.jinyou.vo.admin.ScheduleVO;

public interface ScheduleService {
    PageBean<Schedule> list(ScheduleQueryDTO query);

    PageBean<ScheduleVO> listVO(ScheduleQueryDTO query);
    void add(Schedule schedule);

    void update(Schedule schedule);

    void delete(Long id);

    void updateStatus(Long scheduleId, Integer status);

    void updateIsRecommend(Long scheduleId, Integer isRecommend);
}
