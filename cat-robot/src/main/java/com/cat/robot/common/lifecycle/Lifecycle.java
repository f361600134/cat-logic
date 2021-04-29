package com.cat.robot.common.lifecycle;

/**
 * 
 * 实现该接口的类，进程启动时会调用start()方法，关闭时调用stop()方法
 *
 */
public interface Lifecycle {

	default String name() {
		return this.getClass().getSimpleName();
	}
	
	default void start() throws Throwable {

	}

	default void stop() throws Throwable {

	}
	
	default int priority() {
		return Priority.LOWEST.getPriority();
	}

}