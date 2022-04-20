package com.cat.server.game.module.pet.attr;

import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.module.pet.domain.Pet;

/**
 * 宠物属性-宠物属性点
 * @date 2022年4月9日上午9:04:03
 */
public class PetAttrPointNode extends AbstractPetAttrNode{

	public PetAttrPointNode(Pet pet) {
		super(pet);
	}
	
	 /**
     * 每次升级后宠物增加2点属性点, 玩家自由分配
     */
    @Override
    protected AttributeDictionary calculateAttrDic() {
        AttributeDictionary attrDic = new AttributeDictionary();
        attrDic.addAttr(pet.getUsedAttrPointMap());
        return attrDic;
    }

}
