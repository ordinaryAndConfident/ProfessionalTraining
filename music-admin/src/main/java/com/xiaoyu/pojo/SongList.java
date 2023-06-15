package com.xiaoyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
