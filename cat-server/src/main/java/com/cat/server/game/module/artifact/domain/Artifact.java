
package com.cat.server.game.module.artifact.domain;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.helper.attribute.IAttributeEntity;
import com.cat.server.game.helper.attribute.IAttributeNode;
import com.cat.server.game.module.artifact.attr.ArtifactAttrNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PO(name = "artifact")
public class Artifact extends ArtifactPo implements IPersistence , IAttributeEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3098985354070239423L;
	
	private static final Logger log = LoggerFactory.getLogger(Artifact.class);
	
//	@Column(PROP_MISSIONSTR)
//	private MissionTypeData<ArtifactMissionType> missionData;
	
	/**
	 * 武将属性根节点
	 */
	private transient ArtifactAttrNode artifactAttrNode;
	
	public Artifact() {
//		this.missionData = new MissionTypeData<>();
		artifactAttrNode = new ArtifactAttrNode(this);
	}
	
//	public MissionTypeData<ArtifactMissionType> getMissionData() {
//		return missionData;
//	}
	
	/**
	 * 生成一个神器
	 * @param playerId
	 * @param configId 神器配置id
	 * @return
	 */
	public static Artifact create(long playerId, int configId) {
		Artifact artifact = new Artifact();
		artifact.setPlayerId(playerId);
		artifact.setConfigId(configId);
		artifact.setLevel(1);
		artifact.setExp(0);
		artifact.setRefineLv(1);
		artifact.setSkillLv(1);
		artifact.setState(0);
		artifact.setHolySealLv(1);
		artifact.setSkinId(0);
//		artifact.init(playerId);
		artifact.save();
		return artifact;
	}
	
	/**
	 * 神器重铸
	 */
	public void onRecast() {
		this.setLevel(1);
		this.setExp(0);
		this.setRefineLv(1);
		this.setSkillLv(1);
		this.setHolySealLv(1);
	}
	
	@Override
	public IAttributeNode getAttributeNode() {
		return artifactAttrNode;
	}
	
	////////////////////////////////////////////////
	
	
	public int getSkillId() {
//		ConfigArtifactSkill config = ConfigArtifactSkill.getConfig(configId, skillLv);
//		return config.getSkillID();
		return 0;
	}

	public static Artifact get(long id) {
//		return Artifact.load(Artifact.class, id);
		return null;
	}

	public static Artifact get(long playerId, int configId) {
//		List<Artifact> artifacts = Artifact.load(Artifact.class, "playerId", playerId);
//		for (Artifact artifact : artifacts) {
//			if (artifact.getConfigId() == configId) {
//				return artifact;
//			}
//		}
		return null;
	}


	//////////////非持久化数据//////////

//	//基础属性
//	private Map<Integer, Integer> baseAttrs;
//	
//	//技能加成属性
//	private Map<Integer, Integer> skillAttrs;
	
//	public Artifact() {
//		missions = Lists.newArrayList();
//		usedMaterialHM = Maps.newHashMap();
//		this.artifactMissionHelper = new ArtifactMissionHelper();
//	}
	
//	/**
//	 * 获取任务列表
//	 * @return
//	 */
//	public List<Integer> getMissions() {
//		return missions;
//	}
	
//	public Map<Integer, Integer> getUsedMaterialHM() {
//		return usedMaterialHM;
//	}
	
//	public ArtifactMissionHelper getArtifactMissionHelper() {
//		return artifactMissionHelper;
//	}
	
//	/**
//	 * 记录全部的材料
//	 * @param useMaterialMap
//	 */
//	public void recordUsedMaterialHM(Map<Integer, Integer> useMaterialMap) {
//		CollectionUtil.mergeToMap(useMaterialMap, usedMaterialHM);
//	}
	
//	public PBArtifact.PBArtifactInfo toProto() {
//		PBArtifactInfoBuilder builder = PBArtifactInfoBuilder.newInstance();
//		builder.setConfigId(getConfigId());
//		builder.setLevel(getLevel());
//		builder.setExp(getExp());
//		builder.setRefineLv(getRefineLv());
//		builder.setSkillLv(getSkillLv());
//		builder.setState(getState());
//		builder.setSkinId(getSkinId());
//		builder.setUseStampStoneNum(costStampStoneNum());
//		//任务列表
//		builder.addAllMissions(artifactMissionHelper.toProto());
//		return builder.build();
//	}
	
//	/**
//	 * 初始化任务列表,每个神器根据自己的配置初始化任务
//	 */
//	public void init(long playerId) {
//		Map<Integer, EntityMission> maps = Maps.newConcurrentMap();
//		ConfigArtifact config = ConfigManager.get(ConfigArtifact.class,getConfigId());
//		for (Integer missionId : config.getMissionIds()) {
//			ConfigArtifactTasks configTask = ConfigManager.get(ConfigArtifactTasks.class, missionId);
//			EntityMission entityMission = new EntityMission(missionId, EntityMission.STATE_NONE, 0,
//					configTask.getCompleteType(), configTask.getCompleteCondition(), configTask.getCompleteValue());
//			maps.put(missionId, entityMission);
//		}
//		artifactMissionHelper.init(playerId, maps);
//	}
	
//	/**
//	 * 获得此神器总属性
//	 * @return
//	 */
//	public Map<Integer, Integer> getTotalAttrs(){
//		Map<Integer, Integer> attrs = Maps.newConcurrentMap();
//		if (!isActivite()) {//未解锁
//			return attrs;
//		}
//		//计算基础属性
//		ConfigArtifactLevelInfo levelConfig = ConfigArtifactLevelInfo.getConfig(getConfigId(), getLevel());
//		if (levelConfig == null) {
//			log.info("getTotalAttrs error, configId:{}, level:{}", getConfigId(), getLevel());
//			return attrs;
//		}
//		CollectionUtil.mergeMap(levelConfig.getAdditionMap(), attrs);
//		
//		//计算技能加成属性
//		ConfigArtifactSkill skillConfig = ConfigArtifactSkill.getConfig(getConfigId(), getSkillLv());
//		if (skillConfig == null) {
//			log.info("getTotalAttrs error, configId:{}, level:{}", getConfigId(), getLevel());
//			return attrs;
//		}
//		CollectionUtil.mergeMap(skillConfig.getSkillAttrMap(), attrs);
//		
//		//计算刻印石属性
//		int costStoneNum = costStampStoneNum();
//		Map<Integer, Integer> stampStoneAttr = Maps.newConcurrentMap();
//		for (Integer key : ConfigConstantPlus.artifactStampAttribute.keySet()) {
//			stampStoneAttr.put(key, ConfigConstantPlus.artifactStampAttribute.get(key) * costStoneNum);
//		}
//		CollectionUtil.mergeMap(stampStoneAttr, attrs);
//		return attrs;
//	}
	
//	/**
//	 * 获取圣印消耗道具数量
//	 * @return
//	 */
//	public int costStampStoneNum() {
//		Iterator<Integer> iter = ConfigConstantPlus.artifactHolySealStampCost.keySet().iterator();
//		int stoneId = iter.hasNext() ? iter.next() : 0;
//		return getUsedMaterialHM().getOrDefault(stoneId, 0);
//	}
	
//	/**
//	 * 是否锁住状态
//	 * @return true 是, false 否
//	 * @return
//	 */
//	public boolean isLock() {
//		return getState() == ConfigArtifact.ARTIFACT_STATE_LOCK;
//	}
//	
//	/**
//	 * 是否解锁状态
//	 * @return true 是, false 否
//	 * @return
//	 */
//	public boolean isUnlock() {
//		return getState() == ConfigArtifact.ARTIFACT_STATE_NOACTIVITE;
//	}
//	
//	/**
//	 * 是否激活
//	 * @return true 是, false 否
//	 */
//	public boolean isActivite() {
//		return getState() == ConfigArtifact.ARTIFACT_STATE_ACTIVITE;
//	}
	
//	/**
//	 * 当任务完成
//	 * @return
//	 */
//	public void onFinishMission(int configId) {
//		this.missions.add(configId);
//	}
	
	/**
	 * 检测是否可以激活神器
	 */
	public boolean checkCanActivite() {
//		boolean bool = artifactMissionHelper.isAllReward();
//		if (bool) {//如果可以激活
//			//神器改变为激活状态 
//			setState(ConfigArtifact.ARTIFACT_STATE_ACTIVITE);
//		}
//		return bool;
		return false;
	}

}
