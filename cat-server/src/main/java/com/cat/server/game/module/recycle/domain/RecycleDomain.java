package com.cat.server.game.module.recycle.domain;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.data.config.local.ConfigRecycle;
import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import com.cat.server.utils.Pair;
import com.cat.server.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

/**
* RecycleDomain
* @author Jeremy
* @param Long playerId
* @param Long ResourceId,等同于RecycleId
* @param Recycle 可以回收的资源快照 
*/
public class RecycleDomain extends AbstractModuleMultiDomain<Long, Long, Recycle> {

	private static final Logger log = LoggerFactory.getLogger(RecycleDomain.class);
	
	public RecycleDomain(){
		
	}
	
	////////////业务代码////////////////////
	
	/**
	 * 添加一个回收信息对象
	 * @param uniqueId 唯一id
	 * @param configId 配置id
	 * @param number 当前数量
	 */
	public void updateRecycle(long uniqueId, int configId, int number) {
		//如果数量小于等于0, 则表示移除此道具
		if (number <= 0) {
			Recycle recycle = beanMap.remove(uniqueId);
			recycle.delete();
		}else {
			Recycle recycle = beanMap.get(uniqueId);
			if (recycle == null) {//创建回收对象
				recycle = Recycle.create(getId(), uniqueId, configId);
			}
			recycle.setNumber(number);
			recycle.update();
		}
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
	 * 清除资源,回收资源
	 * @param configIds 需要回收的资源ids
	 * @return void  
	 * @date 2022年2月9日下午12:44:06
	 */
	public ResourceGroup clearResource(Collection<Integer> configIds) {
		ResourceGroup reward = new ResourceGroup();
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
					//奖励转化
					ConfigRecycle config = ConfigManager.getInstance().getConfig(ConfigRecycle.class, configId);
					if (config == null) {
						continue;
					}
					//转换奖励
					int realNum = ResourceHelper.percentage(recycle.getNumber(), config.getResRate());
					reward.addCount(configId, realNum);
//					int number = reward.getOrDefault(configId, 0);
//					number = number + realNum;
//					reward.put(configId, number);
				}
			}
		});
		return reward;
	}
	
	/**
	 * 检测所有存档是否有需要清掉的存档
	 * @return Pair key:可回收资源id列表, value:回收转化资源map
	 */
	public Pair<Set<Integer>, ResourceGroup> clearResource() {
		Set<Integer> ret = new HashSet<>();
		ResourceGroup rewardMap = new ResourceGroup();
		ConfigRecycle config = null;
		Iterator<Entry<Long, Recycle>> iter = getBeanMap().entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Long, Recycle> entry = iter.next();
			Recycle recycle = entry.getValue();
			config = ConfigManager.getInstance().getConfig(ConfigRecycle.class, recycle.getConfigId());
			if (config == null) {
				//FIXME 如果配置为null,则清理掉过期资源???
				continue;
			}
			long expireTime = config.getStrategy().calculateTimePoint(recycle.getRecieveTime());
			if (TimeUtil.now() >= expireTime) {
				//统计回收资源
				ret.add(config.getId());
				//回收资源转换奖励
				int realNum = ResourceHelper.percentage(recycle.getNumber(), config.getResRate());
//				int number = rewardMap.getOrDefault(config.getId(), 0);
//				number = number + realNum;
//				rewardMap.put(config.getId(), number);
				rewardMap.addCount(config.getId(), realNum);
				//表示过期, 处理移除
				recycle.delete();
				iter.remove();
			}
		}
		return Pair.of(ret, rewardMap);
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
		//当前时间大于存档时间, 表示可以被回收
		recycle.delete();
		beanMap.remove(recycle.getResourceId());
		return true;
	}
	
}
