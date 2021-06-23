package com.cat.zproto.domain.system;

import java.io.File;
import java.util.Date;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

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
	private transient String protoDataPath;
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
	public String getProtoDataPath() {
		return protoDataPath;
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
	
	
//	public String modulePath() {
//		return modulePath;
//	}
//
//	public String protoDataPath() {
//		return protoDataPath;
//	}
//
//	public String protoIdPath() {
//		return protoIdPath;
//	}
//
//	public String codePath() {
//		return codePath;
//	}
//	
//	public String protoMessagePath() {
//		return protoMessagePath;
//	}
//	/**
//	 * 生成目录
//	 * @return
//	 */
//	public String genDir() {
//		return genDir;
//	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 初始化信息
	 */
	public void init() {
		try {
//			File dir = ResourceUtils.getFile(CommonConstant.RESOURCE_CONFIGDATA_PATH);
//			ClassPathResource resource = new ClassPathResource(CommonConstant.SYSTEM_SETTING);
//			ClassPathResource resource = new ClassPathResource();
//			InputStream inputStream= resource.getInputStream();
//			String content = FileUtils.readFileToString(file,StandardCharsets.UTF_8);
//			String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
//			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/configdata";
			
			String path = CommonConstant.RESOURCE_CONFIGDATA_PATH;
			//TODO 这里写入文件在linux环境可能存在问题
//			ClassPathResource resource = new ClassPathResource(CommonConstant.RESOURCE_CONFIGDATA_PATH);
//			String path = resource.getURI().getPath();
//			System.out.println("====="+resource.getURI());
//			System.out.println("====="+resource.getURL());
//			System.out.println("====="+resource.getFile());

			String versionDir = path.concat(File.separator).concat(version);
//			File file = new File(versionDir);
//			file.mkdirs();
			this.configDataDirPath = versionDir;
			this.modulePath = versionDir.concat(CommonConstant.MODULE_FILE_NAME);
			this.protoDataPath = versionDir.concat(CommonConstant.PROTO_FILE_NAME);
			this.protoIdPath = versionDir.concat(CommonConstant.PROTO_ID_FILE_NAME);
			
			//生成路径
//			ClassPathResource genResource = new ClassPathResource(CommonConstant.GENERATOR_PATH);
			this.genDir = CommonConstant.GENERATOR_PATH.concat(version);
//			this.genDir = genResource.getPath().concat(version);
//			file = new File(versionDir);
//			file.mkdirs();
			
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
