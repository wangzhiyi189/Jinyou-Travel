package com.jinyou.service.app.user;

import com.jinyou.pojo.app.user.WxUser;
import jakarta.validation.Valid;


public interface WxUserService {
    void register(String username, String password);

    WxUser findByAppid(String code);

    String wxLogin(@Valid String code);
}
