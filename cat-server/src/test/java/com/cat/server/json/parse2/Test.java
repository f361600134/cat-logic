package com.cat.server.json.parse2;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.cat.server.game.module.resource.domain.ResourceGroup;

public class Test {
	
	public static void main(String[] args) {
		
		String json = "{\"group\" : {\"dictionary\" : {\"100001\" : 2, \"100002\" : 3} } }";
		System.out.println(json);
		
		//ParserConfig.getGlobalInstance().putDeserializer(ResourceGroup.class, new ResourceGroupParser());
//		ParserConfig.getGlobalInstance().putDeserializer(Map.class, new ResourceGroupParser());
//		String json = "\"group\" : {\"100001\" : 2,\"100002\" : 3}";
//		Stu stu = Stu.create();
////		System.out.println(stu);
////		System.out.println(JSON.toJSON(stu));
//		String json = JSON.toJSON(stu).toString();
//		System.out.println(json);
//		
		Stu stu2 = JSON.parseObject(json, Stu.class);
		System.out.println(stu2);
//		stu2.getGroup().addCount(1, 1);
		
	}
	
	


}

