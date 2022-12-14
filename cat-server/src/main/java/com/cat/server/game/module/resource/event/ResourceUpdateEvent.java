package com.cat.server.game.module.resource.event;

import com.cat.server.core.event.PlayerBaseEvent;
import com.cat.server.game.module.resource.IResource;

/**
 * 资源数量变化事件
 * @author Jeremy
 */
public class ResourceUpdateEvent extends PlayerBaseEvent {
	
	public static String ID = ResourceUpdateEvent.class.getSimpleName();

    private final long itemId;  //物品唯一id
    private final int configId; //物品配置id
    private final int curCount; //物品当前数量
    private final int quality;  //物品品质
    private final int added;  	//增加的数量

    private ResourceUpdateEvent(long playerId, long itemId, int configId, int curCount, int quality, int added) {
    	super(playerId);
    	this.itemId = itemId;
        this.configId = configId;
        this.curCount = curCount;
        this.quality = quality;
        this.added = added;
    }
    
    /**
     * 构建资源新增事件
     * @param resource 资源
     * @param added 资源增加数量
     * @return
     */
    public static ResourceUpdateEvent create(IResource resource, int added){
        return new ResourceUpdateEvent(
        		resource.getPlayerId(), 
        		resource.getUniqueId(), 
        		resource.getConfigId(), 
        		resource.getCount(), 
        		resource.getQuality(),
        		added);
    }

    public static ResourceUpdateEvent create(long itemId, long playerId, int configId, int curCount, int quality, int added){
        return new ResourceUpdateEvent(playerId, itemId, configId, curCount, quality, added);
    }

    public long getItemId() {
		return itemId;
	}
    
    public int getConfigId() {
        return configId;
    }

    public int getCurCount() {
		return curCount;
	}

    public int getQuality() {
        return quality;
    }
    
    public int getAdded() {
		return added;
	}
    
}
