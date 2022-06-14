package com.cat.server.game.module.equip.attr;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigEquip;
import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.helper.attribute.AttributeUtils;
import com.cat.server.game.module.equip.domain.Equip;

/**
 * 装备基础属性节点<br>
 * 增益百分比,都是基于装备的基础属性
 * @auth Jeremy
 * @date 2022年4月9日上午9:04:03
 */
public class EquipAttrBaseNode extends AbstractEquipAttrNode{

	public EquipAttrBaseNode(Equip equip) {
		super(equip);
	}
	
	 /**
     * 	基础属性
     */
    @Override
    protected AttributeDictionary calculateAttrDic() {
        ConfigEquip config = ConfigManager.getInstance().getConfig(ConfigEquip.class, equip.getConfigId());
        if (config == null) {
        	logger.info("calculateAttrDic error, config is null, name:{}", this.getName());
			return AttributeUtils.EMPTY;
		}
        AttributeDictionary attrDic = new AttributeDictionary();
        //获取宠物属性节点属性 * level
        attrDic.addAttr(config.getAttribute());
        return attrDic;
    }

}
