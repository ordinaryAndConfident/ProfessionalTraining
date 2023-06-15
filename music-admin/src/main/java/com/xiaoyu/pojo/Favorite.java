package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private Integer id;
    private Integer consumerId;
    private Integer type;
    private Integer songId;
    private Integer songListId;
    private Date createTime;
}
