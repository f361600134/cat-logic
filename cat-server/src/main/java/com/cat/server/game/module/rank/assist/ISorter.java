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
	public Long getId();
	
	/**
	 * 一级排序值
	 * @return
	 */
	public Long getFirstOrder();
	
	
	/**
	 * 二级排序值
	 * @return
	 */
	public Long getSecondOrder();
	
	/**
	 * 三级排序值, 通常用于时间
	 * @return
	 */
	public Long getThirdOrder();
	
}
