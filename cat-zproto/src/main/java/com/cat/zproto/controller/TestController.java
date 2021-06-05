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
import com.cat.zproto.domain.module.ModuleEntity;
import com.cat.zproto.service.ModuleService;

@Controller
@RequestMapping("/test")
public class TestController {

//	@RequestMapping("/index")
//	public String index() {
////		 ModelAndView mv = new ModelAndView();
////		 mv.setViewName("index.html");
//		return "index";
//	}
	
	@Autowired private ModuleService moduleService;
	
	
	
	@RequestMapping("/index")
    public ModelAndView sayHello(){
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("index");
    	mv.addObject("version", "1.0.0");
        return mv;
    }
	
	@ResponseBody
	@RequestMapping("/getModules")
	public String getModules(String version) {
        Collection<ModuleEntity> moduleEntities = moduleService.getAllModuleEntity();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "");
//        Collections.sort(moduleEntities, Comparator.comparingInt(ModuleEntity::getId));
        result.put("data", moduleEntities);
        return JSON.toJSONString(result);
//        return JsonUtil.toString(result);
    }
	
	@RequestMapping("/select")
    public String select(){
        return "select";
    }
	
	@RequestMapping("/head")
    public String head(){
        return "head";
    }
	
	@RequestMapping("/aaa")
	public String aaa() {
		return "aaa";
	}

}
