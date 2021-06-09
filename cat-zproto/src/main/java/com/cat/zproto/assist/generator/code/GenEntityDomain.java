package com.cat.zproto.assist.generator.code;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractCodeGenerator;
import com.cat.zproto.dto.TableFreemarkerDto;

@Component
public class GenEntityDomain extends AbstractCodeGenerator{

	@Override
	public String getChildDir() {
		return "/domain";
	}

	@Override
	public String getFileNameLatterPart() {
		return "Domain";
	}

	@Override
	public String getProtoName(TableFreemarkerDto dto) {
		if (dto.getModule().isOne2one()) {
			return "EntityDomain1.ftl";
		}else {
			return "EntityDomain2.ftl";
		}
	
	}

}
