package com.cat.server.game.module.shop.strategy;

import com.cat.server.game.module.shop.strategy.impl.NonRefreshStrategy;
import com.cat.server.game.module.shop.type.AbstractShopType;

public enum RefreshStrategyEnum {
	
	NonRefreshStrategy(1) {
		@Override
		public IRefreshStrategy newStrategy(AbstractShopType<?> shopType) {
			return new NonRefreshStrategy(shopType);
		}
	},
	;
	private int type;
	private RefreshStrategyEnum(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public abstract IRefreshStrategy newStrategy(AbstractShopType<?> shopType);
	
	public static RefreshStrategyEnum valueOf(int type) {
		for (RefreshStrategyEnum renum : values()) {
			if (renum.getType() == type) {
				return renum;
			}
		}
		return NonRefreshStrategy;
	}
}
