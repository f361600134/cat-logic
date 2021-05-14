package com.cat.server.game.module.team.assist;

import com.cat.server.game.module.family.assist.FamilyPosition;

/**
 * 组队职位
 * @author  Jeremy
 */
public enum TeamPosition {

    /**
     * 队长
     */
    LEADER(1),
    /**
     * 普通成员
     */
    NOMAL(2),
    ;

    private final int value;

    TeamPosition(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TeamPosition getPosition(int type) {
        for (TeamPosition position : values()) {
            if (position.getValue() == type) {
                return position;
            }
        }
        return null;
    }


}
