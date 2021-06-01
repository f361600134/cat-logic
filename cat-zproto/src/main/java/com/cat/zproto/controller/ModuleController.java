package com.cat.zproto.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
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
	 * 添加界面
	 */
	@RequestMapping("/addView")
    public ModelAndView addView(){
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("add_module");
        mv.addObject("version", "1.0.0");
        return mv;
    }
	
	/**
	 * 新增模块
	 */
	@RequestMapping("/addModule")
	public String addModule(String version) {
        return JSON.toJSONString(null);
    }
	
	
	/**
	 * 模块列表
	 * @param version
	 */
	@ResponseBody
	@RequestMapping("/moduleList")
	public String getModules(String version) {
        Collection<ModuleEntity> moduleEntities = moduleService.getModuleentitymap().values();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", moduleEntities);
        return JSON.toJSONString(result);
    }
	
	
}
