package com.cat.server.game.module.home;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.home.domain.HomeDomain;
import com.cat.server.utils.RandomUtil;

/**
* @author Jeremy
*/
@Component
public class HomeManager extends AbstractModuleManager<Long, HomeDomain>{
	
	//TODO 提供接口譬如通过一些特殊条件获取到对应的家园信息
	public Collection<Long> getNeighbours(int size){
		Collection<Long> playerIds = this.domains.keySet();
		Collection<Long> ret = RandomUtil.randomList(playerIds, size, true);
		return ret;
	}
	

}
