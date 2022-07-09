package com.cat.server.game.module.stall.assist;

/**
 * 可以被出售的资源枚举
 * @author Jeremy
 *
 */
public enum StallEnum {
	
	/** 道具 */
	Item(2),	
	/** 装备 */
	Equip(3),	
	/** 英雄 */
	Hero(4),
	/**活动道具*/
	ActivityItem(5),
	/**宠物*/
	Pet(6),
	/**宠物装备*/
	PetEquip(7),
	;
	private int type;
	private StallEnum(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	
	
}
