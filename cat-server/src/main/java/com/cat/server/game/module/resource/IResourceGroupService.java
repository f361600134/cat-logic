package com.cat.server.game.module.resource;

import java.util.Collection;

import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.utils.Pair;

/**
 * 资源组接口
 * @author Jeremy
 */
public interface IResourceGroupService {
	
	/**
	 * 检查数量是否足够
	 * @param playerId 玩家id
	 * @param costGroup  消耗组, value值为正整数. 逐个判断, 全部满足返回true, 否则返回false
	 * @return true 表示满足, 否则不满足
	 */
	public boolean checkEnought(long playerId, ResourceGroup costGroup);
	
	/**
	 * 消耗
	 * @param playerId 玩家id
	 * @param costGroup value值为正整数
	 * @param nEnum 资源枚举
	 * @param desc 其他描述
	 */
	public void cost(long playerId, ResourceGroup costGroup, NatureEnum nEnum);
	
	/**
	 * 检查数量是否足够, 足够则消耗, 但凡有一个资源不满足, 就不能消耗
	 * @param playerId 玩家id
	 * @param costGroup  消耗map, value值为正整数. 逐个判断, 全部满足返回true, 否则返回false
	 * @return true 表示满足, 否则不满足
	 */
	public boolean checkAndCost(long playerId, ResourceGroup costGroup, NatureEnum nEnum);
	
	/**
	 * 奖励
	 * @param playerId 玩家id
	 * @param rewardGroup value值为正整数
	 * @param nEnum 资源枚举
	 */
	public void reward(long playerId, ResourceGroup rewardGroup, NatureEnum nEnum);
	
	/**
	 * 获取资源数量
	 * @param configId 资源配置id
	 * @return 数量
	 */
	public int getCount(long playerId, int configId);
	
	/**
	 * 清理过期资源
	 * @return void
	 * @date 2021年11月16日下午10:49:44
	 */
	public void clearExpire(long playerId, Collection<Integer> configIds);
	
	/**
	 * 扣除掉指定唯一id的道具
	 * @param playerId 玩家id
	 * @param pairs 要删掉的数据
	 * @param 资源枚举
	 */
	public void costByUniqueId(long playerId, Collection<Pair<Integer, Long>> pairs, NatureEnum nEnum);
	
	/**
	 * 检查是否可以增加指定数量的资源
	 * @param playerId 玩家id
	 * @param costGroup  消耗组, value值为正整数. 逐个判断, 全部满足返回true, 否则返回false
	 * @return true 表示满足, 否则不满足
	 */
	public boolean checkAddResource(long playerId, Collection<IResource> resources);
	
	/**
	 * 增加资源,非奖励接口
	 * @param playerId 玩家id
	 * @param pairs 要删掉的数据
	 * @param 资源枚举
	 */
	public void addResource(long playerId, Collection<IResource> resources, NatureEnum nEnum);
	
}
