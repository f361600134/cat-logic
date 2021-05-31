package com.cat.server.game.module.${entityName ? lower_case}.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import ${protocolObj.getDependenceImport()};
import ${protocolObj.getJavaImport()};

/**
* ${protoClassName}
* @author Jeremy
*/
public class ${protoClassName} implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(${protoClassName}.class);
	
	private final ${struct.name}.Builder builder = ${struct.name}.newBuilder();
	
	public ${protoClassName}() {
	}
	
	public static ${protoClassName} newInstance() {
		return new ${protoClassName}();
	}
	
	public ${struct.name} build() {
		return builder.build();
	}
	
	<#if protoFields ? exists>
	<#list protoFields as field>
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
	</#list>
	</#if>
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
