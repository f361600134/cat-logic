package com.cat.zproto.assist.generator.code;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractCodeGenerator;
import com.cat.zproto.dto.TableFreemarkerDto;

@Component
public class GenEntityController extends AbstractCodeGenerator{

	@Override
	public String getChildDir() {
		return "";
	}
	
	@Override
	public String getFileNameLatterPart() {
		return "Controller";
	}

	@Override
	public String getProtoName(TableFreemarkerDto dto) {
		return "EntityContorller.ftl";
	}

}
