package com.cat.server.game.module.hero.attr;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.base.ConfigHeroLeveBase;
import com.cat.server.game.module.attribute.domain.AttributeDictionary;
import com.cat.server.game.module.hero.domain.Hero;

/**
 * 	武将等级属性节点
 * @author Administrator
 *
 */
public class HeroLevelNode extends AbstractHeroAttrNode {

    public HeroLevelNode(Hero hero) {
        super(hero);
    }

    @Override
    public String getName() {
        return "level";
    }

    /**
     * 	等级属性
     */
    @Override
    protected AttributeDictionary calculateAttrDic() {
        AttributeDictionary attrDic = new AttributeDictionary();
        int level = hero.getLevel();
        ConfigHeroLeveBase config = ConfigManager.getInstance().getConfig(ConfigHeroLeveBase.class, hero.getConfigId());
        AttributeDictionary dictionary = config.initBaseAttr();
        dictionary.getDictionary().forEach((key, value)->{
        	attrDic.addAttr(key, value * level);
        });
        return attrDic;
    }

}
