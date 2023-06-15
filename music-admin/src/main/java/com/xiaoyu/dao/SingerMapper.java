package com.xiaoyu.dao;

import com.xiaoyu.general.BaseDao;
import com.xiaoyu.pojo.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface SingerMapper extends BaseDao<Singer> {

    public int insert(Singer singer);

    public int deleteSinger(Integer id);

    public int update(Singer singer);

    public List<Singer> selectAll();

    public Singer selectById(Integer id);

    /*根据歌手名称模糊查询列表*/
    public List<Singer> selectSingerByName(String name);

    /*根据性别模糊查询列表*/
    public List<Singer> selectSingerBySex(Integer sex);

}
