package com.cat.server.game.module.family;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.orm.core.db.process.IDataProcess;
import com.cat.server.common.ServerConfig;
import com.cat.server.core.lifecycle.Lifecycle;
import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.family.domain.FamilyDomain;
import com.cat.server.game.module.family.domain.FamilyMember;
import com.cat.server.game.module.rank.domain.Rank;

@Component
class FamilyManager implements Lifecycle{
	
	private static final Logger logger = LoggerFactory.getLogger(FamilyManager.class);
	
	@Autowired
	private IDataProcess dataProcess;
	
	@Autowired
	private ServerConfig serverConfig;
	
	/**
	 * 家族domain
	 */
	private FamilyDomain domain;

	/**
	 * key:familyName
	 * value: familyId
	 */
	private Map<String, Long> familyNameMap = new ConcurrentHashMap<>();
	
	/**
	 * key: 玩家id
	 * value: 家族id
	 */
	private Map<Long, Long> familyPlayerIdMap = new ConcurrentHashMap<>();
	
	/**
	 * 根据玩家id获取家族id
	 * @return  
	 * @return long  
	 * @date 2021年5月10日下午10:21:54
	 */
	public long getFamilyIdByPlayerId(long playerId) {
		return familyPlayerIdMap.get(playerId);
	}
	
	public Map<String, Long> getFamilyNameMap() {
		return familyNameMap;
	}
	
	public FamilyDomain getDomain() {
		return domain;
	}
	
	/**
	 * 从数据库获取
	 *   
	 * @return void  
	 * @date 2021年5月10日下午10:12:41
	 */
	private FamilyDomain getFromDb() {
		try {
			FamilyDomain domain = new FamilyDomain();
			final int serverId = serverConfig.getServerId();
			String[] cols = new String[] {Rank.PROP_CURSERVERID};
			List<Family> list = dataProcess.selectByIndex(Family.class, cols, new Object[] {serverId});
			if (list.isEmpty()) {
				//	无数据
				domain.initData(serverId);
			}else {
				//	有数据初始化
				domain.initData(serverId, list);
			}
			domain.afterInit();
			return domain;
		}catch (Exception e) {
			logger.error("getFromDb error", e);
		}
		return null;
	}
	
	@Override
	public void start() throws Throwable {
		this.domain = getFromDb();
		if (domain == null) {
			throw new RuntimeException("init family error");
		}
		for (Family family : this.domain.getBeans()) {
			familyNameMap.put(family.getName(), family.getId());
			for (FamilyMember member : family.getMembers()) {
				familyPlayerIdMap.put(member.getPlayerId(), family.getId());
			}
		}
	}
	
	/**
	 * 循环家族, 丢进家族线程池, 存储家族信息
	 */
	@Override
	public void stop() throws Throwable {
	}
	
}
