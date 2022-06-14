package com.cat.server.game.module.resource.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.event.GameEventBus;
import com.cat.server.game.data.config.local.ConfigPetEquip;
import com.cat.server.game.module.petequip.domain.PetEquip;
import com.cat.server.game.module.resource.event.ResourceUpdateEvent;

/**
 * @author Jeremy
 */
public class PetEquipResourceDomain extends AbstractEquipResourceDomain<Long, PetEquip> {

	PetEquipResourceDomain(long playerId, Map<Long, PetEquip> beanMap) {
		super(playerId, beanMap);
	}

	@Override
	public void initUsedEquips(Map<Long, PetEquip> itemMap) {
		for (PetEquip equip : getBeanMap().values()) {
			if (equip.getHolder() != 0L) {
				continue;
			}
			ConfigPetEquip configPetEquip = ConfigManager.getInstance().getConfig(ConfigPetEquip.class, equip.getConfigId());
			if (configPetEquip == null) {
				continue;
			}
			usedEquips.put(equip.getHolder(), configPetEquip.getSlot(), equip.getId());
		}
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
	public List<PetEquip> add(int configId, int count) {
		List<PetEquip> ret = new ArrayList<>();
		PetEquip equip = null;
		// 生成一个装备,需要唯一id
		for (int i = 0; i < count; i++) {
			equip = PetEquip.create(playerId, configId);
			getBeanMap().put(equip.getId(), equip);
			ret.add(equip);
			updateList.add(equip);
			// 发送事件
			GameEventBus.getInstance().post(ResourceUpdateEvent.create(equip, 1));
		}
		return ret;
	}

	@Override
	public boolean deduct(PetEquip equip, int count) {
		equip.delete();
		getBeanMap().remove(equip.getId());
		this.deleteList.add(equip.getUniqueId());
		return true;
	}

	public static PetEquipResourceDomain create(long playerId, Map<Long, PetEquip> beanMap) {
		PetEquipResourceDomain domain = new PetEquipResourceDomain(playerId, beanMap);
		return domain;
	}

}