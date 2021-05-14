package com.cat.server.game.module.group;

import java.util.Map;

/**
 * 团体接口类
 * @author Jeremy
 */
public interface IGroup {

	/**
	 * 获取团体id
	 * @return 团体的唯一id
	 */
	public long getId();

	/**
	 * 获取团体名字
	 * @return 团体的名字
	 */
	public String getName();

	/**
	 * 设置团体名字
	 * @return 团体的名字
	 */
	public void setName(String newName);

	/**
	 * 获取职位
	 * @param playerId
	 * @return
	 */
	public int getPosition(long playerId);

	/**
	 * 获取团体的成员, 以map的方式返回
	 * @return 团体成员map,key为成员id, value为成员接口对象
	 */
	public Map<Long, IMember> getMembers();
	
	/**
	 * 创建一个新的成员对象
	 * @param memberId 成员id,一般视为玩家id
	 * @return IMember
	 * @date 2021年5月12日下午11:28:57
	 */
	default public IMember newMember(long memberId) {
		return new DefaultMember(memberId);
	}
	
	/**
	 * 创建一个领导人对象
	 * @param memberId 成员id,一般视为玩家id
	 * @return IMember
	 * @date 2021年5月12日下午11:28:57
	 */
	public IMember newLeader(long memberId);

}
