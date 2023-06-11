package com.xiaoyu.service;

import com.xiaoyu.pojo.Song;

import java.util.List;

public interface SongService {

    public boolean insert(Song song);

    public boolean deleteSong(Integer id);

    public boolean update(Song song);

    public List<Song> selectAll();

    public Song selectById(Integer id);

    public  List<Song> selectSongLikeName(String songName);

    /*根据歌名模糊查询列表*/
    public List<Song> selectSongByName(String name);

    public List<Song> selectSongBySingerId(Integer singerId);

    // 歌曲歌单列表获取歌单中的歌曲列表
    public List<Song> selectSongBySongListId(Integer songListId);
}
