package com.cat.server.game.module.equip;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.cat.server.game.helper.ModuleDefine;
import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.equip.IEquipService;
import com.cat.server.game.module.equip.domain.Equip;
import com.cat.server.game.module.equip.domain.EquipDomain;
import com.cat.server.game.module.equip.proto.RespEquipInfoBuilder;
import com.cat.server.game.module.equip.proto.RespWearEquipBuilder;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.server.IModuleManager;
import com.cat.server.game.data.config.local.ConfigEquip;
import com.cat.server.game.data.proto.PBEquip.*;

/**
 * Equip控制器
 * @author Jeremy
 */
@Service
public class EquipService implements IEquipService, IResourceService {
	
	private static final Logger log = LoggerFactory.getLogger(EquipService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private EquipManager equipManager;
	
	@Override
	public void responseAllInfo(long playerId) {
		EquipDomain domain = equipManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		RespEquipInfoBuilder resp = RespEquipInfoBuilder.newInstance();
		/* 获取所有的装备下发至客户端,包含已使用的装备, 已使用的装备客户端在背包不做展示 */
		Collection<Equip> beans = domain.getBeans();
		for (Equip equip : beans) {
			resp.addEquips(equip.toProto());
		}
		playerService.sendMessage(domain.getId(), resp);
	}
	
	/////////////业务逻辑//////////////////
	
	/**
	* 请求装备信息
	* @param long playerId
	* @param ReqEquipInfo req
	* @param RespEquipInfoResp ack
	*/
	public ErrorCode reqEquipInfo(long playerId, ReqEquipInfo req, RespEquipInfoBuilder ack){
		try {
			EquipDomain domain = equipManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			this.responseAllInfo(playerId);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqEquipInfo error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	
	/**
	* 请求穿戴装备
	* @param long playerId
	* @param ReqWearEquip req
	* @param RespWearEquipResp ack
	*/
	public ErrorCode reqWearEquip(long playerId, ReqWearEquip req, RespWearEquipBuilder ack){
		try {
			EquipDomain domain = equipManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			final long holderId = req.getEquipId();
			final long equipId = req.getEquipId();
			Equip equip = this.getEquip(playerId, equipId);
			if (equip == null) {
				return ErrorCode.EQUIP_NOT_EXIST;
			}
			ConfigEquip configEquip = ConfigManager.getInstance().getConfig(ConfigEquip.class, equip.getConfigId());
			if (configEquip == null) {
				return ErrorCode.CONFIG_NOT_EXISTS;
			}
			if (configEquip.getHolderType() == 1) {
				this.wearToHero(holderId, equipId);
			}else {
				this.wearToPet(domain, equip, holderId);
			}
			//TODO Somthing.
			this.responseAllInfo(domain.getId());
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqWearEquip error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	
	
	private ErrorCode wearToHero(long holderId, long equipId) {
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 穿戴装备, 装备实际还在玩家身上, 只不过没有下发至客户端
	 * @param domain
	 * @param equip
	 * @param holderId
	 * @return  
	 * @return ErrorCode  
	 * @date 2022年6月4日下午1:44:47
	 */
	private ErrorCode wearToPet(EquipDomain domain, Equip equip, long holderId) {
		return ErrorCode.SUCCESS;
	}
	
	/////////////接口方法////////////////////////
	
	@Override
	public Equip getEquip(long playerId, long equipId) {
		EquipDomain domain = equipManager.getDomain(playerId);
		if (domain == null) return null;
		return domain.getBean(equipId);
	}
	
	@Override
	public int resType() {
		return ResourceType.Equip.getType();
	}
	
	@Override
	public boolean checkAdd(long playerId, Integer configId, Integer value) {
		EquipDomain domain = equipManager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkAdd(configId, value);
	}
	
	@Override
	public boolean checkEnough(long playerId, Integer configId, Integer value) {
		EquipDomain domain = equipManager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkEnough(configId, value);
	}

	@Override
	public void reward(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		EquipDomain domain = equipManager.getDomain(playerId);
		if (domain == null)	return;
		//背包加入普通道具
		domain.add(configId, value);
	}

	@Override
	public void cost(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		EquipDomain domain = equipManager.getDomain(playerId);
		if (domain == null)	return;
		domain.costByConfigId(configId, value);
	}
	
	@Override
	public void cost(long playerId, Long id, NatureEnum nEnum) {
		EquipDomain domain = equipManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		/*
		 * 道具默认有数量概念, 唯一的物品数量默认为1.
		 * 所以唯一道具,跟普通道具在扣除上面,逻辑相同,当数量为0则清除.
		 * 这里参数就默认扣除未1
		 */
		domain.costById(id, 1);
	}

	@Override
	public int getCount(long playerId, Integer configId) {
		EquipDomain domain = equipManager.getDomain(playerId);
		if (domain == null) {
			return 0;
		}
		return domain.getCount(configId);
	}

	@Override
	public IModuleManager<Long, ?> getModuleManager() {
		return equipManager;
	}

	@Override
	public int getModuleId() {
		return ModuleDefine.EQUIT.getModuleId();
	}

	@Override
	public void doReset(long playerId, long now) {
		// TODO Auto-generated method stub
	}

	@Override
	public Collection<Long> getEquipIds(long playerId, long holderId) {
		EquipDomain domain = equipManager.getDomain(playerId);
		if (domain == null) {
			return Collections.emptyList();
		}
		return domain.getUsedEquipIds(holderId);
	}

	@Override
	public List<Equip> getEquips(long playerId, long holderId) {
		EquipDomain domain = equipManager.getDomain(playerId);
		if (domain == null) {
			return Collections.emptyList();
		}
		return domain.getUsedEquips(holderId);
	}

}
 
 
