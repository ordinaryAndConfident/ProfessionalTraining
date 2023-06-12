package com.xiaoyu.service.impl;

import com.xiaoyu.dao.SingerMapper;
import com.xiaoyu.pojo.Singer;
import com.xiaoyu.service.SingerService;
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
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerMapper singerMapper;


    @Override
    public boolean insert(Singer singer) {
        return singerMapper.insert(singer) > 0;
    }

    @Override
    public boolean deleteSinger(Integer id) {
        return singerMapper.deleteSinger(id) > 0;
    }

    @Override
    public boolean update(Singer singer) {
        return singerMapper.update(singer) > 0;
    }

    @Override
    public List<Singer> selectAll() {
        return singerMapper.selectAll();
    }

    @Override
    public Singer selectById(Integer id) {
        return singerMapper.selectById(id);
    }

    @Override
    public List<Singer> selectSingerByName(String name) {
        return singerMapper.selectSingerByName(name);
    }

    @Override
    public List<Singer> selectSingerBySex(Integer sex) {
        return singerMapper.selectSingerBySex(sex);
    }
}
