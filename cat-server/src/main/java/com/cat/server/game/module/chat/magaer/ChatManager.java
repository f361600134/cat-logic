package com.cat.server.game.module.chat.magaer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.cat.server.core.server.IModuleManager;
import com.cat.server.game.module.chat.domain.ChatDomain;

@Component
public class ChatManager implements IModuleManager<Integer, ChatDomain>{
	
	/**域缓存*/
	protected final Map<Integer, ChatDomain> domains = new ConcurrentHashMap<>();
	
//	/**
//	 * 聊天缓存, 所有频道聊天缓存, 使用Cache替代掉Map
//	 * key:BigInteger, uniqueId, 唯一id
//	 * value:聊天记录实体bean
//	 */
//	private Cache<BigInteger, Chat> chatRecordMap = CacheBuilder.newBuilder()
//			.expireAfterAccess(1, TimeUnit.HOURS)// 在给定时间内没有被读/写访问,则清除
//			.maximumSize(1000)//	最大条目,超过这个聊天记录, 根据LRU特点移除
//			.removalListener((notification)->{//移除监听,因超时被移除的聊天数据,持久化到数据库
//				Chat bean = (Chat)notification.getValue();
//				bean.delete();
//			}).build();
	
	/**
	 * 获取数据, 获取不到从数据库获取
	 */
	public ChatDomain getDomain(Integer id) {
		ChatDomain domain = domains.get(id);
		if (domain == null) {
			domain = new ChatDomain(id);
			domains.put(id, domain);
		}
		return domain;
	}

	@Override
	public void remove(Integer id) {
		domains.remove(id);
	}
	
}
