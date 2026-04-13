package com.jinyou.service.impl;

import com.jinyou.mapper.UserMapper;
import com.jinyou.pojo.User;
import com.jinyou.service.UserService;
import com.jinyou.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServicelmpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        // 加密
        String md5String = Md5Util.getMD5String(password);
//        添加
        userMapper.add(username,md5String);
    }

}
