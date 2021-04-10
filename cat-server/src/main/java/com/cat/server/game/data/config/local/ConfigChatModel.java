package com.cat.server.game.data.config.local;

import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.core.config.container.IGameConfig;

@ConfigPath("ConfigChatModel.json")
public class ConfigChatModel implements IGameConfig{

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
	
	public void parse(){
		
		
		
		this.parseExt();
    }
	
	
	/////////UserDefine Begin///////////
	private void parseExt(){
	}
	
	/////////UserDefine End/////////////
	
}
