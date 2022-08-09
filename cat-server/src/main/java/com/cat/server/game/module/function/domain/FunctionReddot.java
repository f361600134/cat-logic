package com.cat.server.game.module.function.domain;

import java.util.ArrayList;
import java.util.List;

import com.cat.server.game.data.proto.PBFunction.PBFunctionReddotDto;
import com.cat.server.game.module.function.proto.PBFunctionReddotDtoBuilder;

/**
 * 功能红点对象
 * 
 * @author Jeremy Feng.
 */
public class FunctionReddot {

	/** 红点id */
	private int reddotId;

	/**
	 * 红点值<br>
	 * 这里存储的统一是红点总数量,即某个条件下的产生的红点数量<br>
	 * 对于称号, 聊天气泡, 头像框这类系统, 需要在指定红点位置. 所以选用此容器存储其id.
	 */
	private List<Integer> reddotValues = new ArrayList<>();

	public FunctionReddot() {
	}

	public FunctionReddot(int reddotId) {
		this.reddotId = reddotId;
	}

	public int getReddotId() {
		return reddotId;
	}

	public void setReddotId(int reddotId) {
		this.reddotId = reddotId;
	}

	public List<Integer> getReddotValues() {
		return reddotValues;
	}

	public void setReddotValues(List<Integer> reddotValues) {
		this.reddotValues = reddotValues;
	}
	
	/**
	 * 创建一个功能红点对象
	 * @return
	 */
	public static FunctionReddot create(int reddotId) {
		return new FunctionReddot(reddotId);
	}

	/**
	 * 构建指定的红点信息
	 * 
	 * @return
	 */
	public PBFunctionReddotDto toProto() {
		PBFunctionReddotDtoBuilder builder = PBFunctionReddotDtoBuilder.newInstance();
		builder.setReddotId(reddotId);
		builder.setNumber(reddotValues.size());
		builder.addAllExtendValues(reddotValues);
		return builder.build();
	}

//	/**
//	 * 构建所有的红点信息
//	 * @return
//	 */
//	public Collection<PBFunctionReddotDto> toProtos() {
//		List<PBFunctionReddotDto> result = new ArrayList<>();
//		this.getBean().getReddotMap().forEach((k,v)->{
//			PBFunctionReddotDtoBuilder builder = PBFunctionReddotDtoBuilder.newInstance();
//			builder.setReddotId(k);
//			builder.setNumber(v);
//			result.add(builder.build());
//		});
//		return result;
//	}

}
