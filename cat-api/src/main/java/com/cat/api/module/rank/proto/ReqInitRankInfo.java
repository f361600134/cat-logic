package com.cat.api.module.rank.proto;

import com.cat.api.ProtocolId;
import com.cat.api.core.AbstractStuffProto;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求初始化排行榜, 不需要返回<br>
 * game -> rank, game节点连接rank节点身份验证成功后, game节点的数据初始化到rank节点<br>
 * 初始化排行, 不需要返回结果
 * @author Jeremy
 */
public class ReqInitRankInfo extends AbstractStuffProto {
	/**
	 * 排行榜列表
	 */
	private List<PBRankList> rankLists;

	public ReqInitRankInfo() {
		this.rankLists = new ArrayList<>();
	}

	public List<PBRankList> getRankInfos() {
		return rankLists;
	}

	public void setRankLists(List<PBRankList> rankLists) {
		this.rankLists = rankLists;
	}
	
	public void addRankList(PBRankList rankList) {
		this.rankLists.add(rankList);
	}
	
	public static ReqInitRankInfo create() {
		return new ReqInitRankInfo();
	}

	@Override
	public int protocol() {
		return ProtocolId.ReqInitRankInfo;
	}
}
