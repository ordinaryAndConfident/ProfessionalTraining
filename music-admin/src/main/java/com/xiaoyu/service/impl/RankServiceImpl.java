package com.xiaoyu.service.impl;

import com.xiaoyu.dao.RankMapper;
import com.xiaoyu.pojo.Rank;
import com.xiaoyu.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/11/15 10:36
 */
@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankMapper rankMapper;

    @Override
    public Rank selectRankBySongListId(Integer songListId,Integer consumerId) {
        return rankMapper.selectRankBySongListId(songListId,consumerId);
    }

    @Override
    public boolean insert(Rank rank) {
        return rankMapper.insert(rank) > 0;
    }

    @Override
    public int scoreSumBySongListId(Integer songListId) {
        return rankMapper.scoreSumBySongListId(songListId);
    }

    @Override
    public int rankNumBySongListId(Integer songListId) {
        return rankMapper.rankNumBySongListId(songListId);
    }

    @Override
    public int rankAvgBySongListId(Integer songListId) {
        int rankNum = rankMapper.rankNumBySongListId(songListId);
        if (rankNum == 0){  // 暂无人评价的情况
            return 5;
        }
        return rankMapper.scoreSumBySongListId(songListId) / rankNum;
    }
}
