package com.cat.zproto.controller;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cat.zproto.core.result.SystemCodeEnum;
import com.cat.zproto.core.result.SystemResult;
import com.cat.zproto.domain.module.ModuleEntity;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;
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
    public ModelAndView protoListView(String version, int id){
		ModelAndView mv = new ModelAndView();
		ModuleEntity entity = moduleService.getModuleEntity(id);
		if (entity == null) {
			//TODO 重定向页面
		}
		ProtocolObject protoObject = protoService.getProtoObject(entity.getName());
		if (protoObject == null) {
			//TODO 重定向页面
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
	 * 协议提交, 替换掉协议信息, 生成proto文件, 不生成proto协议
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
		String fileName = protoPath.concat(File.separator).concat(protoObject.getOutClass());
		templateService.printer(protoObject, fileName, "proto.ftl");
        return SystemResult.build(SystemCodeEnum.SUCCESS);
    }
	
	/**
	 * 生成协议文件<br>
	 * CALL "bin/protoc.exe" --java_out=%java_out% --proto_path=%proto_path% %%i	<br>
	 * CALL "bin/protoc.exe" --csharp_out=%csharp_out% --proto_path=%proto_path% %%i	<br>
	 * {protoc} -I={protoPath} --java_out={javaPath} {moduleName}.proto		<br>
	 * %s -I=%s --java_out=%s %s.proto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createMessage")
	public Object createMessage() {
		
		return null;
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
        System.out.println(moduleEntities);
        return SystemResult.build(SystemCodeEnum.SUCCESS, moduleEntities);
    }
	
}
