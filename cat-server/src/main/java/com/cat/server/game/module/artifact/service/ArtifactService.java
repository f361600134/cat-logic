package com.cat.server.game.module.artifact.service;

import java.util.Collection;

import com.cat.server.core.event.PlayerEventBase;
import com.cat.server.game.module.artifact.domain.Artifact;
import com.cat.server.game.module.artifact.domain.ArtifactDomain;
import com.cat.server.game.module.artifact.manager.ArtifactManager;
import com.cat.server.game.module.artifact.proto.AckArtifactListResp;
import com.cat.server.game.module.player.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Artifact控制器
 */
@Service
public class ArtifactService {
	
	@Autowired private PlayerService playerService;
	@Autowired private ArtifactManager manager;
	
	private static final Logger log = LoggerFactory.getLogger(ArtifactService.class);
	
	/**
	 * 当收到事件
	 */
	public void onEvent(PlayerEventBase event) {
		long playerId = event.getPlayerId();
		ArtifactDomain domain = manager.getDomain(playerId);
		if (domain  == null) {
			log.info("onEvent error, playerId:{}", playerId);
			return;
		}
		domain.onProcess(event);
	}
	
	/**
	 * 更新信息
	 */
	private void responseArtifactInfo(long playerId) {
		ArtifactDomain domain = manager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		AckArtifactListResp resp = AckArtifactListResp.newInstance();
		Collection<Artifact> artifacts = domain.getBeans();
		for (Artifact artifact : artifacts) {
//			resp.addArtifactlist(artifact.toProto());
		}
		playerService.sendMessage(playerId, resp);
	}
	
	/////////////业务逻辑//////////////////
	
	
//	/**
//	* 请求神器信息
//	* @param playerId
//	* @param req
//	* @author Jeremy
//	*/
//	public int reqArtifactList(long playerId, PBArtifact.ReqArtifactList req){
//		this.responseArtifactInfo(playerId);
//		return 0;
//	}
	
//	/**
//	* 请求神器操作
//	* @param playerId 玩家id
//	* @param req
//	* @param ack
//	* @author Jeremy
//	*/
//	public int reqArtifactOpt(long playerId, PBArtifact.ReqArtifactOpt req, AckArtifactOptResp ack){
//		int optType = req.getOptType();
//		int configId = req.getConfigId();
//		int code = 0;
//		if (optType == ArtifactEnum.UPGRADE.getType()) {//升级操作
//			code = doUpgrade(playerId, configId);
//		}else if (optType == ArtifactEnum.REFINE.getType()) {
//			code = doRefine(playerId, configId);
//		}else if (optType == ArtifactEnum.SKILLUP.getType()) {
//			code = doSkillUp(playerId, configId);
//		}else if (optType == ArtifactEnum.RECAST.getType()) {
//			code = doRecast(playerId, configId);
//		}else if (optType == ArtifactEnum.UPGRADE_ONEKEY.getType()) {
//			code = doUpgradeOneKey(playerId, configId);
//		}
//		if (code == 0) {
//			this.responseArtifactInfo(playerId);
//		}
//		return code;
//	}

//	/**
//	 * 处理一键升级
//	 * 波波的话: 1次就够了 我们要做到真正的一键升级
//	 * 前端只请求一次, 神器升级成功后, 返回至前端.
//	 *
//	 * 一键升级实现方式: 默认升级十次, 计数器为0时, 再增加十次,直到升级不成功.
//	 * @param configId 神器配置id
//	 * @return 错误码
//	 */
//	public int doUpgradeOneKey(long playerId, int configId) {
//		Artifact artifact = getArtifacts(playerId).stream().filter(e -> e.getConfigId() == configId).findAny().get();
//		if (artifact == null) {
//			return ConfigTipsMgr.Artifact_731;
//		}
//		if (!artifact.isActivite()) {
//			return ConfigTipsMgr.Artifact_739;//未激活
//		}
//		/*
//		 * expire:预期次数, count:计数器, code 错误码
//		 * 如果计算器递减到0, 则程序视为升级成功, 重置计数器, 继续升级.
//		 * 如果第一次升级失败, 则返回错误码. 只要成功, 则不返回错误码.
//		 */
//		final int expire = 10;
//		int count = expire, code = 0;
//		while (count > 0) {
//			code = onUpgrade(artifact, configId);
//			if (code != 0) break;
//			if (--count == 0) count += (expire -1);
//		}
//		//如果第一次升级不成功, 返回第一次不成功的错误码
//		return count == expire ? code : 0;
//	}

//	/**
//	 * 重铸
//	 * @param configId 神器配置id
//	 * @return 错误码
//	 */
//	private int doRecast(long playerId, int configId) {
//		Artifact artifact = getArtifact(playerId, configId);
//		if (!artifact.isActivite()) {
//			return ConfigTipsMgr.Artifact_739;//未激活
//		}
//		int level = artifact.getLevel();
//		if (level <= 1) {
//			return ConfigTipsMgr.Artifact_736;
//		}
//		Map<Integer, Integer> materialHM = artifact.getUsedMaterialHM();
//		//移除掉金币
//		materialHM.remove(ResourceType.Copper.getClientType());
//		artifact.onRecast();
//		Context.getItemService().addNewItem(level, materialHM, NatureEnum.ARTIFACT_RECAST, StringUtils.toLogString("configId", configId));
//		artifact.update();
//		return 0;
//	}

//	/**
//	 * 处理技能升级
//	 * @param configId 神器配置id
//	 * @return 错误码
//	 */
//	private int doSkillUp(long playerId, int configId) {
//		Artifact artifact = getArtifact(playerId, configId);
//		if (!artifact.isActivite()) {
//			return ConfigTipsMgr.Artifact_739;//未激活
//		}
//		int level = artifact.getLevel();
//		int skillLevel =  artifact.getSkillLv();
//		//判断等级限制
//		ConfigArtifact artifactConfig = ConfigManager.get(ConfigArtifact.class,configId);
//		if (artifactConfig == null) {
//			return ConfigTipsMgr.Artifact_732;
//		}
//		if (skillLevel >= artifactConfig.getMaxSkillLevel()) {
//			return ConfigTipsMgr.Artifact_733;//已经到最大等级不可升级
//		}
//
//		//判断升级消耗
//		ConfigArtifactSkill artifactSkillConfig = ConfigArtifactSkill.getConfig(configId, skillLevel);
//		if (artifactSkillConfig == null) {
//			return ConfigTipsMgr.Artifact_732;
//		}
//		if (artifactSkillConfig.getNextID() == 0) {//表示最高级
//			return ConfigTipsMgr.Artifact_733;//已经到最大等级不可升级
//		}
//		if (level < artifactSkillConfig.getNeedLevel()) {
//			return ConfigTipsMgr.Artifact_734;//需要提升等级
//		}
//
//		boolean bool = Context.getItemService().enoughAndDeductItem(playerId,
//				artifactSkillConfig.getNeedGoodsMap(), NatureEnum.ARTIFACT_SKILLUP,
//				StringUtils.toLogString("configId", configId, "skillLevel", skillLevel));
//
//		if (!bool) {
//			return ConfigTipsMgr.Bag_204;//道具不足
//		}
//		//记录材料
//		artifact.setSkillLv(skillLevel + 1);
//		artifact.recordUsedMaterialHM(artifactSkillConfig.getNeedGoodsMap());
//		artifact.update();
//		EventPublisher.publishEvent0(new ArtifactSkillUpgradeEvent(artifact));
//		return 0;
//	}

//	/**
//	 * 处理精炼
//	 * @param configId 神器配置id
//	 * @return 错误码
//	 */
//	private int doRefine(long playerId, int configId) {
//		Artifact artifact = getArtifact(playerId, configId);
//		if (!artifact.isActivite()) {
//			return ConfigTipsMgr.Artifact_739;//未激活
//		}
//		int level = artifact.getLevel();
//		int rflevel =  artifact.getRefineLv();
//		//判断等级限制
//		ConfigArtifact artifactConfig = ConfigManager.get(ConfigArtifact.class,configId);
//		if (artifactConfig == null) {
//			return ConfigTipsMgr.Artifact_732;
//		}
//		if (rflevel >= artifactConfig.getMaxRefineLevel()) {
//			return ConfigTipsMgr.Artifact_733;//已经到最大等级不可升级
//		}
//		//判断升级消耗
//		ConfigArtifactRefine artifactRefineConfig = ConfigArtifactRefine.getConfig(configId, rflevel);
//		if (artifactRefineConfig == null) {
//			return ConfigTipsMgr.Artifact_732;
//		}
//		if (artifactRefineConfig.getNextId() == 0) {//表示最高级
//			return ConfigTipsMgr.Artifact_733;//已经到最大等级不可升级
//		}
//		if (level < artifactRefineConfig.getNeedLevel()) {
//			return ConfigTipsMgr.Artifact_735;//需要提升等级技能升级
//		}
//
//		boolean bool = Context.getItemService().enoughAndDeductItem(playerId,
//				artifactRefineConfig.getExpendMap(), NatureEnum.ARTIFACT_REFINE,
//				StringUtils.toLogString("configId", configId, "rflevel", rflevel));
//
//		if (!bool) {
//			return ConfigTipsMgr.Bag_204;//道具不足
//		}
//		//记录材料
//		artifact.setRefineLv(rflevel + 1);
//		artifact.recordUsedMaterialHM(artifactRefineConfig.getExpendMap());
//		artifact.update();
//		EventPublisher.publishEvent0(new ArtifactRefineEvent(artifact));
//		return 0;
//	}

//	/**
//	 * 处理升级
//	 * @param configId 神器配置id
//	 * @return 错误码
//	 */
//	private int doUpgrade(long playerId, int configId) {
//		Artifact artifact = getArtifact(playerId, configId);
//		if (!artifact.isActivite()) {
//			return ConfigTipsMgr.Artifact_739;//未激活
//		}
//		return onUpgrade(artifact, configId);
//	}
//
//	private int onUpgrade(Artifact artifact, int configId) {
//		int level =  artifact.getLevel();
//		//判断等级限制
//		ConfigArtifact artifactConfig = ConfigManager.get(ConfigArtifact.class,configId);
//		if (artifactConfig == null) {
//			return ConfigTipsMgr.Artifact_732;
//		}
//
//		if (level >= artifactConfig.getMaxLevel()) {
//			return ConfigTipsMgr.Artifact_733;//已经到最大等级不可升级
//		}
//		//判断升级消耗
//		ConfigArtifactLevelInfo artifactLevelConfig = ConfigArtifactLevelInfo.getConfig(configId, level);
//		if (artifactLevelConfig == null) {
//			return ConfigTipsMgr.Artifact_732;
//		}
//		if (artifactLevelConfig.getNextId() == 0) {//表示最高级
//			return ConfigTipsMgr.Artifact_733;//已经到最大等级不可升级
//		}
//		boolean bool = Context.getItemService().enoughAndDeductItem(artifact.getPlayerId(),
//				artifactLevelConfig.getNeedGoodsMap(), NatureEnum.ARTIFACT_UPGRADE,
//				StringUtils.toLogString("configId", configId, "level", level));
//
//		if (!bool) {
//			return ConfigTipsMgr.Bag_204;//道具不足
//		}
//		//记录材料
//		artifact.setExp(artifact.getExp() + 10); //TODO 需要策划验证, 目前每次升级默认加10.
//		if (artifact.getExp() >= artifactLevelConfig.getNeedExp()) {
//			artifact.setLevel(artifact.getLevel() + 1);
//		}
//		artifact.recordUsedMaterialHM(artifactLevelConfig.getNeedGoodsMap());
//		artifact.update();
//		//调用活动
//		Context.getSevenDayService().onActifactUpgrade(artifact.getPlayerId());
//		FunctionCondition.checkOpenFunction(FunctionCondition.ArtifactLevel,artifact.getPlayerId(),artifact.getLevel());
//
//		Context.getEventPublisher().publishEvent(ArtifactUpgradeEvent.create(artifact.getPlayerId(), configId, level));
//		return 0;
//	}
	
//	/**
//	* 请求领取神器任务
//	* @param playerId
//	* @param req
//	* @param ack
//	* @author Jeremy
//	*/
//	public int reqArtifactReceiveTask(long playerId, PBArtifact.ReqArtifactReceiveTask req, AckArtifactReceiveTaskResp ack){
//		int code = doTaskReward(playerId, req.getConfigId(), req.getTaskId());
//		if (code == 0) {
//			this.responseArtifactInfo(playerId);
//		}
//		return code;
//	}

//	/**
//	 * 领取任务奖励
//	 * 如果领完了所有奖励, 激活下一个神器
//	 * @param configId 神器配置id
//	 * @return 错误码
//	 */
//	private int doTaskReward(long playerId, int configId, int taskConfigId) {
//		Artifact artifact = getArtifact(playerId, configId);
//		ArtifactMissionHelper helper = artifact.getArtifactMissionHelper();
//		if (helper == null) {
//			return ConfigTipsMgr.Artifact_731;
//		}
//		EntityMission mission = helper.getMission(taskConfigId);
//		if (!mission.isActived()) {//未激活
//			return ConfigTipsMgr.Artifact_740;
//		}
//		ConfigArtifactTasks config = ConfigManager.get(ConfigArtifactTasks.class,taskConfigId);
//		if (config == null) {
//			return ConfigTipsMgr.Artifact_731;
//		}
//		//该状态,获取奖励,激活下一个神器
//		mission.setState(EntityMission.STATE_REWARDED);
//		boolean bool = artifact.checkCanActivite();
//		if (bool) {
//			checkUnlockNext(playerId, configId);//检测当前神器下一个是否可以解锁
//		}
//		artifact.update();
//		Context.getItemService().addNewItem(playerId, config.getRewardMap(), NatureEnum.ARTIFACT_REFINE_MISSION, StringUtils.toLogString("configId", configId, "taskConfigId", taskConfigId));
//		AckRewardsResp resp = AckRewardsResp.newInstance().addAllRewards(config.getRewardMap());
//		SendMessageUtil.sendResponse(playerId, resp);
//		return 0;
//	}

//	/**
//	 * 检测是否可以解锁下一个
//	 * @param configId 神器配置id
//	 * @return true:可以解锁, false:不可
//	 */
//	private boolean checkUnlockNext(long playerId, int configId) {
//		ConfigArtifact config = ConfigManager.get(ConfigArtifact.class,configId);
//		if (config.getNext() == 0)
//			return false;
//
//		Artifact artifact = getArtifact(playerId, configId);
//		artifact.setState(ConfigArtifact.ARTIFACT_STATE_NOACTIVITE);
//		artifact.update();
//		Context.getEventPublisher().publishEvent(ArtifactUnlockEvent.create(playerId, configId));
//		return true;
//	}
	
//	/**
//	* 请求圣印
//	* @param playerId
//	* @param req
//	* @param ack
//	* @author Jeremy
//	*/
//	public int reqArtifactHolySeal(long playerId, PBArtifact.ReqArtifactHolySeal req, AckArtifactHolySealResp ack){
//		int code = doHolySeal(playerId, req.getConfigId(), req.getNum());
//		if (code == 0) {
//			this.responseArtifactInfo(playerId);
//		}
//		return code;
//	}

//	/**
//	 * 圣印
//	 * @param configId 神器配置id
//	 * @return 错误码
//	 */
//	private int doHolySeal(long playerId, int configId, int costNumber) {
//		if (costNumber <= 0) {
//			return ConfigTipsMgr.Artifact_738;
//		}
//		Artifact artifact = getArtifact(playerId, configId);
//		if (!artifact.isActivite()) {
//			return ConfigTipsMgr.Artifact_739;//未激活
//		}
//		ConfigArtifactLevelInfo artifactLevelConfig = ConfigArtifactLevelInfo.getConfig(configId, artifact.getLevel());
//		if (artifactLevelConfig == null) {
//			return ConfigTipsMgr.Artifact_732;
//		}
//		int costStampStoneNum = artifact.costStampStoneNum();
//		if (costStampStoneNum >= artifactLevelConfig.getStampLimit()) {
//			return ConfigTipsMgr.Artifact_737;
//		}
//		int realNum = artifactLevelConfig.getStampLimit() - costStampStoneNum;
//		realNum = Math.min(realNum, costNumber);
//
//		Map<Integer, Integer> costMap = Maps.newHashMap();
//		for (Integer key : ConfigConstantPlus.artifactHolySealStampCost.keySet()) {
//			costMap.put(key, ConfigConstantPlus.artifactHolySealStampCost.get(key) * realNum);
//		}
//
//		//判断道具
//		boolean bool = Context.getItemService().enoughAndDeductItem(playerId,
//				costMap, NatureEnum.ARTIFACT_SKILLUP,
//				StringUtils.toLogString("configId", configId));
//
//		if (!bool) {
//			return ConfigTipsMgr.Bag_204;//道具不足
//		}
//		artifact.recordUsedMaterialHM(costMap);
//		artifact.setHolySealLv(artifact.getHolySealLv() + realNum);
//		artifact.update();
//		EventPublisher.publishEvent0(new ArtifactHolySealEvent(artifact));
//		return 0;
//	}

}