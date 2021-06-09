package com.cat.zproto.assist.generator.proto;

import java.io.File;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractProtoGenerator;
import com.cat.zproto.domain.proto.ProtocolConstant;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.dto.ProtoDto;
import com.cat.zproto.dto.TableFreemarkerDto;

@Component
public class GenEntityAck extends AbstractProtoGenerator{

	@Override
	public String getChildDir() {
		return "/proto";
	}

	@Override
	public String getFileNameLatterPart() {
		return "Resp";
	}

	@Override
	public String getProtoName() {
		return "EntityAckBuilder.ftl";
	}
	
	@Override
	public void generate(ProtocolObject protocolObj) {
		//生成路径
		String codePath = setting.getCodePath();
		String entityName = protocolObj.getModuleName();
		String path = codePath.concat(File.separator)
				.concat(entityName.toLowerCase()).concat(File.separator)
				.concat(getChildDir());
		File file = new File(path);
		file.mkdirs();
		
		ProtoDto dto = new ProtoDto();
		dto.setProtocolObj(protocolObj);
		dto.setModuleName(entityName);
		Collection<ProtocolStructure> protoStructList = protocolObj.getStructures().values();
		for (ProtocolStructure struct : protoStructList) {
			if (struct.getName().startsWith(ProtocolConstant.RESP_PREFIX)) {
				String clazzName = getFileNameFrontPart().concat(struct.getName()).concat(getFileNameLatterPart());
				//赋值享元对象
				dto.setStruct(struct);
				dto.setClazzName(clazzName);
				//生成
				String fileName = path.concat(File.separator).concat(clazzName)
						.concat(getFileNameSuffix());
				templateService.printer(dto, fileName, getProtoName());
			}
		}
	}

}
