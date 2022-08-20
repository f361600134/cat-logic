package com.cat.server.game.helper.badword.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典树节点
 */
public class TrieNode {
    /**
     * 表示是否为关键词的结尾
     */
    private boolean end;
    /**
     * 子节点
     */
    private Map<Character, TrieNode> subNodes = new HashMap<>();

    /**
     * 添加子节点
     * 
     * @param c
     * @param node
     */
    public void addSubNode(Character c, TrieNode node) {
        subNodes.put(c, node);
    }

    /**
     * 获取子节点
     * 
     * @param c
     * @return
     */
    public TrieNode getSubNode(Character c) {
        return subNodes.get(c);
    }

    public Map<Character, TrieNode> getSubNodes() {
        return subNodes;
    }

    /**
     * 判断是否是关键词的结尾
     * 
     * @return
     */
    public boolean isEnd() {
        return end;
    }

    /**
     * 设置关键词的结尾为true
     * 
     * @param isKeyWordEnd
     */
    public void setEnd(boolean isKeyWordEnd) {
        this.end = isKeyWordEnd;
    }

    public void setSubNodes(Map<Character, TrieNode> subNodes) {
        this.subNodes = subNodes;
    }

}
