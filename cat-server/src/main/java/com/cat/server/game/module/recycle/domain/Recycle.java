package com.cat.server.game.module.recycle.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.data.config.local.ConfigRecycle;
import com.cat.server.game.data.proto.PBRecycle.PBResourceRecycle;
import com.cat.server.game.module.recycle.proto.PBResourceRecycleBuilder;
import com.cat.server.game.module.recycle.strategy.IRecycleStrategy;
import com.cat.server.utils.TimeUtil; 

/**
* @author Jeremy
*/
@PO(name = "recycle")
public class Recycle extends RecyclePo implements IPersistence{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7275446861661372340L;

	public Recycle() {
		this.recieveTime = TimeUtil.now();
	}
	
	public Recycle(long playerId) {
		this.playerId = playerId;
		this.recieveTime = TimeUtil.now();
	}
	
	/**
	 * 创建一个回收对象
	 * @return
	 */
	public static Recycle create(long playerId, long resouceId, int configId) {
		Recycle recycle = new Recycle(playerId);
		recycle.setResourceId(resouceId);
		recycle.setConfigId(configId);
		return recycle;
	}
	
	/**
	 * 计算回收时间<br>
	 * 如果依赖活动, 则获取活动结束时间<br>
	 * 如果限时类的道具, 则获取限时时间戳, 计算回收时间<br>
	 *  如果即依赖活动, 又限时, 那么限时时长截止时间戳, 大于活动结束时间戳时, 以活动结束时间戳为准
	 * @param configId
	 * @return
	 */
	public long getRecucleTime(int configId) {
		ConfigRecycle config = ConfigManager.getInstance().getConfig(ConfigRecycle.class, configId);
		IRecycleStrategy strategy = config.getStrategy();
		return strategy.calculateTimePoint(recieveTime);
	}
	
	/**
	 * 协议对象
	 * @return
	 */
	public PBResourceRecycle toProto() {
		PBResourceRecycleBuilder builder = PBResourceRecycleBuilder.newInstance();
		builder.setConfigId(configId);
		builder.setRecycleTime(getRecucleTime(configId));
		builder.setUniqueId(resourceId);
		return builder.build();
	} 
	
}
