package com.xiaoyu.service;

import com.xiaoyu.pojo.Singer;

import java.util.List;

public interface SingerService {

    public boolean insert(Singer singer);

    public boolean deleteSinger(Integer id);

    public boolean update(Singer singer);

    public List<Singer> selectAll();

    public Singer selectById(Integer id);

    /*根据歌手名称模糊查询列表*/
    public List<Singer> selectSingerByName(String name);

    /*根据性别模糊查询列表*/
    public List<Singer> selectSingerBySex(Integer sex);
}
