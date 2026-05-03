package com.jinyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.jinyou.mapper")
@SpringBootApplication
// 定时生成车票，自动补齐后面的车票
@EnableScheduling
public class JinyouTravelApplication {
    public static void main(String[] args) {
        SpringApplication.run(JinyouTravelApplication.class, args);
    }

}
