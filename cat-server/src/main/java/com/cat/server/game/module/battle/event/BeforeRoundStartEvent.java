package com.cat.server.game.module.battle.event;

import com.cat.server.core.event.BaseEvent;
import com.cat.server.game.module.battle.domain.Battle;

/**
 * 回合开始前事件
 */
public class BeforeRoundStartEvent extends BaseEvent {

    private Battle battle;

    public BeforeRoundStartEvent(Battle battle) {
        this.battle = battle;
    }

    public Battle getBattle() {
        return battle;
    }

}
