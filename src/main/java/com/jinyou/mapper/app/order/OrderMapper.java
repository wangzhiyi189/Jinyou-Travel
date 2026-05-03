package com.jinyou.mapper.app.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jinyou.DTO.app.order.OrderQueryDTO;
import com.jinyou.pojo.Result;
import com.jinyou.pojo.app.order.WxOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    // 创建订单
    void insertOrder(WxOrder wxOrder);
    // 查询订单列表
    List<WxOrder> selectList(@Param("userId") Long userId, @Param("query") OrderQueryDTO query);
    //    查询支付订单
    List<WxOrder> selectPayList();
    // 修改订单
    int updateById(WxOrder order);
    // 修改订单状态
    int updateOrderStatus(@Param("orderNo") String orderNo, @Param("orderStatus") Integer orderStatus);
    List<WxOrder> selectListByExpire(LocalDateTime now);

    // 根据订单号查询订单
    WxOrder selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 查询该用户+该车次 是否存在待支付未过期订单
     */
    WxOrder getUnPayOrder(@Param("userId") Long userId,
                          @Param("scheduleId") Long scheduleId);
    // 根据订单号查询订单信息
    WxOrder selectOrderByUserAndSchedule(
            @Param("createUser") Long userId,
            @Param("codeNo") Long codeNo
    );

    // 删除订单
    @Delete("delete from wx_order where order_no=#{orderNo} and create_user = #{userId} ")
    void delete(Long userId , String orderNo);
}
