package com.xiaoyu.service.impl;

import com.xiaoyu.dao.FavoriteMapper;
import com.xiaoyu.pojo.Favorite;
import com.xiaoyu.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/11/18 15:31
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public boolean insert(Favorite favorite) {
        return favoriteMapper.insert(favorite) > 0;
    }

    @Override
    public boolean update(Favorite favorite) {
        return favoriteMapper.update(favorite) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return favoriteMapper.delete(id) > 0;
    }

    @Override
    public List<Favorite> selectAll() {
        return favoriteMapper.selectAll();
    }

    @Override
    public List<Favorite> selectByConsumerId(Integer consumerId) {
        return favoriteMapper.selectByConsumerId(consumerId);
    }

    @Override
    public List<Favorite> existSongId(Integer songId) {
        return favoriteMapper.existSongId(songId);
    }

    @Override
    public List<Favorite> existSongList(Integer songListId) {
        return favoriteMapper.existSongList(songListId);
    }

    @Override
    public boolean removeFavorite(Integer consumerId, Integer songId) {
        return favoriteMapper.removeFavorite(consumerId,songId) > 0;
    }
}
