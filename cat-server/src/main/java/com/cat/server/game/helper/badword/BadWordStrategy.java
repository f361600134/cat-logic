package com.cat.server.game.helper.badword;

import com.cat.server.game.helper.badword.struct.Trie;

/**
 * 敏感词策略
 */
public interface BadWordStrategy {

    /**
     * 最小匹配规则<br>
     * 匹配到敏感词就返回
     */
    int MATCH_TYPE_MIN = 1;
    /**
     * 最大匹配规则<br>
     * 若有较长的敏感词包含了短的敏感词<br>
     * 匹配到最长的敏感词才返回
     */
    int MATCH_TYPE_MAX = 2;

    /**
     * 判断是否该文本内容是否存在敏感词
     * 
     * @param trie       敏感词字典树
     * @param text       需要检测的文本内容
     * @param beginIndex 开始检测的字符下标
     * @param matchType  匹配规则
     * @return 从beginIndex开始 属于敏感词的文字长度
     */
    int checkBadWord(Trie trie, String text, int beginIndex, int matchType);
}