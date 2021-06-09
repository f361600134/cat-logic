package com.cat.zproto.assist.generator.code;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractCodeGenerator;
import com.cat.zproto.dto.TableFreemarkerDto;

@Component
public class GenEntityObserver extends AbstractCodeGenerator{

	@Override
	public String getChildDir() {
		return "/event";
	}

	@Override
	public String getFileNameLatterPart() {
		return "Observer";
	}

	@Override
	public String getProtoName(TableFreemarkerDto dto) {
		return "EntityObserver.ftl";
	}

}
