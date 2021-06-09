package com.cat.zproto.assist.generator.code;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractCodeGenerator;
import com.cat.zproto.dto.TableFreemarkerDto;

@Component
public class GenEntityServiceInterface extends AbstractCodeGenerator{

	@Override
	public String getChildDir() {
		return "";
	}
	
	@Override
	public String getFileNameFrontPart() {
		return "I";
	}

	@Override
	public String getFileNameLatterPart() {
		return "Service";
	}

	@Override
	public String getProtoName(TableFreemarkerDto dto) {
		return "EntityServiceInterface.ftl";
	}

}
