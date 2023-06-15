package com.xiaoyu.dao;

import com.xiaoyu.general.BaseDao;
import com.xiaoyu.pojo.Song;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface SongMapper extends BaseDao<Song> {

    public int insert(Song song);

    public int deleteSong(Integer id);

    public int update(Song song);

    public List<Song> selectAll();

    public Song selectById(Integer id);

    /*根据歌曲模糊查询*/
    public  List<Song> selectSongLikeName(String songName);

    /*根据歌名查询列表*/
    public List<Song> selectSongByName(String name);

    public List<Song> selectSongBySingerId(Integer singerId);

    // 歌曲歌单列表获取歌单中的歌曲列表
    public List<Song> selectSongBySongListId(Integer songListId);
}
