package com.cat.zproto.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.pool.DruidDataSource;
import com.cat.zproto.assist.generator.IGenerator;
import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.core.result.SystemCodeEnum;
import com.cat.zproto.core.result.SystemResult;
import com.cat.zproto.domain.module.ModuleEntity;
import com.cat.zproto.domain.proto.ProtocolConstant;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.system.SettingMysql;
import com.cat.zproto.domain.table.TableEntity;
import com.cat.zproto.domain.table.TableFreemarkerDto;
import com.cat.zproto.service.CommandService;
import com.cat.zproto.service.DbService;
import com.cat.zproto.service.ModuleService;
import com.cat.zproto.service.ProtoService;
import com.cat.zproto.service.TemplateService;
import com.google.common.collect.BiMap;

/**
 * 用于处理模块相关操作
 * 1. 模块详细
 * 2. 新增模块
 * 3. 修改模块
 * 4. 删除模块
 * 5. 模块列表
 * 
 * 6. 协议列表
 * 7. 编辑协议
 * 8. 删除协议
 * 9. 新增协议
 * 
 * 10.生成协议
 * 11.生成代码
 * 
 * @author Jeremy
 */
@Controller
@RequestMapping("/module")
public class ModuleController {
	
	private static Logger logger = LoggerFactory.getLogger(ModuleController.class);

	@Autowired private ModuleService moduleService;
	
	@Autowired private ProtoService protoService;
	
	@Autowired private SettingConfig setting;
	
	@Autowired private TemplateService templateService;
	
	@Autowired private CommandService commandService;
	
	@Autowired private DbService dbService;
	
	@Autowired private List<IGenerator> generatorList;
	
	/**
	 * 主页面
	 */
	@RequestMapping("/index")
    public ModelAndView index(){
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("index");
    	mv.addObject("version", "1.0.0");
        return mv;
    }
	
	/**
	 * 添加模块界面
	 */
	@RequestMapping("/addModuleView")
    public ModelAndView addModuleView(){
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("add_module");
        mv.addObject("version", "1.0.0");
        return mv;
    }
	
	/**
	 * 修改界面
	 */
	@RequestMapping("/editModuleView")
    public ModelAndView editModuleView(String version, int id){
		ModuleEntity moduleEntitie = moduleService.getModuleEntity(id);
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("edit_module");
        mv.addObject("version", "1.0.0");
        mv.addObject("data", moduleEntitie);
        return mv;
    }
	
	/**
	 * 协议修改界面
	 * 1.req协议
	 * 2.ack协议
	 * 3.dto协议对象
	 * @param version 版本
	 * @param id 模块id
	 */
	@RequestMapping("/protoEditView")
    public ModelAndView protoEditView(String version, int id){
		ModelAndView mv = new ModelAndView();
		ModuleEntity entity = moduleService.getModuleEntity(id);
		if (entity == null) {
			//TODO 重定向页面
		}
		ProtocolObject protoObject = protoService.getProtoObject(entity.getName());
		if (protoObject == null) {
			//TODO 重定向页面
			return null;
		}
		Collection<ProtocolStructure> structures = protoObject.getStructures().values();
		
    	mv.setViewName("edit_protocol");
    	mv.addObject("version", "1.0.0");
    	mv.addObject("id", id);
    	mv.addObject("data", structures);
    	return mv;
    }
	
	/**
	 * 预览协议界面
	 * 1.req协议
	 * 2.ack协议
	 * 3.dto协议对象
	 * @param version 版本
	 * @param id 模块id
	 */
	@RequestMapping("/protoListView")
    public Object protoListView(String version, int id){
		ModelAndView mv = new ModelAndView();
		ModuleEntity entity = moduleService.getModuleEntity(id);
		if (entity == null) {
			//TODO 重定向页面
			return SystemResult.build(SystemCodeEnum.ERROR_PARAM);
		}
		ProtocolObject protoObject = protoService.getProtoObject(entity.getName());
		if (protoObject == null) {
			//TODO 重定向页面
			mv.setViewName("no_proto");
			return mv;
		}
		BiMap<String, Integer> protoNameIdMap = protoService.getProtoIds();
		Collection<ProtocolStructure> structures = protoObject.getStructures().values();
		List<ProtocolStructure> requests = structures.stream().filter(e ->
        e.getName().toLowerCase().startsWith("req")).collect(Collectors.toList());
		
		List<ProtocolStructure> responses = structures.stream().filter(e ->
        e.getName().toLowerCase().startsWith("ack")).collect(Collectors.toList());
		
		List<ProtocolStructure> pbs = structures.stream().filter(e ->
        e.getName().toLowerCase().startsWith("PB")).collect(Collectors.toList());
		
    	mv.setViewName("view_protpcol");
    	mv.addObject("version", "1.0.0");
    	mv.addObject("requests", requests);
    	mv.addObject("responses", responses);
    	mv.addObject("pbs", pbs);
    	mv.addObject("protoNameIdMap", protoNameIdMap);
		return mv;
    }
	
	/**
	 * 协议提交, 替换掉协议信息, 保存至结构, 不生成proto文件, 不生成proto协议
	 * @param version 版本
	 * @param id 模块id
	 * @param 所有协议信息
	 */
	@ResponseBody
	@RequestMapping("/protoCommit")
    public Object protoCommit(String version, int id, @RequestBody List<ProtocolStructure> protoStructure){
		if (protoStructure == null) {
			return SystemResult.build(SystemCodeEnum.ERROR_PARAM);
		}
		if (protoStructure.isEmpty()) {
			return SystemResult.build(SystemCodeEnum.ERROR_NO_CHANGE);
		}
		ModuleEntity entity = moduleService.getModuleEntity(id);
		if (entity == null) {
			return SystemResult.build(SystemCodeEnum.UNKNOW);
		}
		String entityName = entity.getName();
		ProtocolObject protoObject = protoService.protoObjectUpdate(entityName, protoStructure);
		if(protoObject == null) {
			return SystemResult.build(SystemCodeEnum.ERROR_CANNOT_DOUND_MODULE);
		}
		//	生成proto文件
		String protoPath = setting.getProto().getProtoPath();
		String fileName = protoPath.concat(File.separator).concat(protoObject.getOutClass()).concat(ProtocolConstant.PROTO_SUBFIX);
		templateService.printer(protoObject, fileName, "proto.ftl");
		 /*
		  * 生成protoId文件,这里处理的有点不优雅,没想到如何配置.
		  */
		Map<String, Object> map = new HashMap<String, Object>();
//		Map<String, Integer> protoIdMap = protoService.getSortProtoIdMap(moduleService.getAllModuleEntity());
		Map<String, Integer> protoIdMap = protoService.getProtoIds();
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
	 * @return void  
	 * @date 2021年6月5日下午10:49:16
	 */
	@RequestMapping("/protoReverseLoad")
	public Object protoReverseLoad(String version) {
		ModelAndView mv = new ModelAndView();
		boolean result = protoService.reverseLoad();
		result = result & moduleService.reverseLoad(protoService.getAllProtoObject(), protoService.getProtoIds());
		if (result) {
			mv.setViewName("index");
			mv.addObject("version", "1.0.0");
		}else {
			mv.setViewName("error");
		}
		return mv;
	}
	
	/**
	 * 生成协议文件<br>
	 * CALL "bin/protoc.exe" --java_out=%java_out% --proto_path=%proto_path% %%i	<br>
	 * CALL "bin/protoc.exe" --csharp_out=%csharp_out% --proto_path=%proto_path% %%i	<br>
	 * {protoc} -I={protoPath} --java_out={javaPath} {moduleName}.proto		<br>
	 * %s -I=%s --java_out=%s %s.proto
	 * @param version 版本
	 * @param id 模块id
	 * @param langType 语言类型,all表示所有
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createMessage")
	public Object createMessage(String version, int id, String langType) {
		ModuleEntity entity = moduleService.getModuleEntity(id);
		if (entity == null) {
			//TODO 重定向页面
			return SystemResult.build(SystemCodeEnum.ERROR_PARAM);
		}
		ProtocolObject protoObject = protoService.getProtoObject(entity.getName());
		if (protoObject == null) {
			//TODO 重定向页面
			return SystemResult.build(SystemCodeEnum.ERROR_PARAM);
		}
		//返回信息,包含成功和失败的
		String ret = ""; 
		SystemResult result = null;
		if (langType.equals("all")) {
			Map<String, String> pathMap = setting.getProto().getGeneratorPath();
			for (String languegeType : pathMap.keySet()) {
				result = createMessage(protoObject, languegeType);
				ret = ret.concat(String.format(CommonConstant.GENERATE_RESULT, languegeType, result.tips())).concat("<br>");
			}
		}else {
			result = createMessage(protoObject, langType);
			ret = String.format(CommonConstant.GENERATE_RESULT, langType, result.tips());
		}
		result.setTips(ret);
		return result;
	}
	
	private SystemResult createMessage(ProtocolObject protoObject, String langType) {
		String protoFormat = CommonConstant.PROTOC_EXECUTE_FORMAT;
		String protoExePath = setting.getProto().getProtoExePath();
		String protoPath = setting.getProto().getProtoPath();
		String languageType = langType;
		String genPath = setting.getProto().getGeneratorPath().get(langType);
		String outClassName = protoObject.getOutClass();
		try {
			//TODO 这段代码丢初始化调用里面
			FileUtils.forceMkdir(new File(genPath));
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("forceMkdir error, e", e);
		}
		String command = String.format(protoFormat, protoExePath, protoPath, languageType, genPath, outClassName);
		SystemResult result = commandService.execCommand(command);
		logger.info("command:{}, result:{}", command, result);
		return result;
	}
	
	/**
	 * 生成代码<br>
	 *   根据定义的module信息, 找到数据库中的table, 最终根据table结构生成代码.<br>
	 *   数据库表命名会奇奇怪怪的, 比如加前后缀. 此处要兼容各种奇怪的命名<br>
	 * @note 硬性要求--moduleName必须要跟表命名一致  <br>
	 * 表命通常小写, 模块名这里定义必须要写, 所以判断查询时需要忽略大小写.
	 * @return void  
	 * @date 2021年6月6日下午3:38:25
	 */
	@ResponseBody
	@RequestMapping("/createCode")
	public Object createCode(String version, int id) {
		ModuleEntity entity = moduleService.getModuleEntity(id);
		if (entity == null) {
			return SystemResult.build(SystemCodeEnum.ERROR_CANNOT_FOUND_PROTOOBJECT);
		}
		ProtocolObject protoObject = protoService.getProtoObject(entity.getName());
		if (protoObject == null) {
			return SystemResult.build(SystemCodeEnum.ERROR_CANNOT_DOUND_MODULE);
		}
		List<TableEntity> entitys = dbService.readDb();
		TableEntity tableEntity = null;
		for (TableEntity te : entitys) {
			if (StringUtils.equals(te.getEntityName(), entity.getName())) {
				tableEntity = te;
				break;
			}
		}
		if (tableEntity == null) {
			return SystemResult.build(SystemCodeEnum.ERROR_NOT_FOUND_TABLE);
		}
		TableFreemarkerDto dto = new TableFreemarkerDto(tableEntity, protoObject);
		for (IGenerator generator : generatorList) {
			generator.generate(dto);
		}
		return SystemResult.build(SystemCodeEnum.SUCCESS);
	}
	
	/**
	 * 新增模块
	 */
	@ResponseBody
	@RequestMapping("/addModule")
	public Object addModule(String version, @RequestBody ModuleEntity entity) {
		if (entity == null || entity.getId() == 0 || entity.getName() == "") {
			return  SystemResult.build(SystemCodeEnum.ERROR_PARAM).result();
		}
		if (moduleService.getModuleEntity(entity.getId()) != null) {
			return SystemResult.build(SystemCodeEnum.ERROR_ADD_REPEAT).result();
		}
		moduleService.replacModuleEntity(entity);
        return SystemResult.build(SystemCodeEnum.SUCCESS);
    }
	
	/**
	 * 修改模块
	 */
	@ResponseBody
	@RequestMapping("/editModule")
	public Object editModule(String version, @RequestBody ModuleEntity entity) {
		if (entity == null || entity.getId() == 0 || entity.getName() == "") {
			return  SystemResult.build(SystemCodeEnum.ERROR_PARAM).result();
		}
		moduleService.replacModuleEntity(entity);
		return SystemResult.build(SystemCodeEnum.SUCCESS);
    }
	
	/**
	 * 删除模块
	 */
	@ResponseBody
	@RequestMapping("/deleteModule")
	public Object deleteModule(String version, @RequestParam(value = "ids[]") int[] ids) {
		if (ids.length > 2) {
			return SystemResult.build(SystemCodeEnum.ERROR_DELETE_LIMIT);
		}
		for (Integer id : ids) {
			moduleService.removeModuleEntity(id);
		}
        return SystemResult.build(SystemCodeEnum.SUCCESS);
    }
	
	/**
	 * 模块列表 
	 * @param version
	 */
	@ResponseBody
	@RequestMapping("/moduleList")
	public Object moduleList(String version) {
        Collection<ModuleEntity> moduleEntities = moduleService.getAllModuleEntity();
        return SystemResult.build(SystemCodeEnum.SUCCESS, moduleEntities);
    }
	
}
