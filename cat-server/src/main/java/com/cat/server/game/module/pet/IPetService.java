package com.cat.server.game.module.pet;

import com.cat.server.game.module.pet.domain.Pet;

/**
 * Pet接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IPetService {
	
	public Pet getPet(long playerId, long petId);

}