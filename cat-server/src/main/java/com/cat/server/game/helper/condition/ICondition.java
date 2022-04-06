package com.cat.server.game.helper.condition;

@FunctionalInterface
public interface ICondition {

    /**
     * 	验证指定条件是否成立
     */
    boolean accept(long playerId);
}
