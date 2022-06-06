package com.cat.server.game.module.petequip;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.cat.server.game.helper.ModuleDefine;
import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.equip.domain.Equip;
import com.cat.server.game.module.equip.domain.EquipDomain;
import com.cat.server.game.module.equip.proto.RespEquipInfoBuilder;
import com.cat.server.game.module.item.domain.Item;
import com.cat.server.game.module.item.proto.RespItemDeleteBuilder;
import com.cat.server.game.module.item.proto.RespItemUpdateBuilder;
import com.cat.server.game.module.pet.IPetService;
import com.cat.server.game.module.pet.domain.Pet;
import com.cat.server.game.module.petequip.IPetEquipService;
import com.cat.server.game.module.petequip.domain.PetEquip;
import com.cat.server.game.module.petequip.domain.PetEquipDomain;
import com.cat.server.game.module.petequip.PetEquipManager;
import com.cat.server.game.module.petequip.proto.*;
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
import com.cat.server.game.data.config.local.ConfigPetEquip;
import com.cat.server.game.data.proto.PBPetEquip.*;


/**
 * PetEquip控制器
 * @author Jeremy
 */
@Service
public class PetEquipService implements IPetEquipService, IResourceService{
	
	private static final Logger log = LoggerFactory.getLogger(PetEquipService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private PetEquipManager petEquipManager;
	
	@Autowired private IPetService petService;
	
 
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
		PetEquipDomain domain = petEquipManager.getDomain(playerId);
		Collection<PetEquip> beans = domain.getBeans();
		//FSC todo somthing...
		//Codes for proto
		//playerService.sendMessage(playerId, ack);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		petEquipManager.remove(playerId);
	}
  
	@Override
	public void responseAllInfo(long playerId) {
		PetEquipDomain domain = petEquipManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		RespPetEquipInfoBuilder resp = RespPetEquipInfoBuilder.newInstance();
		/* 获取所有的装备下发至客户端,包含已使用的装备, 已使用的装备客户端在背包不做展示 */
		Collection<PetEquip> beans = domain.getBeans();
		for (PetEquip equip : beans) {
			resp.addPetEquips(equip.toProto());
		}
		playerService.sendMessage(domain.getId(), resp);
	}
	
	/**
	 * 更新单个装备信息给客户端
	 * @param playerId 玩家id
	 * @param equip 要更新的装备id
	 * @date 2022年6月5日下午11:44:42
	 */
	public void responsePetEquipInfo(long playerId, long equip) {
		PetEquipDomain domain = petEquipManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		PetEquip petEquip = domain.getBean(equip);
		RespPetEquipInfoBuilder resp = RespPetEquipInfoBuilder.newInstance();
		resp.addPetEquips(petEquip.toProto());
		playerService.sendMessage(domain.getId(), resp);
	}
	
//	// 推送更新物品列表至前端
//	public void responseUpdateItemList(long playerId, Collection<PetEquip> itemList) {
//		// 更新物品
//		if (!itemList.isEmpty()) {
//			RespItemUpdateBuilder ack = RespItemUpdateBuilder.newInstance();
//			itemList.forEach((item)->{
//				ack.addItems(item.toProto());
//			});
//			playerService.sendMessage(playerId, ack);
//		}
//	}
//	
//	//推送删除物品列表至前端
//	public void responseDeleteItemList(long playerId, Collection<Long> itemList){
//		//更新物品
//		if (!itemList.isEmpty()) {
//			RespItemDeleteBuilder ack = RespItemDeleteBuilder.newInstance();
//			itemList.forEach((uniqueId)->{
//				ack.addIds(uniqueId);
//			});
//			playerService.sendMessage(playerId, ack);
//		}
//	}
	
	
	/////////////业务逻辑//////////////////
	
	/**
	* 请求装备信息
	* @param long playerId
	* @param ReqPetEquipInfo req
	* @param RespPetEquipInfoResp ack
	*/
	public ErrorCode reqPetEquipInfo(long playerId, ReqPetEquipInfo req, RespPetEquipInfoBuilder ack){
		try {
			PetEquipDomain domain = petEquipManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.responseAllInfo(playerId);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqPetEquipInfo error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求穿戴装备
	* @param long playerId
	* @param ReqWearPetEquip req
	* @param RespWearPetEquipResp ack
	*/
	public ErrorCode reqWearPetEquip(long playerId, ReqWearPetEquip req, RespWearPetEquipBuilder ack){
		try {
			PetEquipDomain domain = petEquipManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			final long holderId = req.getEquipId();
			final long equipId = req.getEquipId();
			//判断装备是否存在
			PetEquip petEquip = this.getPetEquip(playerId, equipId);
			if (petEquip == null) {
				return ErrorCode.EQUIP_NOT_EXIST;
			}
			//判断装备是否已经被装备, 或装备重复装备
			if (petEquip.getHolder() != 0 || petEquip.getHolder() == holderId) {
				return ErrorCode.EQUIP_ALREAD_WEAR;
			}
			//判断装备配置
			ConfigPetEquip configPetEquip = ConfigManager.getInstance().getConfig(ConfigPetEquip.class, petEquip.getConfigId());
			if (configPetEquip == null) {
				return ErrorCode.CONFIG_NOT_EXISTS;
			}
			//判断穿戴装备的宠物是否存在
			Pet pet = petService.getPet(domain.getId(), holderId);
			if (pet == null) {
				return ErrorCode.PET_NOT_EXIST;
			}
			final int slot = configPetEquip.getSlot();
			domain.replaceNewEquip(slot, holderId, petEquip);
			this.responsePetEquipInfo(playerId, equipId);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqWearPetEquip error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
  
	
	/////////////接口方法////////////////////////
	
	@Override
	public int resType() {
		return ResourceType.PetEquip.getType();
	}
	
	@Override
	public boolean checkAdd(long playerId, Integer configId, Integer value) {
		PetEquipDomain domain = petEquipManager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkAdd(configId, value);
	}
	
	@Override
	public boolean checkEnough(long playerId, Integer configId, Integer value) {
		PetEquipDomain domain = petEquipManager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkEnough(configId, value);
	}

	@Override
	public void reward(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		PetEquipDomain domain = petEquipManager.getDomain(playerId);
		if (domain == null)	return;
		//背包加入普通道具
		domain.add(configId, value);
	}

	@Override
	public void cost(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		PetEquipDomain domain = petEquipManager.getDomain(playerId);
		if (domain == null)	return;
		domain.costByConfigId(configId, value);
	}
	
	@Override
	public void cost(long playerId, Long id, NatureEnum nEnum) {
		PetEquipDomain domain = petEquipManager.getDomain(playerId);
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
	public IModuleManager<Long, ?> getModuleManager() {
		return petEquipManager;
	}

	@Override
	public int getModuleId() {
		return ModuleDefine.PET_EQUIT.getModuleId();
	}

	@Override
	public void doReset(long playerId, long now) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getCount(long playerId, Integer configId) {
		PetEquipDomain domain = petEquipManager.getDomain(playerId);
		if (domain == null)	return 0;
		return domain.getCount(configId);
	}
	
	@Override
	public PetEquip getPetEquip(long playerId, long petEquipId) {
		PetEquipDomain domain = petEquipManager.getDomain(playerId);
		if (domain == null) return null;
		return domain.getBean(petEquipId);
	}
}
 
 
