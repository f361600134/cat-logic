package com.cat.server.game.module.equip.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
import com.cat.server.game.module.hero.domain.Hero;
import com.cat.server.game.module.resource.IResource; 

/**
* @author Jeremy
*/
@PO(name = "equip")
public class Equip extends EquipPo implements IPersistence, IResource{

	public Equip() {

	}
	
	public Equip(long playerId, long uniqueId, int configId) {
		this.playerId = playerId;
		this.id = uniqueId;
		this.configId = configId;
	}

	@Override
	public long getUniqueId() {
		return this.getId();
	}

	@Override
	public int getCount() {
		return 1;
	}
	
	/**
	 * 创建一个武器对象
	 * @param playerId 玩家id
	 * @param configId 武将配置
	 * @return Equip  
	 * @date 2021年1月17日上午12:22:09
	 */
	public static Equip create(long playerId, int configId) {
		SnowflakeGenerator generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
		long id = generator.nextId();
		Equip equip = new Equip(playerId, id, configId);
		equip.save();
		return equip;
	}

	
}
