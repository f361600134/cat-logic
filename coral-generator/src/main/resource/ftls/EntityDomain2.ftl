package com.cat.server.game.module.${entityName?lower_case}.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cat.server.core.server.AbstractModuleMultiDomain;

<#if config.isResource()>
import com.cat.server.game.module.resource.IResourceDomain;
import com.cat.server.game.module.resource.domain.ItemResourceDomain;
</#if>
<#if !config.isO2o() && config.isMission()>
import com.cat.server.core.event.IEvent;
import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.data.proto.PBBag.PBMissionInfo;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mission.handler.${entityName}MissionHandler;
import com.cat.server.game.module.mission.handler.IMissionHandler;
import com.cat.server.game.module.mission.type.IMission;
</#if>

/**
* ${entityName}Domain
* @author Jeremy
*/
<#assign interf =""/>
<#if config.isMission()>
<#assign mission=" IMissionHandler" />
</#if>
<#if config.isResource()>
<#assign resource=" IResourceDomain<Long, "+ entityName+">" />
</#if>
<#if mission??>
	<#if interf??>
	interf = "implements"
	</#if>
	<#assign interf =interf + mission +","/>
</#if>
<#if resource??>
	<#if interf??>
	interf = "implements"
	</#if>
	<#assign interf =interf + resource/>
</#if>

public class ${entityName}Domain extends AbstractModuleMultiDomain<Long, Long, ${entityName}> ${interf}{

	private static final Logger log = LoggerFactory.getLogger(${entityName}Domain.class);
	
	<#if config.isResource()>
	private static final int LIMIT = 999; 
	
	/**
	 * 资源代理对象
	 */
	private IResourceDomain<Long, ${entityName}> resourceDomainProxy;
	</#if>
	
	<#if !config.isO2o() && config.isMission()>
	private Map<Integer, IMissionHandler> missionHandlerMap;
	<#--<#elseif config.iso2o() && config.isMission()>
	private IMissionHandler missionHandler;-->
	public ${entityName}Domain(){
		this.missionHandlerMap = new ConcurrentHashMap<>();
	}
	<#else>
	public ${entityName}Domain(){
		
	}
	</#if>

	
	////////////业务代码////////////////////
	
	<#if !config.isO2o() && config.isMission()>
	<#--任务相关的业务代码-->
	@Override
	public void afterInit() {
		beanMap.forEach((configId, ${entityName}) -> {
			//	伪代码,初始化任务处理器,不同的任务处理器代理的不同神器内的任务
			IMissionHandler handler = ${entityName}MissionHandler.Builder.newBuilder()
					.playerId(playerId)
					.missionData(${entityName}.getMissionData())
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
	
	<#if config.isResource()>
	<#--资源相关的业务代码-->
	@Override
	public void afterInit() {
		this.resourceDomainProxy = ${entityName}ResourceDomain.create(playerId, getBeanMap());
	}
	
	@Override
	public ${entityName} getBeanByConfigId(int configId) {
		return resourceDomainProxy.getBeanByConfigId(configId);
	}

	//@Override
	//public Collection<${entityName}> getBeansByConfigId(int configId) {
	//	return resourceDomainProxy.getBeansByConfigId(configId);
	//}

	@Override
	public boolean checkAdd(int configId, int count) {
		return resourceDomainProxy.checkAdd(configId, count);
	}

	@Override
	public List<${entityName}> add(int configId, int count) {
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
	public List<${entityName}> getUpdateItemList() {
		return resourceDomainProxy.getUpdateItemList();
	}

	@Override
	public List<${entityName}> getDeleteItemList() {
		return resourceDomainProxy.getDeleteItemList();
	}
	</#if>
}

