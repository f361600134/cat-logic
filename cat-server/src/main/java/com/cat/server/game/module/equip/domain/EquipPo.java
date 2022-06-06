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
	public static final String PROP_STAR = "star";
	public static final String PROP_STARHIDDENATTRSTR = "starHiddenAttrStr";
	public static final String PROP_HOLE = "hole";
	public static final String PROP_HOLEHIDDENATTRSTR = "holeHiddenAttrStr";
	public static final String PROP_CARDSTR = "cardStr";
	public static final String PROP_CARDATTRSTR = "cardAttrStr";
	public static final String PROP_ENCHANTMENTATTRSTR = "enchantmentAttrStr";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_ID,
			PROP_CONFIGID,
			PROP_HOLDER,
			PROP_RECIEVETIME,
			PROP_STAR,
			PROP_STARHIDDENATTRSTR,
			PROP_HOLE,
			PROP_HOLEHIDDENATTRSTR,
			PROP_CARDSTR,
			PROP_CARDATTRSTR,
			PROP_ENCHANTMENTATTRSTR,
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
	protected long holder;
	/** 获得的时间戳*/
	protected long recieveTime;
	/** 星级,加工等级*/
	protected int star;
	/** 加工隐藏属性*/
	protected String starHiddenAttrStr;
	/** 孔数*/
	protected int hole;
	/** 打孔隐藏属性*/
	protected String holeHiddenAttrStr;
	/** 镶嵌卡牌*/
	protected String cardStr;
	/** 卡牌属性列表*/
	protected String cardAttrStr;
	/** 附魂属性*/
	protected String enchantmentAttrStr;
	
	public EquipPo(){
		this.starHiddenAttrStr = "";
		this.holeHiddenAttrStr = "";
		this.cardStr = "";
		this.cardAttrStr = "";
		this.enchantmentAttrStr = "";
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
	public long getHolder(){
		return this.holder;
	}
	
	public void setHolder(long holder){
		this.holder = holder;
	}
	
	/** 获得的时间戳 **/
	public long getRecieveTime(){
		return this.recieveTime;
	}
	
	public void setRecieveTime(long recieveTime){
		this.recieveTime = recieveTime;
	}
	
	/** 星级,加工等级 **/
	public int getStar(){
		return this.star;
	}
	
	public void setStar(int star){
		this.star = star;
	}
	
	/** 加工隐藏属性 **/
	public String getStarHiddenAttrStr(){
		return this.starHiddenAttrStr;
	}
	
	public void setStarHiddenAttrStr(String starHiddenAttrStr){
		this.starHiddenAttrStr = starHiddenAttrStr;
	}
	
	/** 孔数 **/
	public int getHole(){
		return this.hole;
	}
	
	public void setHole(int hole){
		this.hole = hole;
	}
	
	/** 打孔隐藏属性 **/
	public String getHoleHiddenAttrStr(){
		return this.holeHiddenAttrStr;
	}
	
	public void setHoleHiddenAttrStr(String holeHiddenAttrStr){
		this.holeHiddenAttrStr = holeHiddenAttrStr;
	}
	
	/** 镶嵌卡牌 **/
	public String getCardStr(){
		return this.cardStr;
	}
	
	public void setCardStr(String cardStr){
		this.cardStr = cardStr;
	}
	
	/** 卡牌属性列表 **/
	public String getCardAttrStr(){
		return this.cardAttrStr;
	}
	
	public void setCardAttrStr(String cardAttrStr){
		this.cardAttrStr = cardAttrStr;
	}
	
	/** 附魂属性 **/
	public String getEnchantmentAttrStr(){
		return this.enchantmentAttrStr;
	}
	
	public void setEnchantmentAttrStr(String enchantmentAttrStr){
		this.enchantmentAttrStr = enchantmentAttrStr;
	}
	
	
	@Override
	public String toString() {
		return "Equip [playerId= "+ playerId +", id= "+ id +", configId= "+ configId +", holder= "+ holder +", recieveTime= "+ recieveTime
				 +", star= "+ star +", starHiddenAttrStr= "+ starHiddenAttrStr +", hole= "+ hole +", holeHiddenAttrStr= "+ holeHiddenAttrStr +", cardStr= "+ cardStr
				 +", cardAttrStr= "+ cardAttrStr +", enchantmentAttrStr= "+ enchantmentAttrStr+"]";
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
		getStar(),
		getStarHiddenAttrStr(),
		getHole(),
		getHoleHiddenAttrStr(),
		getCardStr(),
		getCardAttrStr(),
		getEnchantmentAttrStr(),
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
