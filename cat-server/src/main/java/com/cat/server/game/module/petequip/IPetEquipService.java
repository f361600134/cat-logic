package com.cat.server.game.module.petequip;

import java.util.Map;

import com.cat.server.game.module.function.IPlayerModuleService;
import com.cat.server.game.module.petequip.domain.PetEquip;

/**
 * PetEquip接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IPetEquipService extends IPlayerModuleService{
	
	/**
	 * 根据玩家id,装备唯一id获取的指定装备
	 * @param playerId 玩家id
	 * @param petEquipId 宠物唯一id
	 * @return
	 */
	public PetEquip getPetEquip(long playerId, long petEquipId);
	
	
	/**
	 * 根据持有者id获取装备id列表
	 * @param playerId 玩家id
	 * @param holderId 装备持有者id，武将/宠物
	 * @return Map<Integer, Long> 装备列表,key:槽位/装备类型,value装备id
	 * @date 2022年6月3日下午10:14:24
	 */
	public Map<Integer, Long> getPetEquipIds(long playerId, long holderId);

}