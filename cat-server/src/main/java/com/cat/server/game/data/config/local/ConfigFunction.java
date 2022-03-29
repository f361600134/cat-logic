package com.cat.server.game.data.config.local;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigFunctionBase;


/**
 * gn.功能控制.xlsx<br>
 * function.json<br>
 * @author auto gen
 */
@ConfigPath("function.json")
public class ConfigFunction extends ConfigFunctionBase {
	
	public long getTime(long now) {
		return 0L;
	}

}
