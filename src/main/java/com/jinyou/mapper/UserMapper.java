package com.jinyou.mapper;

import com.jinyou.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("select * from t_user where username=#{username}")
    User findByUsername(String username);
//    添加
    @Insert("insert into t_user(username,password,create_time,update_time)" +
            " values(#{username},#{password},now(),now())")
    void add(String username, String password);
    
}
