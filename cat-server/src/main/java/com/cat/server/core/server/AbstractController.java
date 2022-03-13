package com.cat.server.core.server;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IController;
import com.cat.server.game.module.faction.IFactionService;

/**
 * 抽象控制器
 * @auth Jeremy
 * @date 2022年3月13日下午8:46:16
 */
public abstract class AbstractController implements IController{
	
	@Autowired IFactionService factionService;
	
	@Override
	public boolean verify(ISession session) {
		long playerId = session.getUserData();
		return factionService.checkOpen(playerId, this.getFactionId());
	}
	
	/**
	 * 获取功能id
	 * @return int 功能id
	 */
	public abstract int getFactionId();

}
