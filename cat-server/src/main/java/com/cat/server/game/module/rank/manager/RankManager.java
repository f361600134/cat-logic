package com.cat.server.game.module.rank.manager;

import java.util.List;

import com.cat.server.common.ServerConfig;
import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.rank.domain.Rank;
import com.cat.server.game.module.rank.domain.RankDomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 排行榜管理器
 * TODO 优化内容, 排行榜初始化时全部加载, 应直接通过服务器id去获取
* @author Jeremy
*/
@Component
public class RankManager extends AbstractModuleManager<Integer, RankDomain>{
	
	@Autowired private ServerConfig serverConfig;
	
	/**
	 * 获取数据, 获取不到从数据库获取
	 */
	@Override
    public synchronized RankDomain getDomain(Integer id) {
		RankDomain domain = domains.get(id);
		if (domain == null) {
			domain = getFromDb(id);
			domains.put(id, domain);
		}
		return domain;
	}
	
	@Override
	public RankDomain getFromDb(Integer id) {
		try {
			RankDomain domain = new RankDomain(id);
			String[] cols = new String[] {Rank.PROP_CURSERVERID, Rank.PROP_RANKTYPE};
			List<Rank> list = process.selectByIndex(Rank.class, cols, new Object[] {serverConfig.getServerId(),id});
			if (list.isEmpty()) {
				//	无数据
				domain.initData(id);
			}else {
				//	有数据初始化
				domain.initData(id, list);
			}
			domain.afterInit();
			return domain;
		}catch (Exception e) {
			logger.error("getFromDb error", e);
		}
		return null;
	}
	
}
