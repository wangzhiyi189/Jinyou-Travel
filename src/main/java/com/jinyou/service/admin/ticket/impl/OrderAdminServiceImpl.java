package com.jinyou.service.admin.ticket.impl;

import com.jinyou.DTO.admin.ticket.OrderAdminQueryDTO;
import com.jinyou.mapper.admin.ticket.OrderAdminMapper;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.app.order.WxOrder;
import com.jinyou.service.admin.ticket.OrderAdminService;
import com.jinyou.utils.PageBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderAdminServiceImpl implements OrderAdminService {
    @Autowired
    OrderAdminMapper orderMapper;
    @Override
    public PageBean<WxOrder> list(OrderAdminQueryDTO query) {
        return PageBeanUtil.toPageBeanWithUser(query.getPageNum(), query.getPageSize(),
                userId -> orderMapper.list(userId, query)
        );
    }
}
