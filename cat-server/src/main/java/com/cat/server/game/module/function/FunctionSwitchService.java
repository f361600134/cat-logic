package com.cat.server.game.module.function;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 功能开关服务, 公共服务, 由后台控制<br>
 * 功能关闭后 对应模块不再处理监听的协议<br>
 * @author Jeremy Feng.
 */
@Service
public class FunctionSwitchService {
	
    private final static Logger logger = LoggerFactory.getLogger(FunctionSwitchService.class);

    /**
     * 后台关闭的功能
     * key:渠道
     * value:关闭的功能列表
     */
    private final Map<String, Set<Integer>> closeFunctionMap = new ConcurrentHashMap<>(); 
    
    /**
     * 通过channel获得关闭的功能列表
     * @param channel
     * @return 不会为null, 返回一个不可修改的空列表
     */
    public Set<Integer> getCloseFunctionIds(String channel) {
    	return closeFunctionMap.getOrDefault(channel, Collections.emptySet());
    }

    /**
     * 通过channel获得关闭的功能列表
     * @param channel
     * @return 创建并返回一个空列表
     */
    public Set<Integer> getOrCreateFunctionIds(String channel) {
    	return closeFunctionMap.computeIfAbsent(channel, v-> new ConcurrentSkipListSet<Integer>());
    }
    
    /**
     * 该渠道的此功能是否被后台强行关闭
     * @param channel
     * @param functionId
     * @return
     */
    public boolean isClose(String channel, int functionId) {
        Set<Integer> ret = closeFunctionMap.get(channel);
        if (ret == null) {
            return false;
        }
        return ret.contains(functionId);
    }
}
