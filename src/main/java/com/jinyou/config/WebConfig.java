package com.jinyou.config;

import com.jinyou.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 给 admin 包下的所有 Controller 自动加上 /admin 前缀
        configurer.addPathPrefix("/admin",
                c -> c.getPackage().getName().startsWith("com.jinyou.controller.admin"));
        // 给 com.jinyou.controller.app 包下所有接口 自动加 /app 前缀
        configurer.addPathPrefix("/app",
                c -> c.getPackage().getName().startsWith("com.jinyou.controller.app")
        );
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                // 白名单
                .excludePathPatterns(
                        "/admin/auth/**",
                        "/app/home/**",
                        "/app/ticket/list",
                        "/app/ticket/details/**",
                        "/app/user/wxLogin"
                );
    }
    // 跨域配置（保留你原来的，但我优化了重复写法）
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:5666","http://localhost:5667","http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    // ==============================================
    // 🔥 关键：加这个Bean，让跨域优先于拦截器执行
    // ==============================================
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("http://localhost:5666");
        config.addAllowedOriginPattern("http://localhost:5667");
        config.addAllowedOriginPattern("http://localhost:5173");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        CorsFilter filter = new CorsFilter(source);
        return filter;
    }
}