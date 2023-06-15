package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


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
