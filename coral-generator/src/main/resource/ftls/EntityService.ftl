package com.cat.server.game.module.${entityName?lower_case}.service;

import java.util.List;
import java.util.Map;
import java.util.Collection;

import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.${entityName?lower_case}.service.I${entityName}Service;
import com.cat.server.game.module.${entityName?lower_case}.domain.${entityName};
import com.cat.server.game.module.${entityName?lower_case}.domain.${entityName}Domain;
import com.cat.server.game.module.${entityName?lower_case}.manager.${entityName}Manager;
import com.cat.server.game.module.player.service.IPlayerService;
import com.cat.server.game.module.resource.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cat.server.game.helper.result.ErrorCode;


/**
 * ${entityName}控制器
 * @author Jeremy
 */
<#if config.isResource()>
<#assign text=", IResourceService" />
</#if>
@Service
public class ${entityName}Service implements I${entityName}Service ${text}{
	
	private static final Logger log = LoggerFactory.getLogger(${entityName}Service.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private ${entityName}Manager ${entityName?uncap_first}Manager;
	
	<#if !config.isCommon()>
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
		${entityName}Domain domain = ${entityName?uncap_first}Manager.getDomain(playerId);
		Collection<${entityName}> beans = domain.getBeans();
		//FSC todo somthing...
		//Codes for proto
		//playerService.sendMessage(playerId, ack);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		${entityName?uncap_first}Manager.remove(playerId);
	}
	
	/**
	 * 更新信息
	 */
	public void response${entityName}Info(${entityName}Domain domain) {
		Collection<${entityName}> beans = domain.getBeans();
		try {
			for (${entityName} ${entityName ? uncap_first} : beans) {
				//resp.addArtifactlist(${entityName ? uncap_first}.toProto());
			}
			//playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("response${entityName}Info error, playerId:{}", domain.getId());
			log.error("response${entityName}Info error, e:", e);
		}
	}
	</#if>
	
	/////////////业务逻辑//////////////////
	
	<#--  -->
	<#if protoReqStructList ? exists>
	<#list protoReqStructList as proto>
	/**
	* ${proto.comment}
	* @param long playerId
	* @param ${proto.name} req
	* @param ${protoAckStructMap[proto.name].name}Resp ack
	*/
	public ErrorCode ${proto.name ? uncap_first}(long playerId, ${proto.name} req, ${protoAckStructMap[proto.name].name}Resp ack){
		try {
			${entityName}Domain domain = getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.response${entityName}Info(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("${proto.name ? uncap_first} error, playerId:{}", playerId);
			log.error("${proto.name ? uncap_first} error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	</#list>
	</#if>
	
	/////////////接口方法////////////////////////
	
	<#if config.isResource()>
	@Override
	public int resType() {
		return ResourceType.${entityName}.getType();
	}
	
	@Override
	public boolean checkAdd(long playerId, Integer configId, Integer value) {
		${entityName}Domain domain = ${entityName ? uncap_first}Manager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkAdd(configId, value);
	}
	
	@Override
	public boolean checkEnough(long playerId, Integer configId, Integer value) {
		${entityName}Domain domain = ${entityName ? uncap_first}Manager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkEnough(configId, value);
	}

	@Override
	public void reward(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		${entityName}Domain domain = ${entityName ? uncap_first}Manager.getDomain(playerId);
		if (domain == null)	return;
		//背包加入普通道具
		domain.addItem(configId, value, nEnum);
	}

	@Override
	public void cost(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		${entityName}Domain domain = ${entityName ? uncap_first}Manager.getDomain(playerId);
		if (domain == null)	return;
		domain.deductItemByConfigId(configId, value, nEnum);
	}
	
	@Override
	public void cost(long playerId, Long id, NatureEnum nEnum) {
		${entityName}Domain domain = ${entityName ? uncap_first}Manager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		/*
		 * 道具默认有数量概念, 唯一的物品数量默认为1.
		 * 所以唯一道具,跟普通道具在扣除上面,逻辑相同,当数量为0则清除.
		 * 这里参数就默认扣除未1
		 */
		domain.deductItemById(id, 1, nEnum);
	}
	</#if>
}