package com.cat.zproto.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.zproto.domain.module.ModuleEntity;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.table.po.TableEntity;

/**
 * 协议
 * @auth Jeremy
 * @date 2021年6月6日下午8:38:12
 */
public class TableFreemarkerDto {
	
	/**
	 * 模块信息
	 */
	private final ModuleEntity module;
	
	/**
	 * 模块的表结构信息
	 */
	private final TableEntity entity;
	
	/**
	 * 模块的proto结构信息
	 */
	private final ProtocolObject protocolObj;
	
	/**
	 *模块的请求协议结构列表
	 */
	private final List<ProtocolStructure> protoReqStructList = new ArrayList<>();
	/**
	 * 模块的返回协议结构列表
	 */
	private final Map<String, ProtocolStructure> protoAckStructMap = new HashMap<>();
	/**
	 * 模块的proto对象列表
	 */
	private final List<ProtocolStructure> protoPBStructList = new ArrayList<>();
	
	//==================以下是享元变量================
	/**
	 * 协议结构, 为了生成协议跟代码保持一致, <br>
	 * 此对象可变, 享元对象
	 * @TODO 这里最好是把生成协议部分和生成代码部分拆分开来.
	 */
	private ProtocolStructure struct;
	/**
	 * 文件名字
	 * 此对象可变, 享元对象
	 */
	private String clazzName;
	
	
	public TableFreemarkerDto(TableEntity entity, ProtocolObject protocolObj, ModuleEntity module) {
		this.entity = entity;
		this.protocolObj = protocolObj;
		this.module = module;
	}
	
	public TableEntity getEntity() {
		return entity;
	}

	public ProtocolObject getProtocolObj() {
		return protocolObj;
	}
	
	public ModuleEntity getModule() {
		return module;
	}

	public List<ProtocolStructure> getProtoReqStructList() {
		return protoReqStructList;
	}

	public Map<String, ProtocolStructure> getProtoAckStructMap() {
		return protoAckStructMap;
	}

	public List<ProtocolStructure> getProtoPBStructList() {
		return protoPBStructList;
	}

	public ProtocolStructure getStruct() {
		return struct;
	}

	public void setStruct(ProtocolStructure struct) {
		this.struct = struct;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}
	
}
