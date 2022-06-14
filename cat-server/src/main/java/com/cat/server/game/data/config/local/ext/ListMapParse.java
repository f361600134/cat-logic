package com.cat.server.game.data.config.local.ext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

/**
 * List<Map<>> 结构解析器
 * @author Jeremy
 */
public class ListMapParse implements ObjectDeserializer {
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		//[{},{},{}]
		final String value = parser.lexer.stringVal();
		if (StringUtils.isBlank(value)) {
            return null;
        }
		List<Map<Integer, Integer>> list = JSON.parseObject(value, new TypeReference<List<Map<Integer, Integer>>>(){});
		//集合内所有map转不可变对象
		List<Map<Integer, Integer>> ret = new ArrayList<>();
		for (Map<Integer, Integer> map : list) {
			ret.add(Collections.unmodifiableMap(map));
		}
		//集合转不可变对象
		ret = Collections.unmodifiableList(ret);
		return (T)ret;
	}
	
	
	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_INT;
	}
	
}
