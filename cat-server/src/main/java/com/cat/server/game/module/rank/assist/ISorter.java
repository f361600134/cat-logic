package com.cat.server.game.module.rank.assist;

/**
 * @author Jeremy Feng
 * 如果想使用通用工具,就要实现这个接口
 */
public interface ISorter {
	
	/**
	 * 对象唯一id
	 * @return
	 */
	public long getId();
	
	/**
	 * 一级排序值
	 * @return
	 */
	public long getFirstOrder();
	
	
	/**
	 * 二级排序值
	 * @return
	 */
	public long getSecondOrder();
	
	/**
	 * 三级排序值, 通常用于时间
	 * @return
	 */
	public long getThirdOrder();
	
	/**
	 *  四级级排序值
	 * @return
	 */
	public long getFourthOrder();
	
}
