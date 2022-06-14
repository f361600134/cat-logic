package com.cat.server.game.module.petequip.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.data.proto.PBPetEquip.PBPetEquipDto;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
import com.cat.server.game.module.petequip.proto.PBPetEquipDtoBuilder;
import com.cat.server.game.module.resource.IEquip; 

/**
* @author Jeremy
*/
@PO(name = "pet_equip")
public class PetEquip extends PetEquipPo implements IPersistence, IEquip{

	public PetEquip() {

	}
	
	public PetEquip(long playerId, long uniqueId, int configId) {
		this.playerId = playerId;
		this.id = uniqueId;
		this.configId = configId;
	}
	
	/**
	 * 创建一个带有持有者的武器对象
	 * @param playerId 玩家id
	 * @param configId 武将配置
	 * @return Equip
	 * @date 2021年1月17日上午12:22:09
	 */
	public static PetEquip create(long playerId, int configId) {
		SnowflakeGenerator generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
		long id = generator.nextId();
		PetEquip equip = new PetEquip(playerId, id, configId);
		equip.save();
		return equip;
	}

	@Override
	public long getUniqueId() {
		return id;
	}

	@Override
	public int getCount() {
		return 1;
	}
	
	/**
	 * 装备转协议对象
	 * @return  
	 * @return PBEquipDto  
	 * @date 2022年6月3日下午9:56:40
	 */
	public PBPetEquipDto toProto() {
		PBPetEquipDtoBuilder dto = PBPetEquipDtoBuilder.newInstance();
		dto.setUniqueId(this.getId());
		dto.setConfigId(this.getConfigId());
		dto.setHolderId(this.getHolder());
		return dto.build();
	}
	
}
