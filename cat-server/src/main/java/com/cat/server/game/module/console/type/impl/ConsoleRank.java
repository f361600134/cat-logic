package com.cat.server.game.module.console.type.impl;

import com.cat.server.core.event.GameEventBus;
import com.cat.server.game.module.console.annotation.Console;
import com.cat.server.game.module.console.type.IConsole;
import com.cat.server.game.module.rank.domain.RankTypeEnum;
import com.cat.server.game.module.rank.event.RankInfoUpdateEvent;

/**
 * 控制台命令,更新指定排行榜
 * @author Jeremy
 */
@Console("rank")
public class ConsoleRank implements IConsole {

	/**
	 * rank,6
	 * rank,7
	 * rank,8
	 * rank,9
	 * rank,10
	 * 模拟发送排行榜更新事件
	 */
	@Override
	public void process(String ...content) {
		int num = Integer.parseInt(content[1]);
		GameEventBus.publishEvent(RankInfoUpdateEvent.create(RankTypeEnum.POWER, num, num, num));
	}

}
