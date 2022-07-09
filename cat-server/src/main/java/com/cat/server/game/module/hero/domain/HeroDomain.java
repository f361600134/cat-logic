package com.cat.server.game.module.hero.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.module.resource.IResourceDomain;
import com.cat.server.game.module.resource.domain.HeroResourceDomain;

/**
* HeroDomain
* @author Jeremy
*/
public class HeroDomain extends AbstractModuleMultiDomain<Long, Long, Hero> implements IResourceDomain<Long, Hero>{

	private static final Logger log = LoggerFactory.getLogger(HeroDomain.class);
	
	/**
	 * 资源代理对象
	 */
	private IResourceDomain<Long, Hero> resourceDomainProxy;
//	private HeroResourceDomain resourceDomainProxy;
	
	public HeroDomain(){
		
	}

	////////////业务代码////////////////////

	@Override
	public void afterInit() {
		this.resourceDomainProxy = HeroResourceDomain.create(getId(), getBeanMap());
	}
	
	@Override
	public Hero getBeanByConfigId(int configId) {
		return resourceDomainProxy.getBeanByConfigId(configId);
	}

	@Override
	public boolean checkAdd(int configId, int count) {
		return resourceDomainProxy.checkAdd(configId, count);
	}

	@Override
	public List<Hero> add(int configId, int count) {
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
	public List<Hero> getAndClearUpdateList() {
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

	@Override
	public void addReource(Long k, Hero v) {
		resourceDomainProxy.addReource(k, v);
	}
}

