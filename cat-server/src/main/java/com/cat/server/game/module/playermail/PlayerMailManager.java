package com.cat.server.game.module.playermail;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.orm.core.db.process.IDataProcess;
import com.cat.server.core.server.IModuleDbManager;
import com.cat.server.core.server.IModuleManager;
import com.cat.server.game.module.playermail.domain.PlayerMail;
import com.cat.server.game.module.playermail.domain.PlayerMailDomain;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 玩家个人邮件管理, 因为要支持离线玩家邮件查询, 查询后不能释放掉邮箱内容, 所以要特殊的支持
 * 方案1: 重写实现类, 使用Guava Cache存储
* @author Jeremy
*/
@Component
public class PlayerMailManager implements IModuleManager<Long, PlayerMailDomain>, IModuleDbManager<Long, PlayerMailDomain> {
	
	public static Logger logger = LoggerFactory.getLogger(PlayerMailManager.class); 
	
	@Autowired protected IDataProcess process;
	
	/**
	 * 聊天缓存, 所有频道聊天缓存, 使用Cache替代掉Map
	 * key:BigInteger, uniqueId, 唯一id
	 * value:聊天记录实体bean
	 */
	private Cache<Long, PlayerMailDomain> domains = CacheBuilder.newBuilder()
			//在给定时间内没有被读/写访问,则清除
			.expireAfterAccess(30, TimeUnit.MINUTES)
			//最大条目,超过这个聊天记录, 根据LRU特点移除
			.build();

	@Override
	public Collection<PlayerMailDomain> getAllDomain() {
		return domains.asMap().values();
	}

	@Override
	public PlayerMailDomain getDomain(Long playerId) {
		PlayerMailDomain domain = domains.getIfPresent(playerId);
		if (domain == null) {
			domain = getFromDb(playerId);
		}
		return domain;
	}

	@Override
	public void remove(Long id) {
		domains.invalidate(id);
	}
	
	@Override
	public PlayerMailDomain getFromDb(Long playerId) {
		try {
			PlayerMailDomain domain = new PlayerMailDomain();
			List<PlayerMail> list = process.selectByIndex(domain.getBasePoClazz(), new Object[] {playerId});
			if (list.isEmpty()) {
				//	无数据创建
				domain.initData(playerId);
			}else {
				//	有数据初始化
				domain.initData(playerId, list);
			}
			domains.put(playerId, domain);
			return domain;
		}catch (Exception e) {
			logger.error("getFromDb error", e);
		}
		return null;
	}
	
//	/**
//	 * 获取邮件列表
//	 * @param playerId
//	 * @return  
//	 * @return Collecion<IMail>  
//	 * @date 2021年11月28日下午10:05:44
//	 */
//	public Collection<IMail> getMails(long playerId) {
//		List<IMail> rets = new ArrayList<>();
//		for (IMailServiceContainer mailServiceContainer : mailServiceContainer) {
//			rets.addAll(mailServiceContainer.getMails(playerId));
//		}
//		return rets;
//	}

}
