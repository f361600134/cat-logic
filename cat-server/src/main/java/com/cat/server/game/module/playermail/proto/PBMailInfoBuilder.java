package com.cat.server.game.module.playermail.proto;

import java.util.Collection;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBItem.PBPairInfo;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayerMail.PBMailInfo;

/**
* PBMailInfoBuilder
* @author Jeremy
*/
public class PBMailInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBMailInfoBuilder.class);
	
	private final PBMailInfo.Builder builder = PBMailInfo.newBuilder();
	
	public PBMailInfoBuilder() {}
	
	public static PBMailInfoBuilder newInstance() {
		return new PBMailInfoBuilder();
	}
	
	public PBMailInfo build() {
		return builder.build();
	}
	
	/** Mail id**/
	public void setMailId(long value){
		this.builder.setMailId(value);
	}
	/** 标题**/
	public void setTitle(String value){
		this.builder.setTitle(value);
	}
	/** 聊天内容**/
	public void setContent(String value){
		this.builder.setContent(value);
	}
	/** 附件**/
	public void addRewards(PBPairInfo value){
		this.builder.addRewards(value);
	}
	
	public void addAllRewards(Collection<PBPairInfo> value){
		this.builder.addAllRewards(value);
	}
	/** 0=未读取;1=已读取**/
	public void setState(int value){
		this.builder.setState(value);
	}
	/** yyyy-MM-dd**/
	public void setDate(String value){
		this.builder.setDate(value);
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
