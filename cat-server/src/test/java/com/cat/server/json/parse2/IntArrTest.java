package com.cat.server.json.parse2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.game.module.resource.parse.ResourceGroupParser;

public class IntArrTest {
	
	public static void main(String[] args) {
		ParserConfig.getGlobalInstance().putDeserializer(ResourceGroup.class, new ResourceGroupParser());
		
		String json = "{\"group\":[[1001,2],[1002,2]],\"name\":\"aa\",\"id\":1}";
//		String json = "{\"name\":\"aa\",\"id\":1,\"group\":{\"dictionary\":{\"1\":1,\"2\":2}}}";
		Stu stu2 = JSON.parseObject(json, Stu.class);
		

		System.out.println(stu2);
		
		stu2.getGroup().addCount(1, 1);
		
		System.out.println(stu2);
	}

}
