package com.cat.server.game.module.user.domain;

import com.cat.orm.core.base.BasePo;

/**
* UserPo
* @author Jeremy
*/
public abstract class UserPo extends BasePo {

	public static final String PROP_ID = "id";
	public static final String PROP_NAME = "name";
	public static final String PROP_AGE = "age";
	public static final String PROP_BIRTHDAY = "birthday";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_ID,
			PROP_NAME,
			PROP_AGE,
			PROP_BIRTHDAY,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_ID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			};
	
	
	/** */
	protected int id;
	/** */
	protected String name;
	/** */
	protected int age;
	/** */
	protected String birthday;
	
	public UserPo(){
		this.name = "";
		this.birthday = "";
	}
	
	/**  **/
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	/**  **/
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	/**  **/
	public int getAge(){
		return this.age;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	/**  **/
	public String getBirthday(){
		return this.birthday;
	}
	
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	
	
	@Override
	public String toString() {
		return "User [id= "+ id +", name= "+ name +", age= "+ age +", birthday= "+ birthday+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getId(),
		getName(),
		getAge(),
		getBirthday(),
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
