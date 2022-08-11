package com.cat.server.admin.module.other;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import com.cat.net.http.annatation.RequestMapping;
import com.cat.server.admin.helper.result.IResult;
import com.cat.server.admin.helper.result.SystemCodeEnum;
import com.cat.server.admin.helper.result.SystemResult;
import com.cat.server.admin.module.other.structs.BackstageRemoteConfig;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.config.container.DefaultRemoteConfigContainer;
import com.cat.server.core.config.container.IConfigContainer;
import com.cat.server.core.config.container.IGameConfig;

@Controller
@RequestMapping("/remoteConfig")
public class RemoteConfigHandler {
	
	/**
	 * 刷新远程配置文件
	 * http://localhost:8001/remoteConfig/refresh?fileName=xxx
	 */
	@RequestMapping("/refresh")
	public IResult refresh(BackstageRemoteConfig config) {
		final String fileName = config.getFileName();
		Map<Class<? extends IGameConfig>, IConfigContainer<?>> containers = ConfigManager.getInstance().getContainers();
		int counter = 0;
		for (IConfigContainer<?> containner : containers.values()) {
			if (!(containner instanceof DefaultRemoteConfigContainer)) {
				continue;
			}
			String tempName = containner.getFileName();
			if (!StringUtils.equals(tempName, fileName)) {
				continue;
			}
			containner.refresh();
			counter ++;
		}
		return SystemResult.build(SystemCodeEnum.SUCCESS, counter);
	}
	
}
