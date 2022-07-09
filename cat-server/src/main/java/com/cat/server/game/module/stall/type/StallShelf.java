package com.cat.server.game.module.stall.type;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.resource.IResource;
import com.cat.server.game.module.stall.domain.Stall;
import com.cat.server.utils.Pair;

/**
 * 摆摊货架信息
 * @author Jeremy
 */
@Component
public class StallShelf implements ILifecycle{
	
	private static final Logger log = LoggerFactory.getLogger(StallShelf.class);
	
	@Autowired private List<IStallCommodity> stallList;
	
	public ErrorCode addCommodity(Stall stall, int configId, long uniqueId) {
		int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
		Optional<IStallCommodity> opt = stallList.stream().filter(s->s.getType() == resourceType).findAny();
		if (!opt.isPresent()) {
			return ErrorCode.STALL_INVALID_COMMODITY;
		}
		IStallCommodity stallCommodity = opt.get();
		stallCommodity.add(stall, uniqueId);
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 添加摆摊商品到地摊
	 * @param playerId 玩家id
	 * @param commodities 要检测的商品列表
	 * @return
	 */
	public ErrorCode addCommodities(Stall stall, List<Pair<Integer, Long>> commodities) {
		for (Pair<Integer, Long> pair : commodities) {
			final int configId = pair.getKey();
    		final long uniqueId = pair.getValue();
    		ErrorCode errorCode = this.addCommodity(stall, configId, uniqueId);
    		if (!errorCode.isSuccess()) {
    			log.info("addCommodities error, playerId:{}, configId:{}, uniqueId:{}"
    					,stall.getPlayerId(), configId, uniqueId);
    			continue;
			}
		}
		return ErrorCode.SUCCESS;
	}
	
	public ErrorCode checkCommodity(long playerId, int configId, long uniqueId) {
		int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
		Optional<IStallCommodity> opt = stallList.stream().filter(s->s.getType() == resourceType).findAny();
		if (!opt.isPresent()) {
			return ErrorCode.STALL_INVALID_COMMODITY;
		}
		IStallCommodity stallCommodity = opt.get();
		return stallCommodity.check(playerId, uniqueId);
	}
	
	/**
	 * 检测商品
	 * @param playerId 玩家id
	 * @param commodities 要检测的商品列表
	 * @return
	 */
	public ErrorCode checkCommodities(long playerId, List<Pair<Integer, Long>> commodities) {
		if (commodities.isEmpty()) return ErrorCode.STALL_EMPTY;
		
		for (Pair<Integer, Long> pair : commodities) {
			final int configId = pair.getKey();
    		final long uniqueId = pair.getValue();
    		ErrorCode errorCode = this.checkCommodity(playerId, configId, uniqueId);
    		if (!errorCode.isSuccess()) {
				return errorCode;
			}
		}
		return ErrorCode.SUCCESS;
	}
	
	public ErrorCode removeCommodity(Stall stall, int configId, long uniqueId) {
		int resourceType = configId / IResource.RESOURC_TYPE_SPLIT;
		Optional<IStallCommodity> opt = stallList.stream().filter(s->s.getType() == resourceType).findAny();
		if (!opt.isPresent()) {
			return ErrorCode.STALL_INVALID_COMMODITY;
		}
		IStallCommodity stallCommodity = opt.get();
		stallCommodity.remove(stall, uniqueId);
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 添加摆摊商品到地摊
	 * @param playerId 玩家id
	 * @param commodities 要检测的商品列表
	 * @return
	 */
	public ErrorCode removeCommodities(Stall stall, List<Pair<Integer, Long>> commodities) {
		for (Pair<Integer, Long> pair : commodities) {
			final int configId = pair.getKey();
    		final long uniqueId = pair.getValue();
    		ErrorCode errorCode = this.removeCommodity(stall, configId, uniqueId);
    		if (!errorCode.isSuccess()) {
    			log.info("removeCommodities error, playerId:{}, configId:{}, uniqueId:{}"
    					,stall.getPlayerId(), configId, uniqueId);
				continue;
			}
		}
		return ErrorCode.SUCCESS;
	}
	
}
