package com.jinyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.jinyou.mapper")
@SpringBootApplication
public class JinyouTravelApplication {
    public static void main(String[] args) {
        SpringApplication.run(JinyouTravelApplication.class, args);
    }

}
