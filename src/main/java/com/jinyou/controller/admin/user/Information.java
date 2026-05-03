package com.jinyou.controller.admin.user;



import com.jinyou.pojo.Result;
import com.jinyou.pojo.admin.user.User;
import com.jinyou.service.admin.user.UserService;
import com.jinyou.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/user")
@Validated
public class Information {
    @Autowired
    private UserService userService;
    @GetMapping("/info")
    public Result<User> userInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        return Result.success(loginUser);
    }
    @GetMapping("/text")
    public Result<String> text(){
        System.out.println("text");
        return Result.success("text");
    }
}
