package com.cat.zproto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cat.zproto.domain.system.SettingConfig;

/**
 * 版本设置， 设置信息保存值setting.json文件
 * 1. 版本初始化
 * 2. 切分版本-创建分支
 * 3. 版本修改
 * @auth Jeremy
 * @date 2021年6月12日下午9:43:14
 */
@Controller
@RequestMapping("/version")
public class VersionController {
	
	@Autowired private SettingConfig setting;
	
	/**
	 * 版本选择
	 * @return  
	 * @return ModelAndView  
	 * @date 2021年6月12日下午9:50:40
	 */
	@RequestMapping("/versionSelect")
    public ModelAndView versionSelect(){
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("version_select");
    	//固定第一个是主干版本
    	mv.addObject("trunk", "trunk");
    	mv.addObject("versions", setting.getVersionInfo().keySet());
        return mv;
    } 

	/**
	 * 版本页显示
	 * @return  
	 * @return ModelAndView  
	 * @date 2021年6月12日下午9:50:40
	 */
	@RequestMapping("/versionView")
    public ModelAndView settingView(){
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("view_version");
        return mv;
    } 
	
	/**
	 * 版本添加页面
	 * @return  
	 * @return ModelAndView  
	 * @date 2021年6月12日下午9:50:55
	 */
	@RequestMapping("/versionAddView")
    public ModelAndView versionAddView(){
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("add_version");
        return mv;
    } 
	
	/**
	 * 版本设置保存
	 * @return  
	 * @return ModelAndView  
	 * @date 2021年6月12日下午9:50:55
	 */
	@RequestMapping("/versionSave")
    public ModelAndView settingSave(){
    	ModelAndView mv = new ModelAndView();
        return mv;
    } 
	
	/**
	 * 设置编辑显示
	 * @return  
	 * @return ModelAndView  
	 * @date 2021年6月12日下午9:51:13
	 */
	@RequestMapping("/settingEditView")
    public ModelAndView settingEditView(){
    	ModelAndView mv = new ModelAndView();
        return mv;
    } 
	
	/**
	 * 设置编辑保存
	 * @return  
	 * @return ModelAndView  
	 * @date 2021年6月12日下午9:51:30
	 */
	@RequestMapping("/settingEditSave")
    public ModelAndView settingEditSave(){
    	ModelAndView mv = new ModelAndView();
        return mv;
    } 
	
}
