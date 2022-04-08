package com.cat.server.game.module.pet.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
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
	private LinkedHashMap<Integer, Integer> skillMap;
	
	/**
	 * 宠物装备列表
	 */
	private List<Long> equipList;
	
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
	
	public Map<Integer, Integer> getSkillMap() {
		return skillMap;
	}
	
	/**
	 * 装备列表
	 * @return
	 */
	public List<Long> getEquipList() {
		return equipList;
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
		//TODO something...
		return builder;
	}
	
}
