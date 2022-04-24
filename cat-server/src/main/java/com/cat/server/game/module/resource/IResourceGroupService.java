package com.cat.server.game.module.resource;

import java.util.Collection;

import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.resource.domain.ResourceGroup;

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
	public boolean check(long playerId, ResourceGroup costGroup);
	
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
	
}
