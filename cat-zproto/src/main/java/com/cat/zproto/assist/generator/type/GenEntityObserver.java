package com.cat.zproto.assist.generator.type;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractDefaultGenerator;
import com.cat.zproto.domain.table.TableFreemarkerDto;

@Component
public class GenEntityObserver extends AbstractDefaultGenerator{

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
