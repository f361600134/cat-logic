package com.cat.server.game.module.item.event;

import com.cat.server.core.event.PlayerBaseEvent;
import com.cat.server.game.module.resource.IResource;

/**
 * 新增物品事件
 * @deprecated 弃用,统一使用ResourceAddEvent
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
    
    /**
     * 构建资源新增事件
     * @param resource
     * @return
     */
    public static ItemAddEvent create(IResource resource){
        return new ItemAddEvent(
        		resource.getPlayerId(), 
        		resource.getUniqueId(), 
        		resource.getConfigId(), 
        		resource.getCount(), 
        		resource.getQuality());
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
