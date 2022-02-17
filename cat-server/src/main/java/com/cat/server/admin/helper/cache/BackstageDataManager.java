package com.cat.server.admin.helper.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.orm.core.base.IBasePo;
import com.cat.orm.core.db.process.IDataProcess;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 后台数据管理, 用于缓存后台查询的离线数据
 * @author Jeremy
 *
 */
@Component
public class BackstageDataManager {
	
	@Autowired private IDataProcess dataProcess;
	
	/**
	 * 玩家緩存
	 * 在查询不到玩家时, 应该缓存玩家对象
	 */
	private Cache<Object, IBasePo> cache = CacheBuilder.newBuilder()
			//在给定时间内没有被读/写访问,则清除
			.expireAfterAccess(10, TimeUnit.MINUTES)
			//最大条目,超过这个聊天记录, 根据LRU特点移除
			.maximumSize(100)
			.build();
	
	/**
	 * 查询信息
	 * @date 2020年6月30日
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends IBasePo> T selectByPrimaryKey(Class<T> clazz, Object uniqueId) {
		IBasePo data = cache.getIfPresent(uniqueId);
		if (data == null) {
			data = dataProcess.selectByPrimaryKey(clazz, uniqueId);
			cache.put(uniqueId, data);
		}
		return (T) data;
	} 

}
