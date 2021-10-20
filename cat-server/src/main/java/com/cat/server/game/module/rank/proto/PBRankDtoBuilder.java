package com.cat.server.game.module.rank.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBPlayer.PBPlayerProfile;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBRank.PBRankDto;

/**
* PBRankDtoBuilder
* @author Jeremy
*/
public class PBRankDtoBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(PBRankDtoBuilder.class);
	
	private final PBRankDto.Builder builder = PBRankDto.newBuilder();
	
	public PBRankDtoBuilder() {}
	
	public static PBRankDtoBuilder newInstance() {
		return new PBRankDtoBuilder();
	}
	
	public PBRankDto build() {
		return builder.build();
	}
	
	/** 唯一id**/
	public void setUniqueId(long value){
		this.builder.setUniqueId(value);
	}
	/** 排行榜值**/
	public void setValue(long value){
		this.builder.setValue(value);
	}
	/** 玩家基本信息**/
	public void setPlayerProfile(PBPlayerProfile value){
		this.builder.setPlayerProfile(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
