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
 * @auth Jeremy
 * @date 2022年3月14日上午7:36:16
 */
public abstract class AbstractPlayerModuleService<I extends Number, T extends IModuleDomain<I, ? extends IBasePo>> extends AbstractModuleService {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private IFunctionControlService functionControlService;
	
	@Override
	public boolean checkModuleOpen(long playerId) {
		return functionControlService.checkOpen(playerId, this.getModuleId());
	}
	
	/**
	 * 当玩家登录
	 */
	public void onLogin(I playerId, long now) {
		T t = this.getModuleManager().getDomain(playerId);
		if (t == null) {
			log.info("onLogin error, domain is null, playerId:{}", playerId);
			return;
		}
		this.checkAndReset(t, now);
		this.responseAllInfo(t);
	}
	
	/**
	 * 当玩家登出
	 */
	public void onLogout(I playerId) {
		this.getModuleManager().remove(playerId);
	}
	
	/**
	 * 设置玩家的重置时间
	 * @param playerId
	 * @param now 
	 */
	public abstract void checkAndReset(T domain, long now);
	
	/**
	 * 下发模块所有信息
	 * @param domain  
	 * @return void  
	 * @date 2022年3月17日下午10:16:10
	 */
	public abstract void responseAllInfo(T domain);
	
	/**
	 * 获取manager管理类
	 * @return  
	 * @return IModuleManager<?,?>  
	 * @date 2022年3月17日下午9:45:26
	 */
	public abstract IModuleManager<I, T> getModuleManager();

}
