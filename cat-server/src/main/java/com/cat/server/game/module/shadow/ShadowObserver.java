package com.cat.server.game.module.shadow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.event.IObserver;

/**
 * Shadow事件类
 */
@Component
public class ShadowObserver implements IObserver{

	@Autowired
    private ShadowService shadowService;

	//TODO 当玩家数据变化,影子数据变化

    //TODO 当玩家装备数据变化,影子数据变化

    //TODO 当玩家宠物数据变化,影子数据变化

}