package com.cat.server.game.helper;

/**
 * 资源类型枚举
 * @auth Jeremy
 * @date 2020年8月20日下午5:49:39
 */
public enum ResourceType {
	
	/** 属性 */
	Property(1),
	/** 道具 */
	Item(2),	
	/** 装备 */
	Equip(3),	
	/** 英雄 */
	Hero(4),
	/**活动道具*/
	ActivityItem(5),
	
	;
	private int type;
	private ResourceType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	

}
