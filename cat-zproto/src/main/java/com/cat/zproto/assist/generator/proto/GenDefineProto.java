package com.cat.zproto.assist.generator.proto;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.IDefineProtoGenerator;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.system.SettingVersion;
import com.cat.zproto.domain.template.TemplateStruct;
import com.cat.zproto.dto.ProtoDto;
import com.cat.zproto.enums.TemplateEnum;
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
public class GenDefineProto implements IDefineProtoGenerator{
	
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired protected SettingConfig setting;
	@Autowired protected TemplateService templateService;
	
	/**
	 * 享元变量
	 */
	private ProtocolStructure protoStruct;

	@Override
	public String getChildDir() {
		return "/proto";
	}

	@Override
	public String getFileNameLatterPart() {
		return setting.getProto().getLatterPart();
	}

	@Override
	public String getProtoName(TemplateStruct struct) {
		return struct.getFtlName();
	}
	
	/**
	 * 默认返回无前部分补充
	 */
	@Override
	public String getFileNameFrontPart() {
//		String protoStructName = protoStruct.getName();
//		if (protoStructName.startsWith(setting.getProto().getRespPrefix())) {
//			return setting.getProto().getRespPrefix();
//		}else if(protoStructName.startsWith(setting.getProto().getReqPrefix())){
//			return setting.getProto().getReqPrefix();
//		}else if (protoStructName.startsWith(setting.getProto().getPbPrefix())) {
//			return setting.getProto().getPbPrefix();
//		}
		return StringUtils.EMPTY;
	}
	
	@Override
	public String getFileNameSuffix() {
		return ".java";
	}
	
	@Override
	public int type() {
		return TemplateEnum.PROTO.getType();
	}
	
	@Override
	public void generate(String version, TemplateStruct struct, ProtocolObject protocolObj) {
		//生成路径
		SettingVersion versionInfo = setting.getVersionInfo().get(version);
		String entityName = protocolObj.getModuleName();
		String path = versionInfo.getCodePath().concat(File.separator)
				.concat(entityName.toLowerCase()).concat(File.separator)
				.concat(getChildDir());
		File file = new File(path);
		file.mkdirs();
		
		ProtoDto dto = new ProtoDto();
		dto.setProtocolObj(protocolObj);
		dto.setModuleName(entityName);
		
		Collection<ProtocolStructure> protoStructList = protocolObj.getStructures().values();
		for (ProtocolStructure protoStruct : protoStructList) {
			this.protoStruct = protoStruct;
				String clazzName = getFileNameFrontPart().concat(protoStruct.getName()).concat(getFileNameLatterPart());
				//赋值享元对象
				dto.setStruct(protoStruct);
				dto.setClazzName(clazzName);
				//生成
				String fileName = path.concat(File.separator).concat(clazzName)
						.concat(getFileNameSuffix());
				templateService.printer(dto, fileName, getProtoName(struct));
		}
	}

}