package com.cat.server.game.module.${entity.getEntityName()?lower_case}.domain;

import com.cat.orm.core.base.BasePo;

/**
* ${entity.getEntityName()}Po
* @author Jeremy
*/
public abstract class ${entity.getEntityName()}Po extends BasePo {

	<#-- Static Field -->
	<#if entity.getEntityBeans() ? exists>
	<#list entity.getEntityBeans() as bean>
	public static final String PROP_${bean.field ? upper_case} = "${bean.field}";
	</#list>
	</#if>
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			<#if entity.entityBeans ? exists>
			<#list entity.entityBeans as bean>
			<#--"`${bean.field}`",-->
			PROP_${bean.field ? upper_case},
			</#list>
			</#if>
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			<#if entity.getKeyAndIndexs() ? exists>
			<#list entity.getKeyAndIndexs() as index>
			PROP_${index ? upper_case},
			</#list>
			</#if>
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			<#if entity.getIndexsWithoutKey() ? exists>
			<#list entity.getIndexsWithoutKey() as index>
			PROP_${index ? upper_case},
			</#list>
			</#if>
			};
	
	
	<#-- filed -->
	<#if entity.entityBeans ? exists>
	<#list entity.entityBeans as bean>
	/** ${bean.desc}*/
	protected ${bean.type} ${bean.field};
	</#list>
	</#if>
	
	<#-- Constructor -->
	public ${entity.getEntityName()}Po(){
		<#if entity.entityBeans ? exists>
		<#list entity.entityBeans as bean>
		<#if bean.type == 'String'>
		this.${bean.field} = "";
		</#if>
		</#list>
		</#if>
	}
	
	<#-- Getter Setter-->
	<#if entity.entityBeans ? exists>
	<#list entity.entityBeans as bean>
	/** ${bean.desc} **/
	public ${bean.type} get${bean.field ? cap_first}(){
		return this.${bean.field};
	}
	
	public void set${bean.field ? cap_first}(${bean.type} ${bean.field}){
		this.${bean.field} = ${bean.field};
	}
	
	</#list>
	</#if>
	
	@Override
	public String toString() {
		return ${entity.genToStr()}
	}
	
	@Override
	public String[] props() {
		<#--return new String[] {
		<#if entity.entityBeans ? exists>
		<#list entity.entityBeans as bean>
		PROP_${bean.field ? upper_case},
		</#list>
		</#if>
		};-->
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		<#if entity.entityBeans ? exists>
		<#list entity.entityBeans as bean>
		get${bean.field ? cap_first}(),
		</#list>
		</#if>
		};
	}
	
	@Override
	public Object key() {
		<#if entity.keysEmpty() >
		//主键为空,返回null
		return null;
		<#else>
		return get${entity.genPrimary1()?cap_first}();
		</#if>
	}
	
	@Override
	public String keyColumn() {
		<#if entity.keysEmpty() >
		//主键为空,返回null
		return null;
		<#else>
		return PROP_${entity.genPrimary1()?upper_case};
		</#if>
	}

	@Override
	public String[] keyAndIndexColumn() {
		<#--return new String[] {
			<#if entity.getKeyAndIndexs() ? exists>
			<#list entity.getKeyAndIndexs() as index>
			PROP_${index ? upper_case},
			</#list>
			</#if>
		};-->
		return KEY_AND_INDEX_COLUMN;
	}
	
	@Override
	public Object[] keyAndIndexValues() {
		return new Object[] {
			<#if entity.getKeyAndIndexs() ? exists>
			<#list entity.getKeyAndIndexs() as index>
			get${index?cap_first}(),
			</#list>
			</#if>
		};
	}
	
	@Override
	public String[] indexColumn() {
		<#--return new String[] {
			<#if entity.getIndexsWithoutKey() ? exists>
			<#list entity.getIndexsWithoutKey() as index>
			PROP_${index ? upper_case},
			</#list>
			</#if>
		};-->
		return INDEX_ALL;
	}
	
	@Override
	public String[] indexValues() {
		return new String[] {
			<#if entity.getIndexsWithoutKey() ? exists>
			<#list entity.getIndexsWithoutKey() as index>
			PROP_${index ? upper_case},
			</#list>
			</#if>
		};
	}
	
	@Override
	public String cacheId() {
		<#--<#if entity.keysEmpty() >
		<#assign text="" />
		//主键为空,返回索引组合
		<#if entity.getIndexsWithoutKey() ? exists>
		<#list entity.getIndexsWithoutKey() as index>
		<#assign text += "get"+ index?cap_first+"()+\":\"+"/>
		</#list>
		return ${text?substring(0, text?length-5)};
		</#if>
		<#else>
		<#assign count = 0/>
		<#list entity.getPrimaryKeys() as index>
		<#assign count = count+1 />
		<#assign text += "get"+ index?cap_first+"()+\":\"+"/>
		</#list>
		<#if count gt 1>
		return ${text?substring(0, text?length-5)};
		<#else>
		return String.valueOf(${text?substring(0, text?length-5)});
		</#if>
		</#if>-->
	}
	
}
