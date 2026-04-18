package com.jinyou.mapper.admin.ticket;

import com.jinyou.DTO.admin.ticket.ScheduleQueryDTO;
import com.jinyou.pojo.admin.ticket.Schedule;
import com.jinyou.vo.admin.ScheduleVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    // 获取列表
    List<Schedule> list(@Param("userId") Long userId, @Param("query") ScheduleQueryDTO query);
    // 获取列表并返回站点
    List<ScheduleVO> listVO(@Param("userId") Long userId, @Param("query") ScheduleQueryDTO query);
//    只统计分页不查数据
    Integer countListVO(@Param("userId") Long userId, @Param("query") ScheduleQueryDTO query);

    // 添加
    void add(Schedule schedule);

    // 修改
    @Insert("update bus_schedule set schedule_name = #{scheduleName},line_id = #{lineId},depart_time = #{departTime},arrive_time = #{arriveTime},duration = #{duration},plate_number = #{plateNumber},vehicle_type = #{vehicleType},price = #{price},seat_total = #{seatTotal},seat_remaining = #{seatRemaining},status = #{status},is_recommend = #{isRecommend},update_time = #{updateTime},remark = #{remark},create_user = #{createUser}  where schedule_id = #{scheduleId}")
    void update(Schedule schedule);

    // 删除
    @Insert("delete from bus_schedule where schedule_id = #{id}")
    void delete(Long id);

    // 修改状态
    @Insert("update bus_schedule set status = #{status},update_time=now() where schedule_id = #{scheduleId}")
    void updateStatus(Long scheduleId, Integer status);

    // 修改推荐状态
    @Insert("update bus_schedule set is_recommend = #{isRecommend},update_time=now() where schedule_id = #{scheduleId}")
    void updateIsRecommend(Long scheduleId, Integer isRecommend);

}
