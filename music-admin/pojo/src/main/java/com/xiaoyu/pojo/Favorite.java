package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author YuSir
 * @version 1.0
 * @description: 用户收藏
 * @date 2022/11/18 15:10
 */
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
