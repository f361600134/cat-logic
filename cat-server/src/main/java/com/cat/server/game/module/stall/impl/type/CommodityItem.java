package com.cat.server.game.module.stall.impl.type;

import java.util.Collection;

import com.cat.server.game.data.config.local.ConfigItem;
import com.cat.server.game.module.item.domain.Item;
import com.cat.server.game.module.stall.proto.PBStallCommodityInfoBuilder;
import com.cat.server.game.module.stall.strategy.AbstractCommodityShelf;

public class CommodityItem extends AbstractCommodityShelf<Item, ConfigItem> {

	@Override
	public void search(String keyword, PBStallCommodityInfoBuilder builder) {
		Collection<Item> ret = this.search(keyword);
		ret.forEach(e->builder.addItems(e.toProto()));
	}
	

}
