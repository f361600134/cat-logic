package com.cat.zproto.assist.generator.type;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractDefaultGenerator;
import com.cat.zproto.domain.table.TableFreemarkerDto;

@Component
public class GenEntityController extends AbstractDefaultGenerator{

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
