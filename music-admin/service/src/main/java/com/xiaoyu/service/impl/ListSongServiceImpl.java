package com.xiaoyu.service.impl;

import com.xiaoyu.dao.ListSongMapper;
import com.xiaoyu.pojo.ListSong;
import com.xiaoyu.service.ListSongService;
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
public class ListSongServiceImpl implements ListSongService {

    @Autowired
    private ListSongMapper listSongMapper;

    @Override
    public boolean insert(ListSong listSong) {
        return listSongMapper.insert(listSong) > 0;
    }

    @Override
    public boolean deleteBySongIdAndListSongId(Integer songId, Integer listSongId) {
        return listSongMapper.deleteBySongIdAndListSongId(songId,listSongId) > 0;
    }

    @Override
    public boolean deleteListSongById(Integer id) {
        return listSongMapper.deleteListSongById(id) > 0;
    }

    @Override
    public boolean update(ListSong listSong) {
        return listSongMapper.update(listSong) > 0;
    }

    @Override
    public List<ListSong> selectAll() {
        return listSongMapper.selectAll();
    }

    @Override
    public ListSong selectById(Integer id) {
        return listSongMapper.selectById(id);
    }

    @Override
    public List<ListSong> selectListSongById(Integer songListId) {
        return listSongMapper.selectListSongById(songListId);
    }
}
