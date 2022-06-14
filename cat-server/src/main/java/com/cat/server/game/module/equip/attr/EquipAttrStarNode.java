package com.cat.server.game.module.equip.attr;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigEquip;
import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.helper.attribute.AttributeUtils;
import com.cat.server.game.module.equip.domain.Equip;

/**
 * 装备加工属性
 * @auth Jeremy
 * @date 2022年4月9日上午9:04:03
 */
public class EquipAttrStarNode extends AbstractEquipAttrNode{

	public EquipAttrStarNode(Equip equip) {
		super(equip);
	}
	
	 /**
     * 计算加工属性,每次加工提供增加10%(万分比)的基础属性<br>
     */
    @Override
    protected AttributeDictionary calculateAttrDic() {
        ConfigEquip config = ConfigManager.getInstance().getConfig(ConfigEquip.class, equip.getConfigId());
        if (config == null) {
        	logger.info("calculateAttrDic error, config is null, name:{}", this.getName());
			return AttributeUtils.EMPTY;
		}
        final int star = equip.getStar();
        if (star == 0) {
        	//如果是0级
			return AttributeUtils.EMPTY;
		}
        double addedAttr = star * 0.1d;
        AttributeDictionary dic = new AttributeDictionary(config.getAttribute());
        dic.rate(addedAttr);
        return dic;
    }

}
