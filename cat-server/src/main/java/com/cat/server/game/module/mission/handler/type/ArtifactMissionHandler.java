package com.cat.server.game.module.mission.handler.type;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.server.core.event.IEvent;
import com.cat.server.game.data.config.local.ConfigMissionArtifact;
import com.cat.server.game.helper.ModuleDefine;
import com.cat.server.game.module.artifact.IArtifactService;
import com.cat.server.game.module.mission.define.MissionTypeEnum;
import com.cat.server.game.module.mission.define.QuestState;
import com.cat.server.game.module.mission.domain.Quest;
import com.cat.server.game.module.mission.domain.QuestTypeData;
import com.cat.server.game.module.mission.handler.AbstractMissionHandler;
import com.cat.server.game.module.mission.proto.PBMissionQuestInfoBuilder;
import com.cat.server.game.module.mission.proto.RespMissionInfoBuilder;

/**
 * 神器任务处理类<br>
 * @auth Jeremy
 * @date 2022年3月27日下午9:47:51
 */
public class ArtifactMissionHandler extends AbstractMissionHandler<ConfigMissionArtifact>{
	
	@Autowired private IArtifactService artifactService;

	@Override
	public MissionTypeEnum getMissionType() {
		return MissionTypeEnum.ARTIFACT;
	}
	
	/**
	 * 神器任务, 当完成任务后, 找出其对应的神器, 神器对应的所有的任务. 如果均已完成, 则通知神器模块激活此神器
	 * 玩家登陆刷新此任务时, 需要根据神器id分类任务, 若有完成的任务, 则通知一下神器模块
	 */
	@Override
	protected void afterSubmitMission(long playerId, Quest quest) {
		super.afterSubmitMission(playerId, quest);
		//提交任务后尝试激活神器
		this.artifactActive(playerId, this.getConfig(quest.getConfigId()).getArtifactId());
	}
	
	@Override
	public boolean handleEvent(long playerId, Quest quest, IEvent event) {
		if (this.checkArtifactActive(playerId, quest.getConfigId())) {
			//如果神器当前任务对应的神器已激活,则不处理对应的任务
			return false;
		}
		return super.handleEvent(playerId, quest, event);
	}
	
	/**
	 * 通过任务id, 获取到对应神器id
	 * @param configId 任务id
	 * @return
	 */
	private int getArtifactId(int configId) {
		ConfigMissionArtifact config = this.getConfig(configId);
		if (config == null) {
			return 0;
		}
		return config.getArtifactId();
	}
	
	/**
	 * 检测任务对应的神器是否激活
	 * @return boolean  
	 * @date 2022年3月27日下午10:58:33
	 */
	private boolean checkArtifactActive(long playerId, int configId) {
		return artifactService.checkArtifactActive(playerId, this.getArtifactId(configId));
	}
	
	/**
	 * 激活任务对应的神器, 判断神器对应任务是否全部完成, 如果全部完成则激活神器
	 * @param playerId 玩家id
	 * @param artifactId  神器id
	 * @date 2022年3月27日下午10:57:19
	 */
	private void artifactActive(long playerId, int configId) {
		int artifactId = this.getConfig(configId).getArtifactId();
		//如果未激活,统计出所有任务,尝试激活
		List<ConfigMissionArtifact> configs = this.getConfigs()
		.values()
		.stream()
		.filter(c->c.getArtifactId() == artifactId)
		.collect(Collectors.toList());
		//判断是否所有神器任务都完成了
		QuestTypeData questTypeData = this.getQuestTypeData(playerId, true);
		boolean allFinished = true;
		for (ConfigMissionArtifact config : configs) {
			allFinished &= questTypeData.isFinished(config.getArtifactId());
			if (!allFinished) {
				break;
			}
		}
		if (allFinished) {
			//激活神器
			this.artifactService.artifactActive(playerId, artifactId);
		}
	}
	
	@Override
	public RespMissionInfoBuilder toProto(long playerId) {
		QuestTypeData questTypeData = this.getQuestTypeData(playerId, true);
		RespMissionInfoBuilder missionBuilder = RespMissionInfoBuilder.newInstance();
		missionBuilder.setType(this.getMissionType().getType());
		for (ConfigMissionArtifact config : this.getConfigs().values()) {
			if(this.checkArtifactActive(playerId, config.getArtifactId())) {
				//已激活则跳过任务组装
				continue;
			}
			//继续组装任务
			int state = questTypeData.getState(config.getId());
			if (state == QuestState.STATE_NONE.getValue()) {
				//任务未完成,推送进度
				Quest quest = questTypeData.getQuest(config.getId());
				missionBuilder.addQuests(quest.toProto().build());
			}else {
				//任务已完成,推送状态即可
				PBMissionQuestInfoBuilder questBuilder = PBMissionQuestInfoBuilder.newInstance();
				questBuilder.setId(config.getId());
				questBuilder.setState(state);
				questBuilder.setType(this.getMissionType().getType());
				missionBuilder.addQuests(questBuilder.build());
			}
		}
		return missionBuilder;
	}
	
	@Override
	public int getModuleId() {
		return ModuleDefine.UNDEFINE.getModuleId();
	}

}
