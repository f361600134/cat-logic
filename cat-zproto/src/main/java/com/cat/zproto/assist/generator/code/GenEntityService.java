package com.cat.zproto.assist.generator.code;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractCodeGenerator;
import com.cat.zproto.dto.TableFreemarkerDto;

@Component
public class GenEntityService extends AbstractCodeGenerator{

	@Override
	public String getChildDir() {
		return "";
	}
	
	@Override
	public String getFileNameLatterPart() {
		return "Service";
	}

	@Override
	public String getProtoName(TableFreemarkerDto dto) {
		return "EntityService.ftl";
	}

}
