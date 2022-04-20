package com.cat.server.game.helper.skill;

/**
 * 带有属性的实体对象
 */
public interface ISkillEntity extends ISkillNodeHolder {

    /**
     * 获取该属性实体的属性字典
     * 
     * @return
     */
    default SkillDictionary getAttrDic() {
        return getAttributeNode().getDic();
    }

    /**
     * 该实体某一属性总值
     *
     * @param attributeType
     * @return
     */
    default long getAttributeValue(SkillType attributeType) {
        return getAttributeValue(attributeType.getId());
    }

    /**
     * 该实体某一属性总值
     *
     * @param attributeType
     * @return
     */
    default long getAttributeValue(int attributeType) {
        SkillDictionary dictionary = getAttrDic();
        if (dictionary == null) {
            return 0;
        }
        return dictionary.getSkill(attributeType);
    }
}
