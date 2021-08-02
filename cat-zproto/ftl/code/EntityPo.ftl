package com.cat.server.game.module.${entity.getEntityName()?lower_case}.domain;

import com.cat.orm.core.base.BasePo;

/**
* ${entity.getEntityName()}Po
* @author Jeremy
*/
public abstract class ${entity.getEntityName()}Po extends BasePo {

	<#-- Static Field -->
	<#if entity.properties ? exists>
	<#list entity.properties as prop>
	public static final String PROP_${prop.field ? upper_case} = "${prop.field}";
	</#list>
	</#if>
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			<#if entity.properties ? exists>
			<#list entity.properties as prop>
			<#--"`${prop.field}`",-->
			PROP_${prop.field ? upper_case},
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
	<#if entity.properties ? exists>
	<#list entity.properties as prop>
	/** ${prop.desc}*/
	protected ${prop.type} ${prop.field};
	</#list>
	</#if>
	
	<#-- Constructor -->
	public ${entity.getEntityName()}Po(){
		<#if entity.properties ? exists>
		<#list entity.properties as prop>
		<#if prop.type == 'String'>
		this.${prop.field} = "";
		</#if>
		</#list>
		</#if>
	}
	
	<#-- Getter Setter-->
	<#if entity.properties ? exists>
	<#list entity.properties as prop>
	/** ${prop.desc} **/
	public ${prop.type} get${prop.field ? cap_first}(){
		return this.${prop.field};
	}
	
	public void set${prop.field ? cap_first}(${prop.type} ${prop.field}){
		this.${prop.field} = ${prop.field};
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
		<#if entity.properties ? exists>
		<#list entity.properties as prop>
		PROP_${prop.field ? upper_case},
		</#list>
		</#if>
		};-->
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		<#if entity.properties ? exists>
		<#list entity.properties as prop>
		get${prop.field ? cap_first}(),
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
		<#if entity.keysEmpty() >
    <#assign count = 0/>
		<#assign text="" />
		//主键为空,返回索引组合
		<#if entity.getIndexsWithoutKey() ? exists>
		<#list entity.getIndexsWithoutKey() as index>
		<#assign text += "get"+ index?cap_first+"()+\":\"+"/>
		</#list>
    <#if count lt 1>
    return null;
		<#elseif count gt 1>
		return ${text?substring(0, text?length-5)};
		<#else>
		return String.valueOf(${text?substring(0, text?length-5)});
		</#if>
		</#if>
		<#else>
		<#list entity.getPrimaryKeys() as index>
		<#assign count = count+1 />
		<#assign text += "get"+ index?cap_first+"()+\":\"+"/>
		</#list>    
		<#if count gt 1>
		return ${text?substring(0, text?length-5)};
		<#else>
		return String.valueOf(${text?substring(0, text?length-5)});
		</#if>
		</#if>
	}
	
}
