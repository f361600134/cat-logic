package com.cat.server.game.module.resource.helper;

import com.cat.server.game.data.proto.PBItem.PBPairInfo;
import com.cat.server.game.data.proto.PBItem.PBRewardInfo;
import com.cat.server.game.module.item.proto.PBPairInfoBuilder;
import com.cat.server.game.module.item.proto.PBRewardInfoBuilder;
import com.cat.server.game.module.item.proto.RespRewardsBuilder;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 没有想打合适的办法把资源类, 在配置里反序列化成ResourceMap<br>
 * @deprecated
 * @author Jeremy
 */
public class ResourceHelper {

    /**
     * 将集合map1和map2叠加到map
     */
    public static Map<Integer, Integer> mergeMap(Map<Integer, Integer> map1, Map<Integer, Integer> map2) {
        Map<Integer, Integer> map = Maps.newHashMap();
        mergeMap(map1, map2, map);
        return map;
    }

    /**
     * 将集合map1和map2叠加到map
     */
    public static void mergeMap(Map<Integer, Integer> map1, Map<Integer, Integer> map2, Map<Integer, Integer> map) {
        if (map1 != null) {
            map.putAll(map1);
        }
        mergeToMap(map2, map);
    }

    /**
     * 将集合map1叠加到map
     */
    public static void mergeToMap(Map<Integer, Integer> map1, Map<Integer, Integer> map) {
        if (map1 == null) {
            return;
        }
        for (Entry<Integer, Integer> entry : map1.entrySet()) {
            int v = map.getOrDefault(entry.getKey(), 0);
            v += entry.getValue();
            map.put(entry.getKey(), v);
        }
    }

    /**
     * 按百分比替换资源<br>
     * 向上取整
     * @param per 百分比
     * @return 不改变原对象, 返回一个新的对象
     */
    public static Map<Integer, Integer> rate(Map<Integer, Integer> map, double per) {
        Map<Integer, Integer> temp = new HashMap<>();
        for (Integer configId : map.keySet()) {
            int number = map.get(configId);
            int newValue = (int) Math.ceil(number * per);
            temp.put(configId, newValue);
        }
        return temp;
    }

    /**
     * 根据被除数/除数的比例 替换资源
     * @param dividend 利率
     * @param divisor 除数
     * @return 不改变原对象, 返回一个新的对象
     */
    public Map<Integer, Integer> rate(Map<Integer, Integer> map, int dividend, int divisor) {
        Map<Integer, Integer> temp = new HashMap<>();
        for (Integer configId : map.keySet()) {
            int number = map.get(configId);
            int newValue = (int) Math.ceil(number * 1d * dividend / divisor);
            temp.put(configId, newValue);
        }
        return temp;
    }
    
    /**
     * 百分比
     * @param number 原始值
     * @param dividend 倍数(百分比后)
     * @return 不改变原对象, 返回一个新的对象
     */
    public static int percentage(int number, int dividend) {
    	return number * dividend / 100;
    }

    /**
     * 奖励序封装成奖励消息对象
     * @return 奖励消息对象
     */
    public static Collection<PBRewardInfo> toRewardProto(Map<Integer, Integer> map) {
        Collection<PBRewardInfo> ret = new ArrayList<>();
        map.forEach((key, val) -> {
            PBRewardInfoBuilder builder = new PBRewardInfoBuilder();
            builder.setConfigId(key);
            builder.setCount(val);
            ret.add(builder.build());
        });
        return ret;
    }
    
    /**
     * 奖励序封装成奖励消息对象
     * @return 奖励消息对象
     */
    public static Collection<PBPairInfo> toPairProto(Map<Integer, Integer> map) {
        Collection<PBPairInfo> ret = new ArrayList<>();
        map.forEach((key, val) -> {
            PBPairInfoBuilder builder = new PBPairInfoBuilder();
            builder.setConfigId(key);
            builder.setValue(val);
            ret.add(builder.build());
        });
        return ret;
    }

    /**
     * 奖励序封装成奖励奖励消息对象
     *
     * @return 奖励消息对象
     */
    public static RespRewardsBuilder toProto(Map<Integer, Integer> map) {
        RespRewardsBuilder resp = RespRewardsBuilder.newInstance();
        map.forEach((key, val) -> {
            PBRewardInfoBuilder builder = new PBRewardInfoBuilder();
            builder.setConfigId(key);
            builder.setCount(val);
            resp.addRewards(builder.build());
        });
        return resp;
    }

}
