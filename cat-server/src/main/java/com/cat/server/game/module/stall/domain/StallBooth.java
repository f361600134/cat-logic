package com.cat.server.game.module.stall.domain;

import java.util.HashMap;
import java.util.Map;

import com.cat.server.game.module.equip.domain.Equip;
import com.cat.server.game.module.item.domain.Item;
import com.cat.server.game.module.pet.domain.Pet;

/**
 * 售货棚, 出售摊位
 * @author Jeremy
 *
 */
public class StallBooth {
	
	/**
	 * key: 商品唯一id
	 * value: 价格
	 */
	private Map<Long, Integer> costMap = new HashMap<>();
	
	/**
	 * key: 宠物唯一id
	 * value: 宠物信息
	 */
	private Map<Long, Pet> pets = new HashMap<>();
	private Map<Long, Equip> equips = new HashMap<>();
	private Map<Long, Item> items = new HashMap<>();
	
	public Map<Long, Pet> getPets() {
		return pets;
	}
	public void setPets(Map<Long, Pet> pets) {
		this.pets = pets;
	}
	public Map<Long, Equip> getEquips() {
		return equips;
	}
	public void setEquips(Map<Long, Equip> equips) {
		this.equips = equips;
	}
	public Map<Long, Item> getItems() {
		return items;
	}
	public void setItems(Map<Long, Item> items) {
		this.items = items;
	}
	
	/**
	 * 获取价格
	 * @param uniqueId
	 * @return
	 */
	public int getPrice(long uniqueId) {
		return this.costMap.getOrDefault(uniqueId, 0);
	}
	
}
