package com.cat.server.game.module.console.type.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.server.game.module.console.annotation.Console;
import com.cat.server.game.module.console.type.IConsole;
import com.cat.server.game.module.shadow.IShadowService;
import com.cat.server.game.module.shadow.domain.Shadow;

@Console("shadow")
public class ConsoleShadow implements IConsole {

	@Autowired
	private IShadowService shadowService;

	@Override
	public void process(String ...content) {
		try {
			Shadow shadow = shadowService.get(1);
			System.out.println("获取shadow:" + shadow);
			if (shadow == null) {
				shadow = shadowService.getOrCreate(1);
			}
			System.out.println("再次获取shadow:" + shadow);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
