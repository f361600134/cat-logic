package com.cat.server.game.module.resource;

/**
 * 装备类接口
 * @author Jeremy
 */
public interface IEquip extends IResource {
	
	/**
	 * 设置装备持有者id
	 * @param holderId
	 */
	public void setHolder(long holderId);

}
