package com.cat.robot.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cat.robot.common.lifecycle.Lifecycle;

//	机器人启动函数
@Component
public class InitialRunner implements Lifecycle{
	
	private static final Logger log = LoggerFactory.getLogger(InitialRunner.class);
	
	
	public InitialRunner() {
	}
	
	public void run() throws Exception {
		try {
			
		} catch (Exception e) {
			log.error("服务器初始化过程出现异常, 启动失败", e);
		}
	}
	
	
	@Override
	public void start() throws Throwable {
		this.run();
	}
	
}
