package com.cat.zproto.core.result;

public enum SystemCodeEnum implements CodeEnum{
	
	UNKNOW(-1, "未知异常"),
	SUCCESS(0, "success"),
	ERROR_PARAM(1, "参数为空"),
	ERROR_ADD_REPEAT(2, "模块id重复"),
	ERROR_DELETE_LIMIT(3, "防误删,每次最多删除2条数据"),
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
