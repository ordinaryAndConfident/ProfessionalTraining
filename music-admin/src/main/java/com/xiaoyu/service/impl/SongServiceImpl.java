package com.xiaoyu.service.impl;

import com.xiaoyu.dao.SongMapper;
import com.xiaoyu.pojo.Song;
import com.xiaoyu.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/10/18 22:43
 */
@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;

    @Override
    public boolean insert(Song song) {
        return songMapper.insert(song) > 0;
    }

    @Override
    public boolean deleteSong(Integer id) {
        return songMapper.deleteSong(id) > 0;
    }

    @Override
    public boolean update(Song song) {
        return songMapper.update(song) > 0;
    }

    @Override
    public List<Song> selectAll() {
        return songMapper.selectAll();
    }

    @Override
    public Song selectById(Integer id) {
        return songMapper.selectById(id);
    }

    @Override
    public List<Song> selectSongLikeName(String songName) {
        return songMapper.selectSongLikeName("%"+songName+"%");
    }

    @Override
    public List<Song> selectSongByName(String name) {
        return songMapper.selectSongByName(name);
    }

    @Override
    public List<Song> selectSongBySingerId(Integer singerId) {
        return songMapper.selectSongBySingerId(singerId);
    }

    @Override
    public List<Song> selectSongBySongListId(Integer songListId) {
        return songMapper.selectSongBySongListId(songListId);
    }
}
