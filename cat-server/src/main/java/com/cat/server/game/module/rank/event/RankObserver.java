package com.cat.server.game.module.rank.event;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.rank.service.RankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;

/**
 * Rank事件类
 */
@Component
public class RankObserver implements IObserver{
	
	private static final Logger logger = LoggerFactory.getLogger(RankObserver.class);

	@Autowired
    private RankService rankService;

	@Subscribe
    public void updateRankEvent(RankInfoUpdateEvent event) {
        try {
        	this.rankService.rankUpdateEvent(event.getType(), event.getUniqueId(), event.getValue(), event.getValue2());
        } catch (Exception e) {
        	logger.error("updateRankEvent error, e:", e);
        }
    }

}