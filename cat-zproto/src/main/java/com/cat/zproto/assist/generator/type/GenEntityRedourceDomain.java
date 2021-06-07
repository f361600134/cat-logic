package com.cat.zproto.assist.generator.type;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractDefaultGenerator;
import com.cat.zproto.domain.table.TableFreemarkerDto;

@Component
public class GenEntityRedourceDomain extends AbstractDefaultGenerator{

	@Override
	public String getChildDir() {
		return "/domain";
	}

	@Override
	public String getFileNameLatterPart() {
		return "ResourceDomain";
	}

	@Override
	public String getProtoName(TableFreemarkerDto dto) {
		return "EntityResourceDomain.ftl";
	}

}
