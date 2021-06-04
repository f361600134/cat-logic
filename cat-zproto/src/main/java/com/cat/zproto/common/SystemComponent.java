package com.cat.zproto.common;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.domain.system.SettingConfig;

/**
 * 业务组件注册
 * @author Jeremy
 */
@Configuration
public class SystemComponent {

	private static final Logger logger = LoggerFactory.getLogger(SystemComponent.class);
	
	/**
	 * 注册系统配置
	 * @return
	 */
	@Bean
	public SettingConfig setting(){
		SettingConfig setting = null;
		try {
			String content = FileUtils.readFileToString(new File(CommonConstant.SYSTEM_SETTING), StandardCharsets.UTF_8);
			setting = JSON.parseObject(content, SettingConfig.class);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("setting error");
		}
		logger.info("注册[SettingConfig]服务:{}", setting);
		return setting;
	}

}
