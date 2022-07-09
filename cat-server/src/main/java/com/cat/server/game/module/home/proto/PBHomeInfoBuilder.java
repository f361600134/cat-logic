package com.cat.server.game.module.home.proto;

import java.util.Collection;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBCommon.PBPairInfo;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.PBHomeBetsuin;
import com.cat.server.game.data.proto.PBHome.PBHomeInfo;
import com.cat.server.game.data.proto.PBHome.PBHomeLayoutScheme;

/**
* PBHomeInfoBuilder
* @author Jeremy
*/
public class PBHomeInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBHomeInfoBuilder.class);
	
	private final PBHomeInfo.Builder builder = PBHomeInfo.newBuilder();
	
	public PBHomeInfoBuilder() {}
	
	public static PBHomeInfoBuilder newInstance() {
		return new PBHomeInfoBuilder();
	}
	
	public PBHomeInfo build() {
		return builder.build();
	}
	
	/** 地图 id**/
	public void setMapId(int value){
		this.builder.setMapId(value);
	}
	/** 家园等级**/
	public void setLevel(int value){
		this.builder.setLevel(value);
	}
	/** 家园名字**/
	public void setName(String value){
		this.builder.setName(value);
	}
	/** 最后改名时间**/
	public void setLastRenameTime(long value){
		this.builder.setLastRenameTime(value);
	}
	/** 经验**/
	public void setExp(int value){
		this.builder.setExp(value);
	}
	/** 家具资源**/
	public void addFurnitureRes(PBPairInfo value){
		this.builder.addFurnitureRes(value);
	}
	
	public void addAllFurnitureRes(Collection<PBPairInfo> value){
		this.builder.addAllFurnitureRes(value);
	}
	/** 布局方案信息**/
	public void setLayoutScheme(PBHomeLayoutScheme value){
		this.builder.setLayoutScheme(value);
	}
	/** 主家园信息**/
	public void setMainBetsuin(PBHomeBetsuin value){
		this.builder.setMainBetsuin(value);
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
