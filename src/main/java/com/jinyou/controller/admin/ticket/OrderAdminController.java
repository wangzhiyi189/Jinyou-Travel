package com.jinyou.controller.admin.ticket;

import com.jinyou.DTO.admin.ticket.OrderAdminQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.pojo.app.order.WxOrder;
import com.jinyou.service.admin.ticket.OrderAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Validated
public class OrderAdminController {
    @Autowired
    OrderAdminService orderService;
    @GetMapping("list")
    public Result<PageBean<WxOrder>> list(OrderAdminQueryDTO query){
        PageBean<WxOrder> pb = orderService.list(query);
        return Result.success(pb);
    }
}
