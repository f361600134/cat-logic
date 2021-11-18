package com.cat.server.game.module.recycle.strategy.parse;

/**
 * 回收策略枚举
 * 
 * @author Jeremy
 */
public enum RecycleStrategyEnum {

	/**
	 * 依赖活动
	 */
	ACTIVITY("a", -1, -1),
	/**
	 * xxx日
	 */
	DAY("d", 1, 31),
	/**
	 * xxx时
	 */
	HOUR("h", 1, 24),;

	private final String key;

	private final int minValue;

	private final int maxValue;

	private RecycleStrategyEnum(String key, int minValue, int maxValue) {
		this.key = key;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public String getKey() {
		return key;
	}

	public int getMinValue() {
		return minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public boolean checkValid(int value) {
		if (minValue != -1 && value < minValue) {
			return false;
		}
		if (maxValue != -1 && value > maxValue) {
			return false;
		}
		return true;
	}

	public static RecycleStrategyEnum selectParamType(String param) {
		param = param.toLowerCase();
		for (RecycleStrategyEnum type : values()) {
			if (param.endsWith(type.getKey())) {
				return type;
			}
		}
		return null;
	}

}
