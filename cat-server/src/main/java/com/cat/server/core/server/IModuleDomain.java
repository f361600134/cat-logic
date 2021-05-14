package com.cat.server.core.server;

import java.util.List;

/**
 * 域接口
 * I 域所有者的id
 * T 域模块的bean对象
 * @auth Jeremy
 * @date 2021年3月21日下午9:57:55
 */
public interface IModuleDomain<I, T> {
	
	/**
	 * domain域所持有者的id
	 * 如果domain所持有者是玩家,即为玩家id
	 * 如果持有这是家族,则为家族id
	 * @return  
	 * @return I  
	 * @date 2021年3月21日上午10:12:14
	 */
	public I getId();
	
	public Class<T> getBasePoClazz();
	
	/**
	 * 有数据, 初始化数据
	 * @param v
	 */
	public void initData(I id, List<T> v);
	
	/**
	 * 无数据, 根据id创建数据
	 * @param v
	 */
	public void initData(I id);
	
	/**
	 * 不管是否有数据, 初始化数据后的操作
	 */
	default public void afterInit() {}
	
}
