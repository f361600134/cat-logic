package com.cat.server.game.module.mission2.goal.type;

import org.springframework.stereotype.Component;

import com.cat.server.core.event.IEvent;
import com.cat.server.game.module.mission.domain.MissionGoalEnum;
import com.cat.server.game.module.mission2.goal.AbstractMissionGoalType;
import com.cat.server.game.module.mission2.type.MissionGoal;
import com.cat.server.game.module.player.event.PlayerLoginEndEvent;
import com.cat.server.utils.TimeUtil;

/**
 * 登录任务处理器
 * @auth Jeremy
 * @date 2020年12月28日上午12:17:25
 */
@Component
public class GoalType01Login extends AbstractMissionGoalType{
	
	@Override
	public MissionGoalEnum getMissionGoal() {
		return MissionGoalEnum.TYPE_LOGIN;
	}

	@Override
	public String[] focusEvents() {
		return new String[] {PlayerLoginEndEvent.ID};
	}
	
	@Override
	protected int getAddValue(IEvent event, MissionGoal goal) {
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
