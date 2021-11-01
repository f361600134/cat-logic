package com.cat.rank.service.helper;


/**
 * 错误码(返回码)<br>
 * 需要生成excel提供给客户端读取
 * @author Jeremy
 */
public enum ErrorCode  {
    /**
     *	成功消息
     */
    SUCCESS(0, "成功"),
    AUTHENTICATION_FAILED(1, "网络身份认证失败"),
    
    ;
	
	
	
    private final int code;
    private final String desc;
    
    ErrorCode(int moduleId, String desc) {
        this.code = moduleId * 100;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
    
    /*通常不需要此描述, 仅用于服务器*/
    public String getDesc() {
		return desc;
	}
    
    public boolean isSuccess() {
        return this.code == SUCCESS.getCode();
    }
    
//    /**
//     * 构建默认不带参数协议
//     * @return
//     */
//    public RespTipsBuilder toProto() {
//    	RespTipsBuilder builder = RespTipsBuilder.newInstance();
//    	builder.setTipsId(getCode());
//    	return builder;
//    }
//    
//    /**
//     * 构建带参数协议
//     * @return
//     */
//    public RespTipsBuilder toProto(int param) {
//    	RespTipsBuilder builder = RespTipsBuilder.newInstance();
//    	builder.setTipsId(getCode());
//    	builder.setParams(param);
//    	return builder;
//    }
}
