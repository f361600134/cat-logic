package com.cat.api.module.rank.proto;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.cat.api.ProtocolId;
import com.cat.api.core.AbstractStuffProto;

/**
 * 请求新加排行榜数据到指定排行榜<br>
 * game -> rank, game节点如果有需要更新到排行榜的数据, 直接更新至rank节点<br>
 * 通知新增排行数据到排行榜节点, 不需要返回
 * 
 * @author Jeremy
 */
public class ReqAddDataToRank extends AbstractStuffProto {
	/**
	 * 排行榜列表
	 */
	private List<PBRankList> rankLists;

	public ReqAddDataToRank() {
	}

	public static ReqAddDataToRank create() {
		return new ReqAddDataToRank();
	}

	public List<PBRankList> getRankLists() {
		return rankLists;
	}

	public void setRankList(List<PBRankList> rankLists) {
		this.rankLists = rankLists;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}

	public int protocol() {
		return ProtocolId.ReqAddDataToRank;
	}
}
