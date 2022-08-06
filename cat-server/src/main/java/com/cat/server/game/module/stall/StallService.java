package com.cat.server.game.module.stall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.api.core.task.TokenTaskQueueExecutor;
import com.cat.server.common.ServerConfig;
import com.cat.server.game.data.proto.PBStall.PBStallShelfDto;
import com.cat.server.game.data.proto.PBStall.ReqStallBuy;
import com.cat.server.game.data.proto.PBStall.ReqStartStall;
import com.cat.server.game.helper.PropertiesType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.IResourceGroupService;
import com.cat.server.game.module.stall.domain.Stall;
import com.cat.server.game.module.stall.domain.StallBooth;
import com.cat.server.game.module.stall.domain.StallDomain;
import com.cat.server.game.module.stall.proto.RespStallBuyBuilder;
import com.cat.server.game.module.stall.proto.RespStartStallBuilder;
import com.cat.server.game.module.stall.type.StallShelf;
import com.cat.server.utils.Pair;


/**
 * Stall控制器
 * @author Jeremy
 */
@Service
public class StallService implements IStallService {
	
	private static final Logger log = LoggerFactory.getLogger(StallService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private ServerConfig serverConfig;
	
	@Autowired private StallManager stallManager;
	
	@Autowired private StallShelf stallShelf;
	
	@Autowired private IResourceGroupService resourceGroupService;
	
	/**	公共的线程池处理器*/
    @Autowired private TokenTaskQueueExecutor defaultExecutor;
	
	
	/////////////接口方法////////////////////////
	
	/**
     * 请求开始摆摊
	 * @throws Exception
     */
    public ErrorCode reqStartStall(long playerId, ReqStartStall req, RespStartStallBuilder ack) throws Exception{
    	//TODO 校验名字
    	final String name = req.getName();
    	//TODO 检测名敏感字
    	
    	List<PBStallShelfDto> stallShelfDtos = req.getCommoditiesList();
    	if (stallShelfDtos.isEmpty()) {
			return ErrorCode.STALL_EMPTY;
		}
    	List<Pair<Integer, Long>> pairs = new ArrayList<>();
    	for (PBStallShelfDto dto : stallShelfDtos) {
    		pairs.add(Pair.of(dto.getConfigId(), dto.getUniqueId()));
		}
    	
    	final int serverId = serverConfig.getServerId();
    	StallDomain domain = stallManager.getDomain(serverId);
    	if (domain == null) {
			return ErrorCode.DOMAIN_IS_NULL;
		}
    	//获取玩家的摊位信息
    	if (domain.getBean(playerId) != null) {
    		return ErrorCode.STALL_REPEAT;
		}
    	//检测商品是否可以加入到摆摊系统
    	ErrorCode errorCode = stallShelf.checkCommodities(playerId, pairs);
    	if (!errorCode.isSuccess()) {
			return errorCode;
		}
    	//创建一个摆摊对象
    	Stall stall = Stall.create(playerId, serverId);
    	stallShelf.addCommodities(stall, pairs);
    	stall.update();
    	//移除掉玩身上的出售商品
    	resourceGroupService.costByUniqueId(playerId, pairs, NatureEnum.StallStart);
    	//上架商品到摆摊系统
        defaultExecutor.submit(0, ()->{
        	domain.putBean(playerId, stall);
        });
		return errorCode;
    }
    
    /**
     * 请求结束摆摊
     */
    public ErrorCode reqFinishStall(long playerId, String name) throws Exception{
        Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
            return ErrorCode.SUCCESS;
        });
        return future.get();
    }
    
    /**
     * 请求摆摊购买
     * @return
     */
    public ErrorCode reqStallBuy(long playerId, ReqStallBuy req, RespStallBuyBuilder resp) {
    	//摊位id
    	final long playerStallId = req.getPlayerId();
    	final long uniqueId = req.getUniqueId();
    	final int number = req.getNumber();
    	StallDomain stallDomain = stallManager.getDomain(serverConfig.getServerId());
    	if (stallDomain == null) {
    		return ErrorCode.DOMAIN_IS_NULL;
		}
    	final Stall stall = stallDomain.getBean(playerStallId);
    	final StallBooth commodityInfo = stall.getStallCommodityInfo();
		final int price = commodityInfo.getPrice(uniqueId);
//		final int configId = 
		//判断是否还有空位置
		resourceGroupService.checkAddResource(playerId, null);
		//判断价格
		Map<Integer, Integer> cost = Collections.singletonMap(PropertiesType.Ingot.getType(), price);
		
    	
    	return ErrorCode.SUCCESS;
    }
	
}
 
 
