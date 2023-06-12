package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author YuSir
 * @version 1.0
 * @description: 歌单打分
 * @date 2022/11/15 10:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rank {
    private Integer id;
    private Integer songListId;
    private Integer consumerId;
    private Integer score;
    private Date createTime;
}
