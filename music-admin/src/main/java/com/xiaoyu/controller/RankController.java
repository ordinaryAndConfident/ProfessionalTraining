package com.xiaoyu.controller;

import com.xiaoyu.pojo.Rank;
import com.xiaoyu.service.RankService;
import com.xiaoyu.vo.ResStatus;
import com.xiaoyu.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/11/15 10:58
 */
@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private RankService rankService;

    @PostMapping("/new")
    public ResultVO inserRank(Rank rank){
        Rank rk = rankService.selectRankBySongListId(rank.getSongListId(), rank.getConsumerId());
        if (rk != null){
            return new ResultVO(ResStatus.NO,"已经评分过啦!",null);
        }
        boolean insert = rankService.insert(rank);
        if(insert){
            return new ResultVO(ResStatus.OK,"评分成功",insert);
        }else {
            return new ResultVO(ResStatus.NO,"评分失败",null);
        }
    }

    @GetMapping("/sum")
    public ResultVO scoreSum(Integer songListId){
        int sum = rankService.scoreSumBySongListId(songListId);
        if(sum > 0){
            return new ResultVO(ResStatus.OK,"查询成功",sum);
        }else {
            return new ResultVO(ResStatus.NO,"暂无评价",null);
        }
    }

    @GetMapping("/count")
    public ResultVO rankCount(Integer songListId){
        int count = rankService.rankNumBySongListId(songListId);
        if(count > 0){
            return new ResultVO(ResStatus.OK,"查询成功",count);
        }else {
            return new ResultVO(ResStatus.NO,"暂无评价",null);
        }
    }

    @GetMapping("/avg")
    public ResultVO rankAvg(Integer songListId){
        int avg = rankService.rankAvgBySongListId(songListId);
        if(avg > 0){
            return new ResultVO(ResStatus.OK,"查询成功",avg);
        }else {
            return new ResultVO(ResStatus.NO,"暂无歌单平均分",null);
        }
    }

    @GetMapping("/user/score")
    public ResultVO getUserScore(Integer songListId,Integer consumerId){
        Rank rank = rankService.selectRankBySongListId(songListId, consumerId);
        if(rank != null){
            return new ResultVO(ResStatus.OK,"查询成功",rank.getScore());
        }else {
            return new ResultVO(ResStatus.NO,"暂无该用户的评分",null);
        }
    }
}
