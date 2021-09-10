package com.cat.server.serialize;

import com.alibaba.fastjson.JSON;
import com.cat.server.utils.SerializationUtil;

public class TestSerialization {
	
	public static void main(String[] args) {
		Stu stu = new Stu(1,"aa");
		
		byte[] bytes = SerializationUtil.serialize(stu);
		System.out.println(bytes.length);
		System.out.println(JSON.toJSON(stu).toString().length());
		
		Stu2 stu2 = SerializationUtil.deserialize(bytes, Stu2.class);
		System.out.println(stu+", ===========>> "+stu2);
		System.out.println();
	}

	
	public static class Stu {
		private int id;
		private String name;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Stu() {}
		public Stu(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		@Override
		public String toString() {
			return "Stu [id=" + id + ", name=" + name + "]";
		}
		
	}
	public static class Stu2 {
		private int id;
		private String name;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Stu2() {}
		public Stu2(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		@Override
		public String toString() {
			return "Stu2 [id=" + id + ", name=" + name + "]";
		}
	}
}
