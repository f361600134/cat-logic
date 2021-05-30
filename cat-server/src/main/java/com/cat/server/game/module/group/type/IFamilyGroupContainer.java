package com.cat.server.game.module.group.type;

import com.cat.server.game.module.group.IGroupContainer;

/**
 * 家族容器接口
 * @author Jeremy
 */
public interface IFamilyGroupContainer<T> extends IGroupContainer<T> {

    /**
     * 校验名字是否重复
     * @param tag 家族号
     * @return true 已存在改名字, false不存在
     */
    boolean containsTag(String tag);

    /**
     * 修改家族号
     * @param familyId 家族id
     * @param tag 家族号
     */
    void changeTag(long familyId, String tag);

}
