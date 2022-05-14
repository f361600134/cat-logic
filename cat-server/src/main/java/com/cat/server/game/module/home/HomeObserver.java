package com.cat.server.game.module.home;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.home.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Home事件类
 */
@Component
public class HomeObserver implements IObserver{

	@Autowired
    private HomeService homeService;


}