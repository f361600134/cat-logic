package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.core.config.container.IGameConfig;

@ConfigPath("ConfigChatModel.json")
public class ConfigChatModelBase implements IGameConfig{

	private int ID;//ID
	private String modelDesc;//模板内容

	public int getId(){
        return ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    
	public String getModelDesc(){
        return modelDesc;
    }
    public void setModelDesc(String modelDesc){
        this.modelDesc = modelDesc;
    }
    

	////////////////////// 特殊扩展 //////////////
	
	/////////UserDefine Begin///////////
	
	/////////UserDefine End/////////////
	
}
