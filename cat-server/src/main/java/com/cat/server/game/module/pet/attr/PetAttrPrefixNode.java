package com.cat.server.game.module.pet.attr;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigPetPrefix;
import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.helper.attribute.AttributeUtils;
import com.cat.server.game.module.pet.domain.Pet;

/**
 * 宠物属性资质节点
 * @auth Jeremy
 * @date 2022年4月9日上午9:04:03
 */
public class PetAttrPrefixNode extends AbstractPetAttrNode{

	public PetAttrPrefixNode(Pet pet) {
		super(pet);
	}
	
	 /**
     * 计算前缀增加的属性, 从配置中获取 * level
     */
    @Override
    protected AttributeDictionary calculateAttrDic() {
        ConfigPetPrefix config = ConfigManager.getInstance().getConfig(ConfigPetPrefix.class, pet.getPrefixId());
        if (config == null) {
			return AttributeUtils.EMPTY;
		}
        //资质节点计算
        AttributeDictionary attrDic = new AttributeDictionary();
        attrDic.addAttr(config.getAddedAttribute());
        attrDic.rate(pet.getLevel());
        return attrDic;
    }

}
