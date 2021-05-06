package com.cat.server.game.module.resource;

import java.util.List;

/**
 * 	用于资源管理domain接口
 * K: 唯一id
 * V: 资源配置id
 * @author Jeremy
 */
public interface IResourceDomain<K, V>{
	
	/**
	 * 	通过配置id获取到实体, 默认返回查找到的第一个
	 * @param configId 配置id
	 * @return
	 */
	public V getBeanByConfigId(int configId);
	
//	/**
//	 * 通过配置id获取到实体集合
//	 * @param configId
//	 * @return
//	 */
//	public Collection<V> getBeansByConfigId(int configId);
	
	/**
	 * 校验是否可以添加指定配置id的物资
	 * @param configId
	 * @param count
	 * @return true: 可以添加, false不可添加
	 */
	public boolean checkAdd(int configId, int count);
	
	/**
	 * 添加物资, 不同的模块, 添加物资逻辑不一致, 具体由子类实现,
	 * @param configId
	 * @param count
	 * @return
	 */
	public List<V> add(int configId, int count);
	
	/**
	 * 判断数量是否充足, 用于消耗前的校验
	 * @param configId 配置id
	 * @param count 数量
	 * @return
	 */
	public boolean checkEnough(int configId, int count);
	
	/**
	 *通过配置id消耗指定数量的物资
	 * @param configId 配置id
	 * @param count 数量
	 * @return true:表示执行成功, false:表示执行失败
	 */
	public boolean costByConfigId(int configId, int count);
	
	/**
	 * 通过唯一id消耗指定数量的物资
	 * @param id 唯一id
	 * @param count 数量, 理论上永不上次数量, 唯一id的物品数量只能为1
	 * @return
	 */
	public boolean costById(K id, int count);
	
	/**
	 * 获取有数据有变动的物资集合,
	 * @return
	 */
	public List<V> getAndClearUpdateList();
	
	/**
	 * 获取被删除的物资集合
	 * @return
	 */
	public List<V> getAndClearDeleteList();
	
	
}
