package com.cat.server.game.module.resource;

import java.util.Map;

/**
 * 装备资源域
 * @auth Jeremy
 * @date 2022年6月6日上午12:02:41
 */
public interface IEquipResourceDomain<K, V extends IEquip> extends IResourceDomain<K, V>{
	
	/**
	 * 获取已使用的武器<br>
	 * @return 已使用的装备Map, key: slot, value: equipId
	 */
	public Map<Integer, Long> getUsedEquips(long holderId);
	
	/**
	 * 穿戴装备, 如果有旧装备, 返回旧装备
	 * @param holderId
	 * @param equipId
	 * @return PetEquip  
	 * @date 2022年6月6日上午12:15:49
	 */
	public V wear(long holderId, int slot, V newEquip);
	
	/**
	 * 脱下装备
	 * @param holderId
	 * @param equipId
	 * @return PetEquip  
	 * @date 2022年6月6日上午12:15:49
	 */
	public void takeOut(long holderId, int slot, V equip);

}
