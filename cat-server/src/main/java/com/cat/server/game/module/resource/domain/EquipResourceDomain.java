package com.cat.server.game.module.resource.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.event.GameEventBus;
import com.cat.server.game.data.config.local.ConfigEquip;
import com.cat.server.game.module.equip.domain.Equip;
import com.cat.server.game.module.hero.domain.Hero;
import com.cat.server.game.module.resource.event.ResourceUpdateEvent;

/**
* 装备资源域<br>
* @author Jeremy
*/
public class EquipResourceDomain extends AbstractEquipResourceDomain<Long, Equip>{
	
	EquipResourceDomain(long playerId, Map<Long, Equip> beanMap) {
		super(playerId, beanMap);
	}
	
	@Override
	public int getTotalLimit() {
		return LIMIT;
	}

	@Override
	public int getLimit(int configId) {
		return 1;
	}

	@Override
	public List<Equip> add(int configId, int count) {
		List<Equip> ret = new ArrayList<>();
		Equip equip = null;
		//	生成一个装备,需要唯一id
		for (int i = 0; i < count; i++) {
			equip = Equip.create(playerId, configId);
			getBeanMap().put(equip.getId(), equip);
			ret.add(equip);
			updateList.add(equip);
			//发送事件
			GameEventBus.getInstance().post(ResourceUpdateEvent.create(equip, 1));
		}
		return ret;
	}

	@Override
	public boolean deduct(Equip equip, int count) {
		equip.delete();
		getBeanMap().remove(equip.getId());
		this.deleteList.add(equip.getUniqueId());
		return true;
	}
	
	public static EquipResourceDomain create(long playerId, Map<Long, Equip> beanMap) {
		EquipResourceDomain domain = new EquipResourceDomain(playerId, beanMap);
		return domain;
	}

	@Override
	public void initUsedEquips(Map<Long, Equip> itemMap) {
		for (Equip equip : getBeanMap().values()) {
			if (equip.getHolder() != 0L) {
				continue;
			}
			ConfigEquip configPetEquip = ConfigManager.getInstance().getConfig(ConfigEquip.class, equip.getConfigId());
			if (configPetEquip == null) {
				continue;
			}
			usedEquips.put(equip.getHolder(), configPetEquip.getCategory(), equip.getId());
		}
	}
	
//	@Override
//	public void addReource(Equip res) {
//		getBeanMap().put(res.getUniqueId(), res);
//		updateList.add(res);
//		//发送事件
//		GameEventBus.getInstance().post(ResourceUpdateEvent.create(res, 1));
//	}
}