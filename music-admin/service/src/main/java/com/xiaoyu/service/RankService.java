package com.xiaoyu.service;

import com.xiaoyu.pojo.Rank;

public interface RankService {

    public Rank selectRankBySongListId(Integer songListId,Integer consumerId);

    /*新增评价*/
    public boolean insert(Rank rank);

    /*查询歌单评价的总分数*/
    public int scoreSumBySongListId(Integer songListId);

    /*查询评分人数*/
    public int rankNumBySongListId(Integer songListId);

    /*平均分*/
    public int rankAvgBySongListId(Integer songListId);
}
