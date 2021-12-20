package com.cat.server.game.module.mail.assist;

public enum MailType {
	
	PLAYER_MAIL(1),
	GROUP_MAIL(2),
	;
	
	private final int mailType;
	private MailType(int mailType) {
		this.mailType = mailType;
	}
	
	public int getMailType() {
		return mailType;
	}

	public static MailType valuesOf(int mailType) {
		for (MailType type : values()) {
			if (type.getMailType() == mailType) {
				return type;
			}
		}
		return null;
	}
	
}
