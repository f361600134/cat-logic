package com.cat.server.game.module.shop;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.data.proto.PBShop.ReqShopBuy;
import com.cat.server.game.data.proto.PBShop.ReqShopInfo;
import com.cat.server.game.data.proto.PBShop.ReqShopQuickBuy;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ModuleDefines;
import com.cat.server.game.module.functioncontrol.AbstractPlayerModuleService;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.shop.domain.Shop;
import com.cat.server.game.module.shop.domain.ShopDomain;
import com.cat.server.game.module.shop.proto.RespShopBuyBuilder;
import com.cat.server.game.module.shop.proto.RespShopInfoBuilder;
import com.cat.server.game.module.shop.proto.RespShopQuickBuyBuilder;
import com.cat.server.game.module.shop.type.IShopType;
import com.cat.server.utils.TimeUtil;


/**
 * Shop控制器
 * @author Jeremy
 */
@Service
public class ShopService extends AbstractPlayerModuleService implements IShopService, ILifecycle {
	
	private static final Logger log = LoggerFactory.getLogger(ShopService.class);
	
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
	 * 登陆, 检测重置
	 * 可以新增IResetDomain接口, 专用于处理游戏内的充值业务
	 */
	public void onLogin(long playerId) {
		ShopDomain domain = shopManager.getOrLoadDomain(playerId);
		Collection<Shop> beans = domain.getBeans();
		this.checkAndReset(playerId, TimeUtil.now());
		//FSC todo somthing...
		//Codes for proto
		//playerService.sendMessage(playerId, ack);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		shopManager.remove(playerId);
	}
  
	/**
	 * 更新信息
	 */
	public void responseShopInfo(ShopDomain domain, int shopId) {
		try {
			IShopType shopType = this.shopMap.get(shopId);
			if (shopType == null) {
				return;
			}
			playerService.sendMessage(domain.getId(), shopType.toProto(domain));
		} catch (Exception e) {
			log.error("responseShopInfo error, playerId:{}, shopId:{}", domain.getId(), shopId, e);
		}
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
	public int priority() {
		return Priority.LOGIC.getPriority();
	}

	@Override
	public int getModuleId() {
		return ModuleDefines.SHOP;
	}

	@Override
	public void checkAndReset(long playerId, long now) {
		ShopDomain domain = shopManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		shopMap.forEach((k,v)->{
			v.checkAndReset(domain, now);
		});
	}
	
}
