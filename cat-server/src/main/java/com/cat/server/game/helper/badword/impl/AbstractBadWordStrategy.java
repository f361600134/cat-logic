package com.cat.server.game.helper.badword.impl;

import com.cat.server.game.helper.badword.BadWordStrategy;
import com.cat.server.game.helper.badword.BadWordUtil;
import com.cat.server.game.helper.badword.struct.Trie;
import com.cat.server.game.helper.badword.struct.TrieNode;

public abstract class AbstractBadWordStrategy implements BadWordStrategy {

    @Override
    public int checkBadWord(Trie trie, String text, int beginIndex, int matchType) {
        // 匹配标识数默认为0
        int matchLength = 0;
        int skipLength = 0;
        char firstWord = text.charAt(beginIndex);
        char curWord = firstWord;
        TrieNode curNode = trie.getRootNode();
        for (int i = beginIndex; i < text.length(); i++) {
            curWord = text.charAt(i);
            TrieNode tmpNode = curNode.getSubNode(curWord);
            if (tmpNode == null) {
                if (i == beginIndex) {
                    // 首个字符
                    return 0;
                }
                if (Character.isWhitespace(curWord)// 空格
                        || curWord == BadWordUtil.REPLACEMENT_CHAR// *
                        || isSkip(curWord, firstWord, skipLength)) {
                    // 若前面部分进入了敏感词判断 进行跳字检查
                    // 空格或者*跳过
                    // 若满足跳字条件也跳过
                    skipLength++;
                    continue;
                }
                // 不存在，直接返回
                break;
            }
            curNode = tmpNode;
            skipLength = 0;
            // 存在，则判断是否为最后一个
            // 如果为最后一个匹配规则,结束循环，返回匹配标识数
            if (curNode.isEnd()) {
                matchLength = i - beginIndex + 1;
                // 最小规则，直接返回,最大规则还需继续查找
                if (matchType == MATCH_TYPE_MIN) {
                    return matchLength;
                }
            }
        }
        return matchLength;
    }

    /**
     * 是否跳过该字符
     * 
     * @param curWord
     * @param firstWord
     * @param skipLength 已跳过的字符数 不含当前字符
     * @return
     */
    protected abstract boolean isSkip(char curWord, char firstWord, int skipLength);

}
