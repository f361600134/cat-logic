package com.cat.server.game.module.item.event;

import com.cat.server.core.event.PlayerBaseEvent;

/**
 * 新增物品事件
 */
public class ItemAddEvent extends PlayerBaseEvent {
	
	public static String ID = ItemAddEvent.class.getSimpleName();

    private final long itemId; //物品唯一id
    private final int configId; //物品配置id
    private final int count;    //物品数量
    private final int quality;  //物品品质

    private ItemAddEvent(long playerId, long itemId, int configId, int count, int quality) {
    	super(playerId);
    	this.itemId = itemId;
        this.configId = configId;
        this.count = count;
        this.quality = quality;
    }

    public static ItemAddEvent create(long itemId, long playerId, int configId, int count, int quality){
        return new ItemAddEvent(playerId, itemId, configId, count, quality);
    }

    public long getItemId() {
		return itemId;
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
