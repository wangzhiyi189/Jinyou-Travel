package com.jinyou.service.admin.ticket.impl;

import com.jinyou.DTO.admin.ticket.ScheduleTemplateQueryDTO;
import com.jinyou.DTO.app.order.OrderListDTO;
import com.jinyou.mapper.admin.ticket.ScheduleTemplateMapper;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.admin.ticket.ScheduleTemplate;
import com.jinyou.pojo.app.order.WxOrder;
import com.jinyou.service.admin.ticket.ScheduleTemplateService;
import com.jinyou.utils.EntityFillUtil;
import com.jinyou.utils.PageBeanUtil;
import com.jinyou.utils.ThreadLocalUtil;
import com.jinyou.vo.admin.ScheduleTemplateVO;
import com.jinyou.vo.admin.ScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScheduleTemplateServiceImpl implements ScheduleTemplateService {
    @Autowired
    ScheduleTemplateMapper scheduleTemplateMapper;
    @Override
    public PageBean<ScheduleTemplate> list(ScheduleTemplateQueryDTO query) {
        return PageBeanUtil.toPageBeanWithUser(query.getPageNum(), query.getPageSize(),
                userId -> scheduleTemplateMapper.list(userId, query)
        );
//        Map<String, Object> map = ThreadLocalUtil.get();
//        Long userId = (Long) map.get("id");
//        // 3. 只分页、不count（这一句解决所有问题）
//        int pageStart = (query.getPageNum() - 1) * query.getPageSize();
//        List<ScheduleVO> items = scheduleMapper.list(userId, query, pageStart);
////        PageHelper.startPage(query.getPageNum(), query.getPageSize(), false);
//        // 4. 查数据（你原来的XML完全不变）
////        List<ScheduleVO> items = scheduleMapper.listVO(userId, query);
//        // 5. 封装结果
//        PageBean<ScheduleTemplateQueryDTO> result = new PageBean<>();
//        result.setItems(dtoList);
//        result.setTotal(pageBean.getTotal());
    }

    @Override
    public void add(ScheduleTemplate scheduleTemplate) {
        EntityFillUtil.fillCreateFields(scheduleTemplate);
        scheduleTemplateMapper.add(scheduleTemplate);
    }
}
