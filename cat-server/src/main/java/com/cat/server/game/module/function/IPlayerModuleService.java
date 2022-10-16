package com.cat.server.game.module.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.IModuleManager;

/**
 * 此类为AbstractService扩展
 * 
 * @auth Jeremy
 * @date 2022年3月14日上午7:36:16
 */
public interface IPlayerModuleService extends IFunctionResetService{

	public final Logger log = LoggerFactory.getLogger(IPlayerModuleService.class);

	/**
	 * 当玩家登录
	 */
	default public void onLogin(long playerId, long now) {
		Object obj = this.getModuleManager().getOrLoadDomain(playerId);
		if (obj == null) {
			log.info("onLogin error, domain is null, playerId:{}", playerId);
			return;
		}
		this.checkAndReset(playerId, now);
		this.responseModuleInfo(playerId);
	}

	/**
	 * 当玩家登出
	 */
	default public void onLogout(long playerId) {
		this.getModuleManager().remove(playerId);
	}

	/**
	 * 下发模块所有信息<br>
	 * 客戶端如果有需要，則實現此方法
	 * @param domain
	 * @return void
	 * @date 2022年3月17日下午10:16:10
	 */
	default public void responseModuleInfo(long playerId) {}

	/**
	 * 获取manager管理类
	 * 
	 * @return
	 * @return IModuleManager<?,?>
	 * @date 2022年3月17日下午9:45:26
	 */
	public abstract IModuleManager<Long, ?> getModuleManager();

//	/**
//	 * 监测红点数量, 默认大于0则就通知客户端.<br>
//	 * 有些系统需要统计出所有红点数量,用于客户端显示<br>
//	 * 
//	 * @param playerId
//	 *            玩家id
//	 * @return int
//	 */
//	default public int checkReddot(long playerId) {
//		return 0;
//	}

}
