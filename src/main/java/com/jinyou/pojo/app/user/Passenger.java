package com.jinyou.pojo.app.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    private Long passengerId;//主键ID
    private Long createUser;//创建人ID
    private String name;//姓名
    private String phone;//手机号码
    private String idCard;//证件号码
    private String ticketType;//票种类型
    private String idType;//证件类型
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//修改时间
}
