package com.cat.server.game.module.equip.attr;

import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.module.equip.domain.Equip;

/**
 * 装备镶嵌卡牌提供的属性<br>
 * @auth Jeremy
 * @date 2022年4月9日上午9:04:03
 */
public class EquipAttrCardNode extends AbstractEquipAttrNode{

	public EquipAttrCardNode(Equip equip) {
		super(equip);
	}
	
	 /**
     * 根据隐藏等级, 获取隐藏属性
     */
    @Override
    protected AttributeDictionary calculateAttrDic() {
        return equip.getCatdAttrDic();
    }

}
