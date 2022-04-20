package com.cat.server.game.module.pet.attr;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigPetBase;
import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.helper.attribute.AttributeUtils;
import com.cat.server.game.module.pet.domain.Pet;

/**
 * 宠物基础属性节点
 * @auth Jeremy
 * @date 2022年4月9日上午9:04:03
 */
public class PetAttrBaseNode extends AbstractPetAttrNode{

	public PetAttrBaseNode(Pet pet) {
		super(pet);
	}
	
	 /**
     * 	基础属性*等级
     */
    @Override
    protected AttributeDictionary calculateAttrDic() {
        ConfigPetBase configPet = ConfigManager.getInstance().getConfig(ConfigPetBase.class, pet.getConfigId());
        if (configPet == null) {
        	logger.info("calculateAttrDic error, config is null, name:{}", this.getName());
			return AttributeUtils.EMPTY;
		}
        AttributeDictionary attrDic = new AttributeDictionary();
        //获取宠物属性节点属性 * level
        attrDic.addAttr(configPet.getAttribute());
        attrDic.rate(pet.getLevel());
        return attrDic;
    }

}
