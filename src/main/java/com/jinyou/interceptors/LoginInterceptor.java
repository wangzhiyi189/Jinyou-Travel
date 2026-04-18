package com.jinyou.interceptors;

import com.jinyou.utils.CaseUtil;
import com.jinyou.utils.JwtUtil;
import com.jinyou.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = CaseUtil.getHeaderIgnoreCase(request, "Authorization");
        String token = authHeader.substring(7);
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
//            用户id强转Long
            claims.put("id", Long.valueOf(claims.get("id").toString()));
//            把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
