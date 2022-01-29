package com.cat.server.game.module.hero.attr;

import com.cat.server.game.module.attribute.domain.AbstractAttributeNode;
import com.cat.server.game.module.hero.domain.Hero;

public abstract class AbstractHeroAttrNode extends AbstractAttributeNode {

    protected final Hero hero;

    public AbstractHeroAttrNode(Hero hero) {
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }
    
    /**
     * 玩家id-武将唯一id-武将配置id<br>
     * eg:xxxx-202201291001-202201292001-40001
     */
    @Override
    public String getName() {
    	String name = String.valueOf(hero.getPlayerId())
		.concat("-").concat(String.valueOf(hero.getUniqueId()))
		.concat("-" ).concat(String.valueOf(hero.getConfigId()));
    	return this.getClass().getSimpleName().concat("-").concat(name);
    }

}
