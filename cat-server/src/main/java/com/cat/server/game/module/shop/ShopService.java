package com.cat.server.game.module.shop;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.lifecycle.Priority;
import com.cat.server.core.server.IModuleManager;
import com.cat.server.game.data.proto.PBShop.ReqShopBuy;
import com.cat.server.game.data.proto.PBShop.ReqShopInfo;
import com.cat.server.game.data.proto.PBShop.ReqShopQuickBuy;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ModuleDefines;
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
public class ShopService implements IShopService {
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private ShopManager shopManager;
	
	/**
	 * 更新指定列表
	 */
	public void responseInfos(ShopDomain domain, Collection<Integer> shopIds) {
		try {
			RespShopInfoBuilder builder = RespShopInfoBuilder.newInstance();
			for (Integer shopId : shopIds) {
				IShopType shopType = this.shopManager.getShopType(shopId);
				if (shopType == null) {
					log.info("responseShopInfo error, shopType is null, shopId:{}", shopId);
					return;
				}
				builder.addShopInfos(shopType.toProto(domain).build());
			}
			playerService.sendMessage(domain.getId(), builder);
		} catch (Exception e) {
			log.error("responseShopInfo error, playerId:{}, shopId:{}", domain.getId(), shopIds, e);
		}
	}
	
	/**
	 * 更新指定id
	 */
	public void responseInfo(ShopDomain domain, int shopId) {
		try {
			RespShopInfoBuilder builder = RespShopInfoBuilder.newInstance();
			IShopType shopType = this.shopManager.getShopType(shopId);
			if (shopType == null) {
				log.info("responseShopInfo error, shopType is null, shopId:{}", shopId);
				return;
			}
			builder.addShopInfos(shopType.toProto(domain).build());
			playerService.sendMessage(domain.getId(), builder);
		} catch (Exception e) {
			log.error("responseShopInfo error, playerId:{}, shopId:{}", domain.getId(), shopId, e);
		}
	}
	
	/**
	 * 更新所有
	 */
	@Override
	public void responseAllInfo(long playerId) {
		ShopDomain domain = this.shopManager.getDomain(playerId);
		RespShopInfoBuilder builder = RespShopInfoBuilder.newInstance();
		this.shopManager.getShopMap().forEach((k,shopType)->{
			builder.addShopInfos(shopType.toProto(domain).build());
		});
		playerService.sendMessage(playerId, builder);
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
			IShopType shopType = this.shopManager.getShopType(shopId);
			if (shopType == null) {
				return ErrorCode.SHOP_NOT_EXIST;
			}
			ErrorCode errorCode = shopType.buy(domain, configId, number);
			if (errorCode.isSuccess()) {
				this.responseInfo(domain, shopId);
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
			IShopType shopType = this.shopManager.getShopType(shopId);
			if (shopType == null) {
				return ErrorCode.SHOP_NOT_EXIST;
			}
			ErrorCode errorCode = shopType.quickBuy(domain);
			if (errorCode.isSuccess()) {
				this.responseInfo(domain, shopId);
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
				IShopType shopType = this.shopManager.getShopType(shopId);
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
	public int getModuleId() {
		return ModuleDefines.SHOP;
	}

	@Override
	public boolean checkAndReset(long playerId, long now) {
		ShopDomain domain = shopManager.getDomain(playerId);
		this.shopManager.getShopMap().forEach((k,v)->{
			v.checkAndReset(domain, now);
		});
		return true;
	}

	@Override
	public void doReset(long playerId, long now) {
		// TODO nothing...
		// 
	}

}
