package com.cat.server.game.helper.badword.impl;

import com.cat.server.game.helper.badword.BadWordStrategy;

/**
 * 中文环境的敏感词检查策略<br>
 * 可跳过不同类型的字符 或 跳过设定长度的同类型字符
 */
public class BadWordStrategy_CN extends AbstractBadWordStrategy {

    private static BadWordStrategy_CN strategy;

    /**
     * 汉字
     */
    private final static int CHAR_TYPE_CN = 1;

    /**
     * 英文
     */
    private final static int CHAR_TYPE_EN = 2;
    /**
     * 其他
     */
    private final static int CHAR_TYPE_OTHER = 3;
    public static BadWordStrategy getInstance() {
        if (strategy == null) {
            synchronized (BadWordStrategy_CN.class) {
                if (strategy == null) {
                    strategy = new BadWordStrategy_CN();

                }
            }
        }
        return strategy;
    }

    private BadWordStrategy_CN() {

    }

    private int getWordType(char c) {
        if (Character.isLetter(c)) {
            return CHAR_TYPE_EN;
        } else if (Character.isIdeographic(c)) {
            return CHAR_TYPE_CN;
        } else {
            return CHAR_TYPE_OTHER;
        }
    }

    @Override
    protected boolean isSkip(char curWord, char firstWord, int skipLength) {
        if (getWordType(curWord) != getWordType(firstWord)) {
            return true;
        }
        return false;
    }
}