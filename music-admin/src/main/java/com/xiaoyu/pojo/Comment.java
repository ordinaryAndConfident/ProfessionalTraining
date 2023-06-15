package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


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
