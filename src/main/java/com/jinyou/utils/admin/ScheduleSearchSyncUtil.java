package com.jinyou.utils.admin;


import com.jinyou.mapper.admin.operation.LineMapper;
import com.jinyou.mapper.admin.ticket.ScheduleSearchMapper;
import com.jinyou.pojo.admin.operation.Line;
import com.jinyou.pojo.admin.ticket.Schedule;
import com.jinyou.pojo.admin.ticket.ScheduleSearch;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ScheduleSearchSyncUtil {
    @Resource
    private LineMapper lineMapper;

    @Resource
    private ScheduleSearchMapper scheduleSearchMapper;
    /**
     * 全量同步车次数据到搜索表
     * 存在则更新，不存在则插入（replaceSync 逻辑）
     * @param schedule 车次主表完整对象
     */
    public void syncToSearchTable(Schedule schedule , String type ) {
        // 1. 根据线路ID查询线路信息，获取出发/到达城市
        Line line = lineMapper.selectById(schedule.getLineId());

        // 2. 统一封装组装ScheduleSearch对象
        ScheduleSearch search = new ScheduleSearch();
        // 主键关联字段
        search.setScheduleId(schedule.getScheduleId());
        search.setLineId(schedule.getLineId());
        // 线路城市信息（固定从线路表拿）
        search.setStartCity(line.getStartCity());
        search.setEndCity(line.getEndCity());
        // ========== 严格按照业务规范：只同步前端展示、核心必要字段 ==========
        search.setDepartTime(schedule.getDepartTime());
        search.setArriveTime(schedule.getArriveTime());
        search.setDuration(schedule.getDuration());
        search.setPrice(schedule.getPrice());
        search.setStatus(schedule.getStatus());
        // 同步余票
        search.setSeatRemaining(schedule.getSeatRemaining());

        // 3. 统一执行同步
        if("add".equals(type)){
            scheduleSearchMapper.insert(search);
        } else if ("update".equals(type)) {
            scheduleSearchMapper.updateSync(search);
        }
    }
}
