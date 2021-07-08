package com.cat.zproto.assist.generator.code;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.IDefineGenerator;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.system.SettingVersion;
import com.cat.zproto.domain.template.TemplateStruct;
import com.cat.zproto.dto.TableFreemarkerDto;
import com.cat.zproto.service.TemplateService;

/**
 * 自定义代码生成器.
 * 两种方案, 配置一下内容名
 * 1. template文件名, (xxx.ftl), 文件名包含所需要的信息, 通过文件名解析
 * 2. 对于已经生成的文件, 程序做一个设置功能, 可以对已有的模块, 设置其生成文件的详细信息
 * 目前使用方案1. 方案二优雅, 但是有点麻烦, 还要专门做一个设置页面
 * @author Administrator
 */
@Component
public class GenDefine implements IDefineGenerator{

	/**
	 * 命名关键字
	 */
	private static final String DEFINE = "Entity";
	
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired protected SettingConfig setting;
	@Autowired protected TemplateService templateService;
	
	/**
	 * 享元变量, 每次生成时为新的成员变量
	 */
	private TemplateStruct struct;
	
	@Override
	public int type() {
		return struct.getType();
	}

	@Override
	public String getChildDir() {
		return StringUtils.EMPTY;
	}

	/**
	 * 解析文件名前缀, 根据协议文件解析
	 */
	@Override
	public String getFileNameFrontPart() {
		String name = struct.getName();
		int index = name.indexOf(DEFINE);
		String front = name.substring(0, index);
		return front;
	}

	/**
	 * 后缀如果有数字,移除掉数字
	 */
	@Override
	public String getFileNameLatterPart() {
		String name = struct.getName();
		int startIndex = name.indexOf(DEFINE);
		startIndex = startIndex + DEFINE.length();
//		int endIndex = name.indexOf(".");
		int endIndex = name.length();
		String behind = name.substring(startIndex, endIndex);
		behind = behind.replaceAll("\\d", "");
		return behind;
	}
	
	@Override
	public String getFileNameSuffix() {
		return ".java";
	}

	@Override
	public String getProtoName(TableFreemarkerDto dto) {
		return struct.getFtlName();
	}

	@Override
	public void generate(String version, TemplateStruct struct, TableFreemarkerDto dto) {
		if (struct == null) {
			return;
		}
		this.struct = struct;
		SettingVersion versionInfo = setting.getVersionInfo().get(version);
		String entityName = dto.getEntity().getEntityName();
		//生成路径
		String path = versionInfo.getCodePath().concat(File.separator)
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
