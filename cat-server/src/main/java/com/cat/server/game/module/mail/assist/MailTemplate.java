package com.cat.server.game.module.mail.assist;

public enum MailTemplate {
	/**
	 * 道具回收
	 */
	ITEM_RECYCLE(1001),
	/**
	 * 活动结束
	 */
	ACTIVITY_CLOSE(1002),
	/**
	 * 背包溢出
	 */
	BAG_OVERFLOW(1003),
	/**
	 * 活动道具回收
	 */
	ACTIVITY_ITEM_RECYCLE(1004),
	;
	private final int mailConfigId;
	private MailTemplate(int mailConfigId) {
		this.mailConfigId = mailConfigId;
	}
	
	public int getMailConfigId() {
		return mailConfigId;
	}

	public static MailTemplate valuesOf(int mailConfigId) {
		for (MailTemplate type : values()) {
			if (type.getMailConfigId() == mailConfigId) {
				return type;
			}
		}
		return null;
	}
}
