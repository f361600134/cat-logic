package com.cat.server.game.module.skill.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 技能字典
 */
public class SkillDictionary {

	/**
	 * key: 技能id
	 * value: 技能等级
	 */
    private final Map<Integer, Integer> dictionary;

    public SkillDictionary() {
        this.dictionary = new HashMap<>();
    }

    public SkillDictionary(Map<Integer, Integer> dictionary) {
        if (dictionary == null) {
            throw new NullPointerException();
        }
        this.dictionary = dictionary;
    }

    /**
     * 	获取字典
     * @return
     */
    public Map<Integer, Integer> getDictionary() {
        return dictionary;
    }
    
    /**
     * 	根据类型获取值
     * @param skillType
     * @param value
     */
    public Integer getSkill(int skillType) {
        return dictionary.getOrDefault(skillType, 0);
    }

    /**
     * 	根据类型设置值
     * @param skillType
     * @param value
     */
    public void setSkill(int skillType, Integer value) {
        dictionary.put(skillType, value);
    }
    
    /**
     * 	根据枚举类型设置值
     * @param skillType
     * @return
     */
    public void setSkill(SkillType skillType, Integer value) {
        int id = skillType.getId();
        setSkill(id, value);
    }
    
    /**
     * 	根据枚举类型获取值
     * @param skillType
     * @return
     */
    public Integer getSkill(SkillType skillType) {
        int id = skillType.getId();
        return getSkill(id);
    }
    
    /**
     * 	根据给定的类型, 值增加属性
     * @param skillType
     * @param value
     */
    public <T extends Map<Integer, Integer>> void addSkill(T dictionary) {
        if (dictionary == null) {
            return;
        }
        dictionary.forEach((key, value)->{
        	addSkill(key, (Integer)value);
        });
    }
    
    /**
     * 	根据给定的类型, 值增加属性
     * @param skillType
     * @param value
     */
    public void addSkill(int skillType, Integer value) {
        if (value == 0) {
            return;
        }
        Integer oldValue = dictionary.getOrDefault(skillType, 0);
        Integer newValue = oldValue + value;
        dictionary.put(skillType, newValue);
    }
    
    /**
     * 	其他字典合并到此字典
     * @param otherDic
     */
    public void addSkill(SkillDictionary otherDic) {
        if (otherDic == null || otherDic.isEmpty()) {
            return;
        }
        Map<Integer, Integer> otherDicMap = otherDic.getDictionary();
        for (Entry<Integer, Integer> entry : otherDicMap.entrySet()) {
            int type = entry.getKey();
            Integer value = entry.getValue();
            addSkill(type, value);
        }
    }
    
    /**
     *	根据枚举类型增加值
     * @param skillType
     * @param value
     */
    public void addSkill(SkillType skillType, Integer value) {
        if (value == 0) {
            return;
        }
        int id = skillType.getId();
        addSkill(id, value);
    }
    
    /**
     * 	根据给定的类型, 值减少属性
     * @param skillType
     * @param value
     */
    public <T extends Map<Integer, Integer>> void subSkill(T dictionary) {
        if (dictionary == null) {
            return;
        }
        dictionary.forEach((key, value)->{
        	subSkill(key, (Integer)value);
        });
    }

    /**
     * 	根据基础类型减少值
     * @param skillType
     * @param value
     */
    public void subSkill(int skillType, Integer value) {
        if (value == 0) {
            return;
        }
        Integer oldValue = dictionary.getOrDefault(skillType, 0);
        Integer newValue = oldValue - value;
        dictionary.put(skillType, newValue);
    }

    /**
     * 	根据枚举类型减少值
     * @param skillType
     * @param value
     */
    public void subSkill(SkillType skillType, Integer value) {
        if (value == 0) {
            return;
        }
        int id = skillType.getId();
        subSkill(id, value);
    }

    /**
     * 	根据枚举类型获取百分比值
     * @param skillType
     * @param value
     */
    public double getSkillRate(SkillType skillType) {
        int id = skillType.getId();
        return getSkillRate(id);
    }
    
    /**
     * 	百分比值计算
     * @param skillType
     * @param value
     */
    public double getSkillRate(int skillType) {
        return getSkill(skillType) / 10000d;
    }

    /**
     * 	判断字典是否为空
     * @return
     */
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

}
