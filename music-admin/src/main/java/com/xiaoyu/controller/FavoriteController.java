package com.xiaoyu.controller;

import com.xiaoyu.pojo.Favorite;
import com.xiaoyu.service.FavoriteService;
import com.xiaoyu.vo.ResStatus;
import com.xiaoyu.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/new")
    public ResultVO insert(Favorite favorite){
        boolean insert = favoriteService.insert(favorite);
        if(insert){
            return new ResultVO(ResStatus.OK,"收藏成功!",insert);
        }else {
            return new ResultVO(ResStatus.NO,"收藏失败!",null);
        }
    }

    @GetMapping("/delete")
    public ResultVO delete(Integer id){
        boolean delete = favoriteService.delete(id);
        if(delete){
            return new ResultVO(ResStatus.OK,"取消收藏成功",delete);
        }else {
            return new ResultVO(ResStatus.NO,"取消收藏失败",null);
        }
    }

    @GetMapping("/remove")
    public ResultVO remove(Integer consumerId,Integer songId){
        boolean delete = favoriteService.removeFavorite(consumerId,songId);
        if(delete){
            return new ResultVO(ResStatus.OK,"删除成功",delete);
        }else {
            return new ResultVO(ResStatus.NO,"删除失败",null);
        }
    }

    @PutMapping("/update")
    public ResultVO update(Favorite favorite){
        boolean update = favoriteService.update(favorite);
        if(update){
            return new ResultVO(ResStatus.OK,"修改成功",update);
        }else {
            return new ResultVO(ResStatus.NO,"修改失败",null);
        }
    }

    @GetMapping("/all")
    public ResultVO getAll(){
        List<Favorite> favorites = favoriteService.selectAll();
        if(favorites.size() > 0){
            return new ResultVO(ResStatus.OK,"查询成功",favorites);
        }else {
            return new ResultVO(ResStatus.NO,"修改失败",null);
        }
    }

    @GetMapping("/byId")
    public ResultVO getConsumerFavorite(Integer consumerId){
        List<Favorite> favorites = favoriteService.selectByConsumerId(consumerId);
        if(favorites.size() > 0){
            return new ResultVO(ResStatus.OK,"查询成功",favorites);
        }else {
            return new ResultVO(ResStatus.NO,"查询失败",null);
        }
    }

    @GetMapping("/exist/song")
    public ResultVO existSongId(Integer songId){
        List<Favorite> list = favoriteService.existSongId(songId);
        if(list.size() > 0){
            return new ResultVO(ResStatus.OK,"查询成功",list);
        }else {
            return new ResultVO(ResStatus.NO,"修改失败",null);
        }
    }

    @GetMapping("/exist/songList")
    public ResultVO existSongList(Integer songListId){
        List<Favorite> list = favoriteService.existSongList(songListId);
        if(list.size() > 0){
            return new ResultVO(ResStatus.OK,"查询成功",list);
        }else {
            return new ResultVO(ResStatus.NO,"修改失败",null);
        }
    }
}
