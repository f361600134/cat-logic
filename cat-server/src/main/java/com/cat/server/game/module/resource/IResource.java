package com.cat.server.game.module.resource;

import com.cat.server.core.server.IPersistence;

/**
 * 	资源类对象, 所有资源类的最高接口
 * @author Jeremy
 *
 */
public interface IResource extends IPersistence{
	
	public int RESOURC_TYPE_SPLIT = 100000;
	
	/**
	 * 获取唯一id
	 */
	long getUniqueId();
	
	/**
	 * 获取配置id
	 * @return
	 */
	int getConfigId();
	
	/**
	 * 获取所属玩家id
	 * @return
	 */
	long getPlayerId();
	
	/**
	 * 数量
	 * @return
	 */
	public int getCount();
	
	/**
	 * 获取品质
	 * @return
	 */
	default public int getQuality(){
		return 0;
	}
	
	/**
	 * 判断是否属于指定类型物品
	 * @return
	 */
	default public boolean isType(int type) {
		return ((getConfigId() / RESOURC_TYPE_SPLIT) == type);
	}
	
	/**
	 * 资源获得时间
	 * @return 时间戳
	 */
	default public long getRecieveTime() {
		return 0L;
	}
}
