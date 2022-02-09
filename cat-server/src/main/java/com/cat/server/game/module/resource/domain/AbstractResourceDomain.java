package com.cat.server.game.module.resource.domain;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.module.recycle.IRecycleService;
import com.cat.server.game.module.resource.IResource;
import com.cat.server.game.module.resource.IResourceDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

/**
 * 资源管理类域代理, 处理资源公共操作逻辑封装
 * @auth Jeremy
 * @date 2022年2月9日下午7:42:15
 */
abstract class AbstractResourceDomain<K, V extends IResource> implements IResourceDomain<K, V>{
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	protected long playerId;
	/**
	 * key: 资源唯一id
	 * value: 资源
	 */
	private Map<K, V> beanMap;
	
	/**
	 * 有变动的资源列表, 当外部获取后,清空此集合
	 */
	protected List<V> updateList = new ArrayList<>();
	/**
	 * 有删除的资源列表, 当外部获取后,清空此集合
	 */
	protected List<V> deleteList = new ArrayList<>();
	
	protected IRecycleService recycleService;
	
	AbstractResourceDomain() {
	}
	
	AbstractResourceDomain(long playerId) {
		this.playerId = playerId;
		this.beanMap = new HashMap<>();
		this.recycleService = SpringContextHolder.getBean(IRecycleService.class);
	}
	
	AbstractResourceDomain(long playerId, Map<K, V> itemMap) {
		this.playerId = playerId;
		this.beanMap = itemMap;
		this.recycleService = SpringContextHolder.getBean(IRecycleService.class);
		this.afterInit();
	}
	
	public long getPlayerId() {
		return playerId;
	}
	
//	public void setBeanMap(Map<K, V> beanMap) {
//		this.beanMap = beanMap;
//		this.afterInit();
//	}

	public Map<K, V> getBeanMap() {
		return this.beanMap;
	}
	
	@Override
	public List<V> getAndClearUpdateList() {
		List<V> ret = new ArrayList<>(updateList);
		this.updateList.clear();
		return ret;
	}

	@Override
	public List<V> getAndClearDeleteList() {
		List<V> ret = new ArrayList<>(deleteList);
		this.deleteList.clear();
		return ret;
	}

	/**
	 * 通过配置id获取到实体, 返回找到的第一个实体
	 * @return IItem 道具实体, 无则返回null
	 */
	@Override
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
	@Override
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
	@Override
	public boolean costById(K id, int count) {
		//背包减少普通道具
		V v = beanMap.get(id);
		if (v == null) {
			return false;//没有此实体消耗失败
		}
		return deduct(v, count);
	}
	
	@Override
	public void clearExpire(int configId) {
		Iterator<Entry<K, V>> iter = beanMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<K, V> entry = (Entry<K, V>) iter.next();
			V v = entry.getValue();
			if (v.getConfigId() == configId) {
				this.beforeClearExpire(v);
				v.delete();
				iter.remove();
				deleteList.add(v);
			}
		}
	}
	
	@Override
	public int getCount(int configId) {
		return this.getTotalCountByConfigId(configId);
	}
	
	/**
	 * 初始化后的操作
	 * @return void  
	 * @date 2022年2月9日下午7:43:13
	 */
	public void afterInit() {
		Iterator<Entry<K, V>> iter = beanMap.entrySet().iterator();
		while (iter.hasNext()) {
			V res = iter.next().getValue();
			long playerId = res.getPlayerId();
			long uniqueId = res.getUniqueId();
			int configId = res.getConfigId();
			boolean bool = this.recycleService.checkRecycle(playerId, uniqueId, configId);
			if (bool) {
				//true表示可以被回收,并且已经被回收了
				this.beforeClearExpire(res);
				iter.remove();
			}
		}
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
	
	/**
	 * 处理资源清除后的操作, 对比有消耗类的资源回收, 子类根据需要去处理
	 * @param v
	 */
	public abstract void beforeClearExpire(V v);
	
}
