package com.cat.server.admin.helper.result;

import com.cat.server.core.result.CodeEnum;
import com.cat.server.game.helper.result.ModuleDefines;

public enum SystemCodeEnum implements ModuleDefines, CodeEnum{
	
	UNKNOW(0,-1, "未知异常"),
	SUCCESS(0, 0, "SUCCESS"),
	
	//玩家
	PLAYER_NOT_FOUND(PLAYER, 1, "玩家不存在"),
	
	//邮件
	MAIL_TYPE_UNDEFINE(PLAYERMAIL, 1, "未定义的邮件类型"),
	
	;
	
	private int status;
	private String desc;
	
	private SystemCodeEnum(int moduleId, int seq, String desc) {
		this.status = moduleId * 100 + seq;
		this.desc = desc;
	}
	
	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public String getDesc() {
		return desc;
	}
	
	/**
	 * 获取到指定状态枚举
	 * @param status
	 * @return
	 * @return PayOrderStatusEnum
	 */
	public static SystemCodeEnum getStateEnum(int status) {
		for (SystemCodeEnum statusEnum : values()) {
			if (statusEnum.status == status) {
				return statusEnum;
			}
		}
		return null;
	}

}
