package com.cat.server.game.module.rank.event;

import com.cat.server.core.event.BaseEvent;
import com.cat.server.game.module.rank.domain.RankTypeEnum;

/**
 * 更新排行榜事件
 * @auth Jeremy
 * @date 2019年3月22日下午5:44:07
 */
public class RankInfoUpdateEvent extends BaseEvent {
	
	private final long uniqueId;	//唯一id
	private final long value;	//更新的值
	private final long value2;	//更新的值2
	private final RankTypeEnum type; //更新的排行榜
	
	public RankInfoUpdateEvent(RankTypeEnum type, long uniqueId, long value, long value2) {
		this.type = type;
		this.uniqueId = uniqueId;
		this.value = value;
		this.value2 = value2;
	}
	
	/**
	 * 用于一个排序值
	 */
	public static RankInfoUpdateEvent create(RankTypeEnum type, long uniqueId, long value) {
		return new RankInfoUpdateEvent(type, uniqueId, value, 0);
	}
	
	/**
	 * 用于两个排序值
	 */
	public static RankInfoUpdateEvent create(RankTypeEnum type, long playerId, long value, long value2) {
		return new RankInfoUpdateEvent(type, playerId, value, value2);
	}
	
	public long getUniqueId() {
		return uniqueId;
	}
	
	public RankTypeEnum getType() {
		return type;
	}

	public long getValue() {
		return value;
	}

	public long getValue2() {
		return value2;
	}
	
	
}
