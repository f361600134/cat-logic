package com.cat.server.game.module.rank;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.common.ServerConstant;
import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.rank.event.RankInfoUpdateEvent;
import com.cat.server.game.rpc.event.RpcIdentityAuthenticateSuccessEvent;
import com.google.common.eventbus.Subscribe;

/**
 * Rank事件类
 */
@Component
class RankObserver implements IObserver{
	
	private static final Logger logger = LoggerFactory.getLogger(RankObserver.class);

	@Autowired
    private RankService rankService;


	@Subscribe
    public void rpcIdentityAuthenticateSuccessEvent(RpcIdentityAuthenticateSuccessEvent event){
        //收到rpc身份验证成功事件, 处理相应的业务逻辑
        if (StringUtils.equals(event.getNodeType(), ServerConstant.NODE_TYPE_RANK)){
            //如果身份验证成功的是排行榜服务节点,当前游戏服请求更新排行榜数据覆盖掉排行榜服务
            rankService.rpcIdentityAuthenticateSuccessEvent(event.getNodeType());
        }
    }

	@Subscribe
    public void updateRankEvent(RankInfoUpdateEvent event) {
        try {
        	this.rankService.rankUpdateEvent(event.getType(), event.getUniqueId(), event.getValue(), event.getValue2());
        } catch (Exception e) {
        	logger.error("updateRankEvent error, e:", e);
        }
    }

}