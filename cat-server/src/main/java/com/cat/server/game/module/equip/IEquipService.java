package com.cat.server.game.module.equip;

import com.cat.server.game.module.equip.domain.Equip;
import com.cat.server.game.module.function.IPlayerModuleService;

/**
 * Equip接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IEquipService extends IPlayerModuleService {
	
	/**
	 * 通过武将id获取武将
	 * @param heroId
	 * @return
	 */
	public Equip getEquip(long playerId, long equipId);
	
//	/**
//	 * 获取装备id列表
//	 * @param playerId 玩家id
//	 * @param holderId 装备持有者id，武将/宠物
//	 * @return List<Long> 装备Id列表
//	 * @date 2022年6月3日下午10:14:24
//	 */
//	public Collection<Long> getEquipIds(long playerId, long holderId);
//	
//	/**
//	 * 获取装备列表
//	 * @param playerId 玩家id
//	 * @param holderId 装备持有者id，武将/宠物
//	 * @return List<Long> 装备列表
//	 * @date 2022年6月3日下午10:14:24
//	 */
//	public List<Equip> getEquips(long playerId, long holderId);
	
	

}