package com.cat.zproto.domain;

import java.util.HashMap;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.cat.zproto.util.Pair;

/**
 * 设置配置
 * @auth Jeremy
 * @date 2021年6月2日下午7:53:27
 */
public class SettingConfig {

	/** 
	 * protp文件路径
	 * */
	private String protoPath;
	
	/**
	 * proto生成的文件路径<br>
	 * key:语言,value:路径<br>
	 * 配置在此map内的路径,均可以生成<br>
	 * eg: key:Java, value:javaPath
	 * eg: key:c#  , value:csharpPath
	 * eg: key:nodejs, value:nodejsPath
	 */
	private Map<String, String> generatorPath = new HashMap<>();
	
	/**
	 * 版本控制路径<br>
	 * 因为git跟svn的用法略显不同, 所以可能未来会抽象出一个版本控制类<br>
	 * 分别用于git合svn的版本控制
	 */
	private Pair<String, String> versionControllPath;
	
	/**
	 * 数据源信息, 数据库连接信息, 用于反向生成javapojo.
	 */
	private MysqlInfo dbInfo;
	
	/**
	 * 生成的代码路径
	 */
	private String codePath;
	
	public SettingConfig(Pair<String, String> versionControllPath) {
		this.versionControllPath = versionControllPath;
	}
	
	public String getProtoPath() {
		return protoPath;
	}



	public void setProtoPath(String protoPath) {
		this.protoPath = protoPath;
	}



	public Map<String, String> getGeneratorPath() {
		return generatorPath;
	}



	public void setGeneratorPath(Map<String, String> generatorPath) {
		this.generatorPath = generatorPath;
	}



	public Pair<String, String> getVersionControllPath() {
		return versionControllPath;
	}



	public void setVersionControllPath(Pair<String, String> versionControllPath) {
		this.versionControllPath = versionControllPath;
	}

	public MysqlInfo getDbInfo() {
		return dbInfo;
	}

	public void setDbInfo(MysqlInfo dbInfo) {
		this.dbInfo = dbInfo;
	}
	
	public String getCodePath() {
		return codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}

	public static void main(String[] args) {
		Pair<String, String> pair = Pair.of("svn", "svn://139.9.44.104/rabbit/");
		SettingConfig setting = new SettingConfig(pair);
		setting.setProtoPath("./proto3/proto");
		setting.getGeneratorPath().put("java", "./proto3/server");
		setting.getGeneratorPath().put("c#", "./proto3/csharp");
		setting.setCodePath("./temps/po");
		
		MysqlInfo info = new MysqlInfo();
		info.setDbUrl("jdbc:mysql://139.9.44.104:3306/coral?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true");
		info.setUsername("root");
		info.setPassword("Jeremy2oe5.");
		info.setDbName("coral");
		setting.setDbInfo(info);
		
		System.out.println(JSON.toJSONString(setting, true));
	}
	
	public static class MysqlInfo{
		/**
		 * 数据源是mysql数据库<br>
		 * 如果数据源是excel表/nosql怎么办<br>
		 * 所以此处是否需要支持多数据源? 还是说只支持一个mysql即可<br>
		 * 目前先实现mysql版本
		 */
		private String dbUrl;
		private String username;
		private String password;
		private String dbName;
		
		public String getDbUrl() {
			return dbUrl;
		}

		public void setDbUrl(String dbUrl) {
			this.dbUrl = dbUrl;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getDbName() {
			return dbName;
		}

		public void setDbName(String dbName) {
			this.dbName = dbName;
		}
		
	}
}
