package com.cat.server.game.module.hero.attr;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.base.ConfigHeroInfoBase;
import com.cat.server.game.module.attribute.domain.AttributeDictionary;
import com.cat.server.game.module.hero.domain.Hero;

/**
 * 	武将基础属性节点
 * @author Administrator
 *
 */
public class HeroBaseNode extends AbstractHeroAttrNode {

    public HeroBaseNode(Hero hero) {
        super(hero);
    }

    @Override
    public String getName() {
        return "Base";
    }

    /**
     * 	基础属性
     */
    @Override
    protected AttributeDictionary calculateAttrDic() {
        AttributeDictionary attrDic = new AttributeDictionary();
        ConfigHeroInfoBase config = ConfigManager.getInstance().getConfig(ConfigHeroInfoBase.class, hero.getConfigId());
        config.getAttrsMap().forEach((key, value) -> {
        	 attrDic.addAttr(key, value);
        });
        return attrDic;
    }

}
