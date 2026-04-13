package com.jinyou.controller;


import com.jinyou.pojo.Result;
import com.jinyou.pojo.User;
import com.jinyou.pojo.dto.LoginDTO;
import com.jinyou.service.UserService;
import com.jinyou.utils.JwtUtil;
import com.jinyou.utils.Md5Util;
import com.jinyou.utils.ThreadLocalUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$",message = "用户名格式错误") String username, @Pattern(regexp = "^\\S{5,16}$",message = "用户名格式错误") String password){

            //        查询用户是否存在
            User u = userService.findByUsername(username);
            if(u == null){
//            没有占用
                //        注册
                userService.register(username,password);
                return Result.success();
            }else{
//            占用
                return Result.error("用户名已占用");
            }
    }
    @PostMapping("/login")
    public Result<String> login(
            @Valid @RequestBody LoginDTO loginDTO
    ){
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
//        根据用户名查询用户
        User loginUser = userService.findByUsername(username);
//        判断用户是否存在
        if(loginUser == null){
//            用户不存在
            return Result.error("用户名不存在");
        }
        if(Md5Util.getMD5String( password ).equals(loginUser.getPassword())){
//            登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }
    @PostMapping("/logout")
    public Result<String> logout() {
        // 验证是否登录
        Map<String, Object> map = ThreadLocalUtil.get();
        if (map == null) {
            return Result.error("用户未登录，无需退出");
        }
        ThreadLocalUtil.remove();
        return Result.success("退出登录成功");
    }

}
