package com.cat.server.game.module.battle.event;

import com.cat.server.core.event.BaseEvent;
import com.cat.server.game.module.battle.domain.Battle;

/**
 * 回合结束事件
 */
public class RoundFinishEvent extends BaseEvent {

    private Battle battle;

    public RoundFinishEvent(Battle battle) {
        this.battle = battle;
    }

    public Battle getBattle() {
        return battle;
    }

}
