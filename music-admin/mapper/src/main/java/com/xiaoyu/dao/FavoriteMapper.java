package com.xiaoyu.dao;

import com.xiaoyu.pojo.Favorite;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteMapper {
    public int insert(Favorite favorite);

    public int update(Favorite favorite);

    public int delete(Integer id);

    public List<Favorite> selectAll();

    // 查询某个用户的收藏列表
    public List<Favorite> selectByConsumerId(Integer consumerId);

    // 查询用户是否收藏该歌曲
    public List<Favorite> existSongId(Integer songId);

    // 查询用户是否收藏该歌单
    public List<Favorite> existSongList(Integer songListId);

    // 删除指定用户收藏的歌曲
    public int removeFavorite(Integer consumerId,Integer songId);
}
