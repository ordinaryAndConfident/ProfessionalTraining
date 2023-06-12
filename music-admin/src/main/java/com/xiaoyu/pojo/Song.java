package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author YuSir
 * @version 1.0
 * @description: 歌曲
 * @date 2022/11/2 17:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    private Integer id;
    private Integer singerId;
    private String name;
    private String introduction;
    private Date createTime;
    private Date updateTime;
    private String picture;
    private String lyric;
    private String url;
}
