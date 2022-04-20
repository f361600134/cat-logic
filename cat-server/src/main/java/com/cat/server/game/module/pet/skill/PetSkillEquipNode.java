package com.cat.server.game.module.pet.skill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.server.game.helper.skill.SkillDictionary;
import com.cat.server.game.module.pet.domain.Pet;

/**
 * 宠物装备携带的的技能增益
 * @author Jeremy
 */
public class PetSkillEquipNode extends AbstractPetSkillNode{

	public PetSkillEquipNode(Pet pet) {
		super(pet);
	}
	
	 /**
     * 装备携带的技能增益计算
     * 1. 获取到当前宠物装备
     * 2. 获取到装备技能增益
     * 3. 判断当前宠物是否有领悟此技能, 如果有, 则加入技能字典
     */
    @Override
    protected SkillDictionary calculateDic() {
    	SkillDictionary attrDic = new SkillDictionary();
    	List<Long> equipList = pet.getEquipList();
    	for (Long equipId : equipList) {
    		//获取到装备
    		//获取到装备技能key:技能id,value:技能等级
    		Map<Integer, Integer> skillMap = new HashMap<>();
    		skillMap.forEach((skillId, skillLv)->{
    			if(pet.getSkillMap().containsKey(skillId)) {
    				attrDic.addSkill(skillId, skillLv);
    			}
    		});
		}
        return attrDic;
    }


}
