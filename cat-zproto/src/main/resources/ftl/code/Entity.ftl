package com.cat.server.game.module.${entityName?lower_case}.domain;

import com.cat.server.core.server.IPersistence;

/**
* @author Jeremy
*/
@PO(name = "${entity.getTablName()}")
public class ${entity.getEntityName()} extends ${entity.getEntityName()}Po implements IPersistence{

	public ${entity.getEntityName()}() {

	}
	
	public ${entity.getEntityName()}(long playerId) {
		this.playerId = playerId;
	}
	
	@Override
	public Object key() {
		//TODO
		//return getPlayerId();
	}
	
	public String keyColumn() {
		//TODO 
		//return PROP_PLAYERID;
	}
	
}
