package com.cat.server.game.module.gm.type;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.cat.net.core.reflect.MethodInvoker;
import com.cat.net.network.base.ISession;
import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.module.gm.annotation.Command;

/**
 * 模块相关命令		
 * @author Jeremy
 */
public abstract class AbstractModuleCommand implements ICommand, ILifecycle{
	
	/**
	 * key: 命令头 执行方法名
	 * value: 执行器
	 * @mail sendMail CommandResource.sendMail
	 * @mail reqReadEmail CommandResource.reqReadEmail
	 */
	private Map<String, MethodInvoker> commandMap = new HashMap<>();

	public boolean process(ISession session, String content) {
		Command command = this.getClass().getAnnotation(Command.class);
		if (command == null) {
			return false;
		}
		//	解析出命令以及參數
		String name = command.value();
		String params = content.substring(name.length());
		
		String[] strArr = params.split(",");
		//	解析调用方法名字
		String methodName = strArr[0].trim();
		MethodInvoker methodInvoker = commandMap.get(methodName);
		if (methodInvoker == null) {
			return false;
		}
		methodInvoker.invoke(session, strArr);
		return true;
	}
	
	@Override
	public void start() throws Throwable {
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method method : methods) {
			MethodInvoker invoker = MethodInvoker.create(this, method);
			this.commandMap.put(method.getName(), invoker);
		}
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}
	
}
