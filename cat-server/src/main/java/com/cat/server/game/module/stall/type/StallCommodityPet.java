package com.cat.server.game.module.stall.type;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.pet.IPetService;
import com.cat.server.game.module.pet.domain.Pet;
import com.cat.server.game.module.petequip.IPetEquipService;
import com.cat.server.game.module.stall.domain.Stall;
import com.cat.server.game.module.stall.proto.PBStallCommodityInfoBuilder;

/**
 * 宠物类商品
 * @author Jeremy
 */
@Component
public class StallCommodityPet implements IStallCommodity{
	
	@Override
	public int getType() {
		return ResourceType.Pet.getType();
	}
	
	/**
	 * 判断宠物是否携带了装备
	 */
	@Override
	public ErrorCode check(long playerId, long uniqueId) {
		IPetService petService = SpringContextHolder.getBean(IPetService.class);
		Pet pet = petService.getPet(playerId, uniqueId);
		if (pet == null) {
			return ErrorCode.PET_NOT_EXIST;
		}
		
		IPetEquipService service = SpringContextHolder.getBean(IPetEquipService.class);
		Map<Integer, Long> equips = service.getPetEquipIds(playerId, uniqueId);
		if (!equips.isEmpty()) {
			return ErrorCode.STALL_PET_NEED_CLEAR;
		}
		
		return ErrorCode.SUCCESS;
	}

	@Override
	public void add(Stall stall, long uniqueId) {
		IPetService service = SpringContextHolder.getBean(IPetService.class);
		Pet pet = service.getPet(stall.getPlayerId(), uniqueId);
		stall.getStallCommodityInfo().getPets().put(pet.getUniqueId(), pet);
	}

	@Override
	public void remove(Stall stall, long uniqueId) {
		stall.getStallCommodityInfo().getPets().remove(uniqueId);
	}

	@Override
	public void toProto(Stall stall, PBStallCommodityInfoBuilder builder) {
		stall.getStallCommodityInfo().getPets().values().forEach(commodity->{
			builder.addPets(commodity.toProto());
		});
	}

}
