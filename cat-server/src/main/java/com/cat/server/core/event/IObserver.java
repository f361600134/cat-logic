package com.cat.server.core.event;

/**
 * 观察者接口
 * 定义了是否注册, 注册顺序等接口
 * @author Jeremy
 */
public interface IObserver {
	
	/**排序最前**/
	public int HIGHEST = Integer.MIN_VALUE;
	/**默认顺序**/
	public int DEFAULT = 0;
	/**排序最后**/
	public int LOWEST = Integer.MAX_VALUE;
	
	/**
	 * 默认注册
	 * @return
	 */
	default public boolean isRegister() {
		return true;
	}
	
	/**
	 * 注册顺序<br>
	 * @return
	 */
	default public int order() {
		return DEFAULT;
	}

}
