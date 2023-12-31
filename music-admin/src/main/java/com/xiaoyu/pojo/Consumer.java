package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author YuSir
 * @version 1.0
 * @description: 前端用户
 * @date 2022/11/6 14:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consumer {
    private Integer id;
    private String account;
    private String password;
    private String nickName;
    private String avatar;
    private int sex;
    private String phone;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    private String introduction;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
