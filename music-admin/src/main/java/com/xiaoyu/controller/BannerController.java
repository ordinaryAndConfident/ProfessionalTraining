package com.xiaoyu.controller;

import com.xiaoyu.pojo.Banner;
import com.xiaoyu.service.BannerService;
import com.xiaoyu.utils.FileUtils;
import com.xiaoyu.vo.ResStatus;
import com.xiaoyu.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("/add")
    public ResultVO insertBanner(Banner banner){
        boolean insert = bannerService.insert(banner);
        if (insert){
            return new ResultVO(ResStatus.OK,"上传成功!",insert);
        }else {
            return new ResultVO(ResStatus.OK,"上传失败!",insert);
        }
    }

    @PostMapping("/del")
    public ResultVO deleteBanner(Integer id){
        // 删除轮播图同时删除后端文件夹中的图片资源
        Banner banner = bannerService.getBannerById(id);
        if (banner != null){
            String realPath = System.getProperty("user.dir") + "/" + banner.getUrl();
            FileUtils.deleteDiskFile(realPath);
        }
        boolean delete = bannerService.delBanner(id);
        if (delete){
            return new ResultVO(ResStatus.OK,"删除成功!",delete);
        }else {
            return new ResultVO(ResStatus.OK,"删除失败!",delete);
        }
    }

    @PutMapping("/update")
    public ResultVO updateBanner(Banner banner){
        boolean update = bannerService.update(banner);
        if (update){
            return new ResultVO(ResStatus.OK,"更新成功!",update);
        }else {
            return new ResultVO(ResStatus.OK,"更新失败!",update);
        }
    }

    @GetMapping("/all")
    public ResultVO getAllBanner(){
        List<Banner> banners = bannerService.getAllBanner();
        if (banners.size() > 0){
            return new ResultVO(ResStatus.OK,"查询成功!",banners);
        }else {
            return new ResultVO(ResStatus.OK,"查询失败!",null);
        }
    }

    @GetMapping("/getTop4")
    public ResultVO getTop4Banner(){
        List<Banner> top4Banner = bannerService.getTop4Banner();
        if (top4Banner.size() > 0){
            return new ResultVO(ResStatus.OK,"查询成功!",top4Banner);
        }else {
            return new ResultVO(ResStatus.OK,"查询失败!",null);
        }
    }

    @PostMapping("/upload/banner")
    public ResultVO upload(@RequestParam("file") MultipartFile bannerFile, @RequestParam("id") Integer id) {
        if (bannerFile.isEmpty()) {
            return new ResultVO(ResStatus.NO, "文件上传失败", null);
        }
        // 避免重复
        String fileName = UUID.randomUUID() + bannerFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "images"
                + System.getProperty("file.separator") + "banner"
                + System.getProperty("file.separator");
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        //实际地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //文件地址存储到数据库
        String relativePath = "images/banner/" + fileName;
        try {
            bannerFile.transferTo(dest);
            Banner banner = new Banner();

            // 如果 id为空则表示新增轮播图 否则为更新
            if (id == 3){
                banner.setUrl(relativePath);
                banner.setIsShow(1);
                if (bannerService.insert(banner)) {
                    return new ResultVO(ResStatus.OK, "轮播图上传成功!", relativePath);
                } else {
                    return new ResultVO(ResStatus.NO, "轮播图上传失败!", null);
                }
            }else{
                banner.setId(id);
                banner.setUrl(relativePath);
                banner.setIsShow(1);
                if (bannerService.update(banner)) {
                    return new ResultVO(ResStatus.OK, "文件上传成功", relativePath);
                } else {
                    return new ResultVO(ResStatus.NO, "文件上传失败", null);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
