package com.cat.zproto.core.result;

import com.alibaba.fastjson.JSONObject;
/**
 * 结果集抽象类
 * @auth Jeremy
 * @date 2019年7月31日上午12:11:10
 */
public abstract class AbstractResult implements IResult {

	// 错误码
	private int code;
	// 错误描述
	private String tips;
	
	public AbstractResult(CodeEnum cNum) {
		this.code = cNum.getStatus();
		this.tips = cNum.getDesc();
	}
	
	public AbstractResult(int code, String tips) {
		this.code = code;
		this.tips = tips;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
	
//	/**
//	 * 错误码的错误状态发生改变
//	 */
//	public void change(int code, String tips) {
//		setCode(code);
//		setTips(tips);
//	}
	
	@Override
	public boolean success() {
		return getCode() == 0;
	}

	@Override
	public String result() {
		return  JSONObject.toJSONString(this);
	}
	
	@Override
	public String toString() {
		return "ErrorResult [code=" + code + ", tips=" + tips + "]";
	}
	
	/**
	 * 获取结果集字符串
	 * 
	 * @return
	 * @return boolean
	 */
	public int code(){
		return code;
	}
	
	/**
	 * 获取结果集字符串
	 * 
	 * @return
	 * @return boolean
	 */
	public String tips() {
		return tips;
	}

}
