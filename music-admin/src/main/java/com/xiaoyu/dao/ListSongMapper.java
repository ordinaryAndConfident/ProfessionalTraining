package com.xiaoyu.dao;

import com.xiaoyu.general.BaseDao;
import com.xiaoyu.pojo.ListSong;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface ListSongMapper extends BaseDao<ListSong> {

    public int insert(ListSong listSong);

    public int deleteBySongIdAndListSongId(Integer songId,Integer listSongId);

    public int deleteListSongById(Integer id);

    public int update(ListSong listSong);

    // 查询歌单里面的所有歌曲
    public List<ListSong> selectAll();

    // 根据 id 查询歌单
    public ListSong selectById(Integer id);

    // 根据歌单id查询所有的歌曲
    public List<ListSong> selectListSongById(Integer songListId);
}
