package com.cat.server.game.helper.badword.impl;

/**
 * 英文敏感词检查策略<br>
 * 不做跳字检查 需要全文匹配敏感词才会判断为敏感词
 */
public class BadWordStrategy_EN extends AbstractBadWordStrategy {

    private static BadWordStrategy_EN strategy;

    public static BadWordStrategy_EN getInstance() {
        if (strategy == null) {
            synchronized (BadWordStrategy_EN.class) {
                if (strategy == null) {
                    strategy = new BadWordStrategy_EN();
                }
            }
        }
        return strategy;
    }

    private BadWordStrategy_EN() {

    }

    @Override
    protected boolean isSkip(char curWord, char firstWord, int skipLength) {
        return false;
    }

}