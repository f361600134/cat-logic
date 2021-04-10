package com.cat.server.game.module.resource.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.server.game.module.resource.IResource;
import com.cat.server.game.module.resource.IResourceDomain;

abstract class AbstractResourceDomain<K, V extends IResource> implements IResourceDomain<K, V>{
	
	protected long playerId;
	/**
	 * key: 资源唯一id
	 * value: 资源
	 */
	protected Map<K, V> beanMap;
	
	protected List<V> updateList;
	protected List<V> deleteList;
	
//	AbstractResourceDomain() {
//		this.beanMap = new HashMap<>();
//		this.updateItemList = new ArrayList<>();
//		this.deleteItemList = new ArrayList<>();
//	}
	
	AbstractResourceDomain(long playerId) {
		this.playerId = playerId;
		this.beanMap = new HashMap<>();
		this.updateList = new ArrayList<>();
		this.deleteList = new ArrayList<>();
	}
	
	public long getPlayerId() {
		return playerId;
	}
	
	public void setBeanMap(Map<K, V> beanMap) {
		this.beanMap = beanMap;
	}

	public Map<K, V> getBeanMap() {
		return beanMap;
	}

	public List<V> getUpdateList() {
		return updateList;
	}

	public List<V> getDeleteList() {
		return deleteList;
	}

	/**
	 * 通过配置id获取到实体, 返回找到的第一个实体
	 * @return IItem 道具实体, 无则返回null
	 */
	public V getBeanByConfigId(int configId) {
		for (V v: beanMap.values()) {
			if (v.getConfigId() == configId) {
				return v;
			}
		}
		return null;
	}
	
	/**
	 * 通过配置id获取到实体, 返回找到的第一个实体
	 * @return IItem 道具实体, 无则返回null
	 */
	public int getTotalCountByConfigId(int configId) {
		int count = 0;
		for (V v : beanMap.values()) {
			if (v.getConfigId() == configId) {
				count += v.getCount();
			}
		}
		return count;
	}
	
	/**
	 *	检测是否可以增加物资
	 *1. 判断加物资总量, 总量小于限制, 则可添加
	 *2.如果单个武将有限制的话, 这里可以判断单个武将是是否在限制数量内
	 *1,2条件并存
	 * @return 数量小于最大限制, 则表示可以增加武将
	 */
	@Override
	public boolean checkAdd(int configId, int count) {
		boolean bool = (beanMap.keySet().size() + count) < getTotalLimit();
		if (!bool) return bool;
		return getTotalCountByConfigId(configId) < getLimit(configId);
	}
	
	/**
	 * 检测指定配置的数量是否充足
	 */
	@Override
	public boolean checkEnough(int configId, int count) {
		return getTotalCountByConfigId(configId) >= count;
	}
	
	/**
	 * 背包通过配置id减少普通道具
	 * @param playerId 玩家id
	 * @param configId 配置id
	 * @param count	数量
	 * @return
	 */
	public boolean costByConfigId(int configId, int count) {
		//背包减少普通道具
		V v = getBeanByConfigId(configId);
		if (v == null) {
			return false;//没有此实体消耗失败
		}
		return deduct(v, count);
	}
	
	/**
	 * 背包减少普通道具, 扣除钱都应判断物品是否存在
	 * @param playerId 玩家id
	 * @param id 物资唯一id
	 * @param count 物资数量
	 * @return
	 */
	public boolean costById(K id, int count) {
		//背包减少普通道具
		V v = beanMap.get(id);
		if (v == null) {
			return false;//没有此实体消耗失败
		}
		return deduct(v, count);
	}

	/**
	 * 减少物品数量
	 * @param v
	 * @param count
	 * @return
	 */
	public abstract boolean deduct(V v, int count);
	
	/**
	 * 获取单物品的最大限制
	 * @return
	 */
	public abstract int getLimit(int configId);
	
	/**
	 * 获取仓库数量最大限制
	 * @return
	 */
	public abstract int getTotalLimit();
	
}
