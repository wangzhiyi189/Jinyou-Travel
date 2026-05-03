package com.jinyou.controller.app.order;


import com.jinyou.DTO.app.order.OrderListDTO;
import com.jinyou.DTO.app.order.OrderQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.pojo.app.order.PayRequest;
import com.jinyou.pojo.app.order.WxOrder;
import com.jinyou.service.app.order.OrderService;
import com.jinyou.utils.ThreadLocalUtil;
import com.jinyou.vo.app.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order")
@Validated
public class orderController {
    @Autowired
    OrderService orderService;
//    提交订单
    @PostMapping("submit")
    public Result<?> order(@RequestBody @Validated OrderVO orderVO){
        Result<String> order = orderService.submit(orderVO);
        return order;
    }
//    取消订单
    @PostMapping("cancel")
    public Result<String> cancel(@RequestBody String orderNo){
        orderService.cancel(orderNo);
        return Result.success("订单取消成功");
    }
    // 获取订单列表
    @GetMapping("list")
    public Result<PageBean<OrderListDTO>> list(OrderQueryDTO query){
        return Result.success(orderService.selectList(query));
    }

    //    查询订单详情
    @GetMapping("details/")
    public Result<WxOrder> getOrder(@RequestParam String orderNo){
        return Result.success(orderService.getOrder(orderNo));
    }
    // 支付订单
    @PostMapping("wxpay")
    public Result wxpay(@RequestBody PayRequest request){
        String orderNo = request.getOrderNo();
        return orderService.wxpay(orderNo);
    }
    // 删除订单数据
    @DeleteMapping("delete")
    public Result<String> delete(@RequestBody String orderNo){
        orderService.delete(orderNo);
        return Result.success("删除成功");
    }
}
