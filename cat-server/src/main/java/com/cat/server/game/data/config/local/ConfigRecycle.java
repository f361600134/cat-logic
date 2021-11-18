package com.cat.server.game.data.config.local;

import com.alibaba.fastjson.annotation.JSONField;
import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigRecycleBase;
import com.cat.server.game.module.recycle.strategy.IRecycleStrategy;
import com.cat.server.game.module.recycle.strategy.parse.RecycleStrategyParser;


/**
 * zy.资源回收.xlsx<br>
 * recycle.json<br>
 * 
 * @author auto gen
 *
 */
@ConfigPath("recycle.json")
public class ConfigRecycle extends ConfigRecycleBase {
	
	@JSONField(name="recycleStrategy", deserializeUsing = RecycleStrategyParser.class)
	private IRecycleStrategy strategy;

	public IRecycleStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(IRecycleStrategy strategy) {
		this.strategy = strategy;
	}

}
