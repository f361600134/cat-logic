package com.cat.server.game.module.activity.time.parse;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.cat.server.game.module.activity.time.point.ITimePoint;
import com.cat.server.game.module.activity.time.point.impl.CycleTimePoint;
import com.cat.server.game.module.activity.time.point.impl.DateTimePoint;
import com.cat.server.game.module.activity.time.point.impl.OpenDayTimePoint;

/**
 * 
 * 时间点解析器
 * @author Jeremy
 */
public class TimePointParser implements ObjectDeserializer {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    /**
     * 单个时间点 不同参数的分割符
     */
    private final static String TIME_POINT_PARAM_SPLIT = "_";

	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		final String value = parser.lexer.stringVal();
		if (StringUtils.isBlank(value)) {
            return null;
        }
        String[] paramStrs = StringUtils.split(value, TIME_POINT_PARAM_SPLIT);
        Map<TimePointParamType, Integer> paramMap = new HashMap<>(paramStrs.length);
        for (String paramStr : paramStrs) {
            TimePointParamType paramType = TimePointParamType.selectParamType(paramStr);
            if (paramType == null) {
                logger.error("parse time point error.unknown paramType[{}].timePointStr[{}]", paramStr, value);
                return null;
            }
            String paramValueStr = paramStr.substring(0, paramStr.length() - paramType.getKey().length());
            int paramValue = Integer.parseInt(paramValueStr);
            if (!paramType.checkValid(paramValue)) {
                logger.error("parse time point error.paramType[{}] value[{}] not valid[{},{}]", paramType.name(), paramValue, paramType.getMinValue(),
                        paramType.getMaxValue());
                return null;
            }
            paramMap.put(paramType, paramValue);
        }
        return buildTimePoint(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T buildTimePoint(Map<TimePointParamType, Integer> paramMap) {
        int hour = paramMap.getOrDefault(TimePointParamType.HOUR, 0);
        paramMap.remove(TimePointParamType.HOUR);
        ITimePoint timePoint = null;
        if (paramMap.containsKey(TimePointParamType.OPEN_SERVER_DAY)) {
            // 开服时间
            int openDay = paramMap.get(TimePointParamType.OPEN_SERVER_DAY);
            timePoint = new OpenDayTimePoint(openDay, hour);
        } else if (paramMap.containsKey(TimePointParamType.YEAR) && paramMap.containsKey(TimePointParamType.MONTH)
                && paramMap.containsKey(TimePointParamType.DAY)) {
            // 指定日期
            int year = paramMap.get(TimePointParamType.YEAR);
            int month = paramMap.get(TimePointParamType.MONTH);
            int day = paramMap.get(TimePointParamType.DAY);
            timePoint = new DateTimePoint(year, month, day, hour);
        }
        // 是否有循环参数
        if (paramMap.containsKey(TimePointParamType.CYCLE)) {
            int cycle = paramMap.get(TimePointParamType.CYCLE);
            int cycleTimes = -1;
            Integer cycleTimesParam = paramMap.get(TimePointParamType.CYCLE_TIMES);
            if (cycleTimesParam != null) {
                cycleTimes = cycleTimesParam;
            }
            timePoint = new CycleTimePoint(timePoint, cycle, cycleTimes);
        }
        return (T)timePoint;
    }

	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_INT;
	}

}
