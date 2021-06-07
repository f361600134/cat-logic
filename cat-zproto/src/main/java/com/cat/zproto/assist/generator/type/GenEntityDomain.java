package com.cat.zproto.assist.generator.type;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractDefaultGenerator;
import com.cat.zproto.domain.table.TableFreemarkerDto;

@Component
public class GenEntityDomain extends AbstractDefaultGenerator{

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
