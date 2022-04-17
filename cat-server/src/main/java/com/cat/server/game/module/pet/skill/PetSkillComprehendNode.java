package com.cat.server.game.module.pet.skill;

import com.cat.server.game.module.pet.domain.Pet;
import com.cat.server.game.module.skill.domain.SkillDictionary;

/**
 * 宠物领悟的技能
 * @author Jeremy
 */
public class PetSkillComprehendNode extends AbstractPetSkillNode{

	public PetSkillComprehendNode(Pet pet) {
		super(pet);
	}
	
	 /**
     * 领悟技能计算
     * 技能等级=当前宠物等级-技能领悟时的宠物等级 + 1
     */
    @Override
    protected SkillDictionary calculateDic() {
    	SkillDictionary attrDic = new SkillDictionary();
        final int level = pet.getLevel();
        for (int skillId :  pet.getSkillMap().keySet()) {
        	int comprehendLv = pet.getSkillMap().get(skillId);
        	int skillLv = level - comprehendLv + 1;
        	attrDic.addSkill(skillLv, skillLv);
		}
        return attrDic;
    }


}
