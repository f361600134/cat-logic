package com.cat.server.game.module.equip.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.module.resource.IResourceDomain;
import com.cat.server.game.module.resource.domain.EquipResourceDomain;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
* EquipDomain
* @author Jeremy
*/
public class EquipDomain extends AbstractModuleMultiDomain<Long, Long, Equip> implements IResourceDomain<Long, Equip>{

	private static final Logger log = LoggerFactory.getLogger(EquipDomain.class);
	
	/**
	 * 资源代理对象
	 */
	private IResourceDomain<Long, Equip> resourceDomainProxy;
	
	/**
	 * 已使用的装备列表
	 * Map<Long, Collection<Long>>结构,key:持有者id, value:已使用的装备列表
	 */
	private Multimap<Long, Long> usedEquips = ArrayListMultimap.create();
	
	public EquipDomain(){
		
	}
	
	/**
	 * 获取已使用的装备Ids
	 * @return Collection<Long>  已使用的装备列表id
	 * @date 2022年6月3日下午10:31:32
	 */
	public Collection<Long> getUsedEquipIds(long holderId){
		if (!usedEquips.containsKey(holderId)) {
			return Collections.emptyList();
		}
		return usedEquips.get(holderId);
	}
	
	/**
	 * 获取已使用的装备列表
	 * @return Collection<Long>  已使用的装备列表id
	 * @date 2022年6月3日下午10:31:32
	 */
	public List<Equip> getUsedEquips(long holderId){
		Collection<Long> equipIds = this.getUsedEquipIds(holderId);
		List<Equip> ret = new ArrayList<>();
		for (Long equipId : equipIds) {
			ret.add(this.getBean(equipId));
		}
		return ret;
	}
	
//	/**
//	 * 先把旧的装备移除掉,再设置新的装备id
//	 * @param equip 装备
//	 * @param holderId 持有者id 
//	 * @return void  
//	 * @date 2022年6月4日下午1:51:20
//	 */
//	public void wear(Equip equip, long holderId) {
//		List<Long> equips = (List<Long>)this.usedEquips.get(holderId);
//		equip.setHolder(holderId);
//		this.usedEquips.put(holderId, equip.getId());
//	}
	
//	/**
//	 * 穿戴装备
//	 * @param long holderId 持有者id
//	 * @param long equipId 装备id
//	 * @return void  
//	 * @date 2022年6月3日下午10:45:23
//	 */
//	public void wearToHero(long holderId, long equipId) {
//		
//	}
//	
//	/**
//	 * 穿戴装备
//	 * @param long holderId 持有者id
//	 * @param long equipId 装备id
//	 * @return void  
//	 * @date 2022年6月3日下午10:45:23
//	 */
//	public void wearToPet(long holderId, long equipId) {
//		
//	}
	
	////////////业务代码////////////////////
	
	@Override
	public void afterInit() {
		this.resourceDomainProxy = EquipResourceDomain.create(getId(), getBeanMap());
		for (Equip equip : getBeanMap().values()) {
			if (equip.getHolder() != 0L) {
				continue;
			}
			usedEquips.put(equip.getHolder(), equip.getId());
		}
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
}
