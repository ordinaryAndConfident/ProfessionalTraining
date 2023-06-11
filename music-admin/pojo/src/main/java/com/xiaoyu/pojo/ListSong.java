package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YuSir
 * @version 1.0
 * @description: 该歌单里下的歌曲
 * @date 2022/11/6 10:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListSong {
    private Integer id;
    private Integer songId;
    private Integer songListId;
}
