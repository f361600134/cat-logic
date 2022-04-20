package com.cat.server.game.helper.skill;


/**
 * 属性类型<br>
 * 对应宠物技能表<br>
 */
public enum SkillType {
	/** 收集*/
	COLLECT(1),
	/** 灵通*/
	KNOWLEDGEABLE(2),
	/** 神力*/
	SUPERNATURAL_POWER(3),
	/** 饥渴*/
	THIRSTY(4),
	/** 愚笨*/
	STUPID(5),
	/** 坐骑*/
	MOUNT(6),
	/** 逃逸*/
	ESCAPE(7),
	/** 忠诚*/
	FIDELITY(8),
	/** 刚力*/
	RIGID_FORCE(9),
	/** 霹雳*/
	THUNDERBOLT(10),
	/** 蛮力*/
	BRUTE_FORCE(11),
	/** 灵悟*/
	INSPIRATION(12),
	/** 厚皮*/
	PACHYDERMIA(13),
	/** 魔甲*/
	DEMON_ARMOR(14),
	/** 钢铁*/
	STEEL(15),
	/** 石盾*/
	STONE_SHIELD(16),
	/** 护主*/
	PROTECT(17),
	/** 回魂*/
	REINCARNATION(18),
	/** 解封*/
	DEBLOCK(19),
	/** 阳春*/
	WARM(20),
	/** 驱毒*/
	DISINFECT(21),
	/** 祛病*/
	TREATMENT(22),
	/** 活筋*/
	EXERCISE(23),
	/** 醒脑*/
	WAKE_UP(24),
    ;

    private final int id;

    private SkillType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SkillType valueOf(int type) {
        for (SkillType attrType : values()) {
            if (attrType.id == type) {
                return attrType;
            }
        }
        return null;
    }

}
