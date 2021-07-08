package com.cat.server.game.module.${entity.getEntityName()?lower_case}.domain;

import com.cat.server.core.server.IPersistence; 

/**
* @author Jeremy
*/
@PO(name = "${entity.getTablName()}")
public class ${entity.getEntityName()} extends ${entity.getEntityName()}Po implements IPersistence{

	public ${entity.getEntityName()}() {

	}

	
	public String aaa() {
		return "bbb";
	}

}