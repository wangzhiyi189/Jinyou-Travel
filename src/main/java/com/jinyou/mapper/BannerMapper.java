package com.jinyou.mapper;

import com.jinyou.pojo.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BannerMapper {

    // 查询banner；列表
    @Select("select * from banner where create_user=#{userId}")
    List<Banner> list(Integer userId);
}
