package com.xiaoyu.controller;

import com.xiaoyu.pojo.Singer;
import com.xiaoyu.service.SingerService;
import com.xiaoyu.vo.ResStatus;
import com.xiaoyu.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/10/18 21:55
 */
@RestController
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    private SingerService singerService;

    @PostMapping("/add")
    public ResultVO addSinger(Singer singer) {
        boolean insert = singerService.insert(singer);
        if (insert) {
            return new ResultVO(ResStatus.OK, "添加成功!", insert);
        } else {
            return new ResultVO(ResStatus.NO, "添加失败!", null);
        }
    }

    @GetMapping("/del")
    public ResultVO delete(Integer id) {
        boolean i = singerService.deleteSinger(id);
        if (i) {
            return new ResultVO(ResStatus.OK, "删除成功!", i);
        } else {
            return new ResultVO(ResStatus.NO, "删除失败!", null);
        }
    }

    @PutMapping("/update")
    public ResultVO update(Singer singer) {
        boolean i = singerService.update(singer);
        if (i) {
            return new ResultVO(ResStatus.OK, "更新成功!", i);
        } else {
            return new ResultVO(ResStatus.NO, "更新失败!", null);
        }
    }

    @GetMapping("/all")
    public ResultVO getAll() {
        List<Singer> singers = singerService.selectAll();
        if (singers.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功", singers);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/byName")
    public ResultVO getByName(String name) {
        List<Singer> singers = singerService.selectSingerByName(name);
        if (singers.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功", singers);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/byId")
    public ResultVO getById(Integer id) {
        Singer singer = singerService.selectById(id);
        if (singer != null) {
            return new ResultVO(ResStatus.OK, "查询成功", singer);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/bySex")
    public ResultVO getBySex(Integer sex) {
        List<Singer> singers = singerService.selectSingerBySex(sex);
        if (singers.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功", singers);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @PostMapping("/upload")
    public ResultVO upload(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") Integer id) {
        if (avatarFile.isEmpty()) {
            return new ResultVO(ResStatus.NO, "文件上传失败", null);
        }
        // 避免重复
        String fileName = UUID.randomUUID() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "images"
                + System.getProperty("file.separator") + "singerPic"
                + System.getProperty("file.separator");
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        //实际地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //文件地址存储到数据库
        String relativePath = "images/singerPic/" + fileName;
        try {
            avatarFile.transferTo(dest);
            Singer singer = new Singer();
            singer.setId(id);
            singer.setAvatar(relativePath);

            boolean i = singerService.update(singer);
            if (i) {
                return new ResultVO(ResStatus.OK, "文件上传成功", relativePath);
            } else {
                return new ResultVO(ResStatus.NO, "文件上传失败", null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
