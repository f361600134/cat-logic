package com.cat.server.game.module.pet.attr;

import java.util.List;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigPetPropertyAptitude;
import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.helper.attribute.AttributeUtils;
import com.cat.server.game.module.pet.domain.Pet;

/**
 * 宠物属性资质节点
 * @auth Jeremy
 * @date 2022年4月9日上午9:04:03
 */
public class PetAttrAptitudeNode extends AbstractPetAttrNode{

	public PetAttrAptitudeNode(Pet pet) {
		super(pet);
	}
	
	 /**
     * 鉴定后随机生成, 从宠物上获取
     */
    @Override
    protected AttributeDictionary calculateAttrDic() {
    	List<Integer> aptitudes = pet.getAptitudeAttrList();
    	if (aptitudes.isEmpty()) {
			return AttributeUtils.EMPTY;
		}
    	AttributeDictionary dic = new AttributeDictionary();
    	for (Integer configId : aptitudes) {
    		ConfigPetPropertyAptitude config = ConfigManager.getInstance().getConfig(ConfigPetPropertyAptitude.class, configId);
    		dic.addAttr(config.getType(), config.getValue());
		}
        return dic;
    }

}
