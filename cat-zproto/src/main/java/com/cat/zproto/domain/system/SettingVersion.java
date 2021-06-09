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
	 * 登录账号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * module结构存储的地址
	 */
	private String modulePath;
	/**
	 * proto结构地址
	 */
	private String protoDataPath;
	/**
	 * proto协议号地址
	 */
	private String protoIdPath;
	
	/**
	 * @param versionControllPath
	 */
	public SettingVersion(String version, Pair<String, String> versionControllPath) {
		this.version = version;
		this.versionControllPath = versionControllPath;
		this.initDate = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		String versionDir = CommonConstant.RESOURCE_CONFIGDATA_PATH.concat(version);
		File file = new File(versionDir);
		file.mkdirs();
		
		this.modulePath = versionDir.concat(CommonConstant.MODULE_FILE_NAME);
		this.protoDataPath = versionDir.concat(CommonConstant.PROTO_FILE_NAME);
		this.protoIdPath = versionDir.concat(CommonConstant.PROTO_ID_FILE_NAME);
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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

}
