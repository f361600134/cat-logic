package com.cat.api.module.rank.assist;

/**
 * @author Jeremy Feng
 * 如果想使用通用工具,就要实现这个接口
 */
public interface ISorter {
	
	/**
	 * 对象唯一id
	 * @return 排序对象的id, 玩家id/家族id
	 */
	public long getId();
	
	/**
	 * 一级排序值
	 * @return 排序值1
	 */
	public long getFirstOrder();
	
	
	/**
	 * 二级排序值
	 * @return 排序值2
	 */
	public long getSecondOrder();
	
	/**
	 * 三级排序值, 通常用于时间
	 * @return 排序值3
	 */
	public long getThirdOrder();
	
	/**
	 *  四级级排序值
	 * @return 排序值4
	 */
	public long getFourthOrder();
	
}
