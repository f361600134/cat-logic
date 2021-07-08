package com.cat.zproto.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cat.zproto.assist.generator.IDefineCodeGenerator;
import com.cat.zproto.assist.generator.IDefineProtoGenerator;
import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.core.result.SystemCodeEnum;
import com.cat.zproto.core.result.SystemResult;
import com.cat.zproto.domain.module.ModuleEntity;
import com.cat.zproto.domain.proto.ProtocolConstant;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.system.SettingVersion;
import com.cat.zproto.domain.table.TableEntity;
import com.cat.zproto.domain.template.TemplateStruct;
import com.cat.zproto.dto.TableFreemarkerDto;
import com.cat.zproto.dto.TemplateDto;
import com.cat.zproto.enums.ProtoTypeEnum;
import com.cat.zproto.enums.TemplateEnum;
import com.cat.zproto.service.CommandService;
import com.cat.zproto.service.DbService;
import com.cat.zproto.service.ModuleService;
import com.cat.zproto.service.ProtoService;
import com.cat.zproto.service.SvnService;
import com.cat.zproto.service.TemplateService;
import com.cat.zproto.util.StringUtil;
import com.cat.zproto.util.ZipUtil;
import com.google.common.collect.BiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Template;

/**
 * 用于处理模块相关操作 1. 模块详细 2. 新增模块 3. 修改模块 4. 删除模块 5. 模块列表
 * 
 * 6. 协议列表 7. 编辑协议 8. 删除协议 9. 新增协议
 * 
 * 10.生成协议 11.生成代码
 * 
 * @author Jeremy
 */
@Controller
@RequestMapping("/module")
public class ModuleController {

	private static Logger logger = LoggerFactory.getLogger(ModuleController.class);

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private ProtoService protoService;

	@Autowired
	private SettingConfig setting;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private CommandService commandService;

	@Autowired
	private DbService dbService;

//	@Autowired
//	private List<IProtoGenerator> generatorProtoList;

//	@Autowired
//	private List<ICodeGenerator> generatorCodeList;
	
	@Autowired
	private IDefineProtoGenerator defineProtoGenerator;
	
	@Autowired
	private IDefineCodeGenerator defineCodeGenerator;

	@Autowired
	private SvnService svnService;

//	/**
//	 * 主页面
//	 * @deprecated
//	 */
//	@RequestMapping("/index")
//	public ModelAndView index() {
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("index");
//		mv.addObject("version", version);
//		return mv;
//	}
	/**
	 * home页面信息
	 * @return
	 * @return ModelAndView
	 * @date 2021年6月12日下午9:50:40
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
	
	/**
	 * 主页面
	 */
	@RequestMapping("/viewModule")
	public ModelAndView viewModule(String version) {
		ModelAndView mv = new ModelAndView();
		if (!setting.getVersionInfo().containsKey(version)) {
			mv.setViewName("error");
		}else {
			mv.setViewName("view_module");
			mv.addObject("version", version);
			mv.addObject("dbType", setting.getDbInfo().getDbType());
		}
		return mv;
	}

	/**
	 * 添加模块界面
	 */
	@RequestMapping("/addModuleView")
	public ModelAndView addModuleView(String version) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("add_module");
		mv.addObject("version", version);
		return mv;
	}

	/**
	 * 修改界面
	 */
	@RequestMapping("/editModuleView")
	public ModelAndView editModuleView(String version, int id) {
		ModuleEntity moduleEntitie = moduleService.getModuleEntity(version, id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("edit_module");
		mv.addObject("version", version);
		mv.addObject("data", moduleEntitie);
		return mv;
	}
	
	/**
	 * 编辑数据源
	 */
	@RequestMapping("/editBeanInfoView")
	public Object editBeanInfoView(String version, int id) {
		ModuleEntity moduleEntitie = moduleService.getModuleEntity(version, id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("edit_beanInfo");
		mv.addObject("version", version);
		mv.addObject("data", moduleEntitie);
		return mv;
	}

	/**
	 * 协议修改界面 1.req协议 2.ack协议 3.dto协议对象
	 * 
	 * @param version 版本
	 * @param id      模块id
	 */
	@RequestMapping("/protoEditView")
	public Object protoEditView(String version, int id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("version", version);
		mv.addObject("id", id);
		ModuleEntity entity = moduleService.getModuleEntity(version, id);
		if (entity == null) {
			mv.setViewName("error");
			return mv;
		}
		mv.setViewName("edit_protocol");
		ProtocolObject protoObject = protoService.getProtoObject(version, entity.getName());
		if (protoObject == null) {
			return mv;
		}
		/*
		 *下拉列表 
		 *1. 基础类型
		 *2.其他自定义类型
		 */
		List<String> allTypes = new ArrayList<>(ProtoTypeEnum.enumMap.values());
//		allTypes.addAll(protoService.getAllPbProtoName(version, ProtocolConstant.PB_PREFIX));
		allTypes.addAll(protoService.getAllPbProtoName(version, setting.getProto().getPbPrefix()));
		/*
		 * 也只是为了做一个排序, 写这么多代码,这里的数据结构没有设计好 工具完成后要想办法重构代码 20210603
		 * 工具基本完成, 已经懒得重构了 20210611
		 */
		BiMap<String, Integer> protoIdMap = protoService.getProtoIdMap(version);
		TreeMap<Integer, ProtocolStructure> structureMap = new TreeMap<>();
		List<ProtocolStructure> structures = new ArrayList<ProtocolStructure>();
		for (ProtocolStructure struct : protoObject.getStructures().values()) {
			String name = struct.getName();
			int protoId = protoIdMap.getOrDefault(name, 0);
			if (protoId == 0) {//表示协议对象
				structures.add(struct);
			} else {
				structureMap.put(protoId, struct);
			}
		}
		structures.addAll(structureMap.values());
		mv.addObject("data", structures);
		mv.addObject("allTypes", allTypes);
		return mv;
	}

	/**
	 * 预览协议界面 1.req协议 2.ack协议 3.dto协议对象
	 * 
	 * @param version 版本
	 * @param id      模块id
	 */
	@RequestMapping("/protoListView")
	public Object protoListView(String version, int id) {
		ModelAndView mv = new ModelAndView();
		ModuleEntity entity = moduleService.getModuleEntity(version, id);
		if (entity == null) {
			// TODO 重定向页面
			return SystemResult.build(SystemCodeEnum.ERROR_PARAM);
		}
		ProtocolObject protoObject = protoService.getProtoObject(version, entity.getName());
		if (protoObject == null) {
			// TODO 重定向页面
			mv.setViewName("no_proto");
			return mv;
		}
		BiMap<String, Integer> protoNameIdMap = protoService.getProtoIdMap(version);
		Collection<ProtocolStructure> structures = protoObject.getStructures().values();

		// 使用treeMap是为了根据协议号进行排序
		TreeMap<Integer, ProtocolStructure> requests = new TreeMap<>();
		TreeMap<Integer, ProtocolStructure> responses = new TreeMap<>();
		List<ProtocolStructure> pbs = new ArrayList<>();

//		String reqPrefix = ProtocolConstant.REQ_PREFIX;
//		String respPrefix = ProtocolConstant.RESP_PREFIX;
//		String pbPrefix = ProtocolConstant.PB_PREFIX;
		String reqPrefix = setting.getProto().getReqPrefix();
		String respPrefix = setting.getProto().getRespPrefix();
		String pbPrefix = setting.getProto().getPbPrefix();

		for (ProtocolStructure struct : structures) {
			String name = struct.getName();
			int protoId = protoNameIdMap.getOrDefault(name, 0);
			if (protoId == 0) {
				if (name.startsWith(pbPrefix)) {
					pbs.add(struct);
				}
			} else if (name.startsWith(reqPrefix)) {
				requests.put(protoId, struct);
			} else if (name.startsWith(respPrefix)) {
				responses.put(protoId, struct);
			}
		}
		mv.setViewName("view_protpcol");
		mv.addObject("version", version);
		mv.addObject("requests", requests.values());
		mv.addObject("responses", responses.values());
		mv.addObject("pbs", pbs);
		mv.addObject("protoNameIdMap", protoNameIdMap);
		return mv;
	}

	/**
	 * 协议提交, 替换掉协议信息, 保存至结构, 不生成proto文件, 不生成proto协议
	 * 
	 * @param version      版本
	 * @param id           模块id
	 * @param 本模块对应的所有协议信息
	 */
	@ResponseBody
	@RequestMapping("/protoCommit")
	public Object protoCommit(String version, int id, @RequestBody List<ProtocolStructure> protoStructure) {
		if (protoStructure == null) {
			return SystemResult.build(SystemCodeEnum.ERROR_PARAM);
		}
		if (protoStructure.isEmpty()) {
			return SystemResult.build(SystemCodeEnum.ERROR_NO_CHANGE);
		}
		ModuleEntity entity = moduleService.getModuleEntity(version, id);
		if (entity == null) {
			return SystemResult.build(SystemCodeEnum.UNKNOW);
		}
		String entityName = entity.getName();

		ProtocolObject protoObject = protoService.protoObjectUpdate(version, entityName, protoStructure);
		if (protoObject == null) {
			return SystemResult.build(SystemCodeEnum.ERROR_CANNOT_DOUND_MODULE);
		}
		// 生成协议id
		Map<String, Integer> protoIdMap = protoService.genProtoIds(version, moduleService.getAllModuleEntity(version));
		// 生成proto文件
		String protoPath = setting.getProto().getProtoPath();
		String fileName = protoPath.concat(File.separator).concat(protoObject.getOutClass())
				.concat(ProtocolConstant.PROTO_SUBFIX);
		templateService.printer(protoObject, fileName, "proto.ftl");
		// 生成protoId文件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("javaPath", setting.getProto().getJavaPackagePath());
		map.put("outClass", "PBDefine");
		map.put("protoIdMap", protoIdMap);
		fileName = protoPath.concat(File.separator).concat("PBProtocol").concat(ProtocolConstant.PROTO_SUBFIX);
		templateService.printer(map, fileName, "protoId.ftl");

		return SystemResult.build(SystemCodeEnum.SUCCESS);
	}

	/**
	 * 协议反向加载<br>
	 * 从*.proto文件内,加载结构到protoObject, 存储为本地结构.
	 * 
	 * @return void
	 * @date 2021年6月5日下午10:49:16
	 */
	@RequestMapping("/protoReverseLoad")
	public Object protoReverseLoad(String version) {
		ModelAndView mv = new ModelAndView();
		boolean result = protoService.reverseLoad(version);
		result = result & moduleService.reverseLoad(version, protoService.getAllProtoObject(version),
				protoService.getProtoIdMap(version));
		if (result) {
			mv.setViewName("index");
			mv.addObject("version", version);
		} else {
			mv.setViewName("error");
		}
		return mv;
	}

	/**
	 * 生成协议文件<br>
	 * CALL "bin/protoc.exe" --java_out=%java_out% --proto_path=%proto_path% %%i
	 * <br>
	 * CALL "bin/protoc.exe" --csharp_out=%csharp_out% --proto_path=%proto_path% %%i
	 * <br>
	 * {protoc} -I={protoPath} --java_out={javaPath} {moduleName}.proto <br>
	 * %s -I=%s --java_out=%s %s.proto
	 * 
	 * @param version  版本
	 * @param id       模块id
	 * @param langType 语言类型,all表示所有
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createMessage")
	public Object createMessage(String version, int id, String langType) {
		ModuleEntity entity = moduleService.getModuleEntity(version, id);
		if (entity == null) {
			// TODO 重定向页面
			return SystemResult.build(SystemCodeEnum.ERROR_PARAM);
		}
		ProtocolObject protoObject = protoService.getProtoObject(version, entity.getName());
		if (protoObject == null) {
			// TODO 重定向页面
			return SystemResult.build(SystemCodeEnum.ERROR_PARAM);
		}
		// 返回信息,包含成功和失败的
		String ret = "";
		SystemResult result = null;
		if (langType.equals("all")) {
//			Map<String, String> pathMap = setting.getProto().getGeneratorPath();
//			for (String languegeType : pathMap.keySet()) {
//				result = createMessage(version, protoObject, languegeType);
//				ret = ret.concat(String.format(CommonConstant.GENERATE_RESULT, languegeType, result.tips())).concat("<br>");
//			}
			return SystemResult.build(SystemCodeEnum.ERROR_NOT_SUPPORT);
		} else {
			result = createMessage(version, protoObject, langType);
			ret = String.format(CommonConstant.GENERATE_RESULT, langType, result.tips());
		}
		result.setTips(ret);
		return result;
	}

	private SystemResult createMessage(String version, ProtocolObject protoObject, String langType) {
		String protoFormat = CommonConstant.PROTOC_EXECUTE_FORMAT;
		
		String protoExePath = setting.getProto().getProtoExePath();
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			protoExePath = protoExePath.concat("/").concat("protoc.exe");
		}else {
			protoExePath = protoExePath.concat(File.separator).concat("protoc");
		}
		File exePath = new File(protoExePath);
		protoExePath = exePath.getPath();
		
		String protoPath = setting.getProto().getProtoPath();
		String languageType = langType;
		SettingVersion versionInfo = setting.getVersionInfo().get(version);
		String genPath = versionInfo.getGenDir().concat(File.separator).concat(langType);
		String outClassName = protoObject.getOutClass();
		
		String command = String.format(protoFormat, protoExePath, protoPath, languageType, genPath, outClassName);
		SystemResult result = commandService.execCommand(command);
		logger.info("command:{}, result:{}", command, result);

		// 生成协议文件
//		for (IProtoGenerator generator : generatorProtoList) {
//			generator.generate(version, protoObject);
//		}
		Collection<TemplateStruct> struts = templateService.getAllStruct(TemplateEnum.PROTO.getType());
		for (TemplateStruct templateStruct : struts) {
			defineProtoGenerator.generate(version, templateStruct, protoObject);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/downloadCode")
	public String downloadCode(String version, int id, HttpServletResponse response) {
		ModuleEntity entity = moduleService.getModuleEntity(version, id);
		if (entity == null) {
			return "下载失败";
		}
		String fileName = entity.getName().toLowerCase().concat(".zip");
		String path = CommonConstant.DOWNLOAD_PACKAGE.concat(File.separator).concat(fileName);
		
		File file = new File(path);
		if (!file.isFile()) {
			return "下载失败, 请先生成此文件";
		}
		
		System.out.println("==========================="+file.getPath());
		System.out.println("==========================="+path);
		System.out.println("==========================="+file.getAbsolutePath());
		
		try (InputStream inStream = new FileInputStream(file.getAbsolutePath());
				BufferedInputStream bis = new BufferedInputStream(inStream);) {
			OutputStream os = response.getOutputStream();
			// 设置输出的格式
//            response.reset();
			response.setContentType("application/force-download");// 设置强制下载不打开
			response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
			byte[] b = new byte[1024];
			int len;
			while ((len = inStream.read(b)) > 0) {
				os.write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "未知异常";
		}
		return "下载成功";
	}
	
	/**
	 * 编辑模板页面
	 * @return
	 * @return ModelAndView
	 * @date 2021年6月12日下午9:50:40
	 */
	@ResponseBody
	@RequestMapping("/runTemplate")
	public Object runTemplate(@RequestBody String str) {
		freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_29);
		StringTemplateLoader templateLoader = new StringTemplateLoader();
		configuration.setTemplateLoader(templateLoader);
		configuration.setDefaultEncoding("UTF-8");
		Iterator<Entry<String, SettingVersion>> iter = setting.getVersionInfo().entrySet().iterator();
		SettingVersion first = iter.hasNext() ? iter.next().getValue() : null;
		//使用默认的数据, 生成dto
		TableFreemarkerDto dto = createDto(first.getVersion(), 101);
//		StringTemplateLoader templateLoader = new StringTemplateLoader();
		// template = 虚拟名称, 用来当作获取静态文件的key
		templateLoader.putTemplate("template", str); 
//		configuration.setTemplateLoader(templateLoader);
		
		configuration.setClassicCompatible(true);
		
		String ret = "";
		try (StringWriter stringWriter = new StringWriter();){
			Template template = configuration.getTemplate("template", "utf-8");
			template.process(dto, stringWriter);
			ret = stringWriter.toString();
		} catch (Exception e) {
			logger.error("runTemplate error", e);
			return SystemResult.build(SystemCodeEnum.ERROR_GENERATE_FREEMAKER, e.getMessage());
		}		
		return SystemResult.build(SystemCodeEnum.SUCCESS, ret);
	}
	
	/**
	 * 显示模板详细信息
	 * @return
	 * @return ModelAndView
	 * @date 2021年6月12日下午9:50:40
	 */
	@ResponseBody
	@RequestMapping("/showTemplateDetail")
	public Object showTemplateDetail(@RequestBody TemplateDto templateDto) {
		//获取到模板文件内容
		try {
			int nodeId = templateDto.getCurNode();
			String name = templateDto.getFileName();
			TemplateStruct struct = templateService.getStruct(nodeId, name);
			if (struct == null) {
				return SystemResult.build(SystemCodeEnum.ERROR_NOT_FOUND_FREEMAKER);
			}
			return SystemResult.build(SystemCodeEnum.SUCCESS, struct.getContent());
		} catch (Exception e) {
			logger.error("showTemplate error, {}",e);
			return SystemResult.build(SystemCodeEnum.SUCCESS, e.getMessage());
		}
	}
	
	/**
	 * 更新模板内容,文件一定要存在,否则不能更新
	 * @return
	 * @return ModelAndView
	 * @date 2021年6月12日下午9:50:40
	 */
	@ResponseBody
	@RequestMapping(value = "/updateTemplateDetail", method = {RequestMethod.POST})
	public Object updateTemplateDetail(@RequestBody TemplateDto templateDto) {
		//获取到模板文件内容
		int id = templateDto.getCurNode();
		String fileName = templateDto.getFileName();
		String content = templateDto.getContent();
		try {
			SystemCodeEnum code = templateService.updateStruct(id, fileName, content);
			if (code != SystemCodeEnum.SUCCESS) {
				return SystemResult.build(code);
			}
			return SystemResult.build(SystemCodeEnum.SUCCESS, content);
		} catch (Exception e) {
			logger.error("showTemplate error, {}",e);
			return SystemResult.build(SystemCodeEnum.UNKNOW, e.getMessage());
		}
	}
	
	/**
	 * 提交的内容,生成最新的模板信息
	 * @return
	 * @return ModelAndView
	 * @date 2021年6月12日下午9:50:40
	 */
	@ResponseBody
	@RequestMapping(value = "/saveTemplateDetail", method = {RequestMethod.POST})
	public Object saveTemplateDetail(@RequestBody TemplateDto templateDto) {
		//获取到模板文件内容
		String fileName = templateDto.getFileName();
		String content = templateDto.getContent();
		try {
			//如果不是纯字母,返回错误码
			if (!StringUtil.isEnglish(fileName)) {
				return SystemResult.build(SystemCodeEnum.ERROR_SUBFIX_NOT_RIGHT);
			}
			SystemCodeEnum code = templateService.saveStruct(templateDto.getCurNode(), fileName, content);
			if (code != SystemCodeEnum.SUCCESS) {
				return SystemResult.build(code);
			}
			return SystemResult.build(SystemCodeEnum.SUCCESS, content);
		} catch (Exception e) {
			logger.error("showTemplate error, {}",e);
			return SystemResult.build(SystemCodeEnum.SUCCESS, e.getMessage());
		}
	}
	
	/**
	 * 更新模板内容,文件一定要存在,否则不能更新
	 * @return
	 * @return ModelAndView
	 * @date 2021年6月12日下午9:50:40
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteTemplateFile")
	public Object deleteTemplateFile(@RequestBody TemplateDto templateDto) {
		//获取到模板文件内容
		int id = templateDto.getCurNode();
		String fileName = templateDto.getFileName();
		try {
			SystemCodeEnum code = templateService.deleteStruct(id, fileName);
			if (code != SystemCodeEnum.SUCCESS) {
				return SystemResult.build(code);
			}
			return SystemResult.build(SystemCodeEnum.SUCCESS);
		} catch (Exception e) {
			logger.error("showTemplate error, {}",e);
			return SystemResult.build(SystemCodeEnum.UNKNOW, e.getMessage());
		}
	}
	
	/**
	 * 模板改名
	 * @return
	 * @return ModelAndView
	 * @date 2021年6月12日下午9:50:40
	 */
	@ResponseBody
	@RequestMapping(value = "/renameTemplateFile")
	public Object renameTemplateFile(@RequestBody TemplateDto dto) {
		//获取到模板文件内容
		try {
			String fileName = dto.getFileName();
			if (fileName.endsWith(CommonConstant.TEMPLATE_SUBFIX)) {
				fileName = fileName.replaceAll(CommonConstant.TEMPLATE_SUBFIX, "");
			}
			if (!StringUtil.isEnglish(fileName)) {
				return SystemResult.build(SystemCodeEnum.ERROR_SUBFIX_NOT_RIGHT);
			}
			SystemCodeEnum code = templateService.renameStruct(dto.getCurNode(), dto.getFileName());
			if (code != SystemCodeEnum.SUCCESS) {
				return SystemResult.build(code);
			}
			return SystemResult.build(SystemCodeEnum.SUCCESS);
		} catch (Exception e) {
			logger.error("showTemplate error, {}",e);
			return SystemResult.build(SystemCodeEnum.UNKNOW, e.getMessage());
		}
	}
	
	/**
	 * 模板改名
	 * @return
	 * @return ModelAndView
	 * @date 2021年6月12日下午9:50:40
	 */
	@ResponseBody
	@RequestMapping(value = "/newTemplateFile")
	public Object newTemplateFile(@RequestBody TemplateDto dto) {
		//获取到模板文件内容
		try {
			int curNodeId  = dto.getCurNode();
			String name = "undefined";
			return templateService.newStruct(curNodeId, name);
		} catch (Exception e) {
			logger.error("showTemplate error, {}",e);
			return SystemResult.build(SystemCodeEnum.UNKNOW, e.getMessage());
		}
	}
	
	private TableFreemarkerDto createDto(String version, int moduleId){
		ModuleEntity entity = moduleService.getModuleEntity(version, moduleId);
		if (entity == null) {
			return null;
		}
		ProtocolObject protoObject = protoService.getProtoObject(version, entity.getName());
		if (protoObject == null) {
			return null;
		}
		//TODO 优化点, 这里是读取数据库信息, 匹配模块
		// 每次重新读取数据库所有表, 进行匹配.
		//加入缓存, 先从缓存获取数据, 没有数据再去拉库
//		List<TableEntity> entitys = dbService.readDb();
//		TableEntity tableEntity = null;
//		for (TableEntity te : entitys) {
//			if (StringUtils.equals(te.getEntityName(), entity.getName())) {
//				tableEntity = te;
//				break;
//			}
//		}
		//TODO 优化点，在无数据库时，根据模块信息生成代码。
		TableEntity tableEntity = dbService.getTableEntity(entity.getName());
		if (tableEntity == null) {
			return null;
		}
		TableFreemarkerDto dto = new TableFreemarkerDto(tableEntity, protoObject, entity);
//		//协议层方法获取
		List<ProtocolStructure> protoPBStructList = Lists.newArrayList();
		List<ProtocolStructure> protoReqStructList = Lists.newArrayList();
		Map<String, ProtocolStructure> protoAckStructMap = Maps.newHashMap();
		Map<String, ProtocolStructure> structureMap = protoObject.getStructures();

		String reqPrefix = setting.getProto().getReqPrefix();
		String respPrefix = setting.getProto().getRespPrefix();
		String pbPrefix = setting.getProto().getPbPrefix();
		
		for (String key : structureMap.keySet()) {
			if (key.startsWith(respPrefix)) {
				String newKey = key.replace(respPrefix, reqPrefix);
				protoAckStructMap.put(newKey, structureMap.get(key));
			} else if (key.startsWith(reqPrefix)) {
				protoReqStructList.add(structureMap.get(key));
			} else if (key.startsWith(pbPrefix)) {
				protoPBStructList.add(structureMap.get(key));
			}
		}
		dto.getProtoAckStructMap().putAll(protoAckStructMap);
		dto.getProtoReqStructList().addAll(protoReqStructList);
		dto.getProtoPBStructList().addAll(protoPBStructList);
		return dto;
	}
	
	/**
	 * 生成代码<br>
	 * 根据定义的module信息, 找到数据库中的table, 最终根据table结构生成代码.<br>
	 * 数据库表命名会奇奇怪怪的, 比如加前后缀. 此处要兼容各种奇怪的命名<br>
	 * 
	 * @note 硬性要求--moduleName必须要跟表命名一致 <br>
	 *       表命通常小写, 模块名这里定义必须要写, 所以判断查询时需要忽略大小写.
	 * @return void
	 * @date 2021年6月6日下午3:38:25
	 */
	@ResponseBody
	@RequestMapping("/createCode")
	public Object createCode(String version, int id) {
		ModuleEntity entity = moduleService.getModuleEntity(version, id);
		if (entity == null) {
			return SystemResult.build(SystemCodeEnum.ERROR_CANNOT_FOUND_PROTOOBJECT);
		}
		ProtocolObject protoObject = protoService.getProtoObject(version, entity.getName());
		if (protoObject == null) {
			return SystemResult.build(SystemCodeEnum.ERROR_CANNOT_DOUND_MODULE);
		}
//		List<TableEntity> entitys = dbService.readDb();
//		TableEntity tableEntity = null;
//		for (TableEntity te : entitys) {
//			if (StringUtils.equals(te.getEntityName(), entity.getName())) {
//				tableEntity = te;
//				break;
//			}
//		}
		TableEntity tableEntity = dbService.getTableEntity(entity.getName());
		if (tableEntity == null) {
			return SystemResult.build(SystemCodeEnum.ERROR_NOT_FOUND_TABLE);
		}
		TableFreemarkerDto dto = new TableFreemarkerDto(tableEntity, protoObject, entity);
//		//协议层方法获取
		List<ProtocolStructure> protoPBStructList = Lists.newArrayList();
		List<ProtocolStructure> protoReqStructList = Lists.newArrayList();
		Map<String, ProtocolStructure> protoAckStructMap = Maps.newHashMap();
		Map<String, ProtocolStructure> structureMap = protoObject.getStructures();

		String reqPrefix = setting.getProto().getReqPrefix();
		String respPrefix = setting.getProto().getRespPrefix();
		String pbPrefix = setting.getProto().getPbPrefix();
		
		for (String key : structureMap.keySet()) {
			if (key.startsWith(respPrefix)) {
				String newKey = key.replace(respPrefix, reqPrefix);
				protoAckStructMap.put(newKey, structureMap.get(key));
			} else if (key.startsWith(reqPrefix)) {
				protoReqStructList.add(structureMap.get(key));
			} else if (key.startsWith(pbPrefix)) {
				protoPBStructList.add(structureMap.get(key));
			}
		}
		dto.getProtoAckStructMap().putAll(protoAckStructMap);
		dto.getProtoReqStructList().addAll(protoReqStructList);
		dto.getProtoPBStructList().addAll(protoPBStructList);
		// 生成代码
//		for (ICodeGenerator generator : generatorCodeList) {
//			generator.generate(version, dto);
//		}
		Collection<TemplateStruct> struts = templateService.getAllStruct(TemplateEnum.CODE.getType());
		for (TemplateStruct struct : struts) {
			defineCodeGenerator.generate(version, struct, dto);
		}
		// 压缩代码
		zipCode(version, entity);
		return SystemResult.build(SystemCodeEnum.SUCCESS);
	}

	@ResponseBody
	@RequestMapping("/svn")
	public Object svn(String version, int id, String opt) {
		if (opt.equals("checkOut")) {
			svnService.checkOut();
		}
		if (opt.equals("update")) {
			svnService.update(CommonConstant.GENERATOR_PATH);
		}
		if (opt.equals("commit")) {
			svnService.doCleanup(CommonConstant.GENERATOR_PATH);
			svnService.doCommit(CommonConstant.GENERATOR_PATH);
		}
		return SystemResult.build(SystemCodeEnum.SUCCESS);
	}

	/**
	 * 压缩代码
	 * 
	 * @param version
	 * @param entity
	 */
	private void zipCode(String version, ModuleEntity entity) {
		// 生成路径
		String entityName = entity.getName().toLowerCase();
		SettingVersion versionInfo = setting.getVersionInfo().get(version);
		String srcPath = versionInfo.getCodePath().concat(File.separator).concat(entityName);
		String zipPath = CommonConstant.DOWNLOAD_PACKAGE;
		try {
			ZipUtil.zip(srcPath, zipPath, entityName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新增模块
	 */
	@ResponseBody
	@RequestMapping("/addModule")
	public Object addModule(String version, @RequestBody ModuleEntity entity) {
		if (entity == null || entity.getId() == 0 || entity.getName() == "") {
			return SystemResult.build(SystemCodeEnum.ERROR_PARAM).result();
		}
		if (moduleService.getModuleEntity(version, entity.getId()) != null) {
			return SystemResult.build(SystemCodeEnum.ERROR_ADD_REPEAT).result();
		}
		moduleService.replacModuleEntity(version, entity);
		return SystemResult.build(SystemCodeEnum.SUCCESS);
	}

	/**
	 * 修改模块
	 */
	@ResponseBody
	@RequestMapping("/editModule")
	public Object editModule(String version, @RequestBody ModuleEntity entity) {
		if (entity == null || entity.getId() == 0 || entity.getName() == "") {
			return SystemResult.build(SystemCodeEnum.ERROR_PARAM).result();
		}
		moduleService.replacModuleEntity(version, entity);
		return SystemResult.build(SystemCodeEnum.SUCCESS);
	}

	/**
	 * 删除模块
	 */
	@ResponseBody
	@RequestMapping("/deleteModule")
	public Object deleteModule(String version, @RequestParam(value = "ids[]") int[] ids) {
		if (ids.length > 2) {
			return SystemResult.build(SystemCodeEnum.ERROR_DELETE_LIMIT_TWO);
		}
		for (Integer id : ids) {
			moduleService.removeModuleEntity(version, id);
		}
		return SystemResult.build(SystemCodeEnum.SUCCESS);
	}

	/**
	 * 模块列表
	 * 
	 * @param version
	 */
	@ResponseBody
	@RequestMapping("/moduleList")
	public Object moduleList(String version) {
		Collection<ModuleEntity> moduleEntities = moduleService.getAllModuleEntity(version);
		return SystemResult.build(SystemCodeEnum.SUCCESS, moduleEntities);
	}

}
