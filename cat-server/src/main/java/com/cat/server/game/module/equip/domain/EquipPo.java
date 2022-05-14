package com.cat.server.game.module.equip.domain;

import com.cat.orm.core.base.BasePo;

/**
* EquipPo
* @author Jeremy
*/
public abstract class EquipPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_ID = "id";
	public static final String PROP_CONFIGID = "configId";
	public static final String PROP_HOLDER = "holder";
	public static final String PROP_RECIEVETIME = "recieveTime";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_ID,
			PROP_CONFIGID,
			PROP_HOLDER,
			PROP_RECIEVETIME,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_ID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			};
	
	
	/** 玩家ID*/
	protected long playerId;
	/** 装备唯一id*/
	protected long id;
	/** 装备配置id*/
	protected int configId;
	/** 装备所属，可以是角色，亦可以是宠物*/
	protected int holder;
	/** 获得的时间戳*/
	protected long recieveTime;
	
	public EquipPo(){
	}
	
	/** 玩家ID **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 装备唯一id **/
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	/** 装备配置id **/
	public int getConfigId(){
		return this.configId;
	}
	
	public void setConfigId(int configId){
		this.configId = configId;
	}
	
	/** 装备所属，可以是角色，亦可以是宠物 **/
	public int getHolder(){
		return this.holder;
	}
	
	public void setHolder(int holder){
		this.holder = holder;
	}
	
	/** 获得的时间戳 **/
	public long getRecieveTime(){
		return this.recieveTime;
	}
	
	public void setRecieveTime(long recieveTime){
		this.recieveTime = recieveTime;
	}
	
	
	@Override
	public String toString() {
		return "Equip [playerId= "+ playerId +", id= "+ id +", configId= "+ configId +", holder= "+ holder +", recieveTime= "+ recieveTime
				+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getPlayerId(),
		getId(),
		getConfigId(),
		getHolder(),
		getRecieveTime(),
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
		return String.valueOf(getId());
	}
	
}
