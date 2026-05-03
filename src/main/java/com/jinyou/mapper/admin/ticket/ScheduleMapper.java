package com.jinyou.mapper.admin.ticket;

import com.jinyou.DTO.admin.ticket.ScheduleQueryDTO;
import com.jinyou.pojo.admin.ticket.Schedule;
import com.jinyou.vo.admin.ScheduleVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ScheduleMapper  {
    // 获取列表
    List<Schedule> list(@Param("userId") Long userId, @Param("query") ScheduleQueryDTO query);
    // 获取列表并返回站点
    List<ScheduleVO> listVO(
            @Param("userId") Long userId,
            @Param("query") ScheduleQueryDTO query,
            @Param("pageStart") int pageStart  // 加这行
    );
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

    // 详情
    @Select("select * from bus_schedule where schedule_id = #{scheduleId}")
    Schedule getDetail(Long scheduleId);

    // 只扣减余票，不改动任何其他字段
    int decreaseSeatRemaining(@Param("scheduleId") Long scheduleId,
                              @Param("ticketCount") Integer ticketCount);

    int increaseSeatRemaining(@Param("scheduleId") Long scheduleId,
                               @Param("ticketCount") Integer ticketCount);

    // 查询今天有效班次
    List<Schedule> selectTodaySchedule(LocalDate today);

    // 检查某天某线路是否已存在
    int checkExist(@Param("targetDate") LocalDate targetDate,
                   @Param("scheduleId") Long scheduleId);

    /**
     * 查询30天前的所有班次 ID
     */
    List<Long> selectOldScheduleIds(@Param("monthAgo") LocalDate monthAgo);

    /**
     * 根据 ID 批量删除
     */
    void deleteBatchByIds(@Param("ids") List<Long> ids);
}
