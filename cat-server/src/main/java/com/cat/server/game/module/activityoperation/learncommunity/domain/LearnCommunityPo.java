package com.cat.server.game.module.activityoperation.learncommunity.domain;

import com.cat.orm.core.base.BasePo;

/**
* LearnCommunityPo
* @author Jeremy
*/
public abstract class LearnCommunityPo extends BasePo {

	public static final String PROP_TODAYEXP = "todayExp";
	public static final String PROP_EXP = "exp";
	public static final String PROP_LEVEL = "level";
	public static final String PROP_EXCLUSIVE = "exclusive";
	public static final String PROP_REWARDDATAMAPSTR = "rewardDataMapStr";
	public static final String PROP_QUESTTYPEDATASTR = "questTypeDataStr";
	public static final String PROP_DAILYACTIVEMAPSTR = "dailyActiveMapStr";
	public static final String PROP_PLAYERID = "playerId";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_TODAYEXP,
			PROP_EXP,
			PROP_LEVEL,
			PROP_EXCLUSIVE,
			PROP_REWARDDATAMAPSTR,
			PROP_QUESTTYPEDATASTR,
			PROP_DAILYACTIVEMAPSTR,
			PROP_PLAYERID,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_PLAYERID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			};
	
	
	/** 今日获得经验*/
	protected int todayExp;
	/** 总经验=每日任务经验+每周任务经验*/
	protected int exp;
	/** 等级*/
	protected int level;
	/** 是否购买了专属奖励*/
	protected boolean exclusive;
	/** 奖励记录, 仅记录已领奖的配置*/
	protected String rewardDataMapStr;
	/** 任务数据,跟随者活动,活动结束,任务关闭*/
	protected String questTypeDataStr;
	/** 活跃记录, 记录已领取的日活跃奖励*/
	protected String dailyActiveMapStr;
	/** 玩家id*/
	protected long playerId;
	/** 当前所处服务器id*/
	protected int activityId;
	
	public LearnCommunityPo(){
		this.rewardDataMapStr = "";
		this.questTypeDataStr = "";
		this.dailyActiveMapStr = "";
	}
	
	/** 今日获得经验 **/
	public int getTodayExp(){
		return this.todayExp;
	}
	
	public void setTodayExp(int todayExp){
		this.todayExp = todayExp;
	}
	
	/** 总经验=每日任务经验+每周任务经验 **/
	public int getExp(){
		return this.exp;
	}
	
	public void setExp(int exp){
		this.exp = exp;
	}
	
	/** 等级 **/
	public int getLevel(){
		return this.level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	/** 是否购买了专属奖励 **/
	public boolean getExclusive(){
		return this.exclusive;
	}
	
	public void setExclusive(boolean exclusive){
		this.exclusive = exclusive;
	}
	
	/** 奖励记录, 仅记录已领奖的配置 **/
	public String getRewardDataMapStr(){
		return this.rewardDataMapStr;
	}
	
	public void setRewardDataMapStr(String rewardDataMapStr){
		this.rewardDataMapStr = rewardDataMapStr;
	}
	
	/** 任务数据,跟随者活动,活动结束,任务关闭 **/
	public String getQuestTypeDataStr(){
		return this.questTypeDataStr;
	}
	
	public void setQuestTypeDataStr(String questTypeDataStr){
		this.questTypeDataStr = questTypeDataStr;
	}
	
	/** 活跃记录, 记录已领取的日活跃奖励 **/
	public String getDailyActiveMapStr(){
		return this.dailyActiveMapStr;
	}
	
	public void setDailyActiveMapStr(String dailyActiveMapStr){
		this.dailyActiveMapStr = dailyActiveMapStr;
	}
	
	/** 玩家id **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	public int getActivityId() {
		return activityId;
	}
	
	
	@Override
	public String toString() {
		return "LearnCommunity [todayExp= "+ todayExp +", exp= "+ exp +", level= "+ level +", exclusive= "+ exclusive +", rewardDataMapStr= "+ rewardDataMapStr
				 +", questTypeDataStr= "+ questTypeDataStr +", dailyActiveMapStr= "+ dailyActiveMapStr +", playerId= "+ playerId+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getTodayExp(),
		getExp(),
		getLevel(),
		getExclusive(),
		getRewardDataMapStr(),
		getQuestTypeDataStr(),
		getDailyActiveMapStr(),
		getPlayerId(),
		};
	}
	
	@Override
	public Object key() {
		return getPlayerId();
	}
	
	@Override
	public String keyColumn() {
		return PROP_PLAYERID;
	}

	@Override
	public String[] keyAndIndexColumn() {
		return KEY_AND_INDEX_COLUMN;
	}
	
	@Override
	public Object[] keyAndIndexValues() {
		return new Object[] {
			getPlayerId(),
		};
	}
	
	@Override
	public String[] indexColumn() {
		return INDEX_ALL;
	}
	
	@Override
	public String[] indexValues() {
		return new String[] {
		};
	}
	
	@Override
	public String cacheId() {
		return String.valueOf(getPlayerId());
	}
	
}
