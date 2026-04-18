package com.jinyou.mapper.admin.ticket;

import com.jinyou.pojo.admin.ticket.ScheduleSearch;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleSearchMapper {

    // ====================== 基础查询 ======================
    // 根据主键id查询
    ScheduleSearch selectById(Long scheduleSearchId);

    // 根据车次 schedule_id 查询（主表ID）
    ScheduleSearch selectByScheduleId(Long scheduleId);

    // 小程序查询车票：出发城市 + 到达城市 + 日期
    List<ScheduleSearch> searchByCityAndDate(
            @Param("startCity") String startCity,
            @Param("endCity") String endCity,
            @Param("departDate") String departDate
    );

    // ====================== 新增/同步 ======================
    // 新增到搜索表
    int insert(ScheduleSearch search);
    // 更新到搜索表
    void updateSync(ScheduleSearch search);
    // 同步覆盖（主表数据变化后调用）
    int replaceSync(ScheduleSearch search);

    // ====================== 修改 ======================
    // 更新余票（购票后）
    int updateSeatRemaining(@Param("scheduleId") Long scheduleId,
                            @Param("seatRemaining") Integer seatRemaining);

    // 更新票价
    int updatePrice(@Param("scheduleId") Long scheduleId,
                    @Param("price") BigDecimal price);

    // 更新状态（启用/禁用）
    int updateStatus(@Param("scheduleId") Long scheduleId,
                     @Param("status") Integer status);

    // ====================== 删除 ======================
    // 根据主键删除
    int deleteById(Long scheduleSearchId);

    // 根据 schedule_id 删除（主表删除时调用）
    int deleteByScheduleId(Long scheduleId);

    // 定时任务：删除30天前历史数据
    int deleteOldDataBefore30Days();

}