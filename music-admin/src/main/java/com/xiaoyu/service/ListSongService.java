package com.xiaoyu.service;

import com.xiaoyu.pojo.ListSong;

import java.util.List;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/11/6 10:34
 */
public interface ListSongService {
    public boolean insert(ListSong listSong);

    public boolean deleteBySongIdAndListSongId(Integer songId,Integer listSongId);

    public boolean deleteListSongById(Integer id);

    public boolean update(ListSong listSong);

    // 查询歌单里面的所有歌曲
    public List<ListSong> selectAll();

    // 根据 id 查询歌单
    public ListSong selectById(Integer id);

    // 根据歌单id查询所有的歌曲
    public List<ListSong> selectListSongById(Integer songListId);
}
