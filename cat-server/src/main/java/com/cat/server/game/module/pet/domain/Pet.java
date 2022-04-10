package com.cat.server.game.module.pet.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.data.config.local.ConfigPetPrefix;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
import com.cat.server.game.module.pet.attr.PetAttrRootNode;
import com.cat.server.game.module.pet.define.PetConstant;
import com.cat.server.game.module.pet.proto.PBPetDtoBuilder;
import com.cat.server.game.module.pet.skill.PetSkillRootNode;
import com.cat.server.game.module.resource.IResource; 

/**
* @author Jeremy
*/
@PO(name = "pet")
public class Pet extends PetPo implements IResource{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9007447649101185698L;
	
	/**
	 * key: 技能id<br>
	 * value: 领悟技能时的宠物等级 <br>
	 * 顺序存储
	 */
	@Column(PROP_SKILLSTR)
	private LinkedHashMap<Integer, Short> skillMap;
	
	/**
	 * 等级的属性点使用记录
	 * key: 属性id
	 * value: 屬性点数
	 */
	@Column(PROP_USEDATTRPOINTSTR)
	private Map<Integer, Integer> usedAttrPointMap = new HashMap<>();
	
	/**
	 * 前缀携带的属性点使用记录
	 * key: 属性id
	 * value: 屬性点数
	 */
	@Column(PROP_USEDATTRPREFIXPOINTSTR)
	private Map<Integer, Integer> usedPrefixAttrPointMap = new HashMap<>();
	
	/**
	 *  宠物鉴定的资质
	 * key: 鉴定出来的资质id
	 */
	@Column(PROP_APTITUDESTR)
	private List<Integer> aptitudeAttrList = new ArrayList<>();
	
	/**
	 * 宠物装备列表
	 */
	private List<Long> equipList = new ArrayList<Long>();
	
	/**
	 * 宠物技能根节点
	 */
	private transient final PetAttrRootNode attrRootNode = new PetAttrRootNode(this);
	
	/**
	 * 宠物技能根节点
	 */
	private transient final PetSkillRootNode skillRootNode = new PetSkillRootNode(this);
	
	public Pet() {

	}
	
	public Pet(long playerId) {
		this.playerId = playerId;
	}
	
	public Pet(long playerId, long uniqueId, int configId) {
		this.playerId = playerId;
		this.uniqueId = uniqueId;
		this.configId = configId;
	}
	
	public Map<Integer, Short> getSkillMap() {
		return skillMap;
	}
	
	/**
	 * 装备列表
	 * @return
	 */
	public List<Long> getEquipList() {
		return equipList;
	}
	
	/**
	 * 获取鉴定的属性资质列表
	 * @return List<Integer>  
	 * @date 2022年4月10日下午3:46:17
	 */
	public List<Integer> getAptitudeAttrList() {
		return aptitudeAttrList;
	}
	
	/**
	 * 自定义属性点使用
	 * @return  
	 * @return Map<Integer,Integer>  
	 * @date 2022年4月10日下午3:58:49
	 */
	public Map<Integer, Integer> getUsedAttrPointMap() {
		return usedAttrPointMap;
	}
	
	/**
	 * 自定义前缀属性点使用
	 * @return  
	 * @return Map<Integer,Integer>  
	 * @date 2022年4月10日下午3:59:03
	 */
	public Map<Integer, Integer> getUsedPrefixAttrPointMap() {
		return usedPrefixAttrPointMap;
	}
	
	/***
	 * 宠物属性根节点
	 * @return  
	 * @return PetAttrRootNode  
	 * @date 2022年4月10日下午9:26:37
	 */
	public PetAttrRootNode getAttrRootNode() {
		return attrRootNode;
	}
	
	/**
	 * 宠物技能根节点
	 * @return  
	 * @return PetSkillRootNode  
	 * @date 2022年4月10日下午9:17:55
	 */
	public PetSkillRootNode getSkillRootNode() {
		return skillRootNode;
	}
	
	/**
	 * 获取剩余技能点数
	 * @return int  
	 * @date 2022年4月9日上午10:32:17
	 */
	public int getRemainAtrrPoint() {
		int basePoint = this.getRemainBaseAttrPoint();
		int prefixPoint = this.getRemainPrefixAttrPoint();
		return basePoint + prefixPoint;
	}
	
	/**
	 * 获取剩余当前宠物总属性点<br>
	 * 每升一级携带2点属性点, 所以升级增加的属性点为: 等级*2.<br>
	 * 计算前缀增加的总属性点,当前等级*前缀增益属性点<br>
	 * 剩余点数=总点数-已使用点数 
	 * @return int  
	 * @date 2022年4月9日上午10:02:06
	 */
	private int getRemainBaseAttrPoint() {
		//总点数
		int basePoint = this.getLevel() * PetConstant.ATTR_POINT_ADDED;
		//已用点数
		int usedCnt = usedAttrPointMap.values().stream().reduce(0, Integer::sum);
		//剩余点数
		return basePoint - usedCnt;
	}
	
	/**
	 * 获取前缀增益属性点<br>
	 * 前缀增益总点数 = 前缀单级增益 * 等级<br>
	 * 剩余点数=总点数-已使用点数
	 * @return int  
	 * @date 2022年4月9日上午10:10:42
	 */
	private int getRemainPrefixAttrPoint() {
		ConfigPetPrefix config = ConfigManager.getInstance().getConfig(ConfigPetPrefix.class, this.getPrefixId());
		int prefixPoint = config.getAttributePoint() * this.level;
		//前缀技能点数量 = 前缀等级总技能点数 - 已使用前缀技能点数量;
		int usedCnt = usedPrefixAttrPointMap.values().stream().reduce(0, Integer::sum);
		return prefixPoint - usedCnt;
	}
	
	/**
	 * 获取技能等级<br>
	 * 当前技能等级 = 当前宠物等级 - 领悟技能时的等级
	 * @return  
	 * @return int  
	 * @date 2022年4月10日下午7:11:17
	 */
	public int getSkillLevel(int skillId) {
		short level = this.skillMap.getOrDefault(skillId, (short)0);
		if (level == 0) {
			return 0;
		}
		return this.getLevel() - level;
	}
	
	@Override
	public int getCount() {
		return 1;
	}
	
	/**
	 * 创建一个宠物对象
	 * @note 宠物获取时未激活状态,玩家手动激活,初始化宠物数据
	 * @param playerId 玩家id
	 * @param configId 宠物配置
	 * @return pet 未激活的宠物对象,可视为:蛋
	 */
	public static Pet create(long playerId, int configId) {
		SnowflakeGenerator generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
		long id = generator.nextId();
		Pet pet = new Pet(playerId, id, configId);
		pet.save();
		return pet;
	}
	
	/**
	 * 宠物对象转协议对象
	 */
	public PBPetDtoBuilder toProto() {
		PBPetDtoBuilder builder = PBPetDtoBuilder.newInstance();
		builder.setUniqueId(this.getUniqueId());
		builder.setConfigId(this.getConfigId());
		builder.setActive(this.getActive());
		if (!this.getActive()) {
			//如果没有激活,不组装以下信息
			return builder;
		}
		builder.setExp(this.getExp());
		builder.setLevel(this.getLevel());
		builder.setGender(this.getGender());
		builder.setNickName(this.getNickname());
		builder.setReproductiveNumber(this.getReproductive());
		builder.setPrefixId(this.getPrefixId());
		builder.addAllSkills(skillRootNode.toProto());
		builder.setAttrPoint(this.getRemainAtrrPoint());
		//TODO something...
		return builder;
	}
	
}
