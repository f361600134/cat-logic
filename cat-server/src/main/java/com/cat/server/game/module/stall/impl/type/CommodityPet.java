package com.cat.server.game.module.stall.impl.type;

import java.util.Collection;

import com.cat.server.game.data.config.local.ConfigPetBase;
import com.cat.server.game.module.pet.domain.Pet;
import com.cat.server.game.module.stall.proto.PBStallCommodityInfoBuilder;
import com.cat.server.game.module.stall.strategy.AbstractCommodityShelf;

public class CommodityPet extends AbstractCommodityShelf<Pet, ConfigPetBase> {

	@Override
	public void search(String keyword, PBStallCommodityInfoBuilder builder) {
		Collection<Pet> ret = this.search(keyword);
		ret.forEach(e->builder.addPets(e.toProto()));
	}
	

}
