package com.cat.zproto.assist.generator.type;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractDefaultGenerator;
import com.cat.zproto.domain.table.TableFreemarkerDto;

@Component
public class GenEntityManager extends AbstractDefaultGenerator{

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
