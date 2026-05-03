package com.jinyou.pojo.app.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxUser {
    @NonNull
    private Long id;//主键ID
    private String openid;//用户名
    private String nickname;//昵称
    private String avatar;//邮箱
    private String phone;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
