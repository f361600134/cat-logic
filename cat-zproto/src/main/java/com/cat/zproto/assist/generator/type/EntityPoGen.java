package com.cat.zproto.assist.generator.type;

import org.springframework.stereotype.Component;

import com.cat.zproto.assist.generator.AbstractGenerator;

@Component
public class EntityPoGen extends AbstractGenerator{

	@Override
	public String getChildDir() {
		return "/domain";
	}

	@Override
	public String getFileNameLatterPart() {
		return "Po";
	}

	@Override
	public String getProtoName() {
		return "EntityPo.ftl";
	}

}
