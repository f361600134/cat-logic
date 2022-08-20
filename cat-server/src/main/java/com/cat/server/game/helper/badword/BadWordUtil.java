package com.cat.server.game.helper.badword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.exception.ServerStarupError;
import com.cat.server.game.helper.badword.config.BadWordConfig;
import com.cat.server.game.helper.badword.impl.BadWordStrategy_CN;
import com.cat.server.game.helper.badword.impl.BadWordStrategy_EN;
import com.cat.server.game.helper.badword.struct.Trie;
import com.cat.server.utils.Pair;

/**
 * 敏感词工具类
 */
public class BadWordUtil {

    private final static Logger log = LoggerFactory.getLogger(BadWordUtil.class);

    /**
     * 聊天
     */
    public final static int WORD_TYPE_CHAT = 1;
    /**
     * 名字
     */
    public final static int WORD_TYPE_NAME = 2;

    public final static char REPLACEMENT_CHAR = '*';

    private static Trie chatTrie = new Trie();
    private static Trie nameTrie = new Trie();

    private static BadWordStrategy badWordStrategy = BadWordStrategy_CN.getInstance();

    private final static Map<Integer, String> REPLACEMENT_CACHE = new HashMap<>();

    private static Trie buildChatBadWordTrie(BadWordConfig config) {
        Trie trie = new Trie();
        if (config == null) {
            return trie;
        }
        for (String word : config.getTotal()) {
            String res = word.replaceAll(" ", ""); // 越南文有空格，替换掉空格
            trie.addKeyWord(res, WORD_TYPE_CHAT);
        }
        for (String word : config.getChat()) {
            String res = word.replaceAll(" ", ""); // 越南文有空格，替换掉空格
            trie.addKeyWord(res, WORD_TYPE_CHAT);
        }
        return trie;
    }

    private static Trie buildNameBadWordTrie(BadWordConfig config) {
        Trie trie = new Trie();
        if (config == null) {
            return trie;
        }
        for (String word : config.getTotal()) {
            trie.addKeyWord(word, WORD_TYPE_NAME);
        }
        for (String word : config.getName()) {
            trie.addKeyWord(word, WORD_TYPE_NAME);
        }
        return trie;
    }

    /**
     * 获取文字中的敏感词在原始文本中的开始结束下标
     *
     * @param originText 原始文字
     * @param matchType  匹配规则 1：最小匹配规则，2：最大匹配规则
     * @param trie       敏感词字典树
     * @return
     */
    private static List<Pair<Integer, Integer>> getBadWordIndexs(final String originText, int matchType, Trie trie) {
        List<Pair<Integer, Integer>> resultList = null;
        // 转为小写 方便过滤
        String checkContent = originText.toLowerCase();
        for (int index = 0; index < originText.length(); index++) {
            char c = originText.charAt(index);
            if (Character.isWhitespace(c) || c == REPLACEMENT_CHAR) {
                // 不从空格开始查找
                continue;
            }
            // 判断是否包含敏感字符
            int length = badWordStrategy.checkBadWord(trie, checkContent, index, matchType);
            if (length <= 0) {
                continue;
            }
            // 存在,加入list中
            int endIndex = index + length;
            if (resultList == null) {
                resultList = new ArrayList<>();
            }
            resultList.add(Pair.of(index, endIndex));
            // 减1的原因，是因为for会自增
            index = endIndex - 1;
        }
        return resultList;
    }

    public static BadWordStrategy getBadWordStrategy() {
        return badWordStrategy;
    }

    public static Trie getChatTrie() {
        return chatTrie;
    }

    public static Trie getNameTrie() {
        return nameTrie;
    }

    /**
     * 获取替换字符串
     *
     * @param length
     * @return
     */
    private static String getReplaceChars(int length) {
        String replaceChars = REPLACEMENT_CACHE.get(length);
        if (replaceChars != null) {
            return replaceChars;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(REPLACEMENT_CHAR);
        }
        replaceChars = sb.toString();
        REPLACEMENT_CACHE.putIfAbsent(length, replaceChars);
        return replaceChars;
    }

    public static Map<Integer, String> getReplacementCache() {
        return REPLACEMENT_CACHE;
    }

    public static Trie getTrie(int wordType) {
        if (wordType == WORD_TYPE_CHAT) {
            return chatTrie;
        }
        return nameTrie;
    }

    public static void init(BadWordConfig badWordConfig, boolean startup) {
        try {
            initBadWordStrategy();
            chatTrie = buildChatBadWordTrie(badWordConfig);
            nameTrie = buildNameBadWordTrie(badWordConfig);
        } catch (Exception e) {
            log.error("initBadWord error", e);
            if (startup) {
                throw new ServerStarupError(e);
            }
        }
    }

    private static void initBadWordStrategy() {
    	String country = SystemUtils.USER_COUNTRY;
        if (country == Locale.CHINA.getCountry()) {
            badWordStrategy = BadWordStrategy_CN.getInstance();
        } else {
            badWordStrategy = BadWordStrategy_EN.getInstance();
        }
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param text     文字
     * @param wordType 敏感词类型 1聊天 2名称
     * @return 若包含返回true，否则返回false
     */
    public static boolean isContainsBadWord(String text, int wordType) {
        if (text == null) {
            return false;
        }
        Trie trie = getTrie(wordType);
        // 转为小写
        text = text.toLowerCase();
        for (int i = 0; i < text.length(); i++) {
            // 判断是否包含敏感字符
            int badWordLength = badWordStrategy.checkBadWord(trie, text, i, BadWordStrategy.MATCH_TYPE_MIN);
            // 大于0存在，返回true
            if (badWordLength > 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        nameTrie.addKeyWord("abc", WORD_TYPE_CHAT);
        nameTrie.addKeyWord("胡耀邦", WORD_TYPE_NAME);
//        System.out.println("result=" + replaceBadWord("胡耀邦abv", WORD_TYPE_NAME));
//        System.out.println("result=" + replaceBadWord("胡耀邦", WORD_TYPE_NAME));
        System.out.println("result=" + replaceBadWord("a胡耀邦bv31c", WORD_TYPE_NAME));
    }

    /**
     * 替换敏感字字符<br>
     * 默认为最长匹配规则
     *
     * @param sourceText
     * @param wordType   敏感词类型 1聊天 2名称
     * @return
     */
    public static String replaceBadWord(String sourceText, int wordType) {
        return replaceBadWord(sourceText, BadWordStrategy.MATCH_TYPE_MAX, wordType);
    }

    /**
     * 替换敏感字字符
     *
     * @param originText
     * @param matchType  匹配规则 最长匹配/最短匹配
     * @param wordType   敏感词类型 1聊天 2名称
     * @return
     */
    public static String replaceBadWord(String originText, int matchType, int wordType) {
        if (originText == null) {
            return StringUtils.EMPTY;
        }
        Trie trie = getTrie(wordType);
        try {
            // 获取所有的敏感词
            // 去除掉2端空格
            String text = originText.trim();
            List<Pair<Integer, Integer>> badWordIndexs = getBadWordIndexs(text, matchType, trie);
            while (badWordIndexs != null && !badWordIndexs.isEmpty()) {
                StringBuilder resultSb = new StringBuilder(text);
                for (Pair<Integer, Integer> pair : badWordIndexs) {
                    int startIndex = pair.getKey();
                    int endIndex = pair.getValue();
                    int length = endIndex - startIndex;
                    if (length <= 0) {
                        continue;
                    }
                    String replaceString = getReplaceChars(length);
                    resultSb.replace(startIndex, endIndex, replaceString);
                    text = resultSb.toString();
                    badWordIndexs = getBadWordIndexs(text, matchType, trie);
                }
            }
            return text;
        } catch (Exception e) {
            log.error("replaceBadWord[{}] error.", originText, e);
        }
        return StringUtils.EMPTY;
    }

    public static void setBadWordStrategy(BadWordStrategy badWordStrategy) {
        BadWordUtil.badWordStrategy = badWordStrategy;
    }

    public static void setChatTrie(Trie chatTrie) {
        BadWordUtil.chatTrie = chatTrie;
    }

    public static void setNameTrie(Trie nameTrie) {
        BadWordUtil.nameTrie = nameTrie;
    }
}
