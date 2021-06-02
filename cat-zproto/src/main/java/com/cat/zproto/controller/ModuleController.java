package com.cat.zproto.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cat.zproto.core.result.SystemCodeEnum;
import com.cat.zproto.core.result.SystemResult;
import com.cat.zproto.domain.ModuleEntity;
import com.cat.zproto.service.ModuleService;

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
	public Object deleteModule(String version, int id) {
		moduleService.removeModuleEntity(id);
        return SystemResult.build(SystemCodeEnum.SUCCESS);
    }
	
	/**
	 * 模块列表
	 * @param version
	 */
	@ResponseBody
	@RequestMapping("/moduleList")
	public String getModules(String version) {
        Collection<ModuleEntity> moduleEntities = moduleService.getAllModuleEntity();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", moduleEntities);
        return JSON.toJSONString(result);
    }
	
}
