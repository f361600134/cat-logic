package com.cat.server.game.module.pet;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.server.IModuleManager;
import com.cat.server.game.data.proto.PBPet.ReqPetActive;
import com.cat.server.game.data.proto.PBPet.ReqPetIdentify;
import com.cat.server.game.helper.ModuleDefine;
import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.functioncontrol.IPlayerModuleService;
import com.cat.server.game.module.pet.domain.Pet;
import com.cat.server.game.module.pet.domain.PetDomain;
import com.cat.server.game.module.pet.proto.RespPetActiveBuilder;
import com.cat.server.game.module.pet.proto.RespPetIdentifyBuilder;
import com.cat.server.game.module.pet.proto.RespPetUpdateBuilder;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.IResourceService;


/**
 * Pet控制器
 * @author Jeremy
 */
@Service
public class PetService implements IPetService, IResourceService, IPlayerModuleService{
	
	private static final Logger log = LoggerFactory.getLogger(PetService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private PetManager petManager;
 
	/**
	 * 更新信息
	 * @param domain 宠物域
	 * @param uniqueId 宠物唯一id
	 */
	public void responsePetInfo(PetDomain domain, long uniqueId) {
		Pet pet = domain.getBean(uniqueId);
		if (pet == null) {
			return;
		}
		playerService.sendMessage(domain.getId(), pet.toProto());
	}
	
	
	/////////////业务逻辑//////////////////
	
	/**
	* 请求宠物激活
	* @param long playerId
	* @param ReqPetActive req
	* @param RespPetActiveResp ack
	*/
	public ErrorCode reqPetActive(long playerId, ReqPetActive req, RespPetActiveBuilder ack){
		try {
			PetDomain domain = petManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			final long uniqueId = req.getUniqueId();
			//TODO Somthing.
			this.responsePetInfo(domain, uniqueId);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqPetActive error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	
	/**
	* 宠物资质鉴定
	* @param long playerId
	* @param ReqPetIdentify req
	* @param RespPetIdentifyResp ack
	*/
	public ErrorCode reqPetIdentify(long playerId, ReqPetIdentify req, RespPetIdentifyBuilder ack){
		try {
			PetDomain domain = petManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			final long uniqueId = req.getUniqueId();
			
			//TODO Somthing.
			this.responsePetInfo(domain, uniqueId);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqPetIdentify error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	
	/////////////接口方法////////////////////////
	
	@Override
	public int resType() {
		return ResourceType.Pet.getType();
	}
	
	@Override
	public boolean checkAdd(long playerId, Integer configId, Integer value) {
		PetDomain domain = petManager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkAdd(configId, value);
	}
	
	@Override
	public boolean checkEnough(long playerId, Integer configId, Integer value) {
		PetDomain domain = petManager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkEnough(configId, value);
	}

	@Override
	public void reward(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		PetDomain domain = petManager.getDomain(playerId);
		if (domain == null)	return;
		//背包加入普通道具
		domain.add(configId, value);
	}

	@Override
	public void cost(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		PetDomain domain = petManager.getDomain(playerId);
		if (domain == null)	return;
		domain.costByConfigId(configId, value);
	}
	
	@Override
	public void cost(long playerId, Long id, NatureEnum nEnum) {
		PetDomain domain = petManager.getDomain(playerId);
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
		PetDomain domain = petManager.getDomain(playerId);
		if (domain == null) {
			return 0;
		}
		return domain.getCount(configId);
	}

	@Override
	public int getModuleId() {
		return ModuleDefine.PET.getModuleId();
	}

	@Override
	public void doReset(long playerId, long now) {
		// TODO Auto-generated method stub
	}

	@Override
	public IModuleManager<Long, ?> getModuleManager() {
		return petManager;
	}
	
	@Override
	public void responseAllInfo(long playerId) {
		PetDomain domain = petManager.getDomain(playerId);
		Collection<Pet> beans = domain.getBeans();
		RespPetUpdateBuilder builder = RespPetUpdateBuilder.newInstance();
		for (Pet pet : beans) {
			builder.addPets(pet.toProto().build());
		}
		playerService.sendMessage(playerId, builder);
	}
}
 
 
