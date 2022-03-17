package com.cat.server.game.module.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.core.server.IModuleManager;
import com.cat.server.game.data.proto.PBShop.ReqShopBuy;
import com.cat.server.game.data.proto.PBShop.ReqShopInfo;
import com.cat.server.game.data.proto.PBShop.ReqShopQuickBuy;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ModuleDefines;
import com.cat.server.game.module.functioncontrol.AbstractPlayerModuleService;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.shop.domain.ShopDomain;
import com.cat.server.game.module.shop.proto.RespShopBuyBuilder;
import com.cat.server.game.module.shop.proto.RespShopInfoBuilder;
import com.cat.server.game.module.shop.proto.RespShopQuickBuyBuilder;
import com.cat.server.game.module.shop.type.IShopType;


/**
 * Shop控制器
 * @author Jeremy
 */
@Service
public class ShopService extends AbstractPlayerModuleService<Long, ShopDomain> implements IShopService, ILifecycle {
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private ShopManager shopManager;
	
	private Map<Integer, IShopType> shopMap = new HashMap<>();
	
	@Autowired
	public void initShopMap(List<IShopType> shopTypes) {
		shopTypes.forEach(shopType->{
			this.shopMap.put(shopType.getShopType(), shopType);
		});
	}
	
	/**
	 * 更新信息
	 */
	public void responseShopInfo(ShopDomain domain, int shopId) {
		try {
			IShopType shopType = this.shopMap.get(shopId);
			if (shopType == null) {
				log.info("responseShopInfo error, shopType is null, shopId:{}", shopId);
				return;
			}
			playerService.sendMessage(domain.getId(), shopType.toProto(domain));
		} catch (Exception e) {
			log.error("responseShopInfo error, playerId:{}, shopId:{}", domain.getId(), shopId, e);
		}
	}
	
	@Override
	public void responseAllInfo(ShopDomain domain) {
		//TODO 下发所有商店信息
		//这里可以组装所有商店信息到一个消息下发给玩家
		shopMap.forEach((k,v)->{
			playerService.sendMessage(domain.getId(), v.toProto(domain));
		});
	}
	
	/////////////业务逻辑//////////////////
	
	/**
	* 请求商店购买
	* @param playerId 玩家id
	* @param req 请求
	* @param ack 响应
	*/
	public ErrorCode reqShopBuy(long playerId, ReqShopBuy req, RespShopBuyBuilder ack){
		try {
			ShopDomain domain = shopManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			final int shopId = req.getShopId();
			final int configId = req.getConfigId();
			final int number = req.getNumber();
			IShopType shopType = this.shopMap.get(shopId);
			if (shopType == null) {
				return ErrorCode.SHOP_NOT_EXIST;
			}
			ErrorCode errorCode = shopType.buy(domain, configId, number);
			if (errorCode.isSuccess()) {
				this.responseShopInfo(domain, shopId);
			}
			return errorCode;
		} catch (Exception e) {
			log.error("reqShopBuy error, playerId:{}, shopId:{}, configId:{}, number:{}"
					, playerId, req.getShopId(), req.getConfigId(), req.getNumber(), e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	
	/**
	* 请求商店一键购买
	* @param long playerId
	* @param req 请求
	* @param ack 响应
	*/
	public ErrorCode reqShopQuickBuy(long playerId, ReqShopQuickBuy req, RespShopQuickBuyBuilder ack){
		try {
			ShopDomain domain = shopManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			final int shopId = req.getShopId();
			IShopType shopType = this.shopMap.get(shopId);
			if (shopType == null) {
				return ErrorCode.SHOP_NOT_EXIST;
			}
			ErrorCode errorCode = shopType.quickBuy(domain);
			if (errorCode.isSuccess()) {
				this.responseShopInfo(domain, shopId);
			}
			return errorCode;
		} catch (Exception e) {
			log.error("reqShopQuickBuy error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	
	/**
	* 请求商店信息
	* @param long playerId
	* @param req 请求
	* @param ack 响应
	*/
	public ErrorCode reqShopInfo(long playerId, ReqShopInfo req, RespShopInfoBuilder ack){
		try {
			ShopDomain domain = shopManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			req.getShopIdList().forEach(shopId->{
				IShopType shopType = shopMap.get(shopId);
				ack.addShopInfos(shopType.toProto(domain).build());
			});
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqShopInfo error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/////////////接口方法////////////////////////
	
	@Override
	public IModuleManager<Long, ShopDomain> getModuleManager() {
		return shopManager;
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}

	@Override
	public int getModuleId() {
		return ModuleDefines.SHOP;
	}

	@Override
	public void checkAndReset(ShopDomain domain, long now) {
		shopMap.forEach((k,v)->{
			v.checkAndReset(domain, now);
		});
	}
	
}
