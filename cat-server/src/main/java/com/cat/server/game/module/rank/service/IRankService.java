package com.cat.server.game.module.rank.service;

import com.cat.server.game.module.rank.domain.RankType;

/**
 * Rank接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IRankService {
	
	/**
	 * 通过排行榜类型获取指定id的排名
	 * @param rankType 排行榜类型
	 * @param uniqueId 唯一id
	 * @return  
	 * @return int  
	 * @date 2021年3月21日下午5:09:20
	 */
	public int getRank(RankType rankType, long uniqueId);

}