package com.jinyou.mapper.app.ticket;

import com.jinyou.vo.admin.ScheduleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TicketMapper {
    // 根据id搜索
//    @Insert("select * from bus_schedule where schedule_id = #{scheduleId}")
    ScheduleVO details(@Param("scheduleId") Long scheduleId);
}
