package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author YuSir
 * @version 1.0
 * @description: 管理员
 * @date 2022/10/20 11:00
 */
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
