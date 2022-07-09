package com.cat.server.game.module.stall.type;

import org.springframework.stereotype.Component;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.equip.IEquipService;
import com.cat.server.game.module.equip.domain.Equip;
import com.cat.server.game.module.stall.domain.Stall;
import com.cat.server.game.module.stall.proto.PBStallCommodityInfoBuilder;

/**
 * 宠物类商品
 * @author Jeremy
 */
@Component
public class StallCommodityEquip implements IStallCommodity{
	
	@Override
	public int getType() {
		return ResourceType.Equip.getType();
	}
	
	@Override
	public ErrorCode check(long playerId, long uniqueId) {
		//判断装备是否处于使用状态
		IEquipService equipService = SpringContextHolder.getBean(IEquipService.class);
		Equip equip = equipService.getEquip(playerId, uniqueId);
		if (equip == null) {
			return ErrorCode.PET_NOT_EXIST;
		}
		if (equip.getHolder() != 0) {
			return ErrorCode.STALL_EQUIP_NEED_CLEAR;
		}
		//TODO 判断装备是否为绑定状态
		return ErrorCode.SUCCESS;
	}

	@Override
	public void add(Stall stall, long uniqueId) {
		IEquipService equipService = SpringContextHolder.getBean(IEquipService.class);
		Equip equip = equipService.getEquip(stall.getPlayerId(), uniqueId);
		stall.getStallCommodityInfo().getEquips().put(equip.getUniqueId(), equip);
	}

	@Override
	public void remove(Stall stall, long uniqueId) {
		stall.getStallCommodityInfo().getEquips().remove(uniqueId);
	}

	@Override
	public void toProto(Stall stall, PBStallCommodityInfoBuilder builder) {
		stall.getStallCommodityInfo().getEquips().values().forEach(commodity->{
			builder.addEquips(commodity.toProto());
		});
	}
}
