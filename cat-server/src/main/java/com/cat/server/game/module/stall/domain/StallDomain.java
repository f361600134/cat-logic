package com.cat.server.game.module.stall.domain;

import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.helper.result.ErrorCode;



/**
 * 摆摊域, 需要根据类型, 缓存对应的数据集. 用于快速查询
* @param Integer serverId
* @param Long playerId
* @param Stall 摆摊对象
* @author Jeremy
*/
public class StallDomain extends AbstractModuleMultiDomain<Integer, Long, Stall> {

	private static final Logger log = LoggerFactory.getLogger(StallDomain.class);
	
	public StallDomain(){
		
	}

	
	////////////业务代码////////////////////
	
	/**
	 * 根据关键字查询
	 * @param keywords
	 * @return
	 */
	public Collection<StallCommodityInfo> getStallCommoditys(String keywords){
		return Collections.emptyList();
	}
	
	/**
	 * 请求购买摆摊商品
	 * @param uniqueId 商品唯一id
	 * @param number 数量
	 * @return
	 */
	public ErrorCode buy(long playerStallId, long uniqueId, int number) {
		
		return ErrorCode.SUCCESS;
	}
	
//	/**
//	 * 创建一个摆摊对象
//	 * @param playerId 玩家id
//	 * @param serverId 所在服务器
//	 * @return
//	 */
//	public Stall create(long playerId, int serverId) {
//		Stall stall = Stall.create(playerId, serverId);
//		this.putBean(stall.getPlayerId(), stall);
//		return stall;
//	}
	
	
}
