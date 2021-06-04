package com.cat.zproto.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cat.zproto.core.result.SystemCodeEnum;
import com.cat.zproto.core.result.SystemResult;
import com.cat.zproto.domain.module.ModuleEntity;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.service.ModuleService;
import com.cat.zproto.service.ProtoService;
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

	@Autowired private ModuleService moduleService;
	
	@Autowired private ProtoService protoService;
	
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
//		if (protoObject == null) {
//			//TODO 重定向页面
//		}
		BiMap<String, Integer> protoNameIdMap = protoService.getProtoIds();
		Collection<ProtocolStructure> structures = protoObject.getStructures().values();
//		List<ProtocolStructure> requests = structures.stream().filter(e ->
//        e.getName().toLowerCase().startsWith("req")).collect(Collectors.toList());
//		
//		List<ProtocolStructure> responses = structures.stream().filter(e ->
//        e.getName().toLowerCase().startsWith("ack")).collect(Collectors.toList());
//		
//		List<ProtocolStructure> pbs = structures.stream().filter(e ->
//        e.getName().toLowerCase().startsWith("PB")).collect(Collectors.toList());
//		
    	mv.setViewName("edit_protocol");
    	mv.addObject("version", "1.0.0");
    	mv.addObject("id", id);
    	mv.addObject("data", structures);
//    	mv.addObject("responses", responses);
//    	mv.addObject("pbs", pbs);
//    	mv.addObject("protoNameIdMap", protoNameIdMap);
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
	@RequestMapping("/protoInfoList")
    public Object protoInfoList(String version, int id){
		ModuleEntity entity = moduleService.getModuleEntity(id);
		if (entity == null) {
			//TODO 重定向页面
			SystemResult.build(SystemCodeEnum.UNKNOW);
		}
//		ProtocolObject protoObject = protoService.getProtoObject(entity.getName());
//		Collection<ProtocolStructure> structures = protoObject.getStructures().values();
//		System.out.println(SystemResult.build(SystemCodeEnum.SUCCESS, structures).result());
//		Map<String, Object> ret = new HashMap<>();
//		ret.put("code", 0);
//		ret.put("count", 1);
//		ret.put("msg", "msg");
//		
//		List<Info> list = new ArrayList<>();
//		list.add(Info.create());
//		ret.put("data", list);
//		
//		String json = JSON.toJSONString(ret);
//		System.out.println(json);
		
//        return json;
		return null;
    }
	
//	public static class Info{
//		private String name;
//		private String comment;
//		public String getName() {
//			return name;
//		}
//		public void setName(String name) {
//			this.name = name;
//		}
//		public String getComment() {
//			return comment;
//		}
//		public void setComment(String comment) {
//			this.comment = comment;
//		}
//		public static Info create() {
//			Info info = new Info();
//			info.setName("name1");
//			info.setComment("coment1");
//			return info;
//		}
//	}
	
	
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
	 * 预览协议界面
	 * 1.req协议
	 * 2.ack协议
	 * 3.dto协议对象
	 * @param version 版本
	 * @param id 模块id
	 */
	@RequestMapping("/protoList")
    public Object protoList(String version, int id, int type){
		ModuleEntity entity = moduleService.getModuleEntity(id);
		if (entity == null) {
			//TODO 重定向页面
		}
		String typeStr = "req";
		if (type == 1) {
			typeStr = "req";
		}else if (type == 2) {
			typeStr = "ack";
		}else if (type == 3) {
			typeStr = "PB";
		}
		final String typeString = typeStr;
		ProtocolObject protoObject = protoService.getProtoObject(entity.getName());
		Collection<ProtocolStructure> structures = protoObject.getStructures().values();
		List<ProtocolStructure> data = structures.stream().filter(e ->
        e.getName().toLowerCase().startsWith(typeString)).collect(Collectors.toList());
        return SystemResult.build(SystemCodeEnum.SUCCESS, data);
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
	 * 修改模块
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
	 * /data/server/build/trunk
	 * /data/server/common/trunk/message
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
