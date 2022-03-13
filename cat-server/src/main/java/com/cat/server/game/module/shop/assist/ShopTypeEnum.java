package com.cat.server.game.module.shop.assist;

public enum ShopTypeEnum {
	
    /**
     * 主界面商店
     */
    MAIN_SHOP(1),
    
    /**
     * 研习社商店
     */
    LEARNCOMMUNUTY_SHOP(2),

    ;
    private final int value;

    ShopTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
