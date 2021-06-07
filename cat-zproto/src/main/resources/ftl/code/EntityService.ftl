package com.cat.server.game.module.${entity.getEntityName()?lower_case}.service;

import java.util.List;
import java.util.Map;
import java.util.Collection;

import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.${entity.getEntityName()?lower_case}.service.I${entity.getEntityName()}Service;
import com.cat.server.game.module.${entity.getEntityName()?lower_case}.domain.${entity.getEntityName()};
import com.cat.server.game.module.${entity.getEntityName()?lower_case}.domain.${entity.getEntityName()}Domain;
import com.cat.server.game.module.${entity.getEntityName()?lower_case}.manager.${entity.getEntityName()}Manager;
import com.cat.server.game.module.player.service.IPlayerService;
import com.cat.server.game.module.resource.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cat.server.game.helper.result.ErrorCode;


/**
 * ${entity.getEntityName()}控制器
 * @author Jeremy
 */
<#if module.getExtendInfo()?seq_contains(2)>
<#assign text=", IResourceService" />
</#if>
@Service
public class ${entity.getEntityName()}Service implements I${entity.getEntityName()}Service ${text}{
	
	private static final Logger log = LoggerFactory.getLogger(${entity.getEntityName()}Service.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private ${entity.getEntityName()}Manager ${entity.getEntityName()?uncap_first}Manager;
	
	<#if module.getExtendInfo()?seq_contains(3)>
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
		${entity.getEntityName()}Domain domain = ${entity.getEntityName()?uncap_first}Manager.getDomain(playerId);
		Collection<${entity.getEntityName()}> beans = domain.getBeans();
		//FSC todo somthing...
		//Codes for proto
		//playerService.sendMessage(playerId, ack);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		${entity.getEntityName()?uncap_first}Manager.remove(playerId);
	}
	
	/**
	 * 更新信息
	 */
	public void response${entity.getEntityName()}Info(${entity.getEntityName()}Domain domain) {
		Collection<${entity.getEntityName()}> beans = domain.getBeans();
		try {
			for (${entity.getEntityName()} ${entity.getEntityName() ? uncap_first} : beans) {
				//resp.addArtifactlist(${entity.getEntityName() ? uncap_first}.toProto());
			}
			//playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("response${entity.getEntityName()}Info error, playerId:{}", domain.getId());
			log.error("response${entity.getEntityName()}Info error, e:", e);
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
			${entity.getEntityName()}Domain domain = getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.response${entity.getEntityName()}Info(domain);
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
	
	<#if module.getExtendInfo()?seq_contains(2)>
	@Override
	public int resType() {
		return ResourceType.${entity.getEntityName()}.getType();
	}
	
	@Override
	public boolean checkAdd(long playerId, Integer configId, Integer value) {
		${entity.getEntityName()}Domain domain = ${entity.getEntityName() ? uncap_first}Manager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkAdd(configId, value);
	}
	
	@Override
	public boolean checkEnough(long playerId, Integer configId, Integer value) {
		${entity.getEntityName()}Domain domain = ${entity.getEntityName() ? uncap_first}Manager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkEnough(configId, value);
	}

	@Override
	public void reward(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		${entity.getEntityName()}Domain domain = ${entity.getEntityName() ? uncap_first}Manager.getDomain(playerId);
		if (domain == null)	return;
		//背包加入普通道具
		domain.addItem(configId, value, nEnum);
	}

	@Override
	public void cost(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		${entity.getEntityName()}Domain domain = ${entity.getEntityName() ? uncap_first}Manager.getDomain(playerId);
		if (domain == null)	return;
		domain.deductItemByConfigId(configId, value, nEnum);
	}
	
	@Override
	public void cost(long playerId, Long id, NatureEnum nEnum) {
		${entity.getEntityName()}Domain domain = ${entity.getEntityName() ? uncap_first}Manager.getDomain(playerId);
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
 
 
 