package com.cat.server.game.module.family.assist;

import java.util.HashMap;
import java.util.Map;

import com.cat.orm.util.StateUtils;

public enum FamilyPosition {
	
	/**
	 * 族长
	 */
	PATRIARCH(1),
	/**
	 * 副族长
	 */
	DEPUTY(3),
	/**
	 * 长老
	 */
	ELDERS(5),
	/**
	 * 自定义职位
	 */
	DEFINE(7),
	/**
	 * 普通成员
	 */
	NOMAL(9)
	;
	
	private final int value;

	FamilyPosition(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public long getPrivilege() {
		return PositionPrivilegeMap.get(getValue());
	}
	
	public static FamilyPosition getPosition(int type) {
		for (FamilyPosition position : values()) {
			if (position.getValue() == type) {
				return position;
			}
		}
		return null;
	}
	
    /**
     * 这是测试代码, 真正要实现的话, 权限需要配置在表中
     * 且这里面不包含副族长, 自定义职位, 仅包含族长, 长老, 普通成员
     */
    public static Map<Integer, Long> PositionPrivilegeMap = new HashMap<>();
    static {
    	long highest = 0, medium = 0, lowest = 0;
    	for (FamilyPrivilege privilege : FamilyPrivilege.values()) {
    		if (privilege.getId()<=20) {
    			highest = StateUtils.addState(highest, privilege.getPrivilege());
			}else if (privilege.getId()>=21 && privilege.getId()<=40) {
				medium = StateUtils.addState(medium, privilege.getPrivilege());
			}else if (privilege.getId()>=41 && privilege.getId()<=50) {
				lowest = StateUtils.addState(lowest, privilege.getPrivilege());
			}
		}
    	//族长权限=highest+medium
    	PositionPrivilegeMap.put(PATRIARCH.getValue(), StateUtils.addState(highest, medium));
		//长老权限=medium+lowest
    	PositionPrivilegeMap.put(ELDERS.getValue(), StateUtils.addState(medium, lowest));
		//普通成员权限
    	PositionPrivilegeMap.put(NOMAL.getValue(), lowest);
    }
    
    public static void main(String[] args) {
    	long patriarch = PositionPrivilegeMap.get(PATRIARCH.getValue());
    	long elders = PositionPrivilegeMap.get(ELDERS.getValue());
    	long nomal = PositionPrivilegeMap.get(NOMAL.getValue());
    	System.out.println("族长权限:"+patriarch);
    	System.out.println("长老权限:"+elders);
    	System.out.println("普通成员权限:"+nomal);
    	
    	System.out.println("族长踢人："+StateUtils.check(patriarch, FamilyPrivilege.FIRE.getPrivilege()));
    	System.out.println("长老踢人："+StateUtils.check(elders, FamilyPrivilege.FIRE.getPrivilege()));
    	
    	System.out.println("族长改名："+StateUtils.check(patriarch, FamilyPrivilege.RENAME.getPrivilege()));
    	System.out.println("长老改名："+StateUtils.check(elders, FamilyPrivilege.RENAME.getPrivilege()));
    	
	}
	
}
