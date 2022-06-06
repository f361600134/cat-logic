package com.cat.server.game.module.resource;

import com.cat.server.game.module.petequip.domain.PetEquip;

/**
 * 装备资源域
 * @auth Jeremy
 * @date 2022年6月6日上午12:02:41
 */
public interface IEquipResourceDomain<K, V extends IResource> extends IResourceDomain<K, V>{
	
	/**
	 * 穿戴装备, 如果有旧装备, 返回旧装备
	 * @param holderId
	 * @param equipId
	 * @return PetEquip  
	 * @date 2022年6月6日上午12:15:49
	 */
	public PetEquip wear(long holderId, long equipId);
	
	/**
	 * 脱下装备,返回旧装备
	 * @param holderId
	 * @param equipId
	 * @return PetEquip  
	 * @date 2022年6月6日上午12:15:49
	 */
	public PetEquip takeOut(long holderId, long equipId);

}
