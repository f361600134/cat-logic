package com.cat.server.game.module.shop;

import com.cat.server.core.event.IObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Shop事件类
 */
@Component
public class ShopObserver implements IObserver{

	@Autowired
    private IShopService shopService;


}