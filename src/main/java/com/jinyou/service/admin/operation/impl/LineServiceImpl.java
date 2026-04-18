package com.jinyou.service.admin.operation.impl;

import com.jinyou.DTO.admin.operation.LineQueryDTO;
import com.jinyou.mapper.admin.operation.LineMapper;
import com.jinyou.pojo.admin.operation.Line;
import com.jinyou.pojo.PageBean;
import com.jinyou.service.admin.operation.LineService;
import com.jinyou.utils.EntityFillUtil;
import com.jinyou.utils.PageBeanUtil;
import com.jinyou.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class LineServiceImpl implements LineService {
    @Autowired
    private LineMapper lineMapper;
    @Override
    public PageBean<Line> list(LineQueryDTO query) {
        return PageBeanUtil.toPageBeanWithUser(query.getPageNum(), query.getPageSize(),
                userId -> lineMapper.list(userId, query)
        );
    }

    @Override
    public void add(Line line) {
        EntityFillUtil.fillCreateFields(line);
        lineMapper.add(line);
    }

    @Override
    public void update(Line line) {
        EntityFillUtil.fillUpdateFields(line);
        lineMapper.update(line);
    }

    @Override
    public void delete(Long id) {
        lineMapper.delete(id);
    }

    @Override
    public void updateStatus(Long lineId, Integer status) {
        lineMapper.updateStatus(lineId, status);
    }

    @Override
    public List<Line> listAll() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        return lineMapper.listAll(userId);
    }
}
