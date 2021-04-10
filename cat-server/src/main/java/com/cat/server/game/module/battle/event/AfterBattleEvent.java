package com.cat.server.game.module.battle.event;

import com.cat.server.core.event.BaseEvent;
import com.cat.server.game.module.battle.domain.Battle;

/**
 * @author Klass
 * @date 2020/11/2 16:49
 */
public class AfterBattleEvent extends BaseEvent {

    private Battle battle;

    public AfterBattleEvent(Battle battle) {
        this.battle = battle;
    }

    public Battle getBattle() {
        return battle;
    }

}
