package com.cat.server.game.helper.attribute;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 属性字典
 */
public class AttributeDictionary {

    private final Map<Integer, Integer> dictionary;

    public AttributeDictionary() {
        this.dictionary = new HashMap<>();
    }

    public AttributeDictionary(Map<Integer, Integer> dictionary) {
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
     * @param attrType
     * @param value
     */
    public long getAttr(int attrType) {
        return dictionary.getOrDefault(attrType, 0);
    }

    /**
     * 	根据类型设置值
     * @param attrType
     * @param value
     */
    public void setAttr(int attrType, int value) {
        dictionary.put(attrType, value);
    }
    
    /**
     * 	根据枚举类型设置值
     * @param attrType
     * @return
     */
    public void setAttr(AttributeType attrType, int value) {
        int id = attrType.getId();
        setAttr(id, value);
    }
    
    /**
     * 	根据枚举类型获取值
     * @param attrType
     * @return
     */
    public long getAttr(AttributeType attrType) {
        int id = attrType.getId();
        return getAttr(id);
    }
    
    /**
     * 	根据给定的类型, 值增加属性
     * @param attrType
     * @param value
     */
    public void addAttr(Map<Integer, Integer> dictionary) {
        if (dictionary == null) {
            return;
        }
        dictionary.forEach((key, value)->{
        	addAttr(key,  value);
        });
    }
    
    /**
     * 	根据给定的类型, 值增加属性
     * @param attrType
     * @param value
     */
    public void addAttr(int attrType, int value) {
        if (value == 0) {
            return;
        }
        int oldValue = dictionary.getOrDefault(attrType, 0);
        int newValue = oldValue + value;
        dictionary.put(attrType, newValue);
    }
    
    /**
     * 	其他字典合并到此字典
     * @param otherAttrDic
     */
    public void addAttr(AttributeDictionary otherAttrDic) {
        if (otherAttrDic == null || otherAttrDic.isEmpty()) {
            return;
        }
        Map<Integer, Integer> otherDic = otherAttrDic.getDictionary();
        for (Entry<Integer, Integer> entry : otherDic.entrySet()) {
            int attrType = entry.getKey();
            int value = entry.getValue();
            addAttr(attrType, value);
        }
    }
    
    /**
     *	根据枚举类型增加值
     * @param attrType
     * @param value
     */
    public void addAttr(AttributeType attrType, int value) {
        if (value == 0) {
            return;
        }
        int id = attrType.getId();
        addAttr(id, value);
    }
    
    /**
     * 	根据给定的类型, 值减少属性
     * @param attrType
     * @param value
     */
    public <T extends Map<Integer, Integer>> void subAttr(T dictionary) {
        if (dictionary == null) {
            return;
        }
        dictionary.forEach((key, value)->{
        	subAttr(key, value);
        });
    }

    /**
     * 	根据基础类型减少值
     * @param attrType
     * @param value
     */
    public void subAttr(int attrType, int value) {
        if (value == 0) {
            return;
        }
        int oldValue = dictionary.getOrDefault(attrType, 0);
        int newValue = oldValue - value;
        dictionary.put(attrType, newValue);
    }

    /**
     * 	根据枚举类型减少值
     * @param attrType
     * @param value
     */
    public void subAttr(AttributeType attrType, int value) {
        if (value == 0) {
            return;
        }
        int id = attrType.getId();
        subAttr(id, value);
    }

    /**
     * 	根据枚举类型获取百分比值
     * @param attrType
     * @param value
     */
    public double getRateAttr(AttributeType attrType) {
        int id = attrType.getId();
        return getRateAttr(id);
    }
    
    /**
     * 	百分比值计算
     * @param attrType
     * @param value
     */
    public double getRateAttr(int attrType) {
        return getAttr(attrType) / 10000d;
    }

    /**
     * 	判断字典是否为空
     * @return
     */
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }    
    
    /**
     * 按百分比替换资源<br>
     * 向上取整
     * @param per 百分比
     */
    public void rate(double per) {
        for (Integer configId : dictionary.keySet()) {
            long number = dictionary.getOrDefault(configId, 0);
            if (number == 0L) {
				continue;
			}
            int newValue = (int)Math.ceil(number * per);
            dictionary.put(configId, newValue);
        }
    }
}
