package com.jinyou.service.admin.ticket.impl;

import com.github.pagehelper.PageHelper;
import com.jinyou.DTO.admin.ticket.ScheduleQueryDTO;
import com.jinyou.mapper.admin.operation.LineMapper;
import com.jinyou.mapper.admin.ticket.ScheduleMapper;
import com.jinyou.mapper.admin.ticket.ScheduleSearchMapper;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.admin.operation.Line;
import com.jinyou.pojo.admin.ticket.Schedule;
import com.jinyou.pojo.admin.ticket.ScheduleSearch;
import com.jinyou.service.admin.ticket.ScheduleService;
import com.jinyou.utils.EntityFillUtil;
import com.jinyou.utils.PageBeanUtil;
import com.jinyou.utils.ThreadLocalUtil;
import com.jinyou.utils.admin.ScheduleSearchSyncUtil;
import com.jinyou.vo.admin.ScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private ScheduleSearchMapper scheduleSearchMapper;
    @Override
    public PageBean<Schedule> list(ScheduleQueryDTO query) {
        return PageBeanUtil.toPageBeanWithUser(query.getPageNum(), query.getPageSize(),
                userId -> scheduleMapper.list(userId, query)
        );
    }
    // 搜索完整车次表
    // 出发城市、到达城市、站点id从车次表搜索
    // 通过站点id搜索出站点的详细信息
    @Override
    public PageBean<ScheduleVO> listVO(ScheduleQueryDTO query) {
//        return PageBeanUtil.toPageBeanWithUser(query.getPageNum(), query.getPageSize(),
//                 userId -> scheduleMapper.listVO(userId, query)
//        );
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        // 2. 【关键】手动查正确总数（只查车次，不联表 → total=1）
        long total = scheduleMapper.countListVO(userId, query);
        // 3. 只分页、不count（这一句解决所有问题）
        PageHelper.startPage(query.getPageNum(), query.getPageSize(), false);
        // 4. 查数据（你原来的XML完全不变）
        List<ScheduleVO> items = scheduleMapper.listVO(userId, query);
        // 5. 封装结果
        PageBean<ScheduleVO> pageBean = new PageBean<>();
        pageBean.setTotal(total);
        pageBean.setItems(items);
        return pageBean;
    }

    @Autowired
    ScheduleSearchSyncUtil searchSyncUtil;
    @Override
    public void add(Schedule schedule) {
        EntityFillUtil.fillCreateFields(schedule);
        scheduleMapper.add(schedule);
        // 添加车次顺便插入搜索表
        searchSyncUtil.syncToSearchTable(schedule,"add");
    }

    @Override
    public void update(Schedule schedule) {
        EntityFillUtil.fillUpdateFields(schedule);
        scheduleMapper.update(schedule);
//       修改车次顺便更新搜索表
        searchSyncUtil.syncToSearchTable(schedule,"update");
    }

    @Override
    public void delete(Long id) {
        scheduleMapper.delete(id);
    }

    @Override
    public void updateStatus(Long scheduleId, Integer status) {
        scheduleMapper.updateStatus(scheduleId, status);
    }

    @Override
    public void updateIsRecommend(Long scheduleId, Integer isRecommend) {
        scheduleMapper.updateIsRecommend(scheduleId, isRecommend);
    }
}
