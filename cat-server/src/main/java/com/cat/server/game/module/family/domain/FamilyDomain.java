package com.cat.server.game.module.family.domain;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.core.task.TokenTaskQueueExecutor;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;

public class FamilyDomain extends AbstractModuleMultiDomain<Integer, Long, Family>{
	
	/**
	 * 公共的线程池处理器
	 */
	private TokenTaskQueueExecutor defaultExecutor;
	
	/**
	 * 雪花id生成器
	 */
	private SnowflakeGenerator generator;
	
	public FamilyDomain() {
		this.defaultExecutor = SpringContextHolder.getBean(TokenTaskQueueExecutor.class);
		this.generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
	}
	
	public Family createFamily(String name) {
		long id = this.generator.nextId();
		Family family = Family.create(id, name);
		this.beanMap.put(id, family);
		return family;
	}
	
}
