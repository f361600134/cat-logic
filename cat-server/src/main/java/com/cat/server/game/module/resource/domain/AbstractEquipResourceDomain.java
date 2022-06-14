package com.cat.server.game.module.resource.domain;

import java.util.Collections;
import java.util.Map;

import com.cat.server.game.module.resource.IEquip;
import com.cat.server.game.module.resource.IEquipResourceDomain;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * 资源管理类域代理, 处理资源公共操作逻辑封装
 * @auth Jeremy
 * @date 2022年2月9日下午7:42:15
 */
abstract class AbstractEquipResourceDomain<K, V extends IEquip> extends AbstractResourceDomain<K, V> implements IEquipResourceDomain<K, V>{
	
	/**
	 * 这种处理方式, 初始化时,需要根据装备的配置信息获取到槽位
	 * @param row PlayerId
	 * @param column index 槽位
	 * @param value equipId
	 */
	protected final transient Table<Long, Integer, Long> usedEquips = HashBasedTable.create();
	
	AbstractEquipResourceDomain(long playerId, Map<K, V> itemMap) {
		super(playerId, itemMap);
		this.initUsedEquips(itemMap);
	}
	
	public abstract void initUsedEquips(Map<K, V> itemMap);	
	
	@Override
	public Map<Integer, Long> getUsedEquips(long holderId) {
		if (!usedEquips.containsRow(holderId)) {
			return Collections.emptyMap();
		}
		return usedEquips.row(holderId);
	}
	
	/**
	 * 穿戴装备, 如果有旧装备, 返回旧装备
	 * @param holderId
	 * @param equipId
	 * @return PetEquip  
	 * @date 2022年6月6日上午12:15:49
	 */
	public V wear(long holderId, int slot, V newEquip) {
		V oldEquip = null;
		if (this.usedEquips.contains(holderId, slot)) {
			long oldEquipId = this.usedEquips.get(holderId, slot);
			oldEquip = this.getBeanMap().get(oldEquipId);
			if (oldEquip != null) {
				//之前有穿过装备,替换下来,直接设置持有者为0
				oldEquip.setHolder(0L);
				this.updateList.add(oldEquip);
			}
		}
		//新装备设置新的持有者id
		newEquip.setHolder(holderId);
		this.updateList.add(newEquip);
		this.usedEquips.put(holderId, slot, newEquip.getUniqueId());
		return oldEquip;
	}
	
	/**
	 * 脱下装备,返回旧装备
	 * @param holderId
	 * @param equipId
	 * @return PetEquip  
	 * @date 2022年6月6日上午12:15:49
	 */
	public void takeOut(long holderId, int slot, V equip) {
		equip.setHolder(0L);
		this.updateList.add(equip);
		this.usedEquips.remove(holderId, slot);
	}
	
}
