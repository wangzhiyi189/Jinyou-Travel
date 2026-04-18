package com.jinyou.service.admin.user;

import com.jinyou.pojo.admin.user.User;

public interface UserService {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);
//    注册
    void register(String username, String password);
}
