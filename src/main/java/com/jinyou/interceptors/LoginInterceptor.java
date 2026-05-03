package com.jinyou.interceptors;

import com.jinyou.utils.CaseUtil;
import com.jinyou.utils.JwtUtil;
import com.jinyou.utils.ThreadLocalUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    // 用于返回JSON格式提示
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求头
        String authHeader = CaseUtil.getHeaderIgnoreCase(request, "Authorization");

        // ====================== 【没有token】直接返回提示 ======================
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(401);
            Map<String, Object> result = new HashMap<>();
            result.put("code", 401);
            result.put("msg", "用户未登录，请先登录");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }

        // 2. 截取token
        String token = authHeader.substring(7);

        try {
            // 3. 解析token
            Map<String, Object> claims = JwtUtil.parseToken(token);

            // ====================== 安全处理ID，防止空指针 ======================
            Object idObj = claims.get("id");
            if (idObj != null) {
                claims.put("id", Long.valueOf(idObj.toString()));
            }

            // 存入线程变量
            ThreadLocalUtil.set(claims);
            return true;

        } catch (Exception e) {
            // ====================== token过期/无效 ======================
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(401);
            Map<String, Object> result = new HashMap<>();
            result.put("code", 401);
            result.put("msg", "token已过期，请重新登录");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}