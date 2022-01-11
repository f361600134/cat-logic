package com.cat.server.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.cat.api.core.task.impl.CommonTaskExecutor;
import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;

/**
 * 业务组件销毁
 * @author Jeremy
 */
@Configuration
public class LogicDestoryComponent implements ILifecycle{

	private static final Logger logger = LoggerFactory.getLogger(LogicDestoryComponent.class);

	@Autowired(required=false)
	private CommonTaskExecutor executor;
	
	public void destory() {
		if (executor != null) {
			executor.shutdown();
			logger.info("====> LogicDestoryComponent destory executor");
		}
	}
	
	@Override
	public void stop() throws Throwable {
		ILifecycle.super.stop();
		destory();
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC_INITIALIZATION.getPriority();
	}
	
}
