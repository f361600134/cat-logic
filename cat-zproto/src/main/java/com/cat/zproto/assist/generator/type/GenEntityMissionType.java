package com.cat.zproto.assist.generator.type;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractDefaultGenerator;
import com.cat.zproto.domain.table.TableFreemarkerDto;

@Component
public class GenEntityMissionType extends AbstractDefaultGenerator{

	@Override
	public String getChildDir() {
		return "/domain";
	}

	@Override
	public String getFileNameLatterPart() {
		return "MissionType";
	}

	@Override
	public String getProtoName(TableFreemarkerDto dto) {
		return "EntityMissionType.ftl";
	}

}
