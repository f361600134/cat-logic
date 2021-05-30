package com.cat.server.game.module.group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;

/**
 * 团队容器, 多人模块玩法都可以视为团队
 * @warning 此操作非线程安全，多线程环境下, 需要在逻辑层确保线程安全
 * @author Jeremy
 * @date 2021年5月12日下午10:35:10
 */
public abstract class AbstractGroupContainer <T extends IGroup> implements IGroupContainer<T>{
	
	/**
	 * key: 团体唯一id
	 * value: 团体对象
	 */
	protected final Map<Long, T> groupMap;
	
	/**
	 * key: 玩家id
	 * value: 团体id
	 */
	protected final Map<Long, Long> groupPlayerIdMap;

	/**
	 * key:groupName
	 * value: groupId
	 */
	protected final Map<String, Long> groupNameMap;

	/**
	 * 唯一id生成器
	 */
	protected final SnowflakeGenerator generator;
	
	public AbstractGroupContainer() {
		this.groupMap = new ConcurrentHashMap<>();
		this.groupPlayerIdMap = new ConcurrentHashMap<>();
		this.groupNameMap = new ConcurrentHashMap<>();
		this.generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
	}
	
	public AbstractGroupContainer(Map<Long, T> groupMap, Map<Long, Long> groupPlayerIdMap) {
		this.groupMap = groupMap;
		this.groupPlayerIdMap = groupPlayerIdMap;
		this.groupNameMap = new ConcurrentHashMap<>();
		this.generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
	}
	
	/**
	 * 初始化团体
	 * @param list 团体列表
	 * @date 2021年5月12日下午10:49:38
	 */
	public final void initGroup(Collection<T> list) {
		list.forEach(this::initGroup);
	}

	/**
	 * 初始化团体
	 * @param t 团体对象
	 */
	public void initGroup(T t) {
		this.groupMap.put(t.getId(), t);
		t.getMembers().keySet().forEach((playerId)-> this.groupPlayerIdMap.put(playerId, t.getId()));
	}

	@Override
	public T get(long groupId) {
		return groupMap.get(groupId);
	}

	@Override
	public T create(long memberId, String groupName) {
		long id = this.generator.nextId();
		T t = newGroup(id, groupName);
		this.groupMap.put(id, t);
		this.groupNameMap.put(groupName, id);
		//创建领导者对象
		IMember member = t.newLeader(memberId);
		this.enter(member, t.getId());
		return t;
	}

	@Override
	public void enter(IMember member, long groupId) {
		T t = groupMap.get(groupId);
		if (t == null) {
			return;
		}
		t.getMembers().put(member.getMemberId(), member);
		this.groupPlayerIdMap.put(member.getMemberId(), groupId);
	}

	@Override
	public void exit(long memberId, long groupId) {
		T t = groupMap.get(groupId);
		if (t == null) {
			return;
		}
		t.getMembers().remove(memberId);
		this.groupPlayerIdMap.remove(memberId);
	}

	@Override
	public void destroy(T t) {
		for (long memberId : t.getMembers().keySet()) {
			this.groupPlayerIdMap.remove(memberId);
		}
		this.groupMap.remove(t.getId());
		this.groupNameMap.remove(t.getName());
	}

	@Override
	public boolean containsName(String newName) {
		return this.groupNameMap.containsKey(newName);
	}

	@Override
	public boolean rename(long groupId, String newName) {
		T t = getGroupByPlayerId(groupId);
		if (t == null){
			return false;
		}
		this.groupNameMap.remove(t.getName());
		t.setName(newName);
		this.groupNameMap.put(t.getName(), t.getId());
		return true;
	}

	@Override
	public long getGroupIdByPlayerId(long playerId) {
		return this.groupPlayerIdMap.getOrDefault(playerId, 0L);
	}

	@Override
	public T getGroupByPlayerId(long playerId) {
		long groupId = getGroupIdByPlayerId(playerId);
		return groupMap.get(groupId);
	}

	/**
	 * 这里的查找,仅仅是根据关键字进行的模糊查找
	 * @param keyword
	 * @return
	 */
	@Override
	public Collection<T> searchGroup(String keyword) {
		List<T> result = new ArrayList<>();
		//	模糊查找
		for (String familyName : groupNameMap.keySet()) {
			//	只要包含关键字,就返回
			if (familyName.contains(keyword)) {
				long groupId = groupNameMap.get(familyName);
				T t = groupMap.get(groupId);
				result.add(t);
			}
		}
		return result;
	}

	/**
	 * 创建一个新的团体对象
	 * @param groupId 团体id
	 * @return IMember
	 * @date 2021年5月12日下午11:28:57
	 */
	public abstract T newGroup(long groupId, String groupName);
	
}
