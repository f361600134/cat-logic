package com.cat.zproto.domain.system;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.util.Pair;

/**
 * 版本控制
 * @author Jeremy
 */
public class SettingVersion {
	
	/**
	 * 版本号,对内版本号
	 */
	private String version;
	/**
	 * 版本控制路径<br>
	 * 因为git跟svn的用法略显不同, 所以可能未来会抽象出一个版本控制类<br>
	 * 分别用于git合svn的版本控制
	 */
	private Pair<String, String> versionControllPath;
	
	/**
	 * 初始化日期
	 */
	private String initDate;
	
	
	/**
	 * module结构存储的路径
	 */
	private String modulePath;
	/**
	 * proto结构路径
	 */
	private String protoDataPath;
	/**
	 * proto协议号路径
	 */
	private String protoIdPath;
	/**
	 * 生成的代码路径
	 */
	private String codePath;
	/**
	 * 生成的协议路径
	 */
	private String protoMessagePath;
	/**
	 * 生成的目录
	 */
	private String genDir;
	
	/**
	 * @param versionControllPath
	 */
	public SettingVersion(String version, Pair<String, String> versionControllPath) {
		this.version = version;
		this.versionControllPath = versionControllPath;
		this.initDate = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		//版本模块,消息结构文件
		String versionDir = CommonConstant.RESOURCE_CONFIGDATA_PATH.concat(version);
		File file = new File(versionDir);
		file.mkdirs();
		
		this.modulePath = versionDir.concat(CommonConstant.MODULE_FILE_NAME);
		this.protoDataPath = versionDir.concat(CommonConstant.PROTO_FILE_NAME);
		this.protoIdPath = versionDir.concat(CommonConstant.PROTO_ID_FILE_NAME);
		
		//生成路径
		this.genDir = CommonConstant.GENERATOR_PATH.concat(version);
		file = new File(versionDir);
		file.mkdirs();
		
		this.codePath = genDir.concat(CommonConstant.CODE_PACKAGE);
		this.protoMessagePath =  genDir.concat(CommonConstant.PROTO_PACKAGE);
	}
	
	public String getVersion() {
		return version;
	}
	public Pair<String, String> getVersionControllPath() {
		return versionControllPath;
	}
	public void setVersionControllPath(Pair<String, String> versionControllPath) {
		this.versionControllPath = versionControllPath;
	}
	public String getInitDate() {
		return initDate;
	}

	public String modulePath() {
		return modulePath;
	}

	public String protoDataPath() {
		return protoDataPath;
	}

	public String protoIdPath() {
		return protoIdPath;
	}

	public String codePath() {
		return codePath;
	}
	
	public String protoMessagePath() {
		return protoMessagePath;
	}
	/**
	 * 生成目录
	 * @return
	 */
	public String genDir() {
		return genDir;
	}
	
	
}
