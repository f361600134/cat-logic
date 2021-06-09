package com.cat.zproto.assist.generator.code;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractCodeGenerator;
import com.cat.zproto.dto.TableFreemarkerDto;

@Component
public class GenEntityPo extends AbstractCodeGenerator{

	@Override
	public String getChildDir() {
		return "/domain";
	}

	@Override
	public String getFileNameLatterPart() {
		return "Po";
	}

	@Override
	public String getProtoName(TableFreemarkerDto dto) {
		return "EntityPo.ftl";
	}

}
