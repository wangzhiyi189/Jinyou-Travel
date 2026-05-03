package com.jinyou.service.admin.operation;

import com.jinyou.DTO.admin.operation.LineQueryDTO;
import com.jinyou.pojo.admin.operation.Line;
import com.jinyou.pojo.PageBean;

import java.util.List;

public interface LineService {


    PageBean<Line> list(LineQueryDTO query);

    void add(Line line);

    void update(Line line);

    void delete(Long id);

    void updateStatus(Long lineId, Integer status , Integer isPopular) ;

    List<Line> listAll();

    Line selectById(Long lineId);
}
