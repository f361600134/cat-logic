package com.cat.server.game.module.battle.event;

import com.cat.server.core.event.BaseEvent;
import com.cat.server.game.module.battle.domain.BattleSkill;

/**
 * 施放技能事件
 *
 * @author Klass
 * @date 2020/9/17 10:19
 */
public class BeforeCastSkillEvent extends BaseEvent {

    private BattleSkill skill;

    public BeforeCastSkillEvent(BattleSkill skill) {
        this.skill = skill;
    }

    public BattleSkill getSkill() {
        return skill;
    }

}
