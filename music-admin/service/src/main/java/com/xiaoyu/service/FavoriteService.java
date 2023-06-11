package com.xiaoyu.service;

import com.xiaoyu.pojo.Favorite;

import java.util.List;

public interface FavoriteService {
    public boolean insert(Favorite favorite);

    public boolean update(Favorite favorite);

    public boolean delete(Integer id);

    public List<Favorite> selectAll();

    // 查询某个用户的收藏列表
    public List<Favorite> selectByConsumerId(Integer consumerId);

    // 查询用户是否收藏该歌曲
    public List<Favorite> existSongId(Integer songId);

    // 查询用户是否收藏该歌单
    public List<Favorite> existSongList(Integer songListId);

    // 删除指定用户收藏的歌曲
    public boolean removeFavorite(Integer consumerId,Integer songId);
}
