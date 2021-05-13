package com.cat.server.game.module.group;

import java.util.Map;

public interface IGroup {
	
	public long getId();
	
	public Map<Long, IMember> getMembers();
	
	/**
	 * 创建一个新的成员对象
	 * @param playerId
	 * @return  
	 * @return IMember  
	 * @date 2021年5月12日下午11:28:57
	 */
	default public IMember newMember(long memberId) {
		return new DefaultMember(memberId);
	}
	
	/**
	 * 创建一个领导人对象
	 * @param playerId
	 * @return  
	 * @return IMember  
	 * @date 2021年5月12日下午11:28:57
	 */
	public IMember newLeader(long memberId);

}
