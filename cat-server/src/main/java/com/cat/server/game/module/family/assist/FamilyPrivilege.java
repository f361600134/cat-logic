package com.cat.server.game.module.family.assist;

/**
 * 权限状态用于服务器计算
 * <p>
 * 权限类1, 1~20 1-职责分配 2-解散 3-禅让 4-改家族号 5-改家族名,公告 6-外交 7-自定义职位
 * <p>
 * 
 * 权限类2, 21~40 21-审批 22-踢人 23-邀请-直接通过 24-升级家族, 升级祭坛 25-研制商店商品,家族技能
 * 26-开启家族副本,家族战,家族竞赛. 27-开启家族战 28-家族邮件
 * <p>
 * 
 * 权限类3, 41-50-推翻族长 副族长申请,满足10人即可, 长老申请满足15人即可,普通成员满足20人即可 1.族长拥有一票否决权
 * 2.所有长老,副族长全部同意, 可以否掉族长一票否决权. 101-代邀请,绕过家族限制进入申请列表
 * <p>
 * 
 * 权限分配
 * <p>
 * - 族长固定权限.1.职责分配,2.解散,3禅让,4-改家族号,5-改家族名,公告,6-外交,7.自定义职位
 * <p>
 * - 副族长固定权限.1.职责分配,5-改家族名,公告.6-外交
 * <p>
 * -长老权限.7-审批,8-踢人,9-邀请,10-升级家族,升级祭坛,11-研制商店商品,家族技能,12-开启家族副本,家族战,家族竞赛.
 * 13-开启家族战, 14-家族邮件 -自定义权限-权限类2,由族长控制生成
 * <p>
 * - 普通成员 100-推翻族长,101-代邀请
 * <p>
 */
public enum FamilyPrivilege {
	// 权限类1, 1~20
	ASSIGNMENT_OF_RESPONSIBILITIES(1, 1L, "职责分配"), 
	DISBAND(2, 1L << 1, "解散"), 
	ABDICATION(3, 1L << 2, "禅让退位"),
	RETAG(4, 1 << 3, "改家族号"), 
	RENAME(5, 1L << 4, "改家族名,公告"), 
	DIPLOMATIC(6, 1L << 5, "外交"),
	CUSTOM_POSITION(7, 1L << 6, "自定义职位"),

	// 权限类2, 21~40
	APPROVE(21, 1L << 7, "审批"), 
	FIRE(22, 1 << 8, "开除"),
	// INVITE(23, "邀请"),
	UPGRADE(24, 1L << 9, "升级家族"), 
	DEVELOP(25, 1L << 10, "研制商品或技能"), 
	DUNGEON(26, 1L << 11, "家族副本"), 
	WAR(27, 1L << 12, "家族战"),
	MAIL(28, 1L << 13, "家族邮件"),
	CHANGE_NOTICE(29, 1L<<14, "修改公告"),

	// 权限类3, 41-50, 貌似也不需要
	OVERTHROW(41, 1L << 15, "推翻族长"), 
	;

	private final int id;
	private final long privilege;
	private final String desc;

	FamilyPrivilege(int id, long privilege, String desc) {
		this.id = id;
		this.privilege = privilege;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public long getPrivilege() {
		return privilege;
	}

	public String getDesc() {
		return desc;
	}

	public static void main(String[] args) {
		for (FamilyPrivilege privilege : FamilyPrivilege.values()) {
			System.out.println("id:" + privilege.getId() + ", privilege:" + privilege.getPrivilege() + ",desc:"
					+ privilege.getDesc());
		}
	}
}
