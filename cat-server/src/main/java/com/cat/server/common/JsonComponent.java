package com.cat.server.common;

import org.springframework.context.annotation.Configuration;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.game.module.resource.domain.ResourceMap;

/**
 * Json组件
 * 
 * @author Administrator
 *
 */
@Configuration
public class JsonComponent implements ILifecycle{

	void initJsonConfig(){
//		ParserConfig.getGlobalInstance().putDeserializer(ResourceMap.class, new OrderActionEnumDeser());
	}

}
