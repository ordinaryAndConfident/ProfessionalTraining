package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    private Integer id;
    private String account;
    private String password;
    private String nickName;
    private String avatar;
    private String sex;
    private String introduction;
    private Date birthday;
    private Date createTime;


}
