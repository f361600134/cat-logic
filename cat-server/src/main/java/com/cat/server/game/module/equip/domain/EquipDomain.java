package com.cat.server.game.module.equip.domain;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.module.resource.IEquipResourceDomain;
import com.cat.server.game.module.resource.domain.EquipResourceDomain;

/**
* EquipDomain
* @author Jeremy
*/
public class EquipDomain extends AbstractModuleMultiDomain<Long, Long, Equip> implements IEquipResourceDomain<Long, Equip>{

	private static final Logger log = LoggerFactory.getLogger(EquipDomain.class);
	
	/**
	 * 资源代理对象
	 */
	private IEquipResourceDomain<Long, Equip> resourceDomainProxy;
	
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
	public List<Long> getAndClearDeleteList() {
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

	@Override
	public Map<Integer, Long> getUsedEquips(long holderId) {
		return resourceDomainProxy.getUsedEquips(holderId);
	}

	@Override
	public Equip wear(long holderId, int slot, Equip newEquip) {
		return resourceDomainProxy.wear(holderId, slot, newEquip);
	}

	@Override
	public void takeOut(long holderId, int slot, Equip equip) {
		resourceDomainProxy.takeOut(holderId, slot, equip);
	}

	@Override
	public void addReource(Long k, Equip v) {
		resourceDomainProxy.addReource(k, v);
	}
}
