package com.cat.server.game.module.item;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.item.domain.IItem;
import com.cat.server.game.module.item.domain.Item;
import com.cat.server.game.module.item.domain.ItemDomain;
import com.cat.server.game.module.item.proto.RespItemDeleteBuilder;
import com.cat.server.game.module.item.proto.RespItemUpdateBuilder;
import com.cat.server.game.module.pet.domain.Pet;
import com.cat.server.game.module.pet.domain.PetDomain;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.recycle.IRecycleService;
import com.cat.server.game.module.resource.IResource;
import com.cat.server.game.module.resource.IResourceService;

/**
 * 资源服务
 * @author Jeremy
 */
@Service
class ItemService implements IItemService, IResourceService{
	
	private static final Logger log = LoggerFactory.getLogger(ItemService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private ItemManager itemManager;
	
	@Autowired private IRecycleService recycleService;

	/**
	 * 当登陆成功,下发物品列表
	 */
	public void onLogin(long playerId) {
		ItemDomain domain = itemManager.getDomain(playerId);
		Collection<Item> items = domain.getBeans();
		//登陆成功,下发背包信息
		this.responseUpdateItemList(playerId, items);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		itemManager.remove(playerId);
	}
	
	// 推送更新物品列表至前端
	public void responseUpdateItemList(long playerId, Collection<Item> itemList) {
		// 更新物品
		if (!itemList.isEmpty()) {
			RespItemUpdateBuilder ack = RespItemUpdateBuilder.newInstance();
			itemList.forEach((item)->{
				ack.addItems(item.toProto());
			});
			playerService.sendMessage(playerId, ack);
		}
	}
	
	//推送删除物品列表至前端
	public void responseDeleteItemList(long playerId, Collection<Long> itemList){
		//更新物品
		if (!itemList.isEmpty()) {
			RespItemDeleteBuilder ack = RespItemDeleteBuilder.newInstance();
			itemList.forEach((uniqueId)->{
				ack.addIds(uniqueId);
			});
			playerService.sendMessage(playerId, ack);
		}
	}

	@Override
	public int resType() {
		return ResourceType.Item.getType();
	}
	
	@Override
	public boolean checkAdd(long playerId, Integer configId, Integer value) {
		ItemDomain domain = itemManager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkAdd(configId, value);
	}
	
	@Override
	public boolean checkEnough(long playerId, Integer configId, Integer value) {
		ItemDomain domain = itemManager.getDomain(playerId);
		if (domain == null) return false;
		return domain.checkEnough(configId, value);
	}

	@Override
	public void reward(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		ItemDomain domain = itemManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		//背包加入普通道具
		domain.add(configId, value);
	}

	@Override
	public void cost(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		ItemDomain domain = itemManager.getDomain(playerId);
		if (domain == null)	return;
		domain.costByConfigId(configId, value);
	}
	
	@Override
	public void cost(long playerId, Long id, NatureEnum nEnum) {
		ItemDomain domain = itemManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		/*
		 * 道具默认有数量概念, 唯一的物品数量默认为1.
		 * 所以唯一道具,跟普通道具在扣除上面,逻辑相同,当数量为0则清除.
		 * 这里参数就默认扣除未1
		 */
		domain.costById(id, 1);
	}
	
	@Override
	public void notify(long playerId) {
		ItemDomain domain = itemManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		//	通知客户端数据变动
		this.responseUpdateItemList(playerId, domain.getAndClearUpdateList());
		//	通知客户端数据删除
		this.responseDeleteItemList(playerId, domain.getAndClearDeleteList());
	}
	
	@Override
	public void clearExpire(long playerId, int configId) {
		ItemDomain domain = itemManager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		domain.clearExpire(configId);
	}

	@Override
	public int getCount(long playerId, Integer configId) {
		ItemDomain domain = itemManager.getDomain(playerId);
		if (domain == null) {
			return 0;
		}
		IItem item = domain.getBeanByConfigId(configId);
		return item == null ? 0 : item.getCount();
	}
	
//	@Override
//	public void addResource(long playerId, IResource res, NatureEnum nEnum) {
//		ItemDomain domain = itemManager.getDomain(playerId);
//		if (domain == null) return ;
//		if (!(res instanceof Item)) {
//			return;
//		}
//		Item item = (Item)res;
//		domain.addReource(item.getUniqueId(), item);
//	}

}
