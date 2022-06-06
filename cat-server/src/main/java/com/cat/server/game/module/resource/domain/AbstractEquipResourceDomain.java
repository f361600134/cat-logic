package com.cat.server.game.module.resource.domain;

import java.util.Map;
import com.cat.server.game.module.petequip.domain.PetEquip;
import com.cat.server.game.module.resource.IEquipResourceDomain;
import com.cat.server.game.module.resource.IResource;

/**
 * 资源管理类域代理, 处理资源公共操作逻辑封装
 * @auth Jeremy
 * @date 2022年2月9日下午7:42:15
 */
abstract class AbstractEquipResourceDomain<K, V extends IResource> extends AbstractResourceDomain<K, V> implements IEquipResourceDomain<K, V>{
	
	AbstractEquipResourceDomain() {
	}
	
	AbstractEquipResourceDomain(long playerId) {
		super(playerId);
	}
	
	AbstractEquipResourceDomain(long playerId, Map<K, V> itemMap) {
		super(playerId, itemMap);
	}
	
	/**
	 * 穿戴装备, 如果有旧装备, 返回旧装备
	 * @param holderId
	 * @param equipId
	 * @return PetEquip  
	 * @date 2022年6月6日上午12:15:49
	 */
	public PetEquip wear(long holderId, long equipId) {
		return null;
	}
	
	/**
	 * 脱下装备,返回旧装备
	 * @param holderId
	 * @param equipId
	 * @return PetEquip  
	 * @date 2022年6月6日上午12:15:49
	 */
	public PetEquip takeOut(long holderId, long equipId) {
		return null;
	}
	
}
