package com.cat.server.game.module.mission.goal.type;

import org.springframework.stereotype.Component;

import com.cat.server.core.event.IEvent;
import com.cat.server.game.module.mission.define.GoalTypeEnum;
import com.cat.server.game.module.mission.domain.QuestGoal;
import com.cat.server.game.module.mission.goal.AbstractQuestGoalType;
import com.cat.server.game.module.player.event.PlayerLoginEndEvent;
import com.cat.server.utils.TimeUtil;

/**
 * 登录任务处理器
 * @auth Jeremy
 * @date 2020年12月28日上午12:17:25
 */
@Component
public class GoalType01Login extends AbstractQuestGoalType{
	
	@Override
	public GoalTypeEnum getMissionGoal() {
		return GoalTypeEnum.TYPE_LOGIN;
	}

	@Override
	public String[] focusEvents() {
		return new String[] {PlayerLoginEndEvent.ID};
	}
	
	@Override
	protected int getAddValue(IEvent event, QuestGoal goal) {
		if (!(event instanceof PlayerLoginEndEvent)) {
			return 0;
		}
		//	最后登录时间
		long lastLoginTime = goal.getAdditional();
		//	当前时间
		long now = TimeUtil.now();
		boolean bool = TimeUtil.isSameDay(lastLoginTime, now);
		int loginDay = bool ? 0 : 1;
		return loginDay;
	}

	
}
