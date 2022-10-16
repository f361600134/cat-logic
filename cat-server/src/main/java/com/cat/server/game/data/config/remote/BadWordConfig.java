package com.cat.server.game.data.config.remote;

import java.util.HashSet;
import java.util.Set;

import com.cat.server.core.config.annotation.ConfigUrl;
import com.cat.server.core.config.container.IGameConfig;

/**
 * 敏感词库
 */
//@ConfigUrl("bad_word.json")
public class BadWordConfig implements IGameConfig {
    /**
     * 公共敏感词
     */
    private Set<String> total = new HashSet<>();
    /**
     * 名字敏感词
     */
    private Set<String> name = new HashSet<>();
    /**
     * 聊天敏感词
     */
    private Set<String> chat = new HashSet<>();

    public Set<String> getChat() {
        return chat;
    }

    @Override
    public int getId() {
        return 1;
    }

    public Set<String> getName() {
        return name;
    }

    public Set<String> getTotal() {
        return total;
    }

    public void setChat(Set<String> chat) {
        this.chat = chat;
    }

    public void setName(Set<String> name) {
        this.name = name;
    }

    public void setTotal(Set<String> total) {
        this.total = total;
    }
}
