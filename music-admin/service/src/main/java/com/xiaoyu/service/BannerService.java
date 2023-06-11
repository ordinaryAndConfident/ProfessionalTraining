package com.xiaoyu.service;

import com.xiaoyu.pojo.Banner;

import java.util.List;

public interface BannerService {

    public boolean insert(Banner banner);   // 新增轮播图

    public boolean delBanner(Integer id);      // 删除轮播图

    public boolean update(Banner banner);   // 更新轮播图

    public List<Banner> getAllBanner(); // 查询所有轮播图

    public List<Banner> getTop4Banner();    // 根据添加时间，查询靠前的前四张轮播图

    public Banner getBannerById(Integer id);    // 根据ID获取轮播图
}
