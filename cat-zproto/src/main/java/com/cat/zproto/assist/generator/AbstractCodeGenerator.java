package com.cat.zproto.assist.generator;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.dto.TableFreemarkerDto;
import com.cat.zproto.service.TemplateService;

/**
 * 生成器, 生成器内部的享元对象在生成之前, 会被外部替换成最新的对象.
 * @auth Jeremy
 * @date 2021年6月6日下午9:29:12
 */
public abstract class AbstractCodeGenerator implements ICodeGenerator{
	
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired protected SettingConfig setting;
	@Autowired protected TemplateService templateService;
	
	
	@Override
	public int type() {
		return CODE;
	}
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
	public void generate(TableFreemarkerDto dto) {
		String codePath = setting.getCodePath();
		String entityName = dto.getEntity().getEntityName();
		//生成路径
		String path = codePath.concat(File.separator)
				.concat(entityName.toLowerCase()).concat(File.separator)
				.concat(getChildDir());
		File file = new File(path);
		file.mkdirs();
		//实例化文件
		String fileName = path.concat(File.separator).concat(getFileNameFrontPart())
				.concat(entityName).concat(getFileNameLatterPart())
				.concat(getFileNameSuffix());
		templateService.printer(dto, fileName, getProtoName(dto));
		logger.info("成功生成文件{}", fileName);
	}
	
}
