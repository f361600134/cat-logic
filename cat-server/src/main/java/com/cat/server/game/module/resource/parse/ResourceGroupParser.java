package com.cat.server.game.module.resource.parse;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.cat.server.game.module.activity.time.point.ITimePoint;
import com.cat.server.game.module.activity.time.point.impl.CycleTimePoint;
import com.cat.server.game.module.activity.time.point.impl.DateTimePoint;
import com.cat.server.game.module.activity.time.point.impl.OpenDayTimePoint;
import com.cat.server.game.module.resource.domain.ImmutableResourceGroup;
import com.cat.server.game.module.resource.domain.ResourceGroup;

/**
 * 
 * 时间点解析器
 * @author Jeremy
 */
public class ResourceGroupParser implements ObjectDeserializer {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	

	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
//		JSONLexer laxer = parser.lexer;
//		System.out.println("===0===="+parser.parse());
		JSONObject jsonObject = parser.parseObject();
//		jsonObject.get("");
//		System.out.println("=====1=====");
//		System.out.println("laxer.stringVal:"+laxer.stringVal());
//		System.out.println("jsonObject:"+jsonObject);
//		System.out.println("type:"+type);
//		System.out.println("fieldName:"+fieldName);
//		Object obj = parser.input;
//		System.out.println("Object:"+obj);
//		Type obj = parser.parseObject(type);
//		System.out.println("=====2====="+obj);
		Map<Integer, Integer> map = jsonObject.getObject("dictionary", Map.class);
		ResourceGroup group = new ImmutableResourceGroup(map);
//		System.out.println("======3===="+group);
		
		return (T)group;
	}
	

	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_INT;
	}

}
