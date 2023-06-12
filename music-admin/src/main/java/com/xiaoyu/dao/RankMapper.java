package com.xiaoyu.dao;

import com.xiaoyu.general.BaseDao;
import com.xiaoyu.pojo.Rank;
import org.springframework.stereotype.Repository;

@Repository
public interface RankMapper extends BaseDao<Rank> {

    // 用来判断用户是否已经评分了
    public Rank selectRankBySongListId(Integer songListId,Integer consumerId);

    /*新增评价*/
    public int insert(Rank rank);

    /*查询歌单评价的总分数*/
    public int scoreSumBySongListId(Integer songListId);

    /*查询评分人数*/
    public int rankNumBySongListId(Integer songListId);
}
