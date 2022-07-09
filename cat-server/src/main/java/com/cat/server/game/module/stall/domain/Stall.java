package com.cat.server.game.module.stall.domain;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence; 

/**
* @author Jeremy
*/
@PO(name = "stall")
public class Stall extends StallPo implements IPersistence{
	
	@Column(PROP_DATA)
	private StallCommodityInfo stallCommodityInfo;
	
	public Stall() {

	}
	
	public Stall(long playerId) {
		this.playerId = playerId;
	}

	public StallCommodityInfo getStallCommodityInfo() {
		return stallCommodityInfo;
	}

	public void setStallCommodityInfo(StallCommodityInfo stallCommodityInfo) {
		this.stallCommodityInfo = stallCommodityInfo;
	}
	
	public static Stall create(long playerId, int serverId) {
		Stall stall = new Stall(playerId);
		stall.setInitServerId(serverId);
		stall.setCurServerId(serverId);
		return stall;
	}
	
}
