package com.cat.server.game.module.group.type;

import com.cat.net.core.executor.DisruptorStrategy;
import com.cat.server.game.module.family.assist.FamilyPosition;
import com.cat.server.game.module.family.assist.FamilyPrivilege;
import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.group.AbstractGroupContainer;
import com.cat.server.game.module.group.domain.DefaultApply;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 家族团体容器
 * @author Jeremy
 */
public class FamilyGroupContainer extends AbstractGroupContainer<Family> implements IFamilyGroupContainer<Family>{

    /**
     * 加入此Map,可以精确查询
     * key:familyTag
     * value: familyId
     */
    private final Map<String, Long> familyTagMap = new ConcurrentHashMap<>();

    @Override
    public Family newGroup(long groupId, String groupName) {
        return Family.create(groupId, groupName);
    }

    /**
     * 修改家族号, 容器内不做校验, 所有的校验根据业务在业务层处理
     * 1. 移除缓存的旧的家族号
     * 2. 修改家族号
     * 3. 新的家族号加入缓存
     * @param familyId 家族id
     * @param tag 家族号
     */
    @Override
    public void changeTag(long familyId, String tag) {
        Family family = get(familyId);
        this.familyTagMap.remove(family.getTag());
        family.setTag(tag);
        this.familyTagMap.put(family.getTag(), familyId);
    }

    @Override
    public boolean containsTag(String tag) {
        return familyTagMap.containsKey(tag);
    }
    
    @Override
    public Collection<Family> searchGroup(String keyword) {
    	List<Family> result = new ArrayList<>();
    	long familyId = this.familyTagMap.getOrDefault(keyword, 0L);
    	if(familyId != 0L){
            Family family = this.get(familyId);
            if (family != null) {
                result.add(family);
            }
        }
    	result.addAll(super.searchGroup(keyword));
    	return result;
    }
}
