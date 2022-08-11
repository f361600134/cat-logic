package com.cat.server.game.data.config.remote.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.cat.server.core.config.container.IGameConfig;

/**
 * 功能开启配置, 解析json获得的是一个Set类型的功能列表
 * 
 * @author Jeremy Feng.
 * 
 */
//@ConfigUrl("function_switch.json")
public class ConfigFunctionSwitch implements IGameConfig {

	/**
	 * key: 渠道 value: 关闭的功能列表 key: publish value:Set<1,2,3>
	 */
	private Map<Integer, Set<Integer>> functionIds;

	public ConfigFunctionSwitch() {
		this.functionIds = new HashMap<>();
	}

	public Map<Integer, Set<Integer>> getFunctionIds() {
		return functionIds;
	}

	/**
	 * 通过channel获得关闭的功能列表
	 * @param channel
	 * @return 不会为null, 返回一个不可修改的空列表
	 */
	public Set<Integer> getCloseFunctionIds(int channel) {
		return functionIds.getOrDefault(channel, Collections.emptySet());
	}
	
	/**
     * 该渠道的此功能是否被后台强行关闭
     * @param channel
     * @param functionId
     * @return
     */
    public boolean isClose(int channel, int functionId) {
        Set<Integer> ret = functionIds.get(channel);
        if (ret == null) {
            return false;
        }
        return ret.contains(functionId);
    }

	@Override
	public int getId() {
		return 1;
	}

	public void setFunctionIds(Map<Integer, Set<Integer>> functionIds) {
		this.functionIds = functionIds;
	}

}
