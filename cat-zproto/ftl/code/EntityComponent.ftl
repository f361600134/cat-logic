package com.game.module.${module.name?lower_case};

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.game.common.ResultData;
import com.game.defines.ErrorCode;
import com.game.defines.ModuleDefines;
import com.game.module.cost.CostUtil;
import com.game.module.cost.structs.Cost;
import com.game.module.dress.ClothManager;
import com.game.module.log.LogCause;
import com.game.module.pay.event.PlayerVipLevelUpEvent;
import com.game.module.player.component.PlayerBeanComponent;
import com.game.module.resource.ResourceHelper;
import com.game.module.resource.define.ResourceType;
import com.game.module.reward.RewardUtil;
import com.game.module.reward.structs.Reward;
import com.game.module.reward.structs.RewardGroup;
import com.game.module.sundry.event.SundryAddEvent;
import com.game.module.sundry.structs.SundryType;
import com.game.module.${module.name?lower_case}.structs.${entityName}Bean;
import com.game.module.${module.name?lower_case}.protocol.*;

import common.communication.Protocol;
import common.component.event.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ${entityName}Component
 * @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
public class ${module.name}Component extends PlayerBeanComponent<${module.name}Bean> {
	
	private final Logger logger = LoggerFactory.getLogger(${module.name}Component.class);
	
	private ${module.name}Bean bean;
	
	@Override
    public Class<${module.name}Bean> getBeanClazz() {
        return ${module.name}Bean.class;
    }

    @Override
    public ${module.name}Bean getBean() {
        return bean;
    }

    @Override
    public void setBean(${module.name}Bean bean) {
        this.bean = bean;
    }

    @Override
    public int getModuleId() {
        return ModuleDefines.${module.name ? upper_case};
    }

	@Override
    protected boolean isFunctionOpen() {
        return true;
    }
    
    @Override
    protected Protocol handleProtocol(Protocol request) {
        int msgId = request.getId();
        <#assign  count = 0 />
        <#if protoReqStructList ? exists>
		<#list protoReqStructList as proto>
		<#if count == 0>
		if (msgId == ${proto.name}.ID) {//${proto.comment}
			return ${proto.name ? uncap_first}((${proto.name}) request);
		}
		<#else>
		else if (msgId == ${proto.name}.ID) {//${proto.comment}
        	return ${proto.name ? uncap_first}((${proto.name}) request);
    	}
		</#if>
		<#assign  count ++ />
		</#list>
		</#if>
        return null;
    }
    
    @Override
    public void handleGameEvent(GameEvent gameEvent) {
    	//TODO something
        //if (gameEvent.isThisEvent(SundryAddEvent.Id)) {
        //    refreshWishBottle((SundryAddEvent) gameEvent);
        //} else if (gameEvent.isThisEvent(PlayerVipLevelUpEvent.ID)) {
        //    refreshBottleSumTurnCount((PlayerVipLevelUpEvent) gameEvent);
        //}
    }
    
    @Override
    protected int[] focusMessageIds() {
    	//TODO something
    	return new int[] {
    	<#if protoReqStructList ? exists>
    	<#assign  count = protoReqStructList?size />
		<#list protoReqStructList as proto>
		<#assign  count -- />
		<#if count == 0>
			${proto.name}.ID
		<#else>
			${proto.name}.ID,
		</#if>
		</#list>
		</#if>
		};
    }

    @Override
    protected String[] focusEventIds() {
    	//TODO something
        return new String[] {};
    }
    
    @Override
    public void afterLogin(List<Protocol> protocols) {
    	//TODO something
    	//protocols.add(allWishInfo());
    }
		
	////////////以下是协议方法入口/////////////
	<#if protoReqStructList ? exists>
	<#list protoReqStructList as proto>
	/**
	* ${proto.comment}
	*/
	private Protocol ${proto.name ? uncap_first}(${proto.name} req) {
        //TODO something...
        return null;
    }
	</#list>
	</#if>
	
	////////////以下是Res协议构建/////////////
	
	<#if protoAckStructMap ? exists>
	<#list protoAckStructMap?values as proto>
	
	public Protocol build${proto.name}() {
		${proto.name} res = new ${proto.name}();
		//TODO something...
		<#list proto.fields as li>
		//res.set${li.name ? uncap_first}();
		</#list>
		return res;
	}
	</#list>
	</#if>
	
	////////////以下是业务逻辑/////////////
	
	
}
