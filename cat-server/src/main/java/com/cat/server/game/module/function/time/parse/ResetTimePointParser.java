package com.cat.server.game.module.function.time.parse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.cat.server.game.module.function.time.point.DailyResetTimePoint;
import com.cat.server.game.module.function.time.point.IResetTimePoint;
import com.cat.server.game.module.function.time.point.NotResetTimePoint;
import com.cat.server.game.module.function.time.point.WeeklyResetTimePoint;

/**
 * 
 * 时间点解析器
 * @author Jeremy
 */
public class ResetTimePointParser implements ObjectDeserializer {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    /**
     * 下划线分割
     */
    private final static String SPLIT_UNDERLINE = "_";
    /**
     * 竖线分割
     */
    private final static String SPLIT_PARTING_LINE = "|";

	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		final String value = parser.lexer.stringVal();
		if (StringUtils.isBlank(value)) {
            return null;
        }
		//dw_1_3_5_7|h_0_8_16
        String[] paramStrs = StringUtils.split(value, SPLIT_PARTING_LINE);
        EnumMap<ResetTimePointParamType, List<Integer>> paramMap = new EnumMap<>(ResetTimePointParamType.class);
        for (String paramStr : paramStrs) {
        	 ResetTimePointParamType paramType = ResetTimePointParamType.selectParamType(paramStr);
             if (paramType == null) {
                 logger.error("parse time point error.unknown paramType[{}].timePointStr[{}]", paramStr, value);
                 return null;
             }
             String strEnum = paramStr.substring(0, paramStr.indexOf(SPLIT_UNDERLINE));
             String[] strArr= StringUtils.split(paramStr, SPLIT_UNDERLINE);
             for (String str : strArr) {
				if (str.equals(strEnum)) {
					continue;
				}
				int paramValue = Integer.parseInt(str);
				if (!paramType.checkValid(paramValue)) {
	                logger.error("parse time point error.paramType[{}] value[{}] not valid[{},{}]", paramType.name(), paramValue, paramType.getMinValue(),
	                        paramType.getMaxValue());
	                return null;
	            }
				paramMap.computeIfAbsent(paramType, v->new ArrayList<>()).add(paramValue);
			}
		}
        return buildTimePoint(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T buildTimePoint(EnumMap<ResetTimePointParamType, List<Integer>> paramMap) {
		List<Integer> hours = paramMap.getOrDefault(ResetTimePointParamType.HOUR, new ArrayList<>());
        paramMap.remove(ResetTimePointParamType.HOUR);
        
        if (hours.isEmpty()) {
        	return (T)new NotResetTimePoint();
		}
        IResetTimePoint timePoint = null;
        if (paramMap.containsKey(ResetTimePointParamType.DAYOFWEEK)) {
        	List<Integer> dayOfWeeks = paramMap.get(ResetTimePointParamType.DAYOFWEEK);
        	timePoint = new WeeklyResetTimePoint(dayOfWeeks, hours);
        }else {
        	timePoint = new DailyResetTimePoint(new ArrayList<>(hours));
        }
        return (T)timePoint;
    }

	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_INT;
	}

}
