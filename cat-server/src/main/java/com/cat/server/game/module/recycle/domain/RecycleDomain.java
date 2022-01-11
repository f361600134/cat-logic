package com.cat.server.game.module.recycle.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.data.config.local.ConfigRecycle;
import com.cat.server.game.module.recycle.strategy.impl.ActivityRecycleStrategy;
import com.cat.server.utils.TimeUtil;

/**
* RecycleDomain
* @author Jeremy
*/
public class RecycleDomain extends AbstractModuleMultiDomain<Long, Long, Recycle> {

	private static final Logger log = LoggerFactory.getLogger(RecycleDomain.class);
	
	public RecycleDomain(){
		
	}
	
	////////////业务代码////////////////////
	
	/**
	 * 添加一个回收信息对象
	 */
	public void addRecycle(long uniqueId, int configId) {
		Recycle recycle = Recycle.create(getId(), uniqueId, configId);
		beanMap.put(recycle.getResourceId(), recycle);
	}
	
	/**
	 * 通过配置id获取到资源对象
	 * @param configId
	 * @return
	 */
	public Collection<Recycle> getRecycles(int configId){
		List<Recycle> ret = new ArrayList<>();
		for (Recycle recycle : getBeanMap().values()) {
			if (recycle.getConfigId() == configId) {
				ret.add(recycle);
			}
		}
		return ret;
	}
	
	/**
	 * 清理资源
	 * @return 存档的资源数据, 被删除的资源配置id列表
	 */
	public Collection<Integer> clearResource(int activityTypeId) {
		//筛选符合条件的配置id列表
		Set<Integer> configIds = ConfigManager.getInstance().getConfigs(ConfigRecycle.class, c-> 
				(c.getStrategy() instanceof ActivityRecycleStrategy) 
				&& ((ActivityRecycleStrategy)(c.getStrategy())).getActivityTypeId() == activityTypeId)
				.keySet();
		//要清理的回收资源id
		configIds.forEach((configId)->{
			Iterator<Entry<Long, Recycle>> iter = getBeanMap().entrySet().iterator();
			while (iter.hasNext()) {
				Entry<Long, Recycle> entry = iter.next();
				Recycle recycle = entry.getValue();
				if (recycle.getConfigId() == configId) {
					//从库中删除
					recycle.delete();
					//缓存中删除
					iter.remove();
				}
			}
		});
		return configIds;
	}
	
	/**
	 * 检测所有存档是否有需要清掉的存档
	 * @return
	 */
	public void clearResource() {
		ConfigRecycle config = null;
		Iterator<Entry<Long, Recycle>> iter = getBeanMap().entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Long, Recycle> entry = iter.next();
			Recycle recycle = entry.getValue();
			config = ConfigManager.getInstance().getConfig(ConfigRecycle.class, recycle.getConfigId());
			if (config == null) {
				continue;
			}
			long expireTime = config.getStrategy().calculateTimePoint(recycle.getRecieveTime());
			if (TimeUtil.now() >= expireTime) {
				//表示过期, 处理移除
				recycle.delete();
				iter.remove();
			}
		}
	}
	
	/**
	 * 检测指定资源id是否可以被回收
	 * @param uniqueId 唯一id
	 * @param configId 资源配置id
	 * @return true: 可以被回收, false不可以被回收
	 */
	public boolean checkRecycle(long uniqueId, int configId) {
		ConfigRecycle config = ConfigManager.getInstance().getConfig(ConfigRecycle.class, configId);
		if (config == null) {
			//不在回收配置内,不可被回收
			return false;
		}
		Recycle recycle = getBean(uniqueId);
		if (recycle == null) {
			//在回收配置中, 但是不在存档中, 表示已经被回收
			return true;
		}
		//在存档中,判断存档是否可以被回收
		long recycleTime = config.getStrategy().calculateTimePoint(recycle.getRecieveTime());
		if (TimeUtil.now() >= recycleTime) {
			//当前时间大于存档时间, 表示可以被回收
			return true;
		}
		return false;
	}
	
	/**
	 * 处理资源回收
	 * @param uniqueId 唯一id
	 * @param configId 资源配置id
	 * @return true: 可以被回收, false不可以被回收
	 */
	public boolean doRecycle(long uniqueId, int configId) {
		ConfigRecycle config = ConfigManager.getInstance().getConfig(ConfigRecycle.class, configId);
		if (config == null) {
			return false;
		}
		Recycle recycle = getBean(uniqueId);
		if (recycle == null) {
			return false;
		}
//		//在存档中,判断存档是否可以被回收
//		long recycleTime = config.getStrategy().calculateTimePoint(recycle.getRecieveTime());
//		if (TimeUtil.now() >= recycleTime) {
			//当前时间大于存档时间, 表示可以被回收
			recycle.delete();
			beanMap.remove(recycle.getResourceId());
			return true;
//		}
//		return false;
	}
	
}
