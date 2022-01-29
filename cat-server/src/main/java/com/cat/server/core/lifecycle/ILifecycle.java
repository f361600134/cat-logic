package com.cat.server.core.lifecycle;

/**
 * 生命周期接口类, 控制系统环境,业务环境,业务模块之间的启动顺序 
 * 实现该接口的类，进程启动时会调用start()方法，关闭时调用stop()方法
 * @author Jeremy
 * @date 2021年05月20日下午3:20:50
 */
public interface ILifecycle {

	/**
	 * 当前类的名字
	 * @return
	 */
	default String name() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * 启动方法, 需要控制顺序时实现
	 * @throws Throwable
	 */
	default void start() throws Throwable {

	}

	/**
	 * 关闭方法, 需要控制关闭顺序时实现
	 * @throws Throwable
	 */
	default void stop() throws Exception {

	}
	
	/**
	 * 优先级
	 * @return
	 */
	default int priority() {
		return Priority.LOWEST.getPriority();
	}

}