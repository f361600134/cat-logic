package com.cat.server.game.module.resource.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cat.server.core.event.GameEventBus;
import com.cat.server.game.module.pet.domain.Pet;
import com.cat.server.game.module.resource.IResource;
import com.cat.server.game.module.resource.event.ResourceUpdateEvent;

/**
* @author Jeremy
*/
public class PetResourceDomain extends AbstractResourceDomain<Long, Pet>{
	
	public static final int LIMIT = 999;

//	PetResourceDomain(long playerId) {
//		super(playerId);
//	}
	
	PetResourceDomain(long playerId, Map<Long, Pet> petMap) {
		super(playerId, petMap);
	}
	
	@Override
	public int getTotalLimit() {
		return LIMIT;
	}

	@Override
	public int getLimit(int configId) {
		//如需要, 通过配置获取到此类物品的最大限制
		return LIMIT;
	}

	@Override
	public List<Pet> add(int configId, int count) {
		List<Pet> pets = new ArrayList<>();
		Pet pet = null;
		//	生成一个宠物,需要唯一id
		for (int i = 0; i < count; i++) {
			pet = Pet.create(playerId, configId);
			getBeanMap().put(pet.getUniqueId(), pet);
			pets.add(pet);
			updateList.add(pet);
			//发送事件
			GameEventBus.getInstance().post(ResourceUpdateEvent.create(pet, 1));
		}
		return pets;
	}

	@Override
	public boolean deduct(Pet pet, int count) {
		pet.delete();
		getBeanMap().remove(pet.getUniqueId());
		this.deleteList.add(pet.getUniqueId());
		return true;
	}
	
	public static PetResourceDomain create(long playerId, Map<Long, Pet> beanMap) {
		PetResourceDomain domain = new PetResourceDomain(playerId, beanMap);
		return domain;
	}

//	@Override
//	public void addReource(Pet pet) {
//		getBeanMap().put(pet.getUniqueId(), pet);
//		updateList.add(pet);
//		//发送事件
//		GameEventBus.getInstance().post(ResourceUpdateEvent.create(pet, 1));
//	}
}