package com.cat.server.game.module.mission.process;

import com.cat.server.core.event.IEvent;
import com.cat.server.game.module.mission.domain.MissionEnum;
import com.cat.server.game.module.mission.type.IMission;
import com.cat.server.game.module.player.event.PlayerLoginEndEvent;
import com.cat.server.utils.TimeUtil;
import org.springframework.stereotype.Component;

/**
 * 登录任务处理器
 * @auth Jeremy
 * @date 2020年12月28日上午12:17:25
 */
@Component
public class Mission01LoginProcess extends AbstractMissionProcess{
	
	/**
	 * 处理类型
	 */
	public int type() {
		return MissionEnum.TYPE_LOGIN.getType();
	}

	@Override
	public String[] focusEvents() {
		return new String[] {PlayerLoginEndEvent.ID};
	}
	
	/**
	 * 获取条件
	 * 登录类型1
	 * 达成条件1 达成值1, 表示登录第一天,完成1次
	 * 达成条件2 达成值1, 表示登录第二天,完成1次
	 * 具体完成次数, 由任务自行判断, 此处只需要判断是否conditionValue一致
	 */
	@Override
	public int getCondition(IMission mission, IEvent event) {
		if (!(event instanceof PlayerLoginEndEvent)) {
			return 0;
		}
		//	最后登录时间
		long lastLoginTime = mission.getAdditional();
		//	当前时间
		long now = TimeUtil.now();
		boolean bool = TimeUtil.isSameDay(lastLoginTime, now);
		int loginDay = bool ? 0 : 1;
		//	计算登录总时间
		loginDay = mission.getProgress() + loginDay;
		
		mission.setAdditional(now);
		mission.setProgress(loginDay);
		return loginDay;
	}
	
}
