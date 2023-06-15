package com.xiaoyu.controller;

import com.xiaoyu.pojo.SongList;
import com.xiaoyu.service.SongListService;
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
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    private SongListService songListService;

    @PostMapping("/add")
    public ResultVO addSongList(SongList songList) {
        boolean insert = songListService.insert(songList);
        if (insert) {
            return new ResultVO(ResStatus.OK, "添加成功!", insert);
        } else {
            return new ResultVO(ResStatus.NO, "添加失败!", null);
        }
    }

    @GetMapping("/delete")
    public ResultVO deleteSongList(Integer id) {
        boolean delete = songListService.deleteSongList(id);
        if (delete) {
            return new ResultVO(ResStatus.OK, "删除成功!", delete);
        } else {
            return new ResultVO(ResStatus.NO, "删除失败!", null);
        }
    }

    @PutMapping("/update")
    public ResultVO updateSongList(SongList songList) {
        boolean update = songListService.update(songList);
        if (update) {
            return new ResultVO(ResStatus.OK, "修改成功!", update);
        } else {
            return new ResultVO(ResStatus.NO, "修改失败!", null);
        }
    }

    @GetMapping("/all")
    public ResultVO getAllSongList() {
        List<SongList> songLists = songListService.selectAll();
        if (songLists.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功!", songLists);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/byId")
    public ResultVO selectById(Integer id) {
        SongList songList = songListService.selectById(id);
        if (songList != null) {
            return new ResultVO(ResStatus.OK, "查询成功!", songList);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/likeTitle")
    public ResultVO likeTitle(String title) {
        List<SongList> songLists = songListService.likeTitle("%"+title+"%");
        if (songLists.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功!", songLists);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/byTitle")
    public ResultVO byTitle(String title) {
        List<SongList> songLists = songListService.selectSongListByTitle(title);
        if (songLists.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功!", songLists);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/likeStyle")
    public ResultVO likeStyle(String style) {
        List<SongList> songLists = songListService.likeStyle("%"+style+"%");
        if (songLists.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功!", songLists);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    // 修改歌单图片
    @PostMapping("/upload")
    public ResultVO uploadSongPic(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") Integer id) {
        if (avatarFile.isEmpty()) {
            return new ResultVO(ResStatus.NO, "更新失败", null);
        }
        // 避免重复
        String fileName = UUID.randomUUID() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "images"
                + System.getProperty("file.separator") + "songListPic";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        //实际地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //文件地址存储到数据库
        String relativePath = "images/songListPic/" + fileName;
        try {
            avatarFile.transferTo(dest);
            // 更新图片前，先删除原来的图片
            SongList songList = songListService.selectById(id);
            String path = System.getProperty("user.dir") + "/" + songList.getPic();
            FileUtils.deleteDiskFile(path);
            songList.setId(id);
            songList.setPic(relativePath);

            boolean i = songListService.update(songList);
            if (i) {
                return new ResultVO(ResStatus.OK, "修改成功", relativePath);
            } else {
                return new ResultVO(ResStatus.NO, "修改失败", null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
