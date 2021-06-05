package com.cat.zproto.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cat.zproto.domain.module.ModuleEntity;

/**
 * 配置服务, 初始化时生成配置相关信息
 * @author Administrator
 *
 */
public class SettingsService implements InitializingBean{
	
	public static final String FILENAME = "src/main/resources/configdata/module.json";

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
