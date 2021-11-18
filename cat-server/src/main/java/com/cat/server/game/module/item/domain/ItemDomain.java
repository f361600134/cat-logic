package com.cat.server.game.module.item.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.module.resource.IResourceDomain;
import com.cat.server.game.module.resource.domain.ItemResourceDomain;

/**
 * 物品域
 * 1. 根据物品唯一id获取到物品
 * 2. 根据物品配置id获取到物品
 * @auth Jeremy
 * @date 2020年12月17日下午10:02:51
 */
public class ItemDomain extends AbstractModuleMultiDomain<Long, Long, Item> implements IResourceDomain<Long, Item>{
	
	private static final Logger log = LoggerFactory.getLogger(ItemDomain.class);
	
	/**
	 * 资源代理对象
	 */
	private IResourceDomain<Long, Item> resourceDomainProxy;
	
	public ItemDomain() {
		
	}
	
	@Override
	public void afterInit() {
		this.resourceDomainProxy = ItemResourceDomain.create(getId(), getBeanMap());
	}

	@Override
	public Item getBeanByConfigId(int configId) {
		return resourceDomainProxy.getBeanByConfigId(configId);
	}

//	@Override
//	public Collection<Item> getBeansByConfigId(int configId) {
//		return resourceDomainProxy.getBeansByConfigId(configId);
//	}

	@Override
	public boolean checkAdd(int configId, int count) {
		return resourceDomainProxy.checkAdd(configId, count);
	}

	@Override
	public List<Item> add(int configId, int count) {
		return resourceDomainProxy.add(configId, count);
	}

	@Override
	public boolean checkEnough(int configId, int count) {
		return resourceDomainProxy.checkEnough(configId, count);
	}

	@Override
	public boolean costByConfigId(int configId, int count) {
		return resourceDomainProxy.costByConfigId(configId, count);
	}

	@Override
	public boolean costById(Long id, int count) {
		return resourceDomainProxy.costById(id, count);
	}

	@Override
	public List<Item> getAndClearUpdateList() {
		return resourceDomainProxy.getAndClearUpdateList();
	}

	@Override
	public List<Item> getAndClearDeleteList() {
		return resourceDomainProxy.getAndClearDeleteList();
	}

	@Override
	public void clearExpire(int configId) {
		resourceDomainProxy.clearExpire(configId);
	}
	
}




