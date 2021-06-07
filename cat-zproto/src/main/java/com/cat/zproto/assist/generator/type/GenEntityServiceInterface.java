package com.cat.zproto.assist.generator.type;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractDefaultGenerator;
import com.cat.zproto.domain.table.TableFreemarkerDto;

@Component
public class GenEntityServiceInterface extends AbstractDefaultGenerator{

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
