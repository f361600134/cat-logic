package com.cat.server.game.module.item.proto;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBBag;

import com.google.protobuf.AbstractMessageLite.Builder;
import com.google.protobuf.Message;

public class ItemInfoBuilder implements IProtocol {
	
	private PBBag.ItemInfo.Builder builder;
	
	public ItemInfoBuilder() {
		this.builder = PBBag.ItemInfo.newBuilder();
	}
	
	public static ItemInfoBuilder newInstance() {
		return new ItemInfoBuilder();
	}
	
	/**
	 * 简单信息组装
	 * @param configId
	 * @param number
	 * @return
	 */
	public ItemInfoBuilder addReward(Integer configId, Integer number) {
		this.builder.setConfigId(configId);
		this.builder.setNum(number);
		return this;
	}
	
	public PBBag.ItemInfo build() {
		return builder.build();
	}

	@Override
	public short protocol() {
		return 0;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
	
}
