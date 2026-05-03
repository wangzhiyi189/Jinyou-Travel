package com.jinyou.service.admin.ticket;

import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.jinyou.DTO.admin.ticket.ScheduleQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.admin.ticket.Schedule;
import com.jinyou.vo.admin.ScheduleVO;

import java.util.List;

public interface ScheduleService {
    PageBean<Schedule> list(ScheduleQueryDTO query);

    PageBean<ScheduleVO> listVO(ScheduleQueryDTO query);
    void add(Schedule schedule);
//    自动补齐后七天的车票
    void addAndFill7Days(Schedule schedule);
    void update(Schedule schedule);

    void delete(Long id);

    void deleteBatchByIds(List<Long> ids);

    void updateStatus(Long scheduleId, Integer status);

    void updateIsRecommend(Long scheduleId, Integer isRecommend);

    Schedule getDetail(Long scheduleId);

    int decreaseSeatRemaining(Long scheduleId, Integer ticketCount);

    int increaseSeatRemaining(Long scheduleId, Integer ticketCount);

    // 自动补齐后面的车票
    void batchAddSchedule(Schedule baseSchedule, Integer addDays);


}
