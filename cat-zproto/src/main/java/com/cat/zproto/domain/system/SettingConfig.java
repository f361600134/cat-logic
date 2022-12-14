package com.cat.zproto.domain.system;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import com.alibaba.fastjson.JSON;
import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.core.result.SystemCodeEnum;
import com.cat.zproto.core.result.SystemResult;
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
	 * proto相关的设置
	 */
	private SettingProto proto;
	
	/**
	 * svn相关配置
	 */
	private SettingSvn svn;

	/**
	 * 版本控制相关配置<br>
	 * 默认第一个是主干
	 */
	private LinkedHashMap<String, SettingVersion> versionInfo;
	
	public SettingConfig() {
		this.versionInfo = new LinkedHashMap<String, SettingVersion>();
	}
	
	public SettingMysql getDbInfo() {
		return dbInfo;
	}

	public void setDbInfo(SettingMysql dbInfo) {
		this.dbInfo = dbInfo;
	}

	public SettingProto getProto() {
		return proto;
	}

	public void setProto(SettingProto proto) {
		this.proto = proto;
	}
	
	public LinkedHashMap<String, SettingVersion> getVersionInfo() {
		return versionInfo;
	}

	public void addVersionInfo(SettingVersion version) {
		versionInfo.put(version.getVersion(), version);
	}
	
	public SettingSvn getSvn() {
		return svn;
	}

	public void setSvn(SettingSvn svn) {
		this.svn = svn;
	}
	
	public void init() {
		versionInfo.forEach((key, val)->{
			val.init();
		});
	}

	public static void main(String[] args) {
		SettingConfig setting = new SettingConfig();
		//版本控制相关信息
		SettingVersion version = new SettingVersion("1.0.0", Pair.of("svn", "aaa"));
		SettingVersion version2 = new SettingVersion("1.1.0", Pair.of("svn", "bbb"));
		setting.addVersionInfo(version);
		setting.addVersionInfo(version2);
		
		
		SettingSvn svn = new SettingSvn();
		svn.setAccount("fsc");
		svn.setPassword("Jeremy2oe5");
		svn.setSourceCheckOutUrl("svn://139.9.44.104/rabbit/");
		setting.setSvn(svn);
		
		//proto相关信息
		SettingProto proto = new SettingProto();
		proto.setProtoPath("./proto3/proto");
//		proto.getGeneratorPath().put("java", "./temps/server");
//		proto.getGeneratorPath().put("csharp", "./temps/csharp");
		proto.setJavaPackagePath("com.cat.server.game.data.proto");
		proto.setProtoExePath("./proto3/exec/");
		proto.setProtoIdSortBy(1);
		setting.setProto(proto);
		
		//数据源相关信息
		SettingMysql info = new SettingMysql();
		info.setUrl("jdbc:mysql://139.9.44.104:3306/coral?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true");
		info.setUsername("root");
		info.setPassword("Jeremy2oe5.");
//		info.setDbName("coral");
		
		info.setInitialSize(1);
		setting.setDbInfo(info);

		String json = JSON.toJSONString(setting, true);
		System.out.println(json);
		
	}
	
	/**
	 * 保存setting的配置
	 * @return
	 */
	public SystemResult save() {
		String json = JSON.toJSONString(this, true);
		try {
			FileUtils.write(new File(CommonConstant.SYSTEM_SETTING), json, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return SystemResult.build(SystemCodeEnum.ERROR_NOT_WRITE_FAILED);
		}
		return SystemResult.build(SystemCodeEnum.SUCCESS);
	}

}