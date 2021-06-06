package com.cat.zproto.domain.system;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.DruidDataSourceUtils;
import com.alibaba.fastjson.JSON;
import com.cat.zproto.util.Pair;

/**
 * 设置配置
 * 
 * @auth Jeremy
 * @date 2021年6月2日下午7:53:27
 */
public class SettingConfig {

	/**
	 * 版本控制路径<br>
	 * 因为git跟svn的用法略显不同, 所以可能未来会抽象出一个版本控制类<br>
	 * 分别用于git合svn的版本控制
	 */
	private Pair<String, String> versionControllPath;

	/**
	 * 数据源信息, 数据库连接信息, 用于反向生成javapojo.
	 */
	private SettingMysql dbInfo;

	/**
	 * 生成的代码路径
	 */
	private String codePath;
	
	private SettingProto proto;

	
	public SettingConfig() {
	}
	
	public SettingConfig(Pair<String, String> versionControllPath) {
		this.versionControllPath = versionControllPath;
	}

	public Pair<String, String> getVersionControllPath() {
		return versionControllPath;
	}

	public void setVersionControllPath(Pair<String, String> versionControllPath) {
		this.versionControllPath = versionControllPath;
	}

	public SettingMysql getDbInfo() {
		return dbInfo;
	}

	public void setDbInfo(SettingMysql dbInfo) {
		this.dbInfo = dbInfo;
	}

	public String getCodePath() {
		return codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}
	
	public SettingProto getProto() {
		return proto;
	}

	public void setProto(SettingProto proto) {
		this.proto = proto;
	}
	
	public static void main(String[] args) {
		Pair<String, String> pair = Pair.of("svn", "svn://139.9.44.104/rabbit/");
		SettingConfig setting = new SettingConfig(pair);
		
		SettingProto proto = new SettingProto();
		proto.setProtoPath("./proto3/proto");
		proto.getGeneratorPath().put("java", "./proto3/server");
		proto.getGeneratorPath().put("csharp", "./proto3/csharp");
		proto.setJavaPackagePath("com.cat.server.game.data.proto");
		proto.setProtoExePath("./proto3/exec/protoc.exe");
		setting.setProto(proto);
		
		setting.setCodePath("./temps/po");

		SettingMysql info = new SettingMysql();
		info.setUrl(
				"jdbc:mysql://139.9.44.104:3306/coral?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true");
		info.setUsername("root");
		info.setPassword("Jeremy2oe5.");
		info.setDbName("coral");
		info.setInitialSize(1);
		setting.setDbInfo(info);

		String json = JSON.toJSONString(setting, true);
		System.out.println(json);
		
	}

}
