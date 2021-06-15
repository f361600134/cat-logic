package com.kepe.dragon.persistent.provider;

import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.artifact.domain.ArtifactDomain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
* @author Jeremy
*/
public class PlayerResourceDomain extends AbstractResourceDomain<Long, Player>{
	
	public static final int LIMIT = 999;

	HeroResourceDomain(long playerId) {
		super(playerId);
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
}