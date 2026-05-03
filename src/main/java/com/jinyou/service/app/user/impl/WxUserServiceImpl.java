package com.jinyou.service.app.user.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jinyou.mapper.app.user.WxUserMapper;
import com.jinyou.pojo.app.user.WxUser;
import com.jinyou.service.app.user.WxUserService;
import com.jinyou.utils.JwtUtil;
import com.jinyou.utils.Md5Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WxUserServiceImpl implements WxUserService {
    @Autowired
    WxUserMapper wxUserMapper;
    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String APPID = "wx2b1d88f4acb7ca8e";
    private static final String SECRET = "680d20f396cee4ad480d24c850e97638";
    @Override
    public WxUser findByAppid(String code) {
        WxUser u = wxUserMapper.selectByOpenid(code);
        return u;
    }

    @Override
    public void register(String username, String password) {
        // 加密
        String md5String = Md5Util.getMD5String(password);
//        添加
        wxUserMapper.add(username,md5String);
    }

    @Override
    public String wxLogin(String code) {
        System.out.println(code);
        String url = "https://api.weixin.qq.com/sns/jscode2session"
                + "?appid=" + APPID
                + "&secret=" + SECRET
                + "&js_code=" + code
                + "&grant_type=authorization_code";

        // 2. 调用微信接口
        String wxRes = restTemplate.getForObject(url, String.class);
        Map<String, String> wxResultMap = null;
        // ===================== 原生Jackson解析JSON，替代fastjson =====================
        try {
            wxResultMap = objectMapper.readValue(wxRes, Map.class);
        } catch (JsonProcessingException e) {
            // 解析失败直接抛出运行时异常，统一返回给前端
            throw new RuntimeException("微信信息解析失败", e);
        }
        String openid = wxResultMap.get("openid");

        // 3. 根据openid查询用户（完全对齐你原账号登录逻辑）
        WxUser wxUser = wxUserMapper.selectByOpenid(openid);
        System.out.println(wxUser);
        // 4. 用户不存在自动注册
        if (wxUser == null) {
            wxUser = new WxUser();
            wxUser.setOpenid(openid);
            System.out.println(wxUser);
            wxUserMapper.insert(wxUser);
            wxUser = wxUserMapper.selectByOpenid(openid);
        }

        // 5. 生成JWT token（和你原本登录代码100%完全一致）
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", wxUser.getId());
        claims.put("openid", wxUser.getOpenid());
        String token = JwtUtil.genWxToken(claims);

        return token;
    }
}
