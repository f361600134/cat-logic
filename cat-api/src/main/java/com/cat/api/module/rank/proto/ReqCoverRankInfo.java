package com.cat.api.module.rank.proto;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.cat.api.ProtocolId;
import com.cat.api.core.AbstractStuffProto;

/**
 * 请求覆盖排行榜数据, 不需要返回<br>
 * rank->game, rank节点定时同步一次最新的排行榜数据至game节点
 * 
 * @author Jeremy
 */
public class ReqCoverRankInfo extends AbstractStuffProto {

	/**
	 * 排行榜列表
	 */
	private List<PBRankList> rankList;

	public ReqCoverRankInfo() {
	}
	
	public ReqCoverRankInfo(List<PBRankList> rankList) {
		this.rankList = rankList;
	}

	public List<PBRankList> getRankList() {
		return rankList;
	}

	public void setRankList(List<PBRankList> rankList) {
		this.rankList = rankList;
	}
	
	public static ReqCoverRankInfo create(List<PBRankList> rankList) {
		return new ReqCoverRankInfo(rankList);
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

	public int protocol() {
		return ProtocolId.ReqCoverRankInfo;
	}
}
