package com.cat.zproto.domain.template;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.core.result.SystemCodeEnum;
import com.cat.zproto.enums.TemplateEnum;
import org.springframework.core.io.ClassPathResource;

public class TemplateDomain {
	
	private static Logger logger = LoggerFactory.getLogger(TemplateDomain.class);
	
	/**
	 * 模板类型
	 */
	private int type;
	/**
	 * 模板列表<br>
	 * key: 文件名 <br>
	 * value: 模板结构
	 */
	private Map<Integer, TemplateStruct> templateMap;
	
	/**
	 * id生成器
	 */
	private AtomicInteger generator;
	
	public final static int COEFFICIENT = 1000;
	
	public TemplateDomain(int type) {
		super();
		this.type = type;
		this.templateMap = new HashMap<>();
		this.generator = new AtomicInteger(type * COEFFICIENT);
	}
	
	public TemplateDomain(int type, Map<Integer, TemplateStruct> templateMap) {
		super();
		this.type = type;
		this.templateMap = templateMap;
		this.generator = new AtomicInteger(type * COEFFICIENT);
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public Map<Integer, TemplateStruct> getTemplateMap() {
		return templateMap;
	}
	public void setTemplateMap(Map<Integer, TemplateStruct> templateMap) {
		this.templateMap = templateMap;
	}
	public TemplateStruct getTemplate(int id) {
		return templateMap.get(id);
	}
	/**
	 * 判断是否存在该文件
	 * @param name
	 * @return
	 */
	public boolean contains(String name) {
		for (TemplateStruct struct: templateMap.values()) {
			if (StringUtils.equals(struct.getName(), name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 创建新的模板
	 * @param name
	 * @param content
	 * @return
	 */
	public TemplateStruct create(String name, String content) {
		int id = generator.incrementAndGet();
		TemplateStruct struct = TemplateStruct.create(id, name, type, content);
		return struct;
	}
	
	/**
	 * 文件内容替换
	 * 1. 持久化到本地
	 * 2.更新缓存
	 */
	public SystemCodeEnum replaceTemplate(int id, String name, String content) {
		TemplateStruct struct = getTemplate(id);
		if (struct == null) {
			return SystemCodeEnum.ERROR_NOT_FOUND_FREEMAKER;
		}
		struct.setContent(content);
		this.saveTemplate(id);
		return SystemCodeEnum.SUCCESS;
	}
	
	/**
	 * 文件内容替换
	 * 1. 持久化到本地
	 * 2.更新缓存
	 */
	public SystemCodeEnum createNewTemplate(String name, String content) {
		if (this.contains(name)) {
			return SystemCodeEnum.ERROR_ALREADY_EXITS_FREEMAKER;
		}
		TemplateStruct struct = this.create(name, content);
		this.templateMap.put(struct.getId(), struct);
		this.saveTemplate(struct.getId());
		return SystemCodeEnum.SUCCESS;
	}
	
	/**
	 * 模板文件删除
	 * 1. 删除结构
	 * 2.删除文件
	 */
	public SystemCodeEnum deleteTemplate(int id, String name) {
		TemplateStruct struct = this.templateMap.remove(id);
		if (struct == null) {
			return SystemCodeEnum.ERROR_NOT_FOUND_FREEMAKER;
		}
		TemplateEnum tenum = TemplateEnum.getEnum(type);
		String path = tenum.getStructPath().concat(File.separator).concat(struct.getJsonName());
		File delFile = new File(path);
		try {
			//删除结构文件
			FileUtils.copyFileToDirectory(delFile, new File(CommonConstant.FTL_BACKUP_PATH), true);
			FileUtils.deleteQuietly(delFile);
			
			//删除模板文件
			path = tenum.getPath().concat(File.separator).concat(struct.getFtlName());
			FileUtils.deleteQuietly(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("delete error, BACKUP failed");
		}
		return SystemCodeEnum.SUCCESS;
	}
	
	/**
	 * 模板改名
	 * 1. 修改本地文件
	 * 2. 修改缓存
	 * @param newName 名字不能带有任何后缀
	 */
	public SystemCodeEnum renameTemplate(int id, String newName) {
		TemplateStruct struct = getTemplate(id);
		if (struct == null) {
			return SystemCodeEnum.ERROR_NOT_FOUND_FREEMAKER;
		}
		else if (this.contains(newName)) {
			return SystemCodeEnum.ERROR_ALREADY_EXITS_FREEMAKER;
		}
		TemplateEnum tenum = TemplateEnum.getEnum(type);
		String dirPath = tenum.getPath().concat(File.separator);
		File oldNameFile = new File(dirPath.concat(struct.getFtlName()));
		File newNameFile = new File(dirPath.concat(newName));
		boolean bool = oldNameFile.renameTo(newNameFile);
		if (bool) {
			struct.setName(newName);
		}
		return SystemCodeEnum.SUCCESS;
	}
	
	/**
	 * 模板改名
	 * 1. 修改本地文件
	 * 2. 修改缓存
	 * @param name 名字不能带有任何后缀
	 */
	public TemplateStruct newTemplate(String name) {
		TemplateStruct struct = this.create(name, StringUtils.EMPTY);
		this.templateMap.put(struct.getId(), struct);
		return struct;
	}
	
	/**
	 * 保存指定文件, 持久化到本地
	 * 1. 存储模板结构
	 * 2. 存储模板文件
	 * @param id
	 */
	private void saveTemplate(int id) {
		TemplateStruct struct = getTemplate(id);
		if (struct== null) {
			return;
		}
		TemplateEnum tenum = TemplateEnum.getEnum(type);
		String path = tenum.getPath().concat(File.separator).concat(struct.getFtlName());
		File file = new File(path);
		try {
			FileUtils.write(file, struct.getContent(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("save error", e);
		}
		//保存结构信息
		saveStruct(struct);
	}
	
	/**
	 * 保存指定文件, 持久化到本地
	 * 1. 存储模板结构
	 * @param struct
	 */
	private void saveStruct(TemplateStruct struct) {
		TemplateEnum tenum = TemplateEnum.getEnum(type);
		String path = tenum.getStructPath().concat(File.separator).concat(struct.getJsonName());
		File file = new File(path);
		String data = JSON.toJSONString(struct, true);
		try {
			FileUtils.write(file, data, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("save error", e);
		}
	}
	
	/**
	 * 初始化domain
	 * 1. 从struct目录中加载结构文件, 生成结构
	 * 2.从模板目录中, 加载模板文件, 对比已存在的结构, 有则忽略, 无则加载
	 * @throws IOException 
	 */
	public void initDomain() throws IOException {
		TemplateEnum tenum = TemplateEnum.getEnum(type);
		if (tenum == null) {
			logger.info("initDomain error, tenum is null, type:{}", type);
			return;
		}
		//加载结构目录, 初始化模板结构信息
		File file = new File(tenum.getStructPath());
		if (!file.exists()) {
			FileUtils.forceMkdir(file);
		}
		File[] files = file.listFiles();
		for (File f : files) {
			String content = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
			TemplateStruct struct = JSONObject.parseObject(content, TemplateStruct.class);
			this.templateMap.put(struct.getId(), struct);
		}
		//加载模板文件目录, 对比模板文件目录和结构目录的, 新增的模板文件自动加载进来
		file = new File(tenum.getPath());
		for (File f : file.listFiles()) {
			//存在该文件, 跳过
			String name = f.getName().substring(0, f.getName().indexOf("."));
			if (contains(name)) {
				continue;
			}
			String content = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
			TemplateStruct struct = this.create(name, content);
			this.templateMap.put(struct.getId(), struct);
			this.saveStruct(struct);
		}
		//加载系统目录内的模板
//		initApi();
	}

//	private void initApi() throws IOException {
//		ClassPathResource resource = new ClassPathResource("/ftl/code/Api.ftl");
//		InputStream inputStream = resource.getInputStream();
//		String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
//		TemplateStruct struct = this.create("Api", content);
//		struct.setId(1);//默认第一个
//		this.templateMap.put(struct.getId(), struct);
//		this.saveStruct(struct);
//	}
	
}
