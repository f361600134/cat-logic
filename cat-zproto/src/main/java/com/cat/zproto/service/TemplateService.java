package com.cat.zproto.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.zproto.core.result.SystemCodeEnum;
import com.cat.zproto.core.result.SystemResult;
import com.cat.zproto.domain.template.TemplateDomain;
import com.cat.zproto.domain.template.TemplateStruct;
import com.cat.zproto.enums.TemplateEnum;
import com.cat.zproto.manager.TemplateManager;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 模板service, 缓存模板文件内容
 * @author Administrator
 */
@Service
public class TemplateService implements InitializingBean{
	
	private static Logger logger = LoggerFactory.getLogger(TemplateService.class);
	
	@Autowired private Configuration configuration;
	/**
	 * 模板类管理器
	 */
	@Autowired private TemplateManager templateManager;

	/**
	 * 输出文件, 根据模板内容渲染输出成文件
	 */
	public void printer(Object module, String fileName, String protoName) {
		File file = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(file);
				Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
				){
			Template template = configuration.getTemplate(protoName);
			template.process(module, out);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("printer error, e:", e);
		}
		
	}
	
	/**
	 * 根据id获取到模板结构
	 * @param id
	 * @return
	 */
	public TemplateStruct getStruct(int id, String name) {
		int type = id / TemplateDomain.COEFFICIENT;
		TemplateDomain domain = templateManager.getDomain(type);
		TemplateStruct struct = domain.getTemplate(id);
		return struct;
	}
	
	/**
	 * 根据id获取到模板结构
	 * @param id
	 * @return
	 */
	public Collection<TemplateStruct> getAllStruct(int type) {
		TemplateDomain domain = templateManager.getDomain(type);
		return domain.getTemplateMap().values();
	}
	
	
	
	/**
	 * 保存对应模板信息
	 * @param id
	 * @return
	 */
	public SystemCodeEnum saveStruct(int type, String name, String content) {
		TemplateDomain domain = templateManager.getDomain(type);
		if (domain == null) {
			logger.info("updateStruct error");
			return SystemCodeEnum.ERROR_NOT_FOUND_DOMAIN;
		}
		//写入到本地
		return domain.createNewTemplate(name, content);
	}
	
	/**
	 * 保存对应模板信息
	 * @param id
	 * @return
	 */
	public SystemCodeEnum updateStruct(int id, String name, String content) {
		int type = id / TemplateDomain.COEFFICIENT;
		TemplateDomain domain = templateManager.getDomain(type);
		if (domain == null) {
			logger.info("updateStruct error");
			return SystemCodeEnum.ERROR_NOT_FOUND_DOMAIN;
		}
		//写入到本地
		return domain.replaceTemplate(id, name, content);
	}
	
	/**
	 * 删除对应模板信息
	 * @param id
	 * @return
	 */
	public SystemCodeEnum deleteStruct(int id, String name) {
		int type = id / TemplateDomain.COEFFICIENT;
		TemplateDomain domain = templateManager.getDomain(type);
		if (domain == null) {
			logger.info("deleteStruct error");
			return SystemCodeEnum.ERROR_NOT_FOUND_DOMAIN;
		}
		//写入到本地
		return domain.deleteTemplate(id, name);
	}
	
	/**
	 * 修改模板名字
	 * @param id
	 * @return
	 */
	public SystemCodeEnum renameStruct(int id, String newName) {
		int type = id / TemplateDomain.COEFFICIENT;
		TemplateDomain domain = templateManager.getDomain(type);
		if (domain == null) {
			logger.info("renameStruct error");
			return SystemCodeEnum.ERROR_NOT_FOUND_DOMAIN;
		}
		//写入到本地
		return domain.renameTemplate(id, newName);
	}
	
	/**
	 * 修改模板名字
	 * @param id
	 * @return
	 */
	public SystemResult newStruct(int type, String name) {
		TemplateDomain domain = templateManager.getDomain(type);
		if (domain == null) {
			logger.info("renameStruct error");
			return SystemResult.build(SystemCodeEnum.ERROR_NOT_FOUND_DOMAIN);
		}
		//写入到本地
		if (domain.contains(name)) {
			return SystemResult.build(SystemCodeEnum.ERROR_ALREADY_EXITS_FREEMAKER);
		}
		TemplateStruct struct = domain.newTemplate(name);
		return SystemResult.build(SystemCodeEnum.SUCCESS, struct.getId());
	}
	
	/**
	 * 初始化管理类, 缓存模板文件
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		for (TemplateEnum tenum : TemplateEnum.values()) {
			TemplateDomain domain = templateManager.getOrCreateDomain(tenum.getType());
			domain.initDomain();
		}
	}
	
}
