package com.jinyou.task; // 改成你自己的包名

import com.jinyou.mapper.admin.ticket.ScheduleMapper;
import com.jinyou.pojo.admin.ticket.Schedule;
import com.jinyou.service.admin.ticket.ScheduleService;
import com.jinyou.utils.admin.ScheduleSearchSyncUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AutoGenerateTicketTask {

    @Autowired
    ScheduleMapper scheduleMapper;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ScheduleSearchSyncUtil searchSyncUtil;
    /**
     * 每天凌晨 00:00 执行
     * 自动补齐【第 8 天】的车票，永远保持 7 天库存
     */
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 * * * * ?") // 测试1分钟执行用的
    public void autoGenerate7DaysTicket() {
        // 1. 计算要补齐的日期：今天 + 7天
        LocalDate targetDate = LocalDate.now().plusDays(7);

        // 2. 查询今天所有正常运行的车次
        List<Schedule> todayList = scheduleMapper.selectTodaySchedule(LocalDate.now());
        LocalDate monthAgo = LocalDate.now().minusDays(30);
        List<Long> needDeleteIds = scheduleMapper.selectOldScheduleIds(monthAgo);
        if (!needDeleteIds.isEmpty()) {
            scheduleService.deleteBatchByIds(needDeleteIds);
        }
        // 3. 循环生成第8天的车票
        for (Schedule today : todayList) {
            // 防重判断
            int count = scheduleMapper.checkExist(targetDate, today.getScheduleId());
            if (count > 0) {
                continue;
            }

            // 复制生成新车票
            Schedule newTicket = new Schedule();
            newTicket.setLineId(today.getLineId());
            newTicket.setScheduleName(today.getScheduleName());
            newTicket.setPlateNumber(today.getPlateNumber());
            newTicket.setPrice(today.getPrice());
            newTicket.setSeatTotal(today.getSeatTotal());
            newTicket.setSeatRemaining(today.getSeatTotal());
            newTicket.setArriveTime(today.getArriveTime());
            newTicket.setDuration(today.getDuration());
            newTicket.setVehicleType(today.getVehicleType());
            newTicket.setRemark(today.getRemark());
            newTicket.setCreateUser(today.getCreateUser());
            newTicket.setCreateTime(LocalDateTime.now());
            newTicket.setUpdateTime(LocalDateTime.now());
            // 发车时间：只改日期，时间不变
            LocalDateTime newDepartTime = targetDate.atTime(today.getDepartTime().toLocalTime());
            newTicket.setDepartTime(newDepartTime);
            newTicket.setStatus(1);
//            scheduleMapper.add(newTicket);
//            searchSyncUtil.syncToSearchTable(newTicket,"add");
        }
    }
}