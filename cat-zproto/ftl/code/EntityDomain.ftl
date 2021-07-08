package com.cat.server.game.module.${entity.getEntityName()?lower_case}.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cat.server.core.server.AbstractModuleDomain;

<#--一对一-->
<#if module.isOne2one()>
/**
* ${entity.getEntityName()}Domain
* @author Jeremy
*/
<#if module.getExtendInfo()?seq_contains(1)>
<#assign text="implements IMissionHandler" />
</#if>
public class ${entity.getEntityName()}Domain extends AbstractModuleDomain<Long, ${entity.getEntityName()}> ${text}{

	private static final Logger log = LoggerFactory.getLogger(${entity.getEntityName()}Domain.class);
	
	<#if module.getExtendInfo()?seq_contains(1)>
	// 代理处理代理类
	private IMissionHandler missionHandler;
	</#if>
	
	public ${entity.getEntityName()}Domain(){
	}
	
	////////////业务代码////////////////////
	<#if module.getExtendInfo()?seq_contains(1)>
	<#--任务相关的业务代码-->
	/**
	 * 初次创建对象, 初始化任务,
	 */
	@Override
	public void afterCreate() {
		int configId = 0;
		${entity.getEntityName()}MissionType initNission = ${entity.getEntityName()}MissionType.create(configId);
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
</#if>

<#-- 一对多 -->
<#if !module.isOne2one()>
<#if module.getExtendInfo()?seq_contains(2)>
import com.cat.server.game.module.resource.IResourceDomain;
import com.cat.server.game.module.resource.domain.ItemResourceDomain;
</#if>
<#if !module.isOne2one() && module.getExtendInfo()?seq_contains(1)>
import com.cat.server.core.event.IEvent;
import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.data.proto.PBBag.PBMissionInfo;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mission.handler.${entity.getEntityName()}MissionHandler;
import com.cat.server.game.module.mission.handler.IMissionHandler;
import com.cat.server.game.module.mission.type.IMission;
</#if>

/**
* ${entity.getEntityName()}Domain
* @author Jeremy
*/
<#if module.getExtendInfo()?seq_contains(1)>
<#assign mission=" IMissionHandler" />
</#if>
<#if module.getExtendInfo()?seq_contains(2)>
<#assign resource=" IResourceDomain<Long, "+ entity.getEntityName()+">" />
</#if>
<#assign interf =""/>
<#if mission??>
	<#assign interf = "implements" + mission/>
</#if>
<#if resource??>
	<#if interf?contains("implements")>
	<#assign interf = interf +","+ resource/>
	<#else>
	<#assign interf = "implements" + resource/>
	</#if>
</#if>
public class ${entity.getEntityName()}Domain extends AbstractModuleMultiDomain<Long, Long, ${entity.getEntityName()}> ${interf}{

	private static final Logger log = LoggerFactory.getLogger(${entity.getEntityName()}Domain.class);
	
	<#if module.getExtendInfo()?seq_contains(2)>
	private static final int LIMIT = 999; 
	/**
	 * 资源代理对象
	 */
	private IResourceDomain<Long, ${entity.getEntityName()}> resourceDomainProxy;
	
	</#if>
	<#if !module.isOne2one() && module.getExtendInfo()?seq_contains(1)>
	/**
	 * 任务代理对象
	 */
	private Map<Integer, IMissionHandler> missionHandlerMap;
	
	public ${entity.getEntityName()}Domain(){
		this.missionHandlerMap = new ConcurrentHashMap<>();
	}
	<#else>
	public ${entity.getEntityName()}Domain(){
		
	}
	</#if>

	
	////////////业务代码////////////////////
	
	<#if !module.isOne2one() && module.getExtendInfo()?seq_contains(1)>
	<#--任务相关的业务代码-->
	@Override
	public void afterInit() {
		beanMap.forEach((configId, ${entity.getEntityName()}) -> {
			//	伪代码,初始化任务处理器,不同的任务处理器代理的不同神器内的任务
			IMissionHandler handler = ${entity.getEntityName()}MissionHandler.Builder.newBuilder()
					.playerId(playerId)
					.missionData(${entity.getEntityName()}.getMissionData())
					.build();
			missionHandlerMap.put(configId, handler);
		});
	}
	
	@Override
	public ErrorCode onProcess(IEvent event) {
		ErrorCode code = ErrorCode.SUCCESS;
		for (Integer configId : missionHandlerMap.keySet()) {
			IMissionHandler handler = missionHandlerMap.get(configId);
			ErrorCode cur = handler.onProcess(event);
			if (cur.isSuccess()) {//	如果处理成功, 则保存对应的神兵对象
				getBean(configId).update();
			}else {
				code = cur;
			}
		}
		return code;
	}

	@Override
	public ErrorCode onReward(int configId, NatureEnum nenum) {
		IMissionHandler handler = missionHandlerMap.get(configId);
		return handler.onReward(configId, nenum);
	}

	@Override
	public Collection<PBMissionInfo> toProto() {
		Collection<PBMissionInfo> ret = new ArrayList<>();
		for (IMissionHandler handler : missionHandlerMap.values()) {
			ret.addAll(handler.toProto());
		}
		return ret;
	}

	@Override
	public List<IMission> getUpdateList() {
		List<IMission> ret = new ArrayList<>();
		for (IMissionHandler handler : missionHandlerMap.values()) {
			ret.addAll(handler.getUpdateList());
		}
		return ret;
	}
	</#if>
	
	<#if module.getExtendInfo()?seq_contains(2)>
	<#--资源相关的业务代码-->
	@Override
	public void afterInit() {
		this.resourceDomainProxy = ${entity.getEntityName()}ResourceDomain.create(playerId, getBeanMap());
	}
	
	@Override
	public ${entity.getEntityName()} getBeanByConfigId(int configId) {
		return resourceDomainProxy.getBeanByConfigId(configId);
	}

	//@Override
	//public Collection<${entity.getEntityName()}> getBeansByConfigId(int configId) {
	//	return resourceDomainProxy.getBeansByConfigId(configId);
	//}

	@Override
	public boolean checkAdd(int configId, int count) {
		return resourceDomainProxy.checkAdd(configId, count);
	}

	@Override
	public List<${entity.getEntityName()}> add(int configId, int count) {
		return resourceDomainProxy.add(configId, count);
	}

	@Override
	public boolean checkEnough(int configId, int count) {
		return resourceDomainProxy.checkEnough(configId, count);
	}

	@Override
	public boolean costByConfigId(int configId, int count) {
		return resourceDomainProxy.costByConfigId(configId, count);
	}

	@Override
	public boolean costById(Long id, int count) {
		return resourceDomainProxy.costById(id, count);
	}

	@Override
	public List<${entity.getEntityName()}> getUpdateItemList() {
		return resourceDomainProxy.getUpdateItemList();
	}

	@Override
	public List<${entity.getEntityName()}> getDeleteItemList() {
		return resourceDomainProxy.getDeleteItemList();
	}
	</#if>
}
</#if>
