package com.cat.server.common;

import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.game.module.resource.parse.ResourceGroupParser;

/**
 * Json组件
 * 
 * @author Administrator
 *
 */
@Configuration
public class JsonComponent implements ILifecycle{

	void initJsonConfig(){
		//ResourceMap拦截过滤为不可变对象
		ParserConfig.getGlobalInstance().putDeserializer(ResourceGroup.class, new ResourceGroupParser());
	}
	
	@Override
	public void start() throws Throwable {
		this.initJsonConfig();
	}
	
	@Override
	public int priority() {
		return Priority.SYSTEM_INITIALIZATION.getPriority();
	}

}
