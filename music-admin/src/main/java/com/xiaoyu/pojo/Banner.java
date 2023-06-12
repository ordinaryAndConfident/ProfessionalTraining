package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author YuSir
 * @version 1.0
 * @description: 前端首页轮播图
 * @date 2023/5/13 13:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    private Integer id;
    private String url;
    private Integer isShow;
    private Date createTime;
    private Date updateTime;
}
