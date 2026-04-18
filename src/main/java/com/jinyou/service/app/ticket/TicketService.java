package com.jinyou.service.app.ticket;

import com.jinyou.pojo.admin.ticket.ScheduleSearch;

import java.util.List;

public interface TicketService {
    List<ScheduleSearch> list(String startCity , String endCity , String departDate);
}
