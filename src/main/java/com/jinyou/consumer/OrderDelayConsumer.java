package com.jinyou.consumer;

import com.jinyou.mapper.admin.ticket.ScheduleSearchMapper;
import com.jinyou.mapper.app.order.OrderMapper;
import com.jinyou.pojo.app.order.WxOrder;
import com.jinyou.service.admin.ticket.ScheduleService;
import com.jinyou.service.app.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class OrderDelayConsumer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    ScheduleSearchMapper scheduleSearchMapper;

    @Autowired
    OrderService orderService;

    @PostConstruct
    public void startListen() {
        new Thread(() -> {
            while (true) {
                try {
                    long now = System.currentTimeMillis();

                    // 取出所有已到期的订单
                    Set<Object> orderSet = redisTemplate.opsForZSet()
                            .rangeByScore("order:delay:queue", 0, now);

                    System.out.println("订单：" + orderSet);
                    if (orderSet != null && !orderSet.isEmpty()) {
                        for (Object obj : orderSet) {
                            String orderNo = (String) obj;
                            orderService.cancel(orderNo);
//                            // 从 Redis 移除
//                            redisTemplate.opsForZSet().remove("order:delay:queue", orderNo);
//
//                            // 查询订单
//                            WxOrder order = orderMapper.selectByOrderNo(orderNo);
//                            if (order == null) continue;
//
//                            // 只有待支付的订单才取消
//                            if (order.getOrderStatus() == 0) {
//                                // 更新订单状态为已取消
//                                order.setOrderStatus(3);
//                                orderMapper.updateById(order);
//
//                                // 归还余票
//                                scheduleService.increaseSeatRemaining(
//                                        order.getScheduleId(),
//                                        order.getTicketCount()
//                                );
//                                scheduleSearchMapper.increaseSeatRemaining(order.getScheduleId(),order.getTicketCount());
//                            }
                        }
                    }
                    autoUpdateTravelStatus();

                    // 每 1 秒轮询一次 Redis
                    Thread.sleep(60000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Order-Delay-Consumer").start();
    }

    /**
     * 自动更新：到达时间已过的车票 → 状态改为已出行
     */
    private void autoUpdateTravelStatus() {
        // 查询所有已支付的订单
        List<WxOrder> orderList = orderMapper.selectPayList();
        if (orderList == null || orderList.isEmpty()) {
            return;
        }
        for (WxOrder order : orderList) {
            // 到达时间已过
            orderMapper.updateOrderStatus(order.getOrderNo(),5);
            System.out.println("订单：" + order.getOrderNo() + " 已自动更新为【已出行】");
        }
    }
}