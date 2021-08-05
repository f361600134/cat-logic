package com.cat.zproto.domain.system;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.annotation.JSONField;
import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.util.Pair;

/**
 * 版本控制
 * @author Jeremy
 */
public class SettingVersion {

	private static Logger logger = LoggerFactory.getLogger(SettingVersion.class);
	
	/**
	 * 版本号,对内版本号
	 */
	private String version;
	/**
	 * 版本控制路径<br>
	 * 因为git跟svn的用法略显不同, 所以可能未来会抽象出一个版本控制类<br>
	 * 分别用于git合svn的版本控制<br>
	 * 暂时没用上
	 */
	private Pair<String, String> versionControllPath;
	
	/**
	 * 初始化日期
	 */ 
	private String initDate;
	
	
	//=====================无需存储字段======================
	
	/**
	 * configData的目录路径
	 */
	private transient String configDataDirPath;
	/**
	 * module结构存储的路径
	 */
	private transient String modulePath;
	/**
	 * proto结构路径
	 */
	private transient String protoDataDir;
	/**
	 * proto协议号路径
	 */
	private transient String protoIdPath;
	/**
	 * 生成的代码路径
	 */
	private transient String codePath;
	/**
	 * 生成的协议路径
	 */
	private transient String protoMessagePath;
	/**
	 * 生成的协议目录
	 */
	private transient String genDir;

	/**
	 * 版本路径
	 */
	private transient String versionDir;

	
	public SettingVersion() {
		
	}
	/**
	 * @param versionControllPath
	 */
	public SettingVersion(String version, Pair<String, String> versionControllPath) {
		this.version = version;
		this.versionControllPath = versionControllPath;
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
	
	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}
	@JSONField(serialize = false)
	public String getModulePath() {
		return modulePath;
	}

	@JSONField(serialize = false)
	public String getProtoDataDir() {
		return protoDataDir;
	}

	@JSONField(serialize = false)
	public String getProtoIdPath() {
		return protoIdPath;
	}

	@JSONField(serialize = false)
	public String getCodePath() {
		return codePath;
	}

	@JSONField(serialize = false)
	public String getProtoMessagePath() {
		return protoMessagePath;
	}

	@JSONField(serialize = false)
	public String getGenDir() {
		return genDir;
	}
	
	@JSONField(serialize = false)
	public String getConfigDataDirPath() {
		return configDataDirPath;
	}

	@JSONField(serialize = false)
	public String getVersionDir() {
		return versionDir;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 初始化信息
	 */
	public void init() {
		try {
			String path = CommonConstant.RESOURCE_CONFIGDATA_PATH;
			this.versionDir = path.concat(File.separator).concat(version);
			this.configDataDirPath = versionDir;
			this.modulePath = versionDir.concat(CommonConstant.MODULE_FILE_NAME);
			this.protoDataDir = versionDir.concat(CommonConstant.PROTO_DATA_DIR);
			this.protoIdPath = versionDir.concat(CommonConstant.PROTO_ID_FILE_NAME);
			//生成路径
			this.genDir = CommonConstant.GENERATOR_PATH.concat(version);
			this.codePath = genDir.concat(CommonConstant.CODE_PACKAGE);
			this.protoMessagePath =  genDir.concat(CommonConstant.PROTO_PACKAGE);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("init error, version:{}", version);
			logger.error("init error.", e);
		}
	}
	
	public static SettingVersion create(String version, Pair<String, String> versionControllPath) {
		SettingVersion ver = new SettingVersion(version, versionControllPath);
		String initDate = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date());
		ver.setInitDate(initDate);
		ver.init();
		return ver;
	}
}
