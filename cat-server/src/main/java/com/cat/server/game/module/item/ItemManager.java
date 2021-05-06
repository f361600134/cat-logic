package com.cat.server.game.module.item;

import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.item.domain.ItemDomain;
import org.springframework.stereotype.Component;

/**
 * 数据管理类, 之所以放在Manager内,是以后如果增加缓存机制, 直接实现缓存接口即可
 * @author Jeremy
 *
 */
@Component
class ItemManager extends AbstractModuleManager<Long,ItemDomain>{
	
	
	
}
