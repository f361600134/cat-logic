package com.cat.server.game.module.resource;

/**
 * 	资源类对象, 所有资源类的最高接口
 * @author Jeremy
 *
 */
public interface IResource {
	
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
		return ((getConfigId() / 100000) == type);
	}
}
