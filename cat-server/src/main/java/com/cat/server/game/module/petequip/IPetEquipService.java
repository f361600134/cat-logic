package com.cat.server.game.module.petequip;

import com.cat.server.game.module.functioncontrol.IPlayerModuleService;
import com.cat.server.game.module.petequip.domain.PetEquip;

/**
 * PetEquip接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IPetEquipService extends IPlayerModuleService{
	
	public PetEquip getPetEquip(long playerId, long petEquipId);

}