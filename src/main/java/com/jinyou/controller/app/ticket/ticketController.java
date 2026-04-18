package com.jinyou.controller.app.ticket;

import com.jinyou.pojo.Result;
import com.jinyou.pojo.admin.ticket.ScheduleSearch;
import com.jinyou.service.app.ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@Validated
public class ticketController {
    @Autowired
    TicketService ticketService;
    @GetMapping("list")
    public Result<List<ScheduleSearch>> list(
            @RequestParam String startCity,
            @RequestParam String endCity,
            @RequestParam String departDate
    ){
        List<ScheduleSearch> list = ticketService.list(startCity , endCity , departDate);
        return Result.success(list);

    }
}
