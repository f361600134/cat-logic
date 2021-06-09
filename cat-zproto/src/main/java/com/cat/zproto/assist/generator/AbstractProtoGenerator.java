package com.cat.zproto.assist.generator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.service.TemplateService;

/**
 * 生成器, 生成器内部的享元对象在生成之前, 会被外部替换成最新的对象.
 * @auth Jeremy
 * @date 2021年6月6日下午9:29:12
 */
public abstract class AbstractProtoGenerator implements IProtoGenerator{
	
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired protected SettingConfig setting;
	@Autowired protected TemplateService templateService;
	
	/**
	 * 默认返回无前部分补充
	 */
	@Override
	public String getFileNameFrontPart() {
		return StringUtils.EMPTY;
	}
	
	@Override
	public String getFileNameSuffix() {
		return ".java";
	}
	
	@Override
	public int type() {
		return PROTO;
	}
	
}
