package com.xiaoyu.controller;

import com.xiaoyu.pojo.ListSong;
import com.xiaoyu.service.ListSongService;
import com.xiaoyu.vo.ResStatus;
import com.xiaoyu.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/listSong")
public class ListSongController {

    @Autowired
    private ListSongService listSongService;

    @PostMapping("/add")
    public ResultVO addListSong(ListSong listSong) {
        boolean insert = listSongService.insert(listSong);
        if (insert) {
            return new ResultVO(ResStatus.OK, "添加成功!", insert);
        } else {
            return new ResultVO(ResStatus.NO, "添加失败!", null);
        }
    }

    @GetMapping("/delete")
    public ResultVO deleteListSongById(Integer id) {
        boolean delete = listSongService.deleteListSongById(id);
        if (delete) {
            return new ResultVO(ResStatus.OK, "删除成功!", delete);
        } else {
            return new ResultVO(ResStatus.NO, "删除失败!", null);
        }
    }

    @GetMapping("/delete/list")
    public ResultVO deleteBySongIdAndListSongId(Integer songId,Integer listSongId) {
        boolean delete = listSongService.deleteBySongIdAndListSongId(songId,listSongId);
        if (delete) {
            return new ResultVO(ResStatus.OK, "删除成功!", delete);
        } else {
            return new ResultVO(ResStatus.NO, "删除失败!", null);
        }
    }

    @PutMapping("/update")
    public ResultVO updateListSong(ListSong listSong) {
        boolean update = listSongService.update(listSong);
        if (update) {
            return new ResultVO(ResStatus.OK, "添加成功!", update);
        } else {
            return new ResultVO(ResStatus.NO, "添加失败!", null);
        }
    }

    @GetMapping("/all")
    public ResultVO getAllListSong() {
        List<ListSong> listSongs = listSongService.selectAll();
        if (listSongs.size() > 0) {
            return new ResultVO(ResStatus.OK, "添加成功!", listSongs);
        } else {
            return new ResultVO(ResStatus.NO, "添加失败!", null);
        }
    }

    @GetMapping("/byId")
    public ResultVO selectById(Integer id) {
        ListSong listSong = listSongService.selectById(id);
        if (listSong != null) {
            return new ResultVO(ResStatus.OK, "添加成功!", listSong);
        } else {
            return new ResultVO(ResStatus.NO, "添加失败!", null);
        }
    }

    @GetMapping("/byListSongId")
    public ResultVO selectListSongById(Integer listSongId) {
        List<ListSong> listSongs = listSongService.selectListSongById(listSongId);
        if (listSongs.size() > 0) {
            return new ResultVO(ResStatus.OK, "添加成功!", listSongs);
        } else {
            return new ResultVO(ResStatus.NO, "添加失败!", null);
        }
    }
}
