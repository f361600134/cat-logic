package com.cat.server.game.module.resource.parse;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.cat.server.game.module.resource.domain.ImmutableResourceGroup;
import com.cat.server.game.module.resource.domain.ResourceGroup;

/**
 * 
 *  獎勵組解析器
 * @author Jeremy
 */
public class ResourceGroupParser implements ObjectDeserializer {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		ResourceGroup gourp = null;
		if (parser.lexer.token() == JSONToken.LBRACKET) {
			//以[开头,是策划配置组装的数据结构,转成int二维数组后, 转不可变资源组
			int[][] arrays = parser.parseObject(int[][].class);
			Map<Integer, Integer> map = new HashMap<>();
			for (int[] arrs : arrays) {
				map.put(arrs[0], arrs[1]);
			}
			gourp = new ImmutableResourceGroup(map);
		}else if(parser.lexer.token() == JSONToken.LBRACE){
			//以{开头,是程序持久化的数据结构,转可变资源组
			JSONObject jsonObject = parser.parseObject();
			Map<Integer, Integer> map = jsonObject.getObject("dictionary", new TypeReference<Map<Integer, Integer>>(){});
			gourp = new ResourceGroup(map);
		}
		return (T)gourp;
	}

	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_INT;
	}

}
