package com.cat.zproto.dto;

import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;

public class ProtoDto {
	
	/**
	 * 文件名字
	 * 此对象可变, 享元对象
	 */
	private String moduleName;
	
	/**
	 * 类名字
	 * 此对象可变, 享元对象
	 */
	private String clazzName;
	
	/**
	 * proto对象
	 */
	private ProtocolObject protocolObj;
	
	/**
	 * 协议结构, 为了生成协议跟代码保持一致, <br>
	 * 此对象可变, 享元对象
	 * @TODO 这里最好是把生成协议部分和生成代码部分拆分开来.
	 */
	private ProtocolStructure struct;
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public ProtocolStructure getStruct() {
		return struct;
	}

	public void setStruct(ProtocolStructure struct) {
		this.struct = struct;
	}

	public ProtocolObject getProtocolObj() {
		return protocolObj;
	}

	public void setProtocolObj(ProtocolObject protocolObj) {
		this.protocolObj = protocolObj;
	}
	
}
