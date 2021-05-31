/**
 * 
 */
package com.cat.generator.core.proto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.generator.core.proto.domain.ProtocolField;
import com.cat.generator.core.proto.domain.ProtocolObject;
import com.cat.generator.core.proto.domain.ProtocolStructure;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

/**
 * 协议文件解释器
 * key:协议名 value: 协议结构
 * @date 2019年4月23日下午7:53:10
 */
public class ProtocolParser {
	
	public static final Logger logger = LoggerFactory.getLogger(ProtocolParser.class.getName());
	
	public static void main(String[] args) {
		parse("E:\\jeremy\\proto\\ProtobufTool3.4\\proto");
	}
	
	/**
	 * key:方法名
	 * value: 协议号
	 */
	public static HashBiMap<String, Integer> protoIdMap = HashBiMap.create();
	public static Map<String, ProtocolObject> protoMap = Maps.newHashMap();
	
	public static void parse(String path) {
		File folder = new File(path);
		for(File file : folder.listFiles()) {
			if(!file.getName().endsWith(".proto")) {
				continue;
			}
			if (file.getName().equals("PBProtocol.proto")) {
				//解析协议号对应的方法名
				getProtocol(file);
				continue;
			}
			Pair<List<String>, List<List<String>>> pair = getProtocolScopeList(file);
			List<String> headList = pair.getLeft();
			List<List<String>> scopeList = pair.getRight();
			ProtocolObject object = parseObject(headList);
			for(List<String> scope : scopeList) {
				try {
					object.getStructures().putAll(parseStructure(scope));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			protoMap.put(object.getModuleName(), object);
		}
		for(ProtocolObject ps:protoMap.values()) {
			System.out.println(ps);
		}
		logger.info("protoId:{}", protoIdMap.keySet());
		logger.info("groupList:{}", protoMap.keySet());
	}
	
	/**
	 * 构建一个proto对象
	 * @return
	 */
	private static ProtocolObject parseObject(List<String> headList) {
		final String quo = ProtocolConstant.QUOTATION;
		final String javaPackage = ProtocolConstant.JAVA_PACKAGE;
		final String javaOuterClassname = ProtocolConstant.JAVA_OUTER_CLASSNAME;
		final String javaImport = ProtocolConstant.IMPORT;
		final String spot = ProtocolConstant.SPOT;
		
		ProtocolObject object = new ProtocolObject();
		for(String line : headList) {
			try {
				if (line.contains(javaPackage)) {
				//解析所在目录
				int startIndex = line.indexOf(quo)+1;
				int lastIndex = line.lastIndexOf(quo);
				object.setJavaPath(line.substring(startIndex, lastIndex));
			}
			else if (line.contains(javaOuterClassname)) {
				//解析类名
				int startIndex = line.indexOf(quo)+1;
				int lastIndex = line.lastIndexOf(quo);
				object.setOutClass(line.substring(startIndex, lastIndex));
			}
			else if (line.contains(javaImport)) {
				//解析依赖项
				int startIndex = line.indexOf(quo)+1;
				int lastIndex = line.lastIndexOf(spot);
				object.setDependenceObj(line.substring(startIndex, lastIndex));
			}
			} catch (Exception e) {
				logger.error("parseObject error");
				e.printStackTrace();
			}
		}
		return object;
	}
	
	
	/**
	 * 解释出一个协议结构
	 * @param structList
	 * @return
	 */
	private static Map<String, ProtocolStructure> parseStructure(List<String> structList) {
		final String message = ProtocolConstant.MESSAGE;
		final String slash = ProtocolConstant.DOUBLE_SLASH;
		final String leftBracket = ProtocolConstant.LEFT_BRACKET;
		final String rightBracket = ProtocolConstant.RIGHT_BRACKET;
		final String space = ProtocolConstant.SPACE;
		final String empty = ProtocolConstant.EMPTH_STR;
	
		Map<String, ProtocolStructure> temp = Maps.newHashMap();
		ProtocolStructure struct = new ProtocolStructure();
		for(String line : structList) {
			try {
				line = line.trim();

				if(line.startsWith(slash)) {
					//解析类注释
					String comment = line.replaceAll(slash, empty);
					comment = comment.replaceAll(slash, empty);
					struct.setComment(comment);
				} else if(line.startsWith(message)) {
					//此行是message结构体, 
					String name = line.replaceAll(message, empty);
					name = name.replaceAll("\\"+leftBracket, empty);
					name = name.replaceAll(space, empty);
					struct.setName(name);
				}
				else {
					//最后解析构内的字段
					if (line.equals(empty))
						continue;
					if(line.contains(leftBracket))
						continue;
					if(line.contains(rightBracket))
						continue;
					
					ProtocolField field = new ProtocolField();
					String[] temps = line.split(slash);
					if(temps.length > 1) {
						field.setComment(temps[1].replaceAll(slash, empty).trim());
					}
					String[] fieldStrs = temps[0].substring(0, temps[0].indexOf("=")).split(space);
					if(fieldStrs.length>2) {
						field.setIdentifier(fieldStrs[0].trim());
						field.setType(fieldStrs[1].trim());
						field.setName(fieldStrs[2].trim());
					}else {
						field.setType(fieldStrs[0].trim());
						field.setName(fieldStrs[1].trim());
					}
					struct.getFields().add(field);
				}
			} catch (Exception e) {
				logger.info("parseStructure error, line="+line);
				e.printStackTrace();
			}
		}
		temp.put(struct.getName(), struct);
		return temp;
	}
	
	/**
	 * 获取协议号对应的方法名
	 * @param protocolFile
	 * @return
	 */
	private static HashBiMap<String, Integer> getProtocol(File protocolFile) {
		//协议结构列表
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(protocolFile)));
			String line = reader.readLine();
			boolean start = false;
			while(line != null) {
				if (line.startsWith("enum")) {
					start = true;
				}
				if (start && line.contains("=")) {
					String arr[] = line.split("=");
					String key = RegExUtils.replaceAll(arr[0], " ", "").replaceAll("	", "");
					String value = RegExUtils.replaceAll(arr[1], " ", "").replaceAll(";", "");
					protoIdMap.putIfAbsent(key, Integer.valueOf(value));
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			logger.error("protocolFile:{}", protocolFile.getName());
			e.printStackTrace();
		}
		return protoIdMap;
	}
	
	/**
	 * 划分每个协议结构的字符串
	 * @param protocolFile
	 * @return
	 */
	private static Pair<List<String>, List<List<String>>> getProtocolScopeList(File protocolFile) {
		//协议头结构
		List<String> headList = new ArrayList<>();
		//协议结构列表
		List<List<String>> scopeList = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(protocolFile)));
			List<String> lineList = new ArrayList<>();
			//标记是否为协议结构
			boolean scoping = false;
			//单个协议结构的内容列表
			List<String> scopeBuilder = null;
			String line = reader.readLine();
			while(line != null) {
				//协议结构开始
				if(line.startsWith("message")) {
					scoping = true;
					scopeBuilder = new ArrayList<>();
					//加入主体结构之前的信息
					headList.addAll(lineList);
					//结构的备注
					String comment = lineList.get(lineList.size() - 1);
					if(comment.startsWith("//")) {
						scopeBuilder.add(comment);
					}
				}
				//协议结构结束
				if(line.contains("}")) {
					scopeBuilder.add(line.substring(0, line.indexOf("}") + 1));
					scopeList.add(scopeBuilder);
					scoping = false;
				}
				if(scoping) {
					scopeBuilder.add(line);
				}
				lineList.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			logger.error("protocolFile:{}", protocolFile.getName());
			e.printStackTrace();
		}
		return Pair.of(headList, scopeList);
	}
}
