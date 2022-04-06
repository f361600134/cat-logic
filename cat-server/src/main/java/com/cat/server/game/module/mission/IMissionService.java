package com.cat.server.game.module.mission;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.game.module.functioncontrol.IPlayerModuleService;

/**
 * Mission接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IMissionService extends IPlayerModuleService, ILifecycle{

}