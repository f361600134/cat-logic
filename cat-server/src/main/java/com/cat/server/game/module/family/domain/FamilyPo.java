package com.cat.server.game.module.family.domain;

import com.cat.orm.core.base.BasePo;

/**
* FamilyPo
* @author Jeremy
*/
public abstract class FamilyPo extends BasePo {

	public static final String PROP_ID = "id";
	public static final String PROP_NAME = "name";
	public static final String PROP_APPLYSTR = "applyStr";
	public static final String PROP_MEMBERSTR = "memberStr";
	public static final String PROP_EXP = "exp";
	public static final String PROP_LEVEL = "level";
	public static final String PROP_CREATETIME = "createTime";
	public static final String PROP_NOTICE = "notice";
	public static final String PROP_INITSERVERID = "initServerId";
	public static final String PROP_CURSERVERID = "curServerId";
	public static final String PROP_TAG = "tag";
	
	protected long id;//家族id
	protected String name;//家族名,可修改
	protected String applyStr;//申请列表
	protected String memberStr;//成员列表
	protected int exp;//经验
	protected short level;//等级
	protected long createTime;//创建时间戳
	protected String notice;//公告,可修改
	protected int initServerId;//初始服务器id
	protected int curServerId;//当前服务器id
	protected String tag;//家族号,可以修改.用于精确查询
	
	public FamilyPo(){
		this.name = "";
		this.applyStr = "";
		this.memberStr = "";
		this.notice = "";
		this.tag = "";
	}
	
	/** 家族id **/
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	/** 家族名,可修改 **/
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	/** 申请列表 **/
	public String getApplyStr(){
		return this.applyStr;
	}
	
	public void setApplyStr(String applyStr){
		this.applyStr = applyStr;
	}
	
	/** 成员列表 **/
	public String getMemberStr(){
		return this.memberStr;
	}
	
	public void setMemberStr(String memberStr){
		this.memberStr = memberStr;
	}
	
	/** 经验 **/
	public int getExp(){
		return this.exp;
	}
	
	public void setExp(int exp){
		this.exp = exp;
	}
	
	/** 等级 **/
	public short getLevel(){
		return this.level;
	}
	
	public void setLevel(short level){
		this.level = level;
	}
	
	/** 创建时间戳 **/
	public long getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(long createTime){
		this.createTime = createTime;
	}
	
	/** 公告,可修改 **/
	public String getNotice(){
		return this.notice;
	}
	
	public void setNotice(String notice){
		this.notice = notice;
	}
	
	/** 初始服务器id **/
	public int getInitServerId(){
		return this.initServerId;
	}
	
	public void setInitServerId(int initServerId){
		this.initServerId = initServerId;
	}
	
	/** 当前服务器id **/
	public int getCurServerId(){
		return this.curServerId;
	}
	
	public void setCurServerId(int curServerId){
		this.curServerId = curServerId;
	}
	
	/** 家族号,可以修改.用于精确查询 **/
	public String getTag(){
		return this.tag;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
	
	
	@Override
	public String toString() {
		return "Family [id= "+ id +", name= "+ name +", applyStr= "+ applyStr +", memberStr= "+ memberStr +", exp= "+ exp
				 +", level= "+ level +", createTime= "+ createTime +", notice= "+ notice +", initServerId= "+ initServerId +", curServerId= "+ curServerId
				 +", tag= "+ tag+"]";
	}
	
	@Override
	public String[] props() {
		return new String[] {
		"`id`",
		"`name`",
		"`applyStr`",
		"`memberStr`",
		"`exp`",
		"`level`",
		"`createTime`",
		"`notice`",
		"`initServerId`",
		"`curServerId`",
		"`tag`",
		};
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getId(),
		getName(),
		getApplyStr(),
		getMemberStr(),
		getExp(),
		getLevel(),
		getCreateTime(),
		getNotice(),
		getInitServerId(),
		getCurServerId(),
		getTag(),
		};
	}

	@Override
	public String[] indexColumn() {
		return new String[] {
		};
	}
	
	@Override
	public Object[] indexValues() {
		return new Object[] {
		};
	}
	
	@Override
	public String cacheId() {
		return getId()+"";
	}
	
	
}
