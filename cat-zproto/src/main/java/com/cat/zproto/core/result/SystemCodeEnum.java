package com.cat.zproto.core.result;

public enum SystemCodeEnum implements CodeEnum{
	
	UNKNOW(-1, "未知异常"),
	SUCCESS(0, "SUCCESS"),
	ERROR_PARAM(1, "参数为空"),
	ERROR_ADD_REPEAT(2, "模块id重复"),
	ERROR_DELETE_LIMIT(3, "防误删,每次最多删除2条数据"),
	ERROR_NO_CHANGE(4, "没有任何修改"),
	ERROR_ILLEGAL_PARAM(5, "参数出错"),
	ERROR_CANNOT_DOUND_MODULE(6, "找不到指定模块"),
	ERROR_EXECUTE_COMMAND_FAIL(7, "执行命令行失败")
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
