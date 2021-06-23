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
	ERROR_EXECUTE_COMMAND_FAIL(7, "执行命令行失败"),
	ERROR_NOT_SUPPORT_REVERSE_LOAD(8, "无数据时才能反向加载"),
	ERROR_NOT_FOUND_TABLE(9, "未找到对应表信息,请检查模块名与表命是否保持一致"),
	ERROR_SELECT_EXCPTION(10, "数据查询出现异常"),
	ERROR_CANNOT_FOUND_PROTOOBJECT(11, "找不到协议对象"),
	ERROR_NOT_SUPPORT(12, "不支持的操作"),
	ERROR_NOT_WRITE_FAILED(13, "修改成功但是持久化失败"),
	ERROR_VERSION_EXIST(14, "已存在此版本"),
	ERROR_COPIED_TRUNK_DIR(16, "复制主干目录出错"),
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
