package com.cat.server.game.module.${moduleName ? lower_case}.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import ${protocolObj.getDependenceImport()};
import ${protocolObj.getJavaImport()};

/**
* ${clazzName}
* @author Jeremy
*/
public class ${clazzName} implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(${clazzName}.class);
	
	private final ${struct.name}.Builder builder = ${struct.name}.newBuilder();
	
	public ${clazzName}() {}
	
	public static ${clazzName} newInstance() {
		return new ${clazzName}();
	}
	
	public ${struct.name} build() {
		return builder.build();
	}
	
	<#if struct.getFields() ? exists>
	<#list struct.getFields() as field>
	/** ${field.comment}**/
	<#if field.identifier?contains('repeated')>
	public void add${field.name ? cap_first}(${field.javaType} value){
		this.builder.add${field.name ? cap_first}(value);
	}
	<#else>
	public void set${field.name ? cap_first}(${field.javaType} value){
		this.builder.set${field.name ? cap_first}(value);
	}
	</#if>
	<#--
	<#if field.getPrimitive()>
	public void set${field.name ? cap_first}(${field.javaType} value){
		this.builder.set${field.name ? cap_first}(value);
	}
	<#else>
	public void add${field.name ? cap_first}(${field.javaType} value){
		<#if field.identifier?contains('repeated')>
		this.builder.add${field.name ? cap_first}(value);
		<#else>
		this.builder.set${field.name ? cap_first}(value);
		</#if>
	}
	</#if>-->
	</#list>
	</#if>
	
	@Override
	public int protocol() {
		return PBProtocol.${struct.name}_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
