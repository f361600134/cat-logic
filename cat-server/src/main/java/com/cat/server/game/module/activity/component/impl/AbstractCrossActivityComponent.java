package com.cat.server.game.module.activity.component.impl;

import com.cat.server.game.module.activity.component.IActivityComponent;
import com.cat.server.game.module.activity.type.IActivityType;

/**
 * 抽象的跨服活动组件
 * 1. 房间制活动, 同一个组下的游戏节点共用一个房间. 
 * 2. 游戏节点收到开启活动消息后, 尝试加入房间, 无房间则创建,房间号为后台配置的活动id,于后台唯一,便于数据查找.
 * 3. 活动控制组件生命周期, 活动销毁, 组件销毁. 也就是说, 游戏节点控制跨服的生命周期, 游戏阶段如果进入结束节点, 通知跨服节点, 成功后销毁数据
 * 4.
 * @author Jeremy
 */
public abstract class AbstractCrossActivityComponent implements IActivityComponent {
	
	/**
	 * 跨服活动
	 */
	private IActivityType activityType;
	
	public AbstractCrossActivityComponent(IActivityType activityType) {
		this.activityType = activityType;
	}
	
	public static void main(String[] args) {
		System.out.println(1L<<(2^42-1));
	}

}
