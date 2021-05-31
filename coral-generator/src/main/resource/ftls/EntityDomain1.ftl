package com.cat.server.game.module.${entityName?lower_case}.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cat.server.core.server.AbstractModuleDomain;

/**
* ${entityName}Domain
* @author Jeremy
*/
<#if config.isMission()>
<#assign text="implements IMissionHandler" />
</#if>
public class ${entityName}Domain extends AbstractModuleDomain<Long, ${entityName}> ${text}{

	private static final Logger log = LoggerFactory.getLogger(${entityName}Domain.class);
	
	<#if config.isMission()>
	// 代理处理代理类
	private IMissionHandler missionHandler;
	</#if>
	
	public ${entityName}Domain(){
	}
	
	////////////业务代码////////////////////
	<#if config.isMission()>
	<#--任务相关的业务代码-->
	/**
	 * 初次创建对象, 初始化任务,
	 */
	@Override
	public void afterCreate() {
		int configId = 0;
		${entityName}MissionType initNission = ${entityName}MissionType.create(configId);
		bean.getMissionData().addMissionPojo(initNission);
		bean.update();
	}

	@Override
	public void afterInit() {
		this.missionHandler = Builder.newBuilder().
				playerId(getPlayerId()).missionData(bean.getMissionData())
				.afterRewardedListener((mission, missionData) -> {
					//TODO something...
				}).build();
	}
	
	@Override
	public ErrorCode onProcess(IEvent event) {
		ErrorCode errorCode = missionHandler.onProcess(event);
		if (errorCode.isSuccess()) {
			bean.update();
		}
		return errorCode;
	}

	@Override
	public ErrorCode onReward(int configId, NatureEnum nenum) {
		ErrorCode errorCode = missionHandler.onReward(configId, nenum);
		if (errorCode.isSuccess()) {
			bean.update();
		}
		return errorCode;
	}
	
	@Override
	public Collection<PBMissionInfo> toProto() {
		return missionHandler.toProto();
	}

	@Override
	public List<IMission> getUpdateList() {
		return missionHandler.getUpdateList();
	}
	</#if>
}
