package com.cat.server.game.module.resource.parse;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

public class MapTest {

	public static void main(String[] args) {

		// ParserConfig.getGlobalInstance().putDeserializer(ResourceGroup.class, new
		// ResourceGroupParser());
		ParserConfig.getGlobalInstance().putDeserializer(Map.class, new MapParser());

//		String json = "{\"group\" : {\"dictionary\" : {\"100001\" : 2, \"100002\" : 3} } }";
		String json = "{\"group\" : {\"100001\" : 2,\"100002\" : 3}}";
		System.out.println(json);
		// Stu stu = Stu.create();
		//// System.out.println(stu);
		//// System.out.println(JSON.toJSON(stu));
		// String json = JSON.toJSON(stu).toString();
		// System.out.println(json);
		//
		Stu stu2 = JSON.parseObject(json, Stu.class);
		System.out.println(stu2);
		stu2.getGroup().put(2, 1);
		System.out.println(stu2);

	}

}
