package com.jinyou.controller.app.user;

import com.jinyou.pojo.Result;
import com.jinyou.pojo.app.user.WxUser;
import com.jinyou.service.app.user.WxUserService;
import com.jinyou.utils.ThreadLocalUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class WxUserController {
    @Autowired
    WxUserService wxUserService;
    @PostMapping("wxLogin")
    public Result<?> wxLogin(@Valid @RequestBody String code){
        if (code == null || code.isEmpty()) {
            return Result.error("code不能为空");
        }
        String token = wxUserService.wxLogin(code);
        return Result.success(token);
    }

    @GetMapping("info")
    public Result<WxUser> info(){
        System.out.println("info");
        Map<String,Object> map = ThreadLocalUtil.get();
        String openid = (String) map.get("openid");
        System.out.println(map);
        WxUser info = wxUserService.findByAppid(openid);
        System.out.println(info);
        return Result.success(info);
    }
}
