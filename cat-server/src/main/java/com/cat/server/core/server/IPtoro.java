package com.cat.server.core.server;

/**
 * 序列化接口
 * 如果以後可以改proto生成工具的话, 可以使用这个接口, 默认加上这个接口的对象, 可以传输
 * 但实际上有无这个接口 影响不大
 */
public interface IPtoro<T> {
	
	/**
	 * 	对象转接口
	 * @return
	 */
	public T toProto();

}
