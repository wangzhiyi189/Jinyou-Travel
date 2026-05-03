package com.jinyou.service.app.ticket.impl;


import com.jinyou.mapper.admin.ticket.ScheduleSearchMapper;
import com.jinyou.mapper.app.ticket.TicketMapper;
import com.jinyou.pojo.admin.ticket.ScheduleSearch;
import com.jinyou.service.app.ticket.TicketService;
import com.jinyou.vo.admin.ScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    ScheduleSearchMapper scheduleSearchMapper;
    @Autowired
    TicketMapper ticketMapper;
    @Override
    public List<ScheduleSearch> list(String startCity , String endCity , String departDate) {
        return scheduleSearchMapper.searchByCityAndDate(startCity , endCity , departDate);
    }

    @Override
    public ScheduleVO details(Long scheduleId) {
        return ticketMapper.details(scheduleId);
    }
}
