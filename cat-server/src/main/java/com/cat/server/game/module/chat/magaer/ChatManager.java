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
