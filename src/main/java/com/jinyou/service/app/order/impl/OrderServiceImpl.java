package com.jinyou.service.app.order.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.jinyou.DTO.app.order.OrderListDTO;
import com.jinyou.DTO.app.order.OrderQueryDTO;
import com.jinyou.DTO.app.order.StationSimpleDTO;
import com.jinyou.mapper.admin.operation.StationMapper;
import com.jinyou.mapper.admin.ticket.ScheduleSearchMapper;
import com.jinyou.mapper.app.order.OrderMapper;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.pojo.admin.operation.Station;
import com.jinyou.pojo.admin.ticket.Schedule;
import com.jinyou.pojo.app.order.WxOrder;
import com.jinyou.pojo.app.user.Passenger;
import com.jinyou.service.admin.operation.LineService;
import com.jinyou.service.admin.operation.StationService;
import com.jinyou.service.admin.ticket.ScheduleService;
import com.jinyou.service.app.order.OrderService;
import com.jinyou.service.app.user.PassengerService;
import com.jinyou.utils.*;
import com.jinyou.vo.app.OrderVO;
import com.jinyou.vo.app.PassengetVO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    StationService stationService;
    @Autowired
    PassengerService passengerService;
    @Autowired
    LineService lineService;
    @Autowired
    ScheduleSearchMapper scheduleSearchMapper;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    StationMapper stationMapper;
//    提交订单
    @Override
    public Result<String> submit(OrderVO orderVO)  {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        WxOrder unPayOrder = orderMapper.getUnPayOrder(userId, orderVO.getScheduleId());
        Schedule schedule = scheduleService.getDetail(orderVO.getScheduleId());
        if (DateTimeUtils.isDepartTimePassed(schedule.getDepartTime())) {
            return Result.error("已过发车时间");
        }
        if (unPayOrder != null) {
            return Result.fail("您已有该车次未支付订单，请勿重复下单", unPayOrder.getOrderNo());
        }
        if(orderVO.getTicketCount() != orderVO.getPassengerIdList().size()){
            return Result.error("票数与乘客数量不匹配");
        }
        // ====================== 查询车次信息 ======================

        if(schedule == null) return Result.error("班次不存在");
        if(schedule.getSeatRemaining() < orderVO.getTicketCount()){
            return Result.error("余票不足，仅剩"+schedule.getSeatRemaining()+"张");
        }
        // 获取单价
        BigDecimal unitPrice = schedule.getPrice();
        // 计算总金额
        BigDecimal totalAmount = unitPrice.multiply(new BigDecimal(orderVO.getTicketCount()));
        // ====================== 查询站点信息 ======================
        Station station = stationService.getDetail(orderVO.getBoardPointId());
        // 获取上车站名称
        String boardPointName = station == null ? "" : station.getStationName();
        // ====================== 查询乘客信息 ======================
        List<Passenger> passengers = passengerService.getDetails(orderVO.getPassengerIdList());
        // 只提取姓名和手机号
        List<Map<String, Object>> simpleList = passengers.stream()
                .map(p -> {
                    Map<String, Object> hashmap = new HashMap<>();
                    hashmap.put("name", p.getName());
                    hashmap.put("phone", p.getPhone());
                    return hashmap;
                })
                .toList();
        // 乘客信息
        // 创建转json工具类
        String passengerSnapshot = JsonUtils.toJson(simpleList);
        String passengerIds = StringUtils.join(orderVO.getPassengerIdList(), ",");
        // 车次信息 可以不存
//        Line line = lineService.selectById(schedule.getLineId());
        // ====================== 6. 生成订单 ======================
        String orderNo = "ORDER" + System.currentTimeMillis();
        WxOrder order = new WxOrder();
        // 添加创建时间 下单用户id
        EntityFillUtil.fillCreateFields(order);
        // 订单
        order.setOrderNo(orderNo);
        // 车次id
        order.setScheduleId(schedule.getScheduleId());
        // 可以不存
        // 发车站
//        order.setStartStation(schedule.getStartStation());
        // 到达站
//        order.setEndStation(schedule.getEndStation());
        // 发车时间
        order.setDepartTime(schedule.getDepartTime());
        // 到达时间
        order.setArriveTime(schedule.getArriveTime());
        // 上车点id
        order.setBoardPointId(orderVO.getBoardPointId());
        // 上车点名称
        order.setBoardPointName(boardPointName);

        // 总价
        order.setTicketCount(orderVO.getTicketCount());
        // 单价
        order.setUnitPrice(unitPrice);
        // 购买数量
        order.setTotalAmount(totalAmount);

        // 乘客id列表
        order.setPassengerIds(passengerIds);
        // 乘客信息

        order.setPassengerSnapshot(passengerSnapshot);

        // 联系人
        order.setContactPhone(orderVO.getContactPhone());
        order.setRemark(orderVO.getRemark());

        // 状态
        order.setOrderStatus(0); // 待支付
        order.setPayStatus(0); // 未支付
        // 30秒后过期（测试用）
//        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(30);
        // 5分钟后过期
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(5);
        order.setExpireTime(expireTime);
        // ====================== 7. 扣减库存（防超卖） ======================
//        schedule.setSeatRemaining(schedule.getSeatRemaining() - orderVO.getTicketCount());
//        scheduleService.update(schedule);
        // ====================== 扣减库存（绝对安全，不超卖） ======================
        // 调用自定义 mapper 方法扣减余票
        int rows = scheduleService.decreaseSeatRemaining(
                orderVO.getScheduleId(),
                orderVO.getTicketCount()
        );

        if (rows == 0) {
            throw new RuntimeException("余票不足，当前剩余：" + schedule.getSeatRemaining() + " 张");
        }
        // 扣除搜索表的余票
        scheduleSearchMapper.decreaseSeatRemaining(orderVO.getScheduleId(),orderVO.getTicketCount());
        // ====================== 保存订单 ======================
        this.insertOrder(order);

//        System.out.println("订单信息"+order);
        // ====================== 9. 加入Redis延迟队列（5分钟未支付自动取消） ======================
        long expireMillis = expireTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        redisTemplate.opsForZSet().add("order:delay:queue", orderNo, expireMillis);
        return Result.success(orderNo);
    }

    // 添加订单
    @Override
    public void insertOrder(WxOrder wxOrder) {
        orderMapper.insertOrder(wxOrder);
    }
    // 查询订单列表
    @Override
    public PageBean<OrderListDTO> selectList(OrderQueryDTO query) {
        PageBean<WxOrder> pageBean = PageBeanUtil.toPageBeanWithUser(query.getPageNum(), query.getPageSize(),
                userId -> orderMapper.selectList(userId, query)
        );
        List<OrderListDTO> dtoList = new ArrayList<>();
        for (WxOrder order : pageBean.getItems()) {
            OrderListDTO dto = new OrderListDTO();
            dto.setWxOrderId(order.getWxOrderId());
            dto.setOrderNo(order.getOrderNo());
            dto.setDepartureTime(order.getDepartTime());
            dto.setArriveTime(order.getArriveTime());
            dto.setTicketCount(order.getTicketCount());
            dto.setUnitPrice(order.getUnitPrice());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setOrderStatus(order.getOrderStatus());
            dto.setPayStatus(order.getPayStatus());
            String stationListStr = order.getStationList();
            if (stationListStr != null) {
                // 直接把当前订单的 [3,5,4] 转成数组
                Integer[] ids = JsonUtils.toIntArray(stationListStr);

                // 直接查！
                List<StationSimpleDTO> stationInfo = stationMapper.listByStationIds(ids).stream()
                        .map(s -> new StationSimpleDTO(s.getStationId(), s.getStationName()))
                        .toList();
                // 塞给订单
//                order.setStationInfo(stationInfo);
                if (!stationInfo.isEmpty()) {
                    // 第一个 = 起点
                    dto.setStartStation(stationInfo.get(0).getStationName());
                    // 最后一个 = 终点
                    dto.setEndStation(stationInfo.get(stationInfo.size() - 1).getStationName());
                }
            }
            dtoList.add(dto);
        }
        PageBean<OrderListDTO> result = new PageBean<>();
        result.setItems(dtoList);
        result.setTotal(pageBean.getTotal());
        return result;
    }

    // ====================== 【定时任务】过期订单自动取消 ======================
//    @Scheduled(fixedRate = 30000)
//    public void autoCancelExpireOrder() {
//        LocalDateTime now = LocalDateTime.now();
//
//        // 查询待支付、已过期订单
//        List<WxOrder> expireList = orderMapper.selectListByExpire(now);
//
//        if (expireList.isEmpty()) return;
//
//        for (WxOrder order : expireList) {
//            // 1. 改为已取消
//            order.setOrderStatus(3);
//            orderMapper.updateById(order);
//
//            // 2. 归还余票
//            scheduleService.increaseSeatRemaining(
//                    order.getScheduleId(),
//                    order.getTicketCount()
//            );
//            // 3. 从Redis移除
//            redisTemplate.opsForZSet().remove("order:delay:queue", order.getOrderNo());
//        }
//    }
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public WxOrder getOrder(String orderNo) {
        WxOrder order = orderMapper.selectByOrderNo(orderNo);
        String stationListStr = order.getStationList();
        Integer[] ids = JsonUtils.toIntArray(stationListStr);
        // 处理手机号
        order.setContactPhone(PhoneUtil.maskPhone(order.getContactPhone()));
        String snapshot = order.getPassengerSnapshot();
        if (snapshot != null && !snapshot.isEmpty()) {
            try {
                List<PassengetVO> list = objectMapper.readValue(snapshot, new TypeReference<List<PassengetVO>>(){});
                list.forEach(p -> p.setPhone(PhoneUtil.maskPhone(p.getPhone())));
                order.setPassengerSnapshot(objectMapper.writeValueAsString(list));
            } catch (Exception ignored) {}
        }
        List stationInfo = stationMapper.listByStationIds(ids);
        // 塞给订单
        order.setStationInfo(stationInfo);
        return order;
    }
    // 微信支付

    @Override
    public Result wxpay(String orderNo) {
        System.out.println("订单号"+orderNo);
        WxOrder order = orderMapper.selectByOrderNo(orderNo);
        if(order == null){
            return Result.fail("订单不存在", null);
        }
        if(order.getOrderStatus() == 4){
            return Result.fail("订单退票", null);
        }
        if(order.getOrderStatus() == 3){
            return Result.fail("订单已取消", null);
        }
        if(order.getOrderStatus() == 2){
            return Result.fail("订单已出票", null);
        }
        if(order.getOrderStatus() == 1){
            return Result.fail("订单支付", null);
        }
        try {
            WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
            request.setOutTradeNo(orderNo);                  // 订单号
            request.setBody("车票购买");                     // 商品描述
            int fee = order.getTotalAmount().multiply(new BigDecimal(100)).intValue();
            request.setTotalFee(fee);      // 金额（单位分）
            request.setTradeType("JSAPI");                   // 公众号支付
            Map<String, Object> map = ThreadLocalUtil.get();
            String openid = map.get("openid").toString();
            request.setOpenid(openid);
            // 3. 调用微信SDK获取支付参数
//            WxPayUnifiedOrderResult result = wxPayService.unifiedOrder(request);

            // 4. 生成前端调起支付需要的参数
//            Map<String, String> payParam = new HashMap<>();
//            payParam.put("appId", wxPayConfig.getAppId());
//            payParam.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
//            payParam.put("nonceStr", WxPayUtils.generateNonceStr());
//            payParam.put("package", "prepay_id=" + result.getPrepayId());
//            payParam.put("signType", "MD5");

            // 5. 签名
//            String sign = WxPayUtils.generateSignature(payParam, wxPayConfig.getMchKey());
//            payParam.put("paySign", sign);
            return Result.success();
        }catch (Exception e){
            return Result.fail("支付发起失败：" , e.getMessage());
        }
    }

//    取消订单
    @Override
    public void cancel(String orderNo) {
        WxOrder order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) return;
        System.out.println(order + "取消订单");
        // 只有待支付的订单才取消
        if (order.getOrderStatus() == 0) {
            System.out.println("返还id" + order.getScheduleId());
            System.out.println("返还余票" + order.getTicketCount());
            // 归还余票
            scheduleService.increaseSeatRemaining(
                    order.getScheduleId(),
                    order.getTicketCount()
            );
            scheduleSearchMapper.increaseSeatRemaining(
                    order.getScheduleId(),
                    order.getTicketCount()
            );
            // 从 Redis 移除
            redisTemplate.opsForZSet().remove("order:delay:queue", orderNo);
        }
        orderMapper.updateOrderStatus(orderNo, 3);
    }

    // 删除订单
    @Override
    public void delete(String orderNo) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        orderMapper.delete(userId , orderNo);
    }
}
