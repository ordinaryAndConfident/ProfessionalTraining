package com.xiaoyu.service.impl;

import com.xiaoyu.dao.SongListMapper;
import com.xiaoyu.pojo.SongList;
import com.xiaoyu.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongListServiceImpl implements SongListService {

    @Autowired
    private SongListMapper songListMapper;

    @Override
    public boolean insert(SongList songList) {
        return songListMapper.insert(songList) > 0;
    }

    @Override
    public boolean deleteSongList(Integer id) {
        return songListMapper.deleteSongList(id) > 0;
    }

    @Override
    public boolean update(SongList songList) {
        return songListMapper.update(songList) > 0;
    }

    @Override
    public List<SongList> selectAll() {
        return songListMapper.selectAll();
    }

    @Override
    public SongList selectById(Integer id) {
        return songListMapper.selectById(id);
    }

    @Override
    public List<SongList> likeTitle(String title) {
        return songListMapper.likeTitle(title);
    }

    @Override
    public List<SongList> selectSongListByTitle(String title) {
        return songListMapper.selectSongListByTitle(title);
    }

    @Override
    public List<SongList> likeStyle(String style) {
        return songListMapper.likeStyle(style);
    }
}
