package com.cat.server.game.helper.badword.struct;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字典树
 */
public class Trie {

    private final static Logger log = LoggerFactory.getLogger(Trie.class);

    private final TrieNode rootNode = new TrieNode();

    /**
     * 将一个敏感词添加到前缀树中
     * 
     * @param keyword
     * @param wordType
     */
    public void addKeyWord(String keyword, int wordType) {
        keyword = keyword.toLowerCase();
        try {
            TrieNode curNode = rootNode;
            for (int i = 0; i < keyword.length(); i++) {
                char c = keyword.charAt(i);
                TrieNode subNode = curNode.getSubNode(c);
                // 如果子节点中不存在该字符节点
                if (subNode == null) {
                    subNode = new TrieNode();
                    curNode.addSubNode(c, subNode);
                }
                // 指向下一个节点，进行下一个循环
                curNode = subNode;
            }
            // 如果遍历到字符结束的位置，就要设置节点的字符结束标志位为true
            curNode.setEnd(true);
        } catch (Exception e) {
            log.error("addKeyWord error", e);
        }
    }

    public TrieNode getRootNode() {
        return rootNode;
    }
}
