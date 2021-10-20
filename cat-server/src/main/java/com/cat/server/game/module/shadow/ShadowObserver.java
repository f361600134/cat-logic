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


}