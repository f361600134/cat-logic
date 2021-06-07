package com.cat.zproto.assist.generator.type;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractDefaultGenerator;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.table.TableFreemarkerDto;

@Component
public class GenEntityPB extends AbstractDefaultGenerator{

	@Override
	public String getChildDir() {
		return "/proto";
	}

	@Override
	public String getFileNameLatterPart() {
		return "Builder";
	}

	@Override
	public String getProtoName(TableFreemarkerDto dto) {
		return "EntityPBBuilder.ftl";
	}
	
	@Override
	public void generate(TableFreemarkerDto dto) {
		//生成路径
		String codePath = setting.getCodePath();
		String entityName = dto.getEntity().getEntityName();
		String path = codePath.concat(File.separator)
				.concat(entityName.toLowerCase()).concat(File.separator)
				.concat(getChildDir());
		File file = new File(path);
		file.mkdirs();
		List<ProtocolStructure> protoStructList = dto.getProtoPBStructList();
		for (ProtocolStructure struct : protoStructList) {
			String clazzName = getFileNameFrontPart().concat(struct.getName()).concat(getFileNameLatterPart());
			//赋值享元对象
			dto.setStruct(struct);
			dto.setClazzName(clazzName);
			//生成
			String fileName = path.concat(File.separator).concat(clazzName)
					.concat(getFileNameSuffix());
			templateService.printer(dto, fileName, getProtoName(dto));
		}
	}

}
