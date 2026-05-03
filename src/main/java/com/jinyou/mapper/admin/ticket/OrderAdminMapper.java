package com.jinyou.mapper.admin.ticket;

import com.jinyou.DTO.admin.ticket.OrderAdminQueryDTO;
import com.jinyou.pojo.app.order.WxOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderAdminMapper {
//    查看订单列表
    List<WxOrder> list(@Param("userId") Long userId, @Param("query") OrderAdminQueryDTO query);
}
