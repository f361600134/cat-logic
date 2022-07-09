package com.cat.server.game.module.stall.impl;

import java.util.HashMap;
import java.util.Map;

import com.cat.server.game.module.resource.IResource;
import com.cat.server.game.module.stall.proto.PBStallCommodityInfoBuilder;

/**
 * 电商摆摊入口
 * @author Jeremy
 */
public class Ecommerce implements IComodityContainer {
	
	/**
	 * 电商摊位容器<br>
	 * key: 资源类型
	 * value: 资源容器
	 */
	private Map<Integer, IComodityContainer> containers = new HashMap<>();

	@Override
	public void search(String keyword, PBStallCommodityInfoBuilder builder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(int configId, long uniqueId, int price) {
		int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
		IComodityContainer container = this.containers.get(resourceType);
		if (container == null) {
			throw new IllegalArgumentException(String.format("No such type:%s", resourceType));
		}
		container.add(configId, uniqueId, price);
	}

	@Override
	public void remove(int configId, long uniqueId) {
		int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
		IComodityContainer container = this.containers.get(resourceType);
		if (container == null) {
			throw new IllegalArgumentException(String.format("No such type:%s", resourceType));
		}
		container.remove(configId, uniqueId);
	}
	
	
	

}
