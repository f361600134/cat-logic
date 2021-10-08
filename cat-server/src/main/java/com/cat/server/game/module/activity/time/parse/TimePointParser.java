package com.cat.server.game.module.activity.time.parse;

import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

/**
 * 
 * 时间点解析器
 * @author Jeremy
 */
public class TimePointParser implements ObjectDeserializer {

	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		final String input = parser.lexer.stringVal();
		if (StringUtils.isBlank(input)) {
            return null;
        }
		
		return null;
	}

	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_INT;
	}
	
	

}
