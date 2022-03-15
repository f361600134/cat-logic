package com.cat.server.game.helper;

/**
 * 模块id定义<br>
 */
public enum ModuleDefine {
	
	UNDEFINE(-1),
    // ----------------玩家------------------------
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
	/** 商店-研习社*/
	LEARNCOMMUNITY_SHOP(SHOP, 2),
	/** 研习社*/
	LEARNCOMMUNITY(200),
	/** 活动物品*/
	ActivityItem(201),
	/** 群邮件*/
	GROUPMAIL(900),
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
	
}
