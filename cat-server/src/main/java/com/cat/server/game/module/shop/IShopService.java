package com.cat.server.game.module.shop;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.game.module.function.IPlayerModuleService;

/**
 * Shop接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IShopService extends IPlayerModuleService, ILifecycle{

}