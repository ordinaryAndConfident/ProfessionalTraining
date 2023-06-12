package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YuSir
 * @version 1.0
 * @description: 歌单
 * @date 2022/11/5 21:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongList {
    private  Integer id;
    private  String title;
    private  String pic;
    private  String introduction;
    private  String style;
}
