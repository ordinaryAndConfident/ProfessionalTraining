package com.xiaoyu.dao;

import com.xiaoyu.general.BaseDao;
import com.xiaoyu.pojo.Banner;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BannerMapper extends BaseDao<Banner> {

    public int insert(Banner banner);   // 新增轮播图

    public int delBanner(Integer id);      // 删除轮播图

    public int update(Banner banner);   // 更新轮播图

    public List<Banner> getAllBanner(); // 查询所有轮播图

    public List<Banner> getTop4Banner();    // 根据添加时间，查询靠前的前四张轮播图

    public Banner getBannerById(Integer id);    // 根据ID获取轮播图

}
