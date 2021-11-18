package com.cat.server.game.module.hero.event;

import com.cat.server.core.event.PlayerBaseEvent;

/**
 * 新增武将事件
 */
public class HeroAddEvent extends PlayerBaseEvent {
	
	public static String ID = HeroAddEvent.class.getSimpleName();

    private final long heroId; //物品唯一id
    private final int configId; //物品配置id
    private final int count;    //物品数量
    private final int quality;  //物品品质

    private HeroAddEvent(long playerId, long heroId, int configId, int count, int quality) {
    	super(playerId);
    	this.heroId = heroId;
        this.configId = configId;
        this.count = count;
        this.quality = quality;
    }

    public static HeroAddEvent create(long heroId, long playerId, int configId, int count, int quality){
        return new HeroAddEvent(playerId, heroId, configId, count, quality);
    }

    public long getHeroId() {
		return heroId;
	}
    
    public int getConfigId() {
        return configId;
    }

    public int getCount() {
        return count;
    }

    public int getQuality() {
        return quality;
    }
    
}
