package com.cat.server.json.parse2;

import com.alibaba.fastjson.JSON;

public class StuTest {
	public static void main(String[] args) {
		Stu stu = Stu.create();
		String json = JSON.toJSON(stu).toString();
		System.out.println(json);
	}
}
