package com.cat.server.game.module.functioncontrol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.orm.core.base.IBasePo;
import com.cat.server.core.server.AbstractModuleService;
import com.cat.server.core.server.IModuleDomain;
import com.cat.server.core.server.IModuleManager;

/**
 * 此类为AbstractService扩展
 * 
 * @auth Jeremy
 * @date 2022年3月14日上午7:36:16
 */
public abstract class AbstractPlayerModuleService<T extends IModuleDomain<Long, ? extends IBasePo>> extends AbstractModuleService implements IFunctionReset {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IFunctionControlService functionControlService;

	@Override
	public boolean checkModuleOpen(long playerId) {
		return functionControlService.checkOpen(playerId, this.getModuleId());
	}

	/**
	 * 当玩家登录
	 */
	public void onLogin(long playerId, long now) {
		T t = this.getModuleManager().getDomain(playerId);
		if (t == null) {
			log.info("onLogin error, domain is null, playerId:{}", playerId);
			return;
		}
		this.checkAndReset(playerId, now);
		this.responseAllInfo(t);
	}

	/**
	 * 当玩家登出
	 */
	public void onLogout(long playerId) {
		this.getModuleManager().remove(playerId);
	}

	/**
	 * 下发模块所有信息
	 * 
	 * @param domain
	 * @return void
	 * @date 2022年3月17日下午10:16:10
	 */
	public abstract void responseAllInfo(T domain);

	/**
	 * 获取manager管理类
	 * 
	 * @return
	 * @return IModuleManager<?,?>
	 * @date 2022年3月17日下午9:45:26
	 */
	public abstract IModuleManager<Long, T> getModuleManager();

	/**
	 * 监测红点数量, 默认大于0则就通知客户端.<br>
	 * 有些系统需要统计出所有红点数量,用于客户端显示<br>
	 * 
	 * @param playerId
	 *            玩家id
	 * @return int
	 */
	public int checkReddot(long playerId) {
		return 0;
	}
	
	@Override
	public long getLastResetTime(long playerId) {
		return this.functionControlService.getLastResetTime(playerId, this.getModuleId());
	}
	
	@Override
	public void setLastResetTime(long playerId, long now) {
		this.functionControlService.setLastResetTime(playerId, this.getModuleId(), now);
	}
	

}
