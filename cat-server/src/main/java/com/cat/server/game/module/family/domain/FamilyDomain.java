package com.cat.server.game.module.family.domain;

import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.module.group.IMember;
import com.cat.server.game.module.group.type.FamilyGroupContainer;
import com.cat.server.game.module.group.type.IFamilyGroupContainer;

import java.util.Collection;

/**
 * 一个FamilyDomain管理所有Family对象, 对Family对象的操作全都代理给Domain
 * @author  Jeremy
 */
public class FamilyDomain extends AbstractModuleMultiDomain<Integer, Long, Family> implements IFamilyGroupContainer<Family>{

    /**
     * 家族团体容器
     */
    private FamilyGroupContainer familyGroupContainer;

    /**
     *初始化之后操作,需要在AbstractModuleMultiDomain初始化完毕后
     * 才能把其数据代理出去
     */
    @Override
    public void afterInit() {
        this.familyGroupContainer = new FamilyGroupContainer();
        this.familyGroupContainer.initGroup(getBeanMap().values());
    }

    @Override
    public Family get(long groupId) {
        return this.familyGroupContainer.get(groupId);
    }

    @Override
    public Family create(long memberId, String groupName) {
        return this.familyGroupContainer.create(memberId, groupName);
    }

    @Override
    public void enter(IMember member, long groupId) {
        this.familyGroupContainer.enter(member, groupId);
    }

    @Override
    public void exit(long memberId, long groupId) {
        this.familyGroupContainer.exit(memberId, groupId);
    }

    @Override
    public void destroy(Family family) {
        this.familyGroupContainer.destroy(family);
    }

    @Override
    public boolean containsName(String newName) {
        return this.familyGroupContainer.containsName(newName);
    }

    @Override
    public boolean rename(long groupId, String newName) {
        return this.familyGroupContainer.rename(groupId,newName);
    }

    @Override
    public long getGroupIdByPlayerId(long playerId) {
        return this.familyGroupContainer.getGroupIdByPlayerId(playerId);
    }

    @Override
    public Family getGroupByPlayerId(long playerId) {
        return this.familyGroupContainer.getGroupByPlayerId(playerId);
    }

    @Override
    public Collection<Family> searchGroup(String keyword) {
        return this.familyGroupContainer.searchGroup(keyword);
    }

    @Override
    public boolean containsTag(String tag) {
        return this.familyGroupContainer.containsTag(tag);
    }

    @Override
    public void changeTag(long familyId, String tag) {
        this.familyGroupContainer.changeTag(familyId, tag);
    }
}
