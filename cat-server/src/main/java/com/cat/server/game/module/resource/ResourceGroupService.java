package com.cat.server.game.module.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.annotation.NotUse;
import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.resource.domain.ResourceGroup;

/**
 * 可以考虑对资源进行分类去处理, 比如有3个道具,2个武将<br>
 * 那么根据类型进行分类, 处理完这个类型后, 就通知更新
 * @author Jeremy
 */
@NotUse
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
	public void reward(long playerId, ResourceGroup rewardMap, NatureEnum nEnum) {
		Set<IResourceService> set = new HashSet<>();
		rewardMap.getDictionary().forEach((configId, number)->{
			//先通过资源类型, 获取到对应处理的service
			int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
			IResourceService service = serviceMap.get(resourceType);
			if (service == null) {
				throw new IllegalArgumentException(String.format("No such type:%s", resourceType));
			}
			if (number < 0) {
				log.info("Negative item reward, playerId:{}, value:{}, nEnum:{}", playerId, number, nEnum);
				number = Math.abs(number);
			}
			service.reward(playerId, configId, number, nEnum);
			set.add(service);
		});
		//通知模块数据变化
		set.forEach(service->{
			service.notify(playerId);
		});
	}

	@Override
	public void cost(long playerId, ResourceGroup costMap, NatureEnum nEnum) {
		//有变动的模块
		Set<IResourceService> set = new HashSet<>();
		costMap.getDictionary().forEach((configId, number)->{
			int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
			IResourceService service = serviceMap.get(resourceType);
			if (service == null) {
				throw new IllegalArgumentException(String.format("No such type:%s", resourceType));
			}
			if (number < 0) {
				log.info("Negative item cost, playerId:{}, value:{}, nEnum:{}", playerId, number, nEnum);
				number = Math.abs(number);
			}
			service.cost(playerId, configId, number, nEnum);
			set.add(service);
		});
		//通知模块数据变化
		set.forEach(service->{
			service.notify(playerId);
		});
	}

	@Override
    public boolean check(long playerId, ResourceGroup costMap) {
		for (Integer configId : costMap.getDictionary().keySet()) {
			int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
			IResourceService service = serviceMap.get(resourceType);
			if (service == null) {
				throw new IllegalArgumentException(String.format("No such type:%s", resourceType));
			}
			int number = costMap.getCount(configId);
			if (number < 0) {
				log.info("Negative item cost, playerId:{}, value:{}, nEnum:{}", playerId, number);
				number = Math.abs(number);
			}
			boolean obj = service.checkEnough(playerId, configId, number);
			if (!obj) return false;
		}
		return true;
	}
	
	@Override
	public boolean checkAndCost(long playerId, ResourceGroup costMap, NatureEnum nEnum) {
		if (this.check(playerId, costMap)) {
			this.cost(playerId, costMap, nEnum);
			return true;
		}
		return false;
	}
	
	@Override
	public void clearExpire(long playerId,  Collection<Integer> configIds) {
		Set<IResourceService> set = new HashSet<>();
		for (Integer configId : configIds) {
			int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
			IResourceService service = serviceMap.get(resourceType);
			if (service == null) {
				throw new IllegalArgumentException(String.format("No such type:%s", resourceType));
			}
			service.clearExpire(playerId, configId);
			
		}
		//通知模块数据变化
		set.forEach(service->{
			service.notify(playerId);
		});
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
	
	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}

}
