package com.cat.server.game.module.group;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;


/**
 * 团队容器, 多人模块玩法都可以视为团队
 * @auth Jeremy
 * @date 2021年5月12日下午10:35:10
 */
public abstract class AbstractGroupContainer <T extends IGroup>{
	
	/**
	 * key: 团体唯一id
	 * value: 团体对象
	 */
	private Map<Long, T> groupMap;
	
	/**
	 * key: 玩家id
	 * value: 团体id
	 */
	private Map<Long, Long> groupPlayerIdMap;
	
	private SnowflakeGenerator generator; 
	
	public AbstractGroupContainer() {
		this.groupMap = new ConcurrentHashMap<>();
		this.groupPlayerIdMap = new ConcurrentHashMap<>();
		this.generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
	}
	
	public AbstractGroupContainer(Map<Long, T> groupMap, Map<Long, Long> groupPlayerIdMap) {
		this.groupMap = groupMap;
		this.groupPlayerIdMap = groupPlayerIdMap;
		this.generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
	}
	
	/**
	 * 初始化团体
	 * @param list  
	 * @return void  
	 * @date 2021年5月12日下午10:49:38
	 */
	public final void initGroup(List<T> list) {
		list.forEach((t)->{
			doInitGroup(t);
		});
	}
	
	public void doInitGroup(T t) {
		this.groupMap.put(t.getId(), t);
		t.getMembers().keySet().forEach((playerId)->{
			this.groupPlayerIdMap.put(playerId, t.getId());
		});
	}
	
	
	public IGroup get(long groupId) {
		return groupMap.get(groupId);
	}
	
	/**
	 * 创建团体
	 *   
	 * @return void  
	 * @date 2021年5月12日下午11:03:04
	 */
	public void create(long memberId) {
		long id = this.generator.nextId();
		T t = newGroup(id);
		//创建领导者对象
		IMember member = t.newLeader(memberId);
		
		this.groupMap.put(t.getId(), t);
		t.getMembers().put(member.getId(), member);
		this.groupPlayerIdMap.put(member.getId(), t.getId());
	}
	
	/**
	 * 加入团体
	 * @return void  
	 * @date 2021年5月12日下午10:50:55
	 */
	public void enter(IMember member, long groupId) {
		T t = groupMap.get(groupId);
		if (t == null) {
			return;
		}
		t.getMembers().put(member.getId(), member);
		this.groupPlayerIdMap.put(member.getId(), groupId);
	}
	
	/**
	 * 离开团体
	 * @return void  
	 * @date 2021年5月12日下午10:50:55
	 */
	public void exit(long memberId, long groupId) {
		IGroup group = groupMap.get(groupId);
		if (group == null) {
			return;
		}
		group.getMembers().remove(memberId);
		this.groupPlayerIdMap.remove(memberId);
	}
	
	/**
	 * 注销团体
	 * @return void  
	 * @date 2021年5月12日下午11:03:04
	 */
	public void destroy(T t) {
		for (long memberId : t.getMembers().keySet()) {
			this.groupPlayerIdMap.remove(memberId);
		}
		this.groupMap.remove(t.getId());
	}
	
	/**
	 * 创建一个新的成员对象
	 * @param playerId
	 * @return  
	 * @return IMember  
	 * @date 2021年5月12日下午11:28:57
	 */
	public T newGroup(long groupId) {
		return null;
	}
	
}
