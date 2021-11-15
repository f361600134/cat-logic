package com.cat.server.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cat.server.game.module.user.Stu;

public class Main {
	
	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
//		for (int i = 0; i < 100000; i++) {
//			Stu stu = new Stu();
////			Stu stu;
////			try {
////				stu = Stu.class.newInstance();
////			} catch (InstantiationException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			} catch (IllegalAccessException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//			stu= null;
//		}
//		Stu stud = new Stu();
//		stud.getMap().put(1, 1);
//		stud.getMap().put(2, 2);
//		System.out.println(stud.getMap());
//		
		Map<Integer, Integer> map = new HashMap<>();
		map.put(1, 1);
		map.put(2, 2);
		String json = JSON.toJSONString(map);
		System.out.println(json);
		
		Class<?> clazz = Stu.class;
		Stu stu = null;
		try {
			stu = (Stu)clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(stu.getMap());
		Field[] files = clazz.getDeclaredFields();
		for (Field field: files) {
			System.out.println(field.getGenericType());
			Object obj = JSONObject.parseObject(json,field.getGenericType());
			System.out.println("===>"+obj);
			field.setAccessible(true);
			try {
				field.set(stu, obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(stu.getMap());
//		System.out.println("Cost Time(ms):"+(System.currentTimeMillis() - startTime));
	}

}
