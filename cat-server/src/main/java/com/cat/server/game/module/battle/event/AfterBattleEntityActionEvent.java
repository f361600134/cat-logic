package com.cat.server.game.module.battle.event;

import com.cat.server.core.event.BaseEvent;
import com.cat.server.game.module.battle.domain.BattleEntity;

/**
 * 战斗实体行动后事件
 *
 * @author Klass
 * @date 2020/9/10 13:58
 */
public class AfterBattleEntityActionEvent extends BaseEvent {

    private BattleEntity battleEntity;

    public AfterBattleEntityActionEvent(BattleEntity battleEntity) {
        this.battleEntity = battleEntity;
    }

    public BattleEntity getBattleEntity() {
        return battleEntity;
    }

}
