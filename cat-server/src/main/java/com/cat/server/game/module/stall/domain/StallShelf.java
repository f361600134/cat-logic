package com.cat.server.game.module.stall.domain;

import java.util.HashMap;
import java.util.Map;

import com.cat.server.game.data.proto.PBStall.PBStallCommodityInfo;
import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.module.equip.domain.Equip;
import com.cat.server.game.module.item.domain.Item;
import com.cat.server.game.module.pet.domain.Pet;
import com.cat.server.game.module.resource.IResource;

/**
 * 摆摊货架
 * @author Jeremy
 */
public class StallShelf {
	
	private Map<Long, Pet> pets = new HashMap<>();
	private Map<Long, Equip> equips = new HashMap<>();
	private Map<Long, Item> items = new HashMap<>();
	
	/**
	 * 增加出售商品
	 */
	public void add(IResource resource) {
		final int configId = resource.getConfigId();
		if (configId ==  ResourceType.Equip.getType()) {
			Equip equip = (Equip)resource;
			this.equips.put(equip.getId(), equip);
		}
		else if (configId ==  ResourceType.Pet.getType()) {
			Pet pet = (Pet)resource;
			this.pets.put(pet.getUniqueId(), pet);
		}
		else if (configId ==  ResourceType.Item.getType()) {
			Item item = (Item)resource;
			this.items.put(item.getItemId(), item);
		}
	}
	
	/**
	 * 移除掉出售商品
	 */
	public void remove(IResource resource) {
		final int configId = resource.getConfigId();
		if (configId ==  ResourceType.Equip.getType()) {
			this.equips.remove(resource.getUniqueId());
		}
		else if (configId ==  ResourceType.Pet.getType()) {
			this.pets.remove(resource.getUniqueId());
		}
		else if (configId ==  ResourceType.Item.getType()) {
			this.items.remove(resource.getUniqueId());
		}
	}
	
	/**
	 * 摆摊商店列表转协议对象
	 * @return
	 */
	public PBStallCommodityInfo toProto() {
		PBStallCommodityInfo.Builder builder = PBStallCommodityInfo.newBuilder();
		this.equips.values().forEach(commodity->{
			builder.addEquips(commodity.toProto());
		});
		this.pets.values().forEach(commodity->{
			builder.addPets(commodity.toProto());
		});
		this.items.values().forEach(commodity->{
			builder.addItems(commodity.toProto());
		});
		return null;
	}
	
	

}
