package com.cat.server.game.module.activityitem.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.module.resource.IResourceDomain;
import com.cat.server.game.module.resource.domain.ActivityItemResourceDomain;

/**
* ActivityItemDomain
* @author Jeremy
*/
public class ActivityItemDomain extends AbstractModuleMultiDomain<Long, Long, ActivityItem> implements IResourceDomain<Long, ActivityItem>{

	private static final Logger log = LoggerFactory.getLogger(ActivityItemDomain.class);
	
	/**
	 * 资源代理对象
	 */
	private IResourceDomain<Long, ActivityItem> resourceDomainProxy;
	
	public ActivityItemDomain(){
		
	}

	
	////////////业务代码////////////////////
	
	
	@Override
	public void afterInit() {
		this.resourceDomainProxy = ActivityItemResourceDomain.create(getId(), getBeanMap());
	}
	
	@Override
	public ActivityItem getBeanByConfigId(int configId) {
		return resourceDomainProxy.getBeanByConfigId(configId);
	}

	//@Override
	//public Collection<ActivityItem> getBeansByConfigId(int configId) {
	//	return resourceDomainProxy.getBeansByConfigId(configId);
	//}

	@Override
	public boolean checkAdd(int configId, int count) {
		return resourceDomainProxy.checkAdd(configId, count);
	}

	@Override
	public List<ActivityItem> add(int configId, int count) {
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
	public List<ActivityItem> getAndClearUpdateList() {
		return resourceDomainProxy.getAndClearUpdateList();
	}

	@Override
	public List<Long> getAndClearDeleteList() {
		return resourceDomainProxy.getAndClearDeleteList();
	}
	
	@Override
	public void clearExpire(int configId) {
		resourceDomainProxy.clearExpire(configId);
	}

	@Override
	public int getCount(int configId) {
		return resourceDomainProxy.getCount(configId);
	}

//	@Override
//	public void addReource(Long k, ActivityItem v) {
//		resourceDomainProxy.addReource(k, v);
//	}
	
}
