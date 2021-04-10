package com.cat.server.core.event;

public interface IObserver {
	
	/**
	 * 默认注册
	 * @return
	 */
	default public boolean isRegister() {
		return true;
	}

}
