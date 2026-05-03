package com.jinyou.service.app.ticket;

import com.jinyou.pojo.admin.ticket.ScheduleSearch;
import com.jinyou.pojo.admin.ticket.Schedule;
import com.jinyou.vo.admin.ScheduleVO;

import java.util.List;

public interface TicketService {
    List<ScheduleSearch> list(String startCity , String endCity , String departDate);

    ScheduleVO details(Long scheduleId);
}
