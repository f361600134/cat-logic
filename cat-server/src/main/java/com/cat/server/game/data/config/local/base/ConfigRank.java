package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.core.config.container.IGameConfig;

@ConfigPath("ConfigHeroInfo.json")
public class ConfigRank implements IGameConfig{

	private int ID;//排行榜Id
	private String name;//排行榜名称
	private int sort;//标签排序
	private int type;//排行类型
	private int limit;//排行榜数量
	private int showNum;//排行榜显示数量

	public int getId(){
        return ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    
	public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
	public int getSort(){
        return sort;
    }
    public void setSort(int sort){
        this.sort = sort;
    }
    
	public int getType(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }
    
	public int getLimit(){
        return limit;
    }
    public void setLimit(int limit){
        this.limit = limit;
    }
    
	public int getShowNum(){
        return showNum;
    }
    public void setShowNum(int showNum){
        this.showNum = showNum;
    }
    

	////////////////////// 特殊扩展 //////////////
	
//	public void parse(){
//		
//    }
	
	
	/////////UserDefine Begin///////////
	/////////UserDefine End/////////////
	
}