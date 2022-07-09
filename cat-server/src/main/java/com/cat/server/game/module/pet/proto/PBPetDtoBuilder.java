package com.cat.server.game.module.pet.proto;

import java.util.Collection;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBCommon.PBPairInfo;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPet.PBPetDto;

/**
* PBPetDtoBuilder
* @author Jeremy
*/
public class PBPetDtoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBPetDtoBuilder.class);
	
	private final PBPetDto.Builder builder = PBPetDto.newBuilder();
	
	public PBPetDtoBuilder() {}
	
	public static PBPetDtoBuilder newInstance() {
		return new PBPetDtoBuilder();
	}
	
	public PBPetDto build() {
		return builder.build();
	}
	
	/** 唯一id**/
	public void setUniqueId(long value){
		this.builder.setUniqueId(value);
	}
	/** 配置id**/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	/** 前缀配置id**/
	public void setPrefixId(int value){
		this.builder.setPrefixId(value);
	}
	/** 宠物等级**/
	public void setLevel(int value){
		this.builder.setLevel(value);
	}
	/** 宠物经验**/
	public void setExp(long value){
		this.builder.setExp(value);
	}
	/** 昵称,玩家可更改**/
	public void setNickName(String value){
		this.builder.setNickName(value);
	}
	/** 性别**/
	public void setGender(int value){
		this.builder.setGender(value);
	}
	/** 生育次数**/
	public void setReproductiveNumber(int value){
		this.builder.setReproductiveNumber(value);
	}
	/** 基础属性**/
	public void addBaseAttributes(PBPairInfo value){
		this.builder.addBaseAttributes(value);
	}
	
	public void addAllBaseAttributes(Collection<PBPairInfo> value){
		this.builder.addAllBaseAttributes(value);
	}
	/** 属性资质**/
	public void addAptitudeAttributes(int value){
		this.builder.addAptitudeAttributes(value);
	}
	
	public void addAllAptitudeAttributes(Collection<java.lang.Integer> value){
		this.builder.addAllAptitudeAttributes(value);
	}
	/** 当前拥有技能**/
	public void addSkills(PBPairInfo value){
		this.builder.addSkills(value);
	}
	
	public void addAllSkills(Collection<PBPairInfo> value){
		this.builder.addAllSkills(value);
	}
	/** 信任度**/
	public void setTrust(int value){
		this.builder.setTrust(value);
	}
	/** 饥饿点**/
	public void setHungry(int value){
		this.builder.setHungry(value);
	}
	/** 是否激活,true:是,激活后才下发详细信息**/
	public void setActive(boolean value){
		this.builder.setActive(value);
	}
	/** 属性点,含等级属性点,前缀属性点**/
	public void setAttrPoint(int value){
		this.builder.setAttrPoint(value);
	}
	/** 是否休眠,true为休眠**/
	public void setDormancy(boolean value){
		this.builder.setDormancy(value);
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
