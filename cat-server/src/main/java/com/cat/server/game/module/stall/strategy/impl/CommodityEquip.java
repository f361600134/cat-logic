package com.cat.server.game.module.stall.strategy.impl;

import java.util.Collection;

import com.cat.server.game.data.config.local.ConfigEquip;
import com.cat.server.game.module.equip.domain.Equip;
import com.cat.server.game.module.stall.proto.PBStallCommodityInfoBuilder;
import com.cat.server.game.module.stall.strategy.AbstractCommodityShelf;

public class CommodityEquip extends AbstractCommodityShelf<Equip, ConfigEquip> {

	@Override
	public void search(String keyword, PBStallCommodityInfoBuilder builder) {
		Collection<Equip> ret = this.search(keyword);
		ret.forEach(e->builder.addEquips(e.toProto()));
	}
	

}
