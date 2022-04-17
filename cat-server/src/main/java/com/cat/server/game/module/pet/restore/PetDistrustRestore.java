package com.cat.server.game.module.pet.restore;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigPetBase;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.restore.AbstractRestorableValueUpdater;
import com.cat.server.game.helper.restore.DefaultRestorableValue;
import com.cat.server.game.module.pet.domain.Pet;

/**
 * 宠物饥饿值,不信任度恢复器
 * @author Jeremy
 */
public class PetDistrustRestore extends AbstractRestorableValueUpdater<DefaultRestorableValue>{
	
	private Pet pet;
	
	public PetDistrustRestore(Pet pet) {
		this.pet = pet;
	}

	@Override
	public DefaultRestorableValue getHolder() {
//		return pet.getDistrustRestore();
		return null;
	}

	@Override
	public int getMaxLimit() {
		ConfigPetBase config = ConfigManager.getInstance().getConfig(ConfigPetBase.class, pet.getConfigId());
		return config == null ? 0 : config.getTrustLimit();
	}

	@Override
	public long getRefreshInterval() {
		ConfigPetBase config = ConfigManager.getInstance().getConfig(ConfigPetBase.class, pet.getConfigId());
		return config == null ? 0 : config.getRestoreInterval();
	}

	@Override
	protected void afterChangeNum(int oldNum, int newNum, boolean notify, NatureEnum nenum, Object... logParams) {
		// TODO something
		// 1. 持久化到数据库
		this.pet.update();
		// 2. 记录日志
	}

}
