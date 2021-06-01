package com.cat.zproto.core.result;

public enum SystemCodeEnum implements CodeEnum{
	
	SUCCESS(1, "success"),
	ERROR_RELOAD(2, "刷新服务器列表异常"),
	;
	
	private int status;
	private String desc;
	
	private SystemCodeEnum(int status, String desc) {
		this.status = status;
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
