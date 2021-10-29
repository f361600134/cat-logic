package com.cat.rank.service.module;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.cat.rank.service.module.domain.RankDomain;

/**
 * 用于需要跨服的排行榜
 * @author Jeremy
 */
@Component
public class RankManager {

	 /**
     * key: 排行榜类型
     * value: 排行榜域
     */
    private final Map<Integer, RankDomain> rankMap = new ConcurrentHashMap<>();
    
    /**
     * key: 排行榜key, 由游戏服务节点发送过来
     * @return
     */
    public RankDomain getDomain(int rankType){
        return rankMap.get(rankType);
    }

    /**
     * key: 排行榜key, 由游戏服务节点发送过来
     * @param rankType 排行榜类型
     * @param sorted 排序类型
     * @param limit 排行榜数量限制
     * @return 排行榜结构
     */
    public RankDomain getOrCreateDomain(int rankType, int sorted, int limit){
    	RankDomain domain = getDomain(rankType);
        if (domain == null){
        	domain = new RankDomain(rankType, limit, sorted);
            rankMap.put(rankType, domain);
        }
        return rankMap.get(rankType);
    }

}
