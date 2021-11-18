package com.cat.server.game.module.recycle.strategy.parse;

import java.lang.reflect.Type;
import java.util.EnumMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.cat.server.game.module.recycle.strategy.IRecycleStrategy;
import com.cat.server.game.module.recycle.strategy.impl.ActivityRecycleStrategy;
import com.cat.server.game.module.recycle.strategy.impl.DateRecycleStrategy;

public class RecycleStrategyParser implements ObjectDeserializer {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 不同参数的分割符
	 */
	private final static String TIME_POINT_PARAM_SPLIT = "_";

	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		final String value = parser.lexer.stringVal();
		if (StringUtils.isBlank(value)) {
			return null;
		}
		String[] paramStrs = StringUtils.split(value, TIME_POINT_PARAM_SPLIT);
		EnumMap<RecycleStrategyEnum, Integer> paramMap = new EnumMap<>(RecycleStrategyEnum.class);
		for (String paramStr : paramStrs) {
			RecycleStrategyEnum paramType = RecycleStrategyEnum.selectParamType(paramStr);
			if (paramType == null) {
				logger.error("parse time point error.unknown paramType[{}].timePointStr[{}]", paramStr, value);
				return null;
			}
			String paramValueStr = paramStr.substring(0, paramStr.length() - paramType.getKey().length());
			int paramValue = Integer.parseInt(paramValueStr);
			if (!paramType.checkValid(paramValue)) {
				logger.error("parse time point error.paramType[{}] value[{}] not valid[{},{}]", paramType.name(),
						paramValue, paramType.getMinValue(), paramType.getMaxValue());
				return null;
			}
			paramMap.put(paramType, paramValue);
		}
		return buildRecycleStrategy(paramMap);
	}
	
	/**
	 * 构建回收策略
	 * @param <T>
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T buildRecycleStrategy(EnumMap<RecycleStrategyEnum, Integer> paramMap) {
		int day = paramMap.getOrDefault(RecycleStrategyEnum.DAY, 0);
		int hour = paramMap.getOrDefault(RecycleStrategyEnum.HOUR, 0);
		IRecycleStrategy strategy = null;
		if (paramMap.containsKey(RecycleStrategyEnum.ACTIVITY)) {
			int activityTypeId = paramMap.get(RecycleStrategyEnum.ACTIVITY);
			strategy = new ActivityRecycleStrategy(activityTypeId, day, hour);
		}else {
			strategy = new DateRecycleStrategy(day, hour);
		}
		return (T)strategy;
    }

	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_INT;
	}

}
