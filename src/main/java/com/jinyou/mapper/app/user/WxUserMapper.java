package com.jinyou.mapper.app.user;

import com.jinyou.pojo.app.user.WxUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WxUserMapper {
    //    根据id搜索
    @Select("select * from wx_user where openid=#{openid}")
    WxUser selectByOpenid(String openid);

    @Insert("insert into wx_user(openid,nickname,avatar,phone,create_time,update_time)" +
            " values(#{openid},#{nickname},#{avatar},#{phone},now(),now())")
    void insert(WxUser user);

    void add(String username, String md5String);
}
