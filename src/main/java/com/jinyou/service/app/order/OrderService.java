package com.jinyou.service.app.order;

import com.jinyou.DTO.app.order.OrderListDTO;
import com.jinyou.DTO.app.order.OrderQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.pojo.app.order.WxOrder;
import com.jinyou.vo.app.OrderVO;

public interface OrderService {
    // 提交表单
    Result<String> submit(OrderVO orderVO);
//    查询订单列表
    PageBean<OrderListDTO> selectList(OrderQueryDTO query);

    // 创建订单
    void insertOrder(WxOrder wxOrder);
//    查看订单详情
    WxOrder getOrder(String orderNo);
//支付订单
    Result wxpay(String orderNo);
//    取消订单
    void cancel(String orderNo);

    void delete(String orderNo);
}
