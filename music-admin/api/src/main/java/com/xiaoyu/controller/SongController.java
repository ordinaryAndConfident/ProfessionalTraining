package com.xiaoyu.controller;

import com.xiaoyu.pojo.Song;
import com.xiaoyu.service.SongService;
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

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/10/18 21:55
 */
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/add")
    public ResultVO addSong(Song song, @RequestParam("file") MultipartFile file) {
        song.setPicture("images/songPic/music.png"); // 默认音乐图片
        song.getLyric().trim(); // 去掉歌词空格
        // 上传歌曲文件
        return uploadSong(file, song);
    }

    @GetMapping("/delete")
    public ResultVO delete(Integer id) {
        // 删除磁盘对应的音乐文件
        Song song = songService.selectById(id);
        String path = System.getProperty("user.dir") + song.getUrl();
        FileUtils.deleteDiskFile(path);
        boolean i = songService.deleteSong(id);
        if (i) {
            return new ResultVO(ResStatus.OK, "删除成功!", i);
        } else {
            return new ResultVO(ResStatus.NO, "删除失败!", null);
        }
    }

    @PutMapping("/update")
    public ResultVO update(Song song) {
        boolean i = songService.update(song);
        if (i) {
            return new ResultVO(ResStatus.OK, "更新成功!", i);
        } else {
            return new ResultVO(ResStatus.NO, "更新失败!", null);
        }
    }

    @GetMapping("/all")
    public ResultVO getAll() {
        List<Song> songs = songService.selectAll();
        if (songs.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功", songs);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/byName")
    public ResultVO getByName(String name) {
        List<Song> songs = songService.selectSongByName(name);
        if (songs.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功", songs);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/likeName")
    public ResultVO getSongLikeName(String songName) {
        List<Song> songs = songService.selectSongLikeName(songName);
        if (songs.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功", songs);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/byId")
    public ResultVO getById(Integer id) {
        Song song = songService.selectById(id);
        if (song != null) {
            return new ResultVO(ResStatus.OK, "查询成功", song);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/singer/details")
    public ResultVO getSongBySingerId(Integer singerId) {
        List<Song> songs = songService.selectSongBySingerId(singerId);
        if (songs.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功", songs);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }

    @GetMapping("/songList/{id}")
    public ResultVO getSongListById(@PathVariable("id") Integer songListId){
        List<Song> songs = songService.selectSongBySongListId(songListId);
        if (songs.size() > 0) {
            return new ResultVO(ResStatus.OK, "查询成功", songs);
        } else {
            return new ResultVO(ResStatus.NO, "查询失败!", null);
        }
    }


    // 添加歌曲同时上传文件
    public ResultVO uploadSong(@RequestParam("file") MultipartFile avatarFile, Song song) {
        if (avatarFile.isEmpty()) {
            return new ResultVO(ResStatus.NO, "歌曲上传失败", null);
        }
        // 避免重复
        String fileName = UUID.randomUUID() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "music";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        //实际地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //文件地址存储到数据库
        String relativePath = "music/" + fileName;
        try {
            avatarFile.transferTo(dest);
            song.setUrl(relativePath);

            boolean i = songService.insert(song);
            if (i) {
                return new ResultVO(ResStatus.OK, "歌曲上传成功", relativePath);
            } else {
                return new ResultVO(ResStatus.NO, "歌曲上传失败", null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 修改歌曲图片
    @PostMapping("/upload")
    public ResultVO uploadSongPic(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") Integer id) {
        if (avatarFile.isEmpty()) {
            return new ResultVO(ResStatus.NO, "文件上传失败", null);
        }
        // 避免重复
        String fileName = UUID.randomUUID() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "images"
                + System.getProperty("file.separator") + "songPic";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        //实际地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //文件地址存储到数据库
        String relativePath = "images/songPic/" + fileName;
        try {
            avatarFile.transferTo(dest);
            // 更新图片前，先删除原来的图片
            Song song1 = songService.selectById(id);
            String path = System.getProperty("user.dir") + "/" + song1.getPicture();
            if (!(song1.getPicture() == "images/songPic/music.png")){
                FileUtils.deleteDiskFile(path);
            }
            Song song = new Song();
            song.setId(id);
            song.setPicture(relativePath);

            boolean i = songService.update(song);
            if (i) {
                return new ResultVO(ResStatus.OK, "修改成功", relativePath);
            } else {
                return new ResultVO(ResStatus.NO, "修改失败", null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 修改歌曲
    @PostMapping("/updateSongUrl")
    public ResultVO updateSong(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") Integer id) {
        if (avatarFile.isEmpty()) {
            return new ResultVO(ResStatus.NO, "修改失败", null);
        }
        // 避免重复
        String fileName = UUID.randomUUID() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "music";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        //实际地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //文件地址存储到数据库
        String relativePath = "/music/" + fileName;
        try {
            avatarFile.transferTo(dest);
            // 更新图片前，先删除原来的图片
            Song song = songService.selectById(id);
            String path = System.getProperty("user.dir") + song.getUrl();
            FileUtils.deleteDiskFile(path);
            song.setId(id);
            song.setUrl(relativePath);

            boolean i = songService.update(song);
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
