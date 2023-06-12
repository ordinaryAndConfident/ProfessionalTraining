package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author YuSir
 * @version 1.0
 * @description: 评论
 * @date 2022/11/15 15:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer id;
    private Integer consumerId;
    private Integer type;
    private Integer songId;
    private Integer songListId;
    private Date createTime;
    private Date updateTime;
    private String content;
    private Integer up;
}
