package com.cat.server.game.module.equip.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cat.server.core.server.AbstractModuleDomain;
import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.module.resource.IResourceDomain;
import com.cat.server.game.module.resource.domain.EquipResourceDomain;
import com.cat.server.game.module.resource.domain.ItemResourceDomain;

/**
* EquipDomain
* @author Jeremy
*/
public class EquipDomain extends AbstractModuleMultiDomain<Long, Long, Equip> implements IResourceDomain<Long, Equip>{

	private static final Logger log = LoggerFactory.getLogger(EquipDomain.class);
	
	private static final int LIMIT = 999; 
	/**
	 * 资源代理对象
	 */
	private IResourceDomain<Long, Equip> resourceDomainProxy;
	
	public EquipDomain(){
		
	}

	
	////////////业务代码////////////////////
	
	
	@Override
	public void afterInit() {
		this.resourceDomainProxy = EquipResourceDomain.create(getId(), getBeanMap());
	}
	
	@Override
	public Equip getBeanByConfigId(int configId) {
		return resourceDomainProxy.getBeanByConfigId(configId);
	}

	//@Override
	//public Collection<Equip> getBeansByConfigId(int configId) {
	//	return resourceDomainProxy.getBeansByConfigId(configId);
	//}

	@Override
	public boolean checkAdd(int configId, int count) {
		return resourceDomainProxy.checkAdd(configId, count);
	}

	@Override
	public List<Equip> add(int configId, int count) {
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
	public List<Equip> getAndClearUpdateList() {
		return resourceDomainProxy.getAndClearUpdateList();
	}

	@Override
	public List<Equip> getAndClearDeleteList() {
		return resourceDomainProxy.getAndClearDeleteList();
	}


	@Override
	public int getCount(int configId) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<Equip> add(int configId, int count) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Equip> getAndClearUpdateList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Long> getAndClearDeleteList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void clearExpire(int configId) {
		// TODO Auto-generated method stub
		
	}
}
