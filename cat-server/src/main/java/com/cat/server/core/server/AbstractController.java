package com.cat.server.core.server;

import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IController;

/**
 * 抽象控制器
 * @auth Jeremy
 * @date 2022年3月13日下午8:46:16
 */
public abstract class AbstractController implements IController{
	
	
	@Override
	public boolean verify(ISession session) {
		return getService().checkModuleOpen(session.getUserData());
	}
	
	/**
	 * 获取service层级
	 * @return int 功能id
	 */
	public abstract IModuleService getService();

}
