package com.cat.zproto.core.result;

/**
 * 返回前端接口类, 对于错误码, 每次只生成一条, 如果给予状态的改变即可
 * 
 * <b>避免在接口类中声明 isxxx(); getxxx()方法</b>
 * 
 * @auth Jeremy
 * @date 2019年4月28日下午3:06:49
 */
public interface IResult {

	/**
	 * 是否成功
	 * 
	 * @return
	 * @return boolean
	 */
	public boolean success();

	/**
	 * 获取结果集字符串
	 * 
	 * @return
	 * @return boolean
	 */
	public String result();
	
	/**
	 * 获取结果集字符串
	 * 
	 * @return
	 * @return boolean
	 */
	public int code();
	
	/**
	 * 获取结果集字符串
	 * 
	 * @return
	 * @return boolean
	 */
	public String tips();

}
