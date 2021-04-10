package com.cat.server.game.module.hero.attr;

import com.cat.server.game.module.attribute.domain.AbstractAttributeNode;
import com.cat.server.game.module.hero.domain.Hero;


public abstract class AbstractHeroAttrNode extends AbstractAttributeNode {

    protected final Hero hero;

    public AbstractHeroAttrNode(Hero hero) {
        super();
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }

}
