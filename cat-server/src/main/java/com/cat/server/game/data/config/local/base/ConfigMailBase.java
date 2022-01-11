package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * yj.邮件.xlsx<br>
 * mail.json<br>
 * @author auto gen
 */
public class ConfigMailBase implements IGameConfig {

    /**
     * id<br>
     * 唯一id
     */
    private int id;
    /**
     * 发送者
     */
    private String sender;
    /**
     * 邮件标题
     */
    private String title;
    /**
     * 邮件内容<br>
     * {}:直接显示数据<br>
     * {r}:资源id转资源名字<br>
     * {a}:活动id转活动名字
     */
    private String content;
    /**
     * 过期天数<br>
     * 自定义天数,不填默认15天
     */
    private int expiredDays;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 发送者*/
    public String getSender() {
        return this.sender;
    }

    /** @param sender 发送者*/
    public void setSender(String sender) {
        this.sender = sender;
    }

    /** @return 邮件标题*/
    public String getTitle() {
        return this.title;
    }

    /** @param title 邮件标题*/
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return 邮件内容*/
    public String getContent() {
        return this.content;
    }

    /** @param content 邮件内容*/
    public void setContent(String content) {
        this.content = content;
    }

    /** @return 过期天数*/
    public int getExpiredDays() {
        return this.expiredDays;
    }

    /** @param expiredDays 过期天数*/
    public void setExpiredDays(int expiredDays) {
        this.expiredDays = expiredDays;
    }

}
