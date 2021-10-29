package com.cat.rank.service.module;

import com.cat.rank.service.module.domain.Rank;
import com.cat.rank.service.module.utils.ILeaderboard;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于需要跨服的排行榜
 */
public class RankManager {

    /**
     * key: 排行榜类型
     * value: 排行榜对象
     */
    private final Map<Integer, ILeaderboard<Long, Rank>> rankMap = new ConcurrentHashMap<>();

    /**
     * key: 排行榜key, 由游戏服务节点发送过来
     * @return
     */
    public ILeaderboard<Long, Rank> getLeaderboard(int rankType){
        return rankMap.get(rankType);
    }

    /**
     * key: 排行榜key, 由游戏服务节点发送过来
     * @return
     */
    public ILeaderboard<Long, Rank> getOrCreateLeaderboard(int rankType){
        ILeaderboard<Long, Rank> leaderboard = getLeaderboard(rankType);
        if (leaderboard == null){
            //leaderboard =
        }
        return rankMap.get(rankType);
    }

}
