package com.cat.server.game.module.family.assist;

public enum FamilyPosition {
	
	/**
	 * 族长
	 */
	PATRIARCH(1),
	/**
	 * 副族长
	 */
	Deputy(3),
	/**
	 * 长老
	 */
	ELDERS(5),
	/**
	 * 自定义职位
	 */
	DEFINE(7),
	/**
	 * 普通成员
	 */
	NOMAL(9)
	;
	
	private final int value;

	FamilyPosition(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
