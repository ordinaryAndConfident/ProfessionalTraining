package com.xiaoyu.service.impl;

import com.xiaoyu.dao.BannerMapper;
import com.xiaoyu.pojo.Banner;
import com.xiaoyu.service.BannerService;
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
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public boolean insert(Banner banner) {
        return bannerMapper.insert(banner) > 0;
    }

    @Override
    public boolean delBanner(Integer id) {
        return bannerMapper.delBanner(id) > 0;
    }

    @Override
    public boolean update(Banner banner) {
        return bannerMapper.update(banner) > 0;
    }

    @Override
    public List<Banner> getAllBanner() {
        return bannerMapper.getAllBanner();
    }

    @Override
    public List<Banner> getTop4Banner() {
        return bannerMapper.getTop4Banner();
    }

    @Override
    public Banner getBannerById(Integer id) {
        return bannerMapper.getBannerById(id);
    }
}
