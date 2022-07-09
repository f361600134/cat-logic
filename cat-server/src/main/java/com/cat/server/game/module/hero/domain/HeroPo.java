package com.cat.server.game.module.hero.domain;

import com.cat.orm.core.base.BasePo;
import com.cat.server.game.module.user.Stu;

/**
* HeroPo
* @author Jeremy
*/
public abstract class HeroPo extends BasePo {

	public static final String PROP_ID = "id";
	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_CONFIGID = "configId";
	public static final String PROP_LEVEL = "level";
	public static final String PROP_STARIDSTR = "starIdStr";
	public static final String PROP_TALENT = "talent";
	public static final String PROP_USEDMATERIALSTR = "usedMaterialStr";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_ID,
			PROP_PLAYERID,
			PROP_CONFIGID,
			PROP_LEVEL,
			PROP_STARIDSTR,
			PROP_TALENT,
			PROP_USEDMATERIALSTR,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_ID,
			PROP_PLAYERID,
			PROP_CONFIGID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			PROP_PLAYERID,
			PROP_CONFIGID,
			};
	
	
	/** 武将唯一id*/
	protected long id;
	/** 所属玩家id*/
	protected long playerId;
	/** 配置id*/
	protected int configId;
	/** 等级*/
	protected int level;
	/** 星格信息,List*/
	protected String starIdStr;
	/** 已激活天赋*/
	protected int talent;
	/** 记录使用材料*/
	protected String usedMaterialStr;
	
	public HeroPo(){
		this.starIdStr = "";
		this.usedMaterialStr = "";
	}
	
	/** 武将唯一id **/
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	/** 所属玩家id **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 配置id **/
	public int getConfigId(){
		return this.configId;
	}
	
	public void setConfigId(int configId){
		this.configId = configId;
	}
	
	/** 等级 **/
	public int getLevel(){
		return this.level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	/** 星格信息,List **/
	public String getStarIdStr(){
		return this.starIdStr;
	}
	
	public void setStarIdStr(String starIdStr){
		this.starIdStr = starIdStr;
	}
	
	/** 已激活天赋 **/
	public int getTalent(){
		return this.talent;
	}
	
	public void setTalent(int talent){
		this.talent = talent;
	}
	
	/** 记录使用材料 **/
	public String getUsedMaterialStr(){
		return this.usedMaterialStr;
	}
	
	public void setUsedMaterialStr(String usedMaterialStr){
		this.usedMaterialStr = usedMaterialStr;
	}
	
	
	@Override
	public String toString() {
		return "Hero [id= "+ id +", playerId= "+ playerId +", configId= "+ configId +", level= "+ level +", starIdStr= "+ starIdStr
				 +", talent= "+ talent +", usedMaterialStr= "+ usedMaterialStr+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getId(),
		getPlayerId(),
		getConfigId(),
		getLevel(),
		getStarIdStr(),
		getTalent(),
		getUsedMaterialStr(),
		};
	}
	
	@Override
	public Object key() {
		return getId();
	}
	
	@Override
	public String keyColumn() {
		return PROP_ID;
	}

	@Override
	public String[] keyAndIndexColumn() {
		return KEY_AND_INDEX_COLUMN;
	}

	@Override
	public Object[] keyAndIndexValues() {
		return new Object[] {
			getId(),
			getPlayerId(),
			getConfigId(),
		};
	}
	
	@Override
	public String[] indexColumn() {
		return INDEX_ALL;
	}
	
	@Override
	public String[] indexValues() {
		return new String[] {
			PROP_PLAYERID,
			PROP_CONFIGID,
		};
	}
	
	@Override
	public String cacheId() {
		return String.valueOf(getId());
	}
	
}
