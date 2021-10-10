package com.cat.server.game.data.config.local.base;

import java.util.List;
import java.util.Map;

import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.core.config.container.IGameConfig;

import com.google.common.collect.Maps;

//@ConfigPath("ConfigArtifactTasks.json")
public class ConfigArtifactTasksBase implements IGameConfig{

	private int ID;//ID
	private int usefor;//使用于
	private String taskDescription;//任务描述
	private List<List<Integer>> reward;//奖励
	private int completeType;//完成条件类型
	private int completeCondition;//完成条件
	private int completeValue;//完成值

	public int getId(){
        return ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    
	public int getUsefor(){
        return usefor;
    }
    public void setUsefor(int usefor){
        this.usefor = usefor;
    }
    
	public String getTaskDescription(){
        return taskDescription;
    }
    public void setTaskDescription(String taskDescription){
        this.taskDescription = taskDescription;
    }
    
	public List<List<Integer>> getReward(){
        return reward;
    }
    public void setReward(List<List<Integer>> reward){
        this.reward = reward;
    }
    
	public int getCompleteType(){
        return completeType;
    }
    public void setCompleteType(int completeType){
        this.completeType = completeType;
    }
    
	public int getCompleteCondition(){
        return completeCondition;
    }
    public void setCompleteCondition(int completeCondition){
        this.completeCondition = completeCondition;
    }
    
	public int getCompleteValue(){
        return completeValue;
    }
    public void setCompleteValue(int completeValue){
        this.completeValue = completeValue;
    }
    

	////////////////////// 特殊扩展 //////////////

    public void parse(){
		Map<Integer, Integer> rewardTemps = Maps.newHashMap();
    	for(List<Integer> item : this.reward)
    	{
    		int c = rewardTemps.getOrDefault(item.get(0), 0);
    		c += item.get(1);
    		rewardTemps.put(item.get(0), c);
    	}
    	rewardMap = rewardTemps;
		this.parseExt();
    }
	
	//id_count ID数量
    private Map<Integer, Integer> rewardMap = Maps.newHashMap();
    public Map<Integer, Integer> getRewardMap(){
    	return rewardMap;
    }
    
	
	/////////UserDefine Begin///////////
	private void parseExt(){
	}
	
	/////////UserDefine End/////////////
	
}
