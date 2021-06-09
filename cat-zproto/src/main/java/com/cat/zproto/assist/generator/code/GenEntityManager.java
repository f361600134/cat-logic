package com.cat.zproto.assist.generator.code;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractCodeGenerator;
import com.cat.zproto.dto.TableFreemarkerDto;

@Component
public class GenEntityManager extends AbstractCodeGenerator{

	@Override
	public String getChildDir() {
		return "";
	}

	@Override
	public String getFileNameLatterPart() {
		return "Manager";
	}

	@Override
	public String getProtoName(TableFreemarkerDto dto) {
		return "EntityManager.ftl";
	}

}
