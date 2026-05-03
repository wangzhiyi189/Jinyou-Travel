package com.jinyou.mapper.app.home;

import com.jinyou.pojo.admin.home.Banner;
import com.jinyou.pojo.admin.home.TopBanner;
import com.jinyou.pojo.admin.operation.Line;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeMapper {
    List<Banner> list();

    List<TopBanner> listTop();

    List<Line> listPopular();
}
