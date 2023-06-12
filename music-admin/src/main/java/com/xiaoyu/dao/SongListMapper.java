package com.xiaoyu.dao;

import com.xiaoyu.general.BaseDao;
import com.xiaoyu.pojo.SongList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongListMapper extends BaseDao<SongList> {
    public int insert(SongList songList);

    public int deleteSongList(Integer id);

    public int update(SongList songList);

    public List<SongList> selectAll();

    public SongList selectById(Integer id);

    /*根据标题模糊查询歌单列表*/
    public List<SongList> likeTitle(String title);

    /*根据标题精确查询歌单列表*/
    public List<SongList> selectSongListByTitle(String title);

    /*根据风格模糊查询歌单列表*/
    public List<SongList> likeStyle(String style);
}
