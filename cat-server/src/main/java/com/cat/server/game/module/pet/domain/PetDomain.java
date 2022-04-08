package com.cat.server.game.module.pet.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.module.resource.IResourceDomain;
import com.cat.server.game.module.resource.domain.PetResourceDomain;

/**
* PetDomain
* @author Jeremy
*/
public class PetDomain extends AbstractModuleMultiDomain<Long, Long, Pet> implements IResourceDomain<Long, Pet>{

	private static final Logger log = LoggerFactory.getLogger(PetDomain.class);
	
	private static final int LIMIT = 999; 
	/**
	 * 资源代理对象
	 */
	private IResourceDomain<Long, Pet> resourceDomainProxy;
	
	public PetDomain(){
		
	}

	
	////////////业务代码////////////////////
	
	
	@Override
	public void afterInit() {
		this.resourceDomainProxy = PetResourceDomain.create(getId(), getBeanMap());
	}
	
	@Override
	public Pet getBeanByConfigId(int configId) {
		return resourceDomainProxy.getBeanByConfigId(configId);
	}

	@Override
	public boolean checkAdd(int configId, int count) {
		return resourceDomainProxy.checkAdd(configId, count);
	}

	@Override
	public List<Pet> add(int configId, int count) {
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
	public List<Pet> getAndClearUpdateList() {
		return resourceDomainProxy.getAndClearUpdateList();
	}

	@Override
	public List<Pet> getAndClearDeleteList() {
		return resourceDomainProxy.getAndClearDeleteList();
	}

	@Override
	public int getCount(int configId) {
		return resourceDomainProxy.getCount(configId);
	}

	@Override
	public void clearExpire(int configId) {
		resourceDomainProxy.clearExpire(configId);
	}
}
