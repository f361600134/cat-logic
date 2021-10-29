package com.cat.api.module.rank.proto;

import java.util.List;

import com.cat.api.ProtocolId;
import com.cat.api.core.AbstractStuffProto;

/**
 * 请求新加排行榜数据到指定排行榜<br>
 * game -> rank, game节点如果有需要更新到排行榜的数据, 直接更新至rank节点<br>
 * 通知新增排行数据到排行榜节点, 不需要返回
 * @author Jeremy
 */
public class ReqAddDataToRank extends AbstractStuffProto {

    /**
     * 排行榜类型id
     */
    private int rankType;
    
    /**
	 * 排行榜列表
	 */
	private List<PBRank> rankInfos;
	
	public ReqAddDataToRank() {}

    public int getRankType() {
        return rankType;
    }

    public void setRankType(int rankType) {
        this.rankType = rankType;
    }
    
    public List<PBRank> getRankInfos() {
		return rankInfos;
	}

	public void setRankInfos(List<PBRank> rankInfos) {
		this.rankInfos = rankInfos;
	}

	public int protocol() {
        return ProtocolId.ReqAddDataToRank;
    }
}
