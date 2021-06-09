package com.cat.zproto.domain.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * 数据源信息, 数据库连接信息, 用于反向生成javapojo.
	 */
	private SettingMysql dbInfo;

	/**
	 * 生成的代码路径
	 */
	private String codePath;
	
	/**
	 * proto相关的设置
	 */
	private SettingProto proto;

	/**
	 * 版本控制相关配置
	 */
	private final Map<String, SettingVersion> versionInfo = new HashMap<String, SettingVersion>();
	
	public SettingConfig() {
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
	
	public Map<String, SettingVersion> getVersionInfo() {
		return versionInfo;
	}

	public void addVersionInfo(SettingVersion version) {
		versionInfo.put(version.getVersion(), version);
	}
	
	public static void main(String[] args) {
		SettingConfig setting = new SettingConfig();
		//版本控制相关信息
		Pair<String, String> pair = Pair.of("svn", "svn://139.9.44.104/rabbit/");
		SettingVersion version = new SettingVersion("1.0.0", pair);
		version.setAccount("jeremy");
		version.setPassword("jeremy");
		
		SettingVersion version2 = new SettingVersion("1.1.0", pair);
		version2.setAccount("jeremy");
		version2.setPassword("jeremy");
		
		setting.addVersionInfo(version);
		setting.addVersionInfo(version2);
		
		//proto相关信息
		SettingProto proto = new SettingProto();
		proto.setProtoPath("./proto3/proto");
		proto.getGeneratorPath().put("java", "./temps/server");
		proto.getGeneratorPath().put("csharp", "./temps/csharp");
		proto.setJavaPackagePath("com.cat.server.game.data.proto");
		proto.setProtoExePath("./proto3/exec/protoc.exe");
		proto.setProtoIdSortBy(1);
		setting.setProto(proto);
		
		setting.setCodePath("./temps/code");

		//数据源相关信息
		SettingMysql info = new SettingMysql();
		info.setUrl("jdbc:mysql://139.9.44.104:3306/coral?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true");
		info.setUsername("root");
		info.setPassword("Jeremy2oe5.");
		info.setDbName("coral");
		info.setInitialSize(1);
		setting.setDbInfo(info);

		String json = JSON.toJSONString(setting, true);
		System.out.println(json);
		
	}

}