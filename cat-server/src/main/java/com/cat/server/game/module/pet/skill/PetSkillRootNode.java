package com.cat.server.game.module.pet.skill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cat.server.game.data.proto.PBItem.PBPairInfo;
import com.cat.server.game.module.item.proto.PBPairInfoBuilder;
import com.cat.server.game.module.pet.domain.Pet;
import com.cat.server.game.module.skill.domain.ISkillNode;
import com.cat.server.game.module.skill.domain.SkillDictionary;

/**
 * 宠物技能根节点
 * @author Administrator
 *
 */
public class PetSkillRootNode extends AbstractPetSkillNode {
	
	/**
	 * 装备技能节点
	 */
	private PetSkillEquipNode equipNode;
	/**
	 * 领悟技能节点
	 */
	private PetSkillComprehendNode comprehendNode;

	public PetSkillRootNode(Pet pet) {
		super(pet);
		this.equipNode = new PetSkillEquipNode(pet);
		this.addChild(equipNode);
		this.comprehendNode = new PetSkillComprehendNode(pet);
		this.addChild(comprehendNode);
	}
	
	@Override
    public boolean isLeaf() {
        return false;
    }
	
	@Override
    public String getName() {
        return "pet" + pet.getUniqueId();
    }
	
	@Override
    protected SkillDictionary calculateDic() {
		SkillDictionary dictionary = new SkillDictionary();
        for (ISkillNode node : childs) {
            dictionary.addSkill(node.getDic());
        }
        return dictionary;
    }
	
	/**
	 * 技能信息转协议对象
	 * @return
	 */
	public Collection<PBPairInfo> toProto(){
		List<PBPairInfo> ret = new ArrayList<>();
		this.getDic().getDictionary().forEach((skillId, skillLv)->{
			PBPairInfoBuilder builder = PBPairInfoBuilder.newInstance();
			builder.setConfigId(skillId);
			builder.setValue(skillLv);
			ret.add(builder.build());
		});
		return ret;
	}
	
}
