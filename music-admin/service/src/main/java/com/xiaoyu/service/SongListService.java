package com.xiaoyu.service;

import com.xiaoyu.pojo.SongList;

import java.util.List;

public interface SongListService {
    public boolean insert(SongList songList);

    public boolean deleteSongList(Integer id);

    public boolean update(SongList songList);

    public List<SongList> selectAll();

    public SongList selectById(Integer id);

    /*根据标题模糊查询歌单列表*/
    public List<SongList> likeTitle(String title);

    /*根据标题精确查询歌单列表*/
    public List<SongList> selectSongListByTitle(String title);

    /*根据风格模糊查询歌单列表*/
    public List<SongList> likeStyle(String style);
}
