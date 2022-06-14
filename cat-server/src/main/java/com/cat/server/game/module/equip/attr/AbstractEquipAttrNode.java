package com.cat.server.game.module.equip.attr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.helper.attribute.AbstractAttributeNode;
import com.cat.server.game.module.equip.domain.Equip;

public abstract class AbstractEquipAttrNode extends AbstractAttributeNode {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final Equip equip;

    public AbstractEquipAttrNode(Equip equip) {
        this.equip = equip;
    }

    public Equip getEquip() {
        return equip;
    }
    
    /**
     * 玩家id-唯一id-配置id<br>
     * eg:xxxx-202201291001-202201292001-40001
     */
    @Override
    public String getName() {
    	String name = String.valueOf(equip.getPlayerId())
		.concat("-").concat(String.valueOf(equip.getUniqueId()))
		.concat("-" ).concat(String.valueOf(equip.getConfigId()));
    	return this.getClass().getSimpleName().concat("-").concat(name);
    }

}
