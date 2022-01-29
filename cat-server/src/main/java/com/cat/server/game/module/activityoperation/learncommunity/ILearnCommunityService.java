package com.cat.server.game.module.activityoperation.learncommunity;

import com.cat.server.game.module.activity.type.IPlayerActivityService;
import com.cat.server.game.module.activity.type.impl.LearnCommunityActivityType;
import com.cat.server.game.module.activityoperation.learncommunity.domain.LearnCommunity;

/**
 * LearnCommunity接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface ILearnCommunityService extends IPlayerActivityService<LearnCommunityActivityType, LearnCommunity>{

}