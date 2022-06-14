package com.cat.server.game.module.equip.attr;

import java.util.Map;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigEquip;
import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.helper.attribute.AttributeUtils;
import com.cat.server.game.module.equip.domain.Equip;

/**
 * 装备打孔隐藏属性<br>
 * 
 * @auth Jeremy
 * @date 2022年4月9日上午9:04:03
 */
public class EquipAttrHoleHiddenNode extends AbstractEquipAttrNode {

	public EquipAttrHoleHiddenNode(Equip equip) {
		super(equip);
	}

	/**
	 * 根据隐藏等级, 获取隐藏属性
	 */
	@Override
	protected AttributeDictionary calculateAttrDic() {
		ConfigEquip config = ConfigManager.getInstance().getConfig(ConfigEquip.class, equip.getConfigId());
		if (config == null) {
			logger.info("calculateAttrDic error, config is null, name:{}", this.getName());
			return AttributeUtils.EMPTY;
		}
		final int holeHiddenLv = equip.getHoleHiddenLevel();
		if (holeHiddenLv == 0) {
			// 如果是0级
			return AttributeUtils.EMPTY;
		}
		// 下标=隐藏等级-1
		Map<Integer, Integer> attr = config.getHoleHiddenAttr(holeHiddenLv - 1);
		if (attr == null) {
			return AttributeUtils.EMPTY;
		}
		AttributeDictionary dic = new AttributeDictionary(attr);
		return dic;
	}

}
