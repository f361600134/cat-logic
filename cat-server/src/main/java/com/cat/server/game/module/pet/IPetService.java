package com.cat.server.game.module.pet;

import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.pet.domain.Pet;

/**
 * Pet接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IPetService {
	
	/**
	 * 获取宠物
	 * @param playerId 玩家id
	 * @param petId 宠物id
	 * @return
	 */
	public Pet getPet(long playerId, long petId);
	
	/**
	 * 检测指定宠物id是否存在
	 * @param playerId 玩家id
	 * @param petId 宠物id
	 * @return true:存在
	 */
	default boolean checkExist(long playerId, long petId) {
		return this.getPet(playerId, petId) == null;
	}
	
//	/**
//	 * 增加宠物, 非奖励接口
//	 * @param playerId 玩家id
//	 * @param pet 宠物对象
//	 * @param nEnum 途径
//	 */
//	public void addPet(long playerId, Pet pet, NatureEnum nEnum);

}