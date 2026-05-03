package com.jinyou.service.admin.ticket;

import com.jinyou.DTO.admin.ticket.OrderAdminQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.app.order.WxOrder;

public interface OrderAdminService {
    PageBean<WxOrder> list(OrderAdminQueryDTO query);
}
