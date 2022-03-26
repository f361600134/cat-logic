package com.cat.server.game.module.mission.handler.type;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import com.cat.server.game.data.config.local.ConfigMissionMain;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.mission.define.MissionTypeEnum;
import com.cat.server.game.module.mission.domain.Quest;
import com.cat.server.game.module.mission.domain.QuestTypeData;
import com.cat.server.game.module.mission.handler.AbstractMissionHandler;
import com.cat.server.utils.TimeUtil;


/**
 * 主线任务实现类
 * @author Jeremy
 */
@Component
public class MainMissionHandler extends AbstractMissionHandler<ConfigMissionMain>{
	
	@Override
	public MissionTypeEnum getMissionType() {
		return MissionTypeEnum.MAIN;
	}

	@Override
	protected void afterSubmitMission(long playerId, Quest mission) {
		super.afterSubmitMission(playerId, mission);
		//提交任务后, 初始化下一个个任务
		ConfigMissionMain config = this.getConfig(mission.getConfigId());
		int nextMissionId = config.getNextId();
		if (nextMissionId <= 0) {
			return;
		}
		ResultCodeData<Quest> resulrCodeData = this.accept(playerId, nextMissionId, TimeUtil.now());
		if (resulrCodeData.isSuccess()) {
			this.tellMissionUpdate(playerId, resulrCodeData.getData());
		}
	}
	
	/**
	 * 主线任务是一次性任务链<br>
	 * 不会重置, 若当前无任务, 判断是否有新任务可接
	 * 用于更新版本后, 添加了新任务
	 */
	@Override
	public void checkAndReset(long playerId, long now, boolean notify) {
		QuestTypeData questTypeData = this.getQuestTypeData(playerId, true);
		Map<Integer, Quest> quests = questTypeData.getQuests();
		if (!quests.isEmpty()) {
			//有任务正在做,直接跳过
			return;
		}
		Set<Integer> finishIds = questTypeData.getFinishIds();
		int nextMissionId = this.getNextId(finishIds);
		ResultCodeData<Quest> resulrCodeData = this.accept(playerId, nextMissionId, now);
		if (resulrCodeData.isSuccess() && notify) {
			this.tellMissionUpdate(playerId, resulrCodeData.getData());
		}
	}
	
	/**
	 * 獲取下一個任務Id
	 * @return  
	 * @return int  
	 * @date 2022年3月26日下午3:24:39
	 */
	private int getNextId(Set<Integer> finishIds){
		if (finishIds.isEmpty()) {
			//TODO 返回第一個主线任務,从离散表内获取
			return 0;
		}
		//不按顺序的任务id,要排序取最大,其实直接Collections.max(finishIds)就好
        int lastQuestId = finishIds.stream().max(Integer::compare).get();
        ConfigMissionMain lastConfig = this.getConfig(lastQuestId);
        if (lastConfig == null) {
        	log.info("lastConfig[{}] not exists.", lastQuestId);
            return 0;
        }
        return lastConfig.getNextId();
	}
	
}
