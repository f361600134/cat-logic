package com.cat.server.game.module.group;

/**
 * 团体成员接口类
 * @author Jeremy
 */
public interface IMember {

	/**
	 * 获取成员id
	 * @return
	 */
	public long getMemberId();

	/**
	 * 获取职位
	 * @return
	 */
	public int getPosition();

	/**
	 * 设置职位
	 * @return
	 */
	public void setPosition(int position);
	
}
