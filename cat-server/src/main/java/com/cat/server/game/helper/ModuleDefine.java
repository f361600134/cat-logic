package com.cat.server.game.helper;

/**
 * 模块id定义<br>
 */
public enum ModuleDefine {
	
	UNDEFINE(-1),
    // ----------------玩家------------------------
	/** 公共模块*/
	COMMON(1),
	/**登录*/
	LOGIN(100),
	/** 玩家/角色 */
    PLAYER(101),
	/** 道具 */
	ITEM(102),
	/** 邮件 */
	PLAYERMAIL(103),
	/** 聊天 */
    CHAT(104),
	/** 活动 */
	ACTIVITY(105),
	/** 排行榜 */
	RANK(106),
	/** 影子 */
	SHADOW(107),
	/** 资源回收*/
	RECYCLE(108),
	/** 商店*/
	SHOP(110),
	/** 商店-主界面 */
	MAIN_SHOP(SHOP, 1),
	/**任务*/
	MISSION(111),
	/**主线任务*/
	MISSION_MAIN(MISSION, 1),
	/**宠物*/
	PET(112),
	/**武器*/
	EQUIT(113),
	/**宠物装备*/
	PET_EQUIT(114),
	/** 研习社*/
	LEARNCOMMUNITY(200),
	/** 研习社*/
	LEARNCOMMUNITY_SHOP(LEARNCOMMUNITY, 1),
	/** 研习社*/
	LEARNCOMMUNITY_MISSION(LEARNCOMMUNITY, 2),
	/** 活动物品*/
	ActivityItem(201),
	/** 群邮件*/
	GROUPMAIL(900),
	
	
	FAMILY(901),
	TEAM(902),
	ACCOUNT(903),
	;
	
	/**
	 * 当前功能id
	 */
	private int moduleId;
	
	private ModuleDefine(int moduleId) {
		this.moduleId = moduleId;
	}
	
	private ModuleDefine(ModuleDefine dependentModule, int ModuleId) {
		this.moduleId = dependentModule.getModuleId() * 100 + ModuleId;
	}
	
	public int getModuleId() {
		return moduleId;
	}
	
	public static void main(String[] args) {
		for (ModuleDefine md : ModuleDefine.values()) {
			System.out.println(md +"---->"+ md.getModuleId());
		}
	}
	
}
