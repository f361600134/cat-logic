package com.cat.server.game.module.pet;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.server.IModuleManager;
import com.cat.server.game.data.config.local.ConfigPetBase;
import com.cat.server.game.data.config.local.ConfigPetLevel;
import com.cat.server.game.data.proto.PBPet.ReqPetActive;
import com.cat.server.game.data.proto.PBPet.ReqPetIdentify;
import com.cat.server.game.data.proto.PBPet.ReqPetLevelup;
import com.cat.server.game.helper.ModuleDefine;
import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.function.IPlayerModuleService;
import com.cat.server.game.module.pet.domain.Pet;
import com.cat.server.game.module.pet.domain.PetDomain;
import com.cat.server.game.module.pet.proto.RespPetActiveBuilder;
import com.cat.server.game.module.pet.proto.RespPetDeleteBuilder;
import com.cat.server.game.module.pet.proto.RespPetIdentifyBuilder;
import com.cat.server.game.module.pet.proto.RespPetLevelupBuilder;
import com.cat.server.game.module.pet.proto.RespPetUpdateBuilder;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.IResource;
import com.cat.server.game.module.resource.IResourceService;

/**
 * Pet控制器
 * @author Jeremy
 */
@Service
class PetService implements IPetService, IResourceService, IPlayerModuleService{
	
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
		RespPetUpdateBuilder builder = RespPetUpdateBuilder.newInstance();
		builder.addPets(pet.toProto());
		playerService.sendMessage(domain.getId(), builder);
	}
	
	// 推送更新物品列表至前端
	public void responseUpdateItemList(long playerId, Collection<Pet> petList) {
		if (!petList.isEmpty()) {
			RespPetUpdateBuilder ack = RespPetUpdateBuilder.newInstance();
			petList.forEach((pet)->{
				ack.addPets(pet.toProto());
			});
			playerService.sendMessage(playerId, ack);
		}
	}
	//推送删除物品列表至前端
	public void responseDeleteItemList(long playerId, Collection<Long> petList){
		if (!petList.isEmpty()) {
			RespPetDeleteBuilder ack = RespPetDeleteBuilder.newInstance();
			ack.addAllUniqueId(petList);
			playerService.sendMessage(playerId, ack);
		}
	}
	
	/////////////业务逻辑//////////////////
	
	/**
	* 请求宠物升级
	* @param long playerId
	* @param ReqPetLevelup req
	* @param RespPetLevelupResp ack
	*/
	public ErrorCode reqPetLevelup(long playerId, ReqPetLevelup req, RespPetLevelupBuilder ack){
		PetDomain domain = petManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.DOMAIN_IS_NULL;
		}
		final long uniqueId = req.getUniqueId();
		Pet pet = domain.getBean(uniqueId);
		if (pet == null) {
			return ErrorCode.INVALID_PARAM;
		}
		else if (!pet.getActive()) {
			return ErrorCode.PET_NOT_ACTIVE;
		}
		ConfigPetLevel configPetLevel = ConfigManager.getInstance().getConfig(ConfigPetLevel.class, pet.getLevel());
		if (configPetLevel == null) {
			log.warn("reqPetLevelup error, configPetLevel is null, configId:{}", pet.getLevel());
			return ErrorCode.CONFIG_NOT_EXISTS;
		}
		if (configPetLevel.getExp() == -1) {
			return ErrorCode.PET_LEVEL_LIMIT;
		}
		ConfigPetBase configPetBase = ConfigManager.getInstance().getConfig(ConfigPetBase.class, pet.getConfigId());
		if(configPetBase == null) {
			log.warn("reqPetLevelup error, ConfigPetBase is null, configId:{}", pet.getConfigId());
			return ErrorCode.CONFIG_NOT_EXISTS;
		}
		if (pet.getExp() >= configPetLevel.getExp()) {
			//1.升级
			pet.setLevel((short)(pet.getLevel() + 1));
			pet.setExp(pet.getExp()-configPetLevel.getExp());
			//2.尝试领悟技能
			domain.comprehensionSkill(pet, configPetBase);
			pet.getSkillRootNode().setAttrChange();
			pet.update();
		}
		this.responsePetInfo(domain, uniqueId);
		return ErrorCode.SUCCESS;
	}
	
	/**
	* 请求宠物激活
	* @param playerId 玩家id
	* @param req 消息请求
	* @param ack 消息响应
	*/
	public ErrorCode reqPetActive(long playerId, ReqPetActive req, RespPetActiveBuilder ack){
		PetDomain domain = petManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.DOMAIN_IS_NULL;
		}
		final long uniqueId = req.getUniqueId();
		Pet pet = domain.getBean(uniqueId);
		if (pet == null) {
			return ErrorCode.INVALID_PARAM;
		}
		ConfigPetBase configPetBase = ConfigManager.getInstance().getConfig(ConfigPetBase.class, pet.getConfigId());
		if(configPetBase == null) {
			log.warn("reqPetActive error, ConfigPetBase is null, configId:{}", pet.getConfigId());
			return ErrorCode.CONFIG_NOT_EXISTS;
		}
		pet.setActive(true);
		//初始化性别
		domain.initializeGender(pet, configPetBase);
		//初始化技能池
		domain.initializeSkillPoolType(pet, configPetBase);
		//领悟技能
		domain.comprehensionSkill(pet, configPetBase);
		//初始前缀
		domain.initializePrefix(pet);
		pet.update();
		//计算属性和技能
		pet.getAttrRootNode().setAttrChange();
		pet.getSkillRootNode().setAttrChange();
		//响应给客户端
		this.responsePetInfo(domain, uniqueId);
		return ErrorCode.SUCCESS;
	}
	
	/**
	* 宠物资质鉴定
	* @param playerId
	* @param req
	* @param ack
	*/
	public ErrorCode reqPetIdentify(long playerId, ReqPetIdentify req, RespPetIdentifyBuilder ack){
		PetDomain domain = petManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.DOMAIN_IS_NULL;
		}
		final long uniqueId = req.getUniqueId();
		Pet pet = domain.getBean(uniqueId);
		if (pet == null) {
			return ErrorCode.INVALID_PARAM;
		}
		else if (!pet.getActive()) {
			return ErrorCode.PET_NOT_ACTIVE;
		}
		domain.randomAptitudeAttr(pet);
		pet.update();
		//重新计算宠物属性
		pet.getAttrRootNode().setAttrChange();
		this.responsePetInfo(domain, uniqueId);
		return ErrorCode.SUCCESS;
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
	public void responseModuleInfo(long playerId) {
		PetDomain domain = petManager.getDomain(playerId);
		Collection<Pet> beans = domain.getBeans();
		RespPetUpdateBuilder builder = RespPetUpdateBuilder.newInstance();
		for (Pet pet : beans) {
			builder.addPets(pet.toProto());
		}
		playerService.sendMessage(playerId, builder);
	}
	
	@Override
	public void notify(long playerId) {
		PetDomain domain = petManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		//	通知客户端数据变动
		this.responseUpdateItemList(playerId, domain.getAndClearUpdateList());
		//	通知客户端数据删除
		this.responseDeleteItemList(playerId, domain.getAndClearDeleteList());
	}

	@Override
	public Pet getPet(long playerId, long petId) {
		PetDomain domain = petManager.getDomain(playerId);
		if (domain == null) return null;
		return domain.getBean(petId);
	}

//	@Override
//	public void addResource(long playerId, IResource res, NatureEnum nEnum) {
//		PetDomain domain = petManager.getDomain(playerId);
//		if (domain == null) return ;
//		if (!(res instanceof Pet)) {
//			return;
//		}
//		Pet pet = (Pet)res;
//		domain.addReource(pet.getUniqueId(), pet);
//	}
}
 
 
