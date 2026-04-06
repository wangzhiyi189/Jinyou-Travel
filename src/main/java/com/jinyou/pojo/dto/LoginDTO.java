package com.jinyou.pojo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDTO {
    @Pattern(regexp = "^\\S{5,16}$", message = "用户名需要5-16位")
    private String username;

    @Pattern(regexp = "^\\S{5,16}$", message = "密码需要匹配5-16位")
    private String password;

    private Boolean captcha;
}