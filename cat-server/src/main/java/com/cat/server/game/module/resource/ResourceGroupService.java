package com.cat.server.game.module.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.helper.log.NatureEnum;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * 可以考虑对资源进行分类去处理, 比如有3个道具,2个武将<br>
 * 那么根据类型进行分类, 处理完这个类型后, 就通知更新
 * @author Jeremy
 */
@Service
class ResourceGroupService implements IResourceGroupService, ILifecycle {

	private static final Logger log = LoggerFactory.getLogger(ResourceGroupService.class);

	private Map<Integer, IResourceService> serviceMap = new HashMap<>();
	
	@Autowired
	public void initServiceMap(List<IResourceService> resourceServices) {
		for (IResourceService service : resourceServices) {
			this.serviceMap.put(service.resType(), service);
		}
	}

	@Override
	public void reward(long playerId, Map<Integer, Integer> rewardMap, NatureEnum nEnum) {
		Map<Integer, Map<Integer, Integer>> resourceTypeMap = splitByType(rewardMap);
		resourceTypeMap.forEach((resourceType, resourceMap)->{
			//先通过资源类型, 获取到对应处理的service
			IResourceService service = serviceMap.get(resourceType);
			if (service == null) {
				throw new IllegalArgumentException(String.format("No such type:%s", resourceType));
			}
			//service校验通过后, 资源开始加入背包
			resourceMap.forEach((configId, number)->{
				if (number < 0) {
					log.info("Negative item reward, playerId:{}, value:{}, nEnum:{}", playerId, number, nEnum);
					number = Math.abs(number);
				}
				service.reward(playerId, configId, number, nEnum);
			});
			//资源加入成功后, 变动数据通知客户端
			service.notify(playerId);
		});
	}

	@Override
	public void cost(long playerId, Map<Integer, Integer> costMap, NatureEnum nEnum) {
		Map<Integer, Map<Integer, Integer>> resourceTypeMap = splitByType(costMap);
		resourceTypeMap.forEach((resourceType, resourceMap)->{
			IResourceService service = serviceMap.get(resourceType);
			if (service == null) {
				throw new IllegalArgumentException(String.format("No such type:%s", resourceType));
			}
			resourceMap.forEach((configId, number)->{
				if (number < 0) {
					log.info("Negative item cost, playerId:{}, value:{}, nEnum:{}", playerId, number, nEnum);
					number = Math.abs(number);
				}
				service.cost(playerId, configId, number, nEnum);
			});
			service.notify(playerId);
		});
	}

	@Override
    public boolean check(long playerId, Map<Integer, Integer> costMap) {
		Map<Integer, Map<Integer, Integer>> resourceTypeMap = splitByType(costMap);
		for (Integer resourceType : resourceTypeMap.keySet()) {
			IResourceService service = serviceMap.get(resourceType);
			if (service == null) {
				throw new IllegalArgumentException(String.format("No such type:%s", resourceType));
			}
			Map<Integer, Integer> resourceMap = resourceTypeMap.get(resourceType);
			for (Integer configId : resourceMap.keySet()) {
				int number = resourceMap.get(configId);
				if (number < 0) {
					log.info("Negative item cost, playerId:{}, value:{}, nEnum:{}", playerId, number);
					number = Math.abs(number);
				}
				boolean obj = service.checkEnough(playerId, configId, number);
				if (!obj) return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean checkAndCost(long playerId, Map<Integer, Integer> costMap, NatureEnum nEnum) {
		if (this.check(playerId, costMap)) {
			this.cost(playerId, costMap, nEnum);
			return true;
		}
		return false;
	}
	
	@Override
	public void clearExpire(long playerId,  Collection<Integer> configIds) {
		Multimap<Integer, Integer> resourceTypeMap = splitByType(configIds);
		for (Integer resourceType : resourceTypeMap.keySet()) {
			IResourceService service = serviceMap.get(resourceType);
			if (service == null) {
				throw new IllegalArgumentException(String.format("No such type:%s", resourceType));
			}
			Collection<Integer> tempIds = resourceTypeMap.get(resourceType);
			tempIds.forEach(configId->{
				service.clearExpire(playerId, configId);
			});
			service.notify(playerId);
		}
	}
	
	@Override
	public int getCount(long playerId, int configId) {
		int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
		IResourceService service = serviceMap.get(resourceType);
		if (service == null) {
			throw new IllegalArgumentException(String.format("No such type:%s", resourceType));
		}
		return service.getCount(playerId, configId);
	}
	
	/**
	 * 把资源组按照类型分类
	 */
	private Map<Integer, Map<Integer, Integer>> splitByType(Map<Integer, Integer> resourceMap) {
		Map<Integer, Map<Integer, Integer>> resourceTypeMap = new HashMap<>();
		resourceMap.forEach((configId, number)->{
			int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
			Map<Integer, Integer> map = resourceTypeMap.get(resourceType);
			if(map == null) {
				map = new HashMap<>();
				resourceTypeMap.put(resourceType, map);
			}
			map.put(configId, number);
		});
		return resourceTypeMap;
	}
	
	/**
	 * 把资源组按照类型分类
	 */
	private Multimap<Integer, Integer> splitByType(Collection<Integer> configIds) {
		Multimap<Integer, Integer> resourceTypeMap = ArrayListMultimap.create();
		configIds.forEach((configId)->{
			int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
			resourceTypeMap.put(resourceType, configId);
		});
		return resourceTypeMap;
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}

}
