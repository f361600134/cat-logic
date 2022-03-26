package com.cat.server.game.module.mission.handler.type;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.server.core.event.IEvent;
import com.cat.server.game.data.config.local.ConfigMissionArtifact;
import com.cat.server.game.module.artifact.IArtifactService;
import com.cat.server.game.module.mission.define.MissionTypeEnum;
import com.cat.server.game.module.mission.domain.Quest;
import com.cat.server.game.module.mission.domain.QuestTypeData;
import com.cat.server.game.module.mission.handler.AbstractMissionHandler;

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
		this.active(playerId, this.getArtifactId(quest.getConfigId()));
	}
	
	@Override
	public boolean handleEvent(long playerId, Quest quest, IEvent event) {
		if (this.checkArtifactActive(playerId, quest.getConfigId())) {
			//如果神器当前任务对应的神器已激活,则处理对应的任务
			return false;
		}
		return super.handleEvent(playerId, quest, event);
	}
	
	/**
	 * 获取神器id
	 * @return  
	 * @return int  
	 * @date 2022年3月27日下午11:00:43
	 */
	private int getArtifactId(int configId) {
		ConfigMissionArtifact configMissionArtifact = this.getConfig(configId);
		return configMissionArtifact.getArtifactId();
	}
	
	/**
	 * 检测神器是否激活
	 * @return  
	 * @return boolean  
	 * @date 2022年3月27日下午10:58:33
	 */
	private boolean checkArtifactActive(long playerId, int configId) {
		int artifactId = this.getArtifactId(configId);
		return artifactService.checkArtifactActive(playerId, artifactId);
	}
	
	/**
	 * 激活神器
	 * @param playerId 玩家id
	 * @param artifactId  神器id
	 * @date 2022年3月27日下午10:57:19
	 */
	private void active(long playerId, int artifactId) {
		//如果未激活,统计出所有任务,尝试激活
		List<ConfigMissionArtifact> configs = this.getConfigs()
		.values()
		.stream()
		.filter(c->c.getArtifactId() == artifactId)
		//.filter(c->questTypeData.isFinished(c.getArtifactId()))
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

}
