package com.cat.generator.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.generator.core.common.Config;
import com.cat.generator.core.common.Config.Info;
import com.cat.generator.core.db.TableEntity;
import com.cat.generator.core.proto.ProtocolParser;
import com.cat.generator.core.proto.domain.ProtocolObject;
import com.cat.generator.core.proto.domain.ProtocolStructure;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import freemarker.template.Template;

public class Printer {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Printer.class);
	
	
	public static void genDefault() {
		Config.defaultProto.forEach((entityName)->{
			TableEntity entity = new TableEntity();
			entity.setEntityName(entityName);
			try {
				genPBbuilder(entity);
				genAckbuilder(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public static void genPojo(List<TableEntity> entitys) {
		for (TableEntity entity : entitys) {
			try {
				if (Config.genTbMap.keySet().contains(entity.getEntityName())) {
					genEntity(entity);
					genManager(entity);
					genController(entity);
					genIService(entity);
					genService(entity);
					genDomain(entity);
					genPBbuilder(entity);
					genReqbuilder(entity);
					genAckbuilder(entity);
					genEntityPo(entity);
					genMissionType(entity);
					genMissionHandler(entity);
					genResourceDomain(entity);
					genObserver(entity);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
//	/**
//	 * 根据ExcelEntity生成PoJo
//	 * @param entity
//	 * @throws Exception  
//	 * @return void  
//	 * @date 2019年9月16日上午11:33:16
//	 */
//	public static void genPojo(ExcelEntity entity) throws Exception
//	{
//		Template template = FreeMarkerTemplateUtils.getTemplate("DbEntity.ftl");
//		//生成路径
//		File file = new File(Config.outputPath);
//		file.mkdirs();
//		
//		String filename = Config.outputPath + File.separator + entity.getEntityName() + Config.suffix;
//		file = new File(filename);
//
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		dataMap.put("entityName", entity.getEntityName());
//		dataMap.put("entityBeans", entity.getEntityBeans());
//		dataMap.put("primaryKey", entity.getPrimaryKey());
//		dataMap.put("toStr", entity.getToStr());
//		dataMap.put("entityIndexs", entity.getEntityIndexs());
//		dataMap.put("indexNames", entity.getIndexNames());
//		dataMap.put("indexs", entity.getIndexs());
//		dataMap.put("options", entity.getOptions());
//		
//        FileOutputStream fos = new FileOutputStream(file);
//        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
//        template.process(dataMap, out);
//        log.info("成功生成文件{}", filename);
//	}
	
	/**
	 * 根据ExcelEntity生成Entity
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genEntity(TableEntity entity) throws Exception
	{
		Template template = FreeMarkerTemplateUtils.getTemplate("Entity.ftl");
		//生成路径
		String subDir = "/domain";
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase()+subDir;
		File file = new File(path);
		file.mkdirs();
		
		String filename = path +  File.separator + entity.getEntityName() + Config.suffix;
		file = new File(filename);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entity", entity);
//		dataMap.put("entityName", entity.getEntityName());
//		dataMap.put("entityBeans", entity.getEntityBeans());
		
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
        log.info("成功生成文件{}", filename);
	}
	
//	/**
//	 * 根据ExcelEntity生成EntityBase
//	 * @param entity
//	 * @throws Exception  
//	 * @return void  
//	 * @date 2019年9月16日上午11:33:16
//	 */
//	public static void genEntityBase(ExcelEntity entity) throws Exception
//	{
//		Template template = FreeMarkerTemplateUtils.getTemplate("EntityBase.ftl");
//		//生成路径
//		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase();
//		File file = new File(path);
//		file.mkdirs();
//		
//		String filename = path + File.separator + entity.getEntityName() +"Base" + Config.suffix;
//		file = new File(filename);
//
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		dataMap.put("entityName", entity.getEntityName());
//		dataMap.put("entityBeans", entity.getEntityBeans());
//		dataMap.put("toStr", entity.getToStr());
//		
//        FileOutputStream fos = new FileOutputStream(file);
//        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
//        template.process(dataMap, out);
//        log.info("成功生成文件{}", filename);
//	}
	
	/**
	 * 根据ExcelEntity生成EntityBase
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genEntityPo(TableEntity entity) throws Exception
	{
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityPo.ftl");
		//生成路径
		String subDir = "/domain";
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase()+subDir;
		File file = new File(path);
		file.mkdirs();
		
		String filename = path + File.separator + entity.getEntityName() +"Po" + Config.suffix;
		file = new File(filename);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		dataMap.put("entity", entity);
		dataMap.put("entityBeans", entity.getEntityBeans());
		dataMap.put("primarys", entity.getPrimaryKeys());
		dataMap.put("keyAndIndexs", entity.getKeyAndIndexs());
		dataMap.put("indexsWithoutKey", entity.getIndexsWithoutKey());
		
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
        log.info("成功生成文件{}", filename);
	}
	
//	/**
//	 * 根据ExcelEntity生成Entity
//	 * @param entity
//	 * @throws Exception  
//	 * @return void  
//	 * @date 2019年9月16日上午11:33:16
//	 */
//	public static void genDao(TableEntity entity) throws Exception
//	{
//		Template template = FreeMarkerTemplateUtils.getTemplate("EntityDao.ftl");
//		//生成路径
//		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase();
//		File file = new File(path);
//		file.mkdirs();
//		
//		String filename = path +  File.separator + entity.getEntityName()+"DAO" + Config.suffix;
//		file = new File(filename);
//
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		dataMap.put("entityName", entity.getEntityName());
//		dataMap.put("entityBeans", entity.getEntityBeans());
//		dataMap.put("tableName", entity.getTablName());
//		dataMap.put("primary", entity.getPrimary());
//		dataMap.put("fileds", entity.getFileds());
//		dataMap.put("javaFileds", entity.getJavaFiled());
//		
//		dataMap.put("updateSql", entity.getUpdateSql());
//		dataMap.put("insertSql", entity.getInsertSql());
//		dataMap.put("iso2o", Config.genTbMap.get(entity.getEntityName()).isO2o());
//		dataMap.put("deleteAllSql", entity.getDeleteAllSql());
//		dataMap.put("selectOneSql", entity.getSelectOneSql());
//		dataMap.put("selectMoreSql", entity.getSelectMoreSql());
//		
//		
//        FileOutputStream fos = new FileOutputStream(file);
//        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
//        template.process(dataMap, out);
//        log.info("成功生成文件{}", filename);
//	}

	/**
	 * 根据ExcelEntity生成Entity
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genManager(TableEntity entity) throws Exception
	{
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityManager.ftl");
		//生成路径
		String subDir = "/manager";
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase()+subDir;
		File file = new File(path);
		file.mkdirs();
		
		String filename = path + File.separator + entity.getEntityName() +"Manager" + Config.suffix;
		file = new File(filename);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entityName", entity.getEntityName());
		
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
        log.info("成功生成文件{}", filename);
	}

	/**
	 * 根据ExcelEntity生成Entity
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genController(TableEntity entity) throws Exception
	{
		//EntityContorller.ftl
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityContorller.ftl");
		//生成路径
		String subDir = "/controller";
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase()+subDir;
		File file = new File(path);
		file.mkdirs();
		
		String filename = path +  File.separator + entity.getEntityName()+"Controller" + Config.suffix;
		file = new File(filename);
		
		//协议层方法获取
		List<ProtocolStructure> protoReqStructList = Lists.newArrayList();
		Map<String, ProtocolStructure> protoAckStructMap = Maps.newHashMap();
		ProtocolObject protocolObj = ProtocolParser.protoMap.get(entity.getEntityName());
		if (protocolObj == null) {
			Info info = Config.genTbMap.get(entity.getEntityName());
			if (info == null) {
				return;
			}
			protocolObj = ProtocolParser.protoMap.get(info.getRelation());
			if (protocolObj == null) {
				return;
			}
		}
		Map<String, ProtocolStructure> structureMap = protocolObj.getStructures();
		for (String key : structureMap.keySet()) {
			if (key.startsWith("Ack")) {
				String newKey = key.replace("Ack", "Req");
				protoAckStructMap.put(newKey, structureMap.get(key));
			}else if (key.startsWith("Req")) {
				protoReqStructList.add(structureMap.get(key));
			}
		}
		//模板信息创建
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entityName", entity.getEntityName());
		dataMap.put("entityBeans", entity.getEntityBeans());
		dataMap.put("protoReqStructList", protoReqStructList);
		dataMap.put("protoAckStructMap", protoAckStructMap);
		dataMap.put("protocolObj", protocolObj);
		
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
        log.info("成功生成文件{}", filename);
	}
	
	/**
	 * 根据ExcelEntity生成Entity
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genService(TableEntity entity) throws Exception
	{
		//选择模板
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityService.ftl");
		//生成路径
		String subDir = "/service";
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase()+subDir;
		File file = new File(path);
		file.mkdirs();
		
		String filename = path +  File.separator + entity.getEntityName()+"Service" + Config.suffix;
		file = new File(filename);

		//协议层方法获取
		List<ProtocolStructure> protoReqStructList = Lists.newArrayList();
		Map<String, ProtocolStructure> protoAckStructMap = Maps.newHashMap();
		ProtocolObject protocolObj = ProtocolParser.protoMap.get(entity.getEntityName());
		if (protocolObj == null) {
			Info info = Config.genTbMap.get(entity.getEntityName());
			if (info == null) {
				return;
			}
			protocolObj = ProtocolParser.protoMap.get(info.getRelation());
			if (protocolObj == null) {
				return;
			}
		}
		Map<String, ProtocolStructure> structureMap = protocolObj.getStructures();
		for (String key : structureMap.keySet()) {
			if (key.startsWith("Ack")) {
				protoAckStructMap.put(key, structureMap.get(key));
			}else if (key.startsWith("Req")) {
				protoReqStructList.add(structureMap.get(key));
			}
		}
		
		//模板信息创建
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entityName", entity.getEntityName());
		dataMap.put("entityBeans", entity.getEntityBeans());
		dataMap.put("protoReqStructList", protoReqStructList);
		dataMap.put("protoAckStructMap", protoAckStructMap);
		dataMap.put("config", Config.genTbMap.get(entity.getEntityName()));
		dataMap.put("protocolObj", protocolObj);
		
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
        log.info("成功生成文件{}", filename);
	}
	
	/**
	 * 根据ExcelEntity生成Entity
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genIService(TableEntity entity) throws Exception
	{
		//选择模板
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityServiceInterface.ftl");
		//生成路径
		String subDir = "/service";
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase()+subDir;
		File file = new File(path);
		file.mkdirs();
		
		String filename = path +  File.separator +"I"+entity.getEntityName()+"Service" + Config.suffix;
		file = new File(filename);

		//模板信息创建
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entityName", entity.getEntityName());
		
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
        log.info("成功生成文件{}", filename);
	}
	
	
	/**
	 * 根据ExcelEntity生成Entity
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genDomain(TableEntity entity) throws Exception
	{
		//选择模板
		Template template = null;
		Info info = Config.genTbMap.get(entity.getEntityName());
		if (info.isO2o()) {
			template = FreeMarkerTemplateUtils.getTemplate("EntityDomain1.ftl");
		}else {
			template = FreeMarkerTemplateUtils.getTemplate("EntityDomain2.ftl");
		}
		
		//生成路径
		String subDir = "/domain";
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase()+subDir;
		File file = new File(path);
		file.mkdirs();
		
		String filename = path +  File.separator + entity.getEntityName()+"Domain" + Config.suffix;
		file = new File(filename);

		//模板信息创建
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entityName", entity.getEntityName());
		dataMap.put("entityBeans", entity.getEntityBeans());
		dataMap.put("config", Config.genTbMap.get(entity.getEntityName()));
		
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
        log.info("成功生成文件{}", filename);
	}
	
	/**
	 * 根据协议生成builder 
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genPBbuilder(TableEntity entity) throws Exception
	{
		//模板
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityPBBuilder.ftl");
		
		List<ProtocolStructure> protoStructList = Lists.newArrayList();
		ProtocolObject protocolObj = ProtocolParser.protoMap.get(entity.getEntityName());
		if (protocolObj == null) {
			Info info = Config.genTbMap.get(entity.getEntityName());
			if (info == null) {
				return;
			}
			protocolObj = ProtocolParser.protoMap.get(info.getRelation());
			if (protocolObj == null) {
				return;
			}
		}
		Map<String, ProtocolStructure> structureMap = protocolObj.getStructures();
		for (String key : structureMap.keySet()) {
			if (key.startsWith("PB")) {
				protoStructList.add(structureMap.get(key));
			}
		}
		
		//生成路径
		String subDir = "/proto";
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase()+subDir;
		File file = new File(path);
		file.mkdirs();
		/*
		 * 生成收集到的PB所有文件, 开始循环生成 
		 */
		for (ProtocolStructure struct : protoStructList) {
			String protoClassName = struct.getName() + "Builder";
			
			String filename = path +  File.separator + protoClassName + Config.suffix;
			file = new File(filename);

			//模板信息创建
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("protoClassName", protoClassName);
			dataMap.put("entityName", entity.getEntityName());
			dataMap.put("struct", struct);
			dataMap.put("protoFields", struct.getFields());
			dataMap.put("protocolObj", protocolObj);
			
	        FileOutputStream fos = new FileOutputStream(file);
	        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
	        template.process(dataMap, out);
	        log.info("成功生成文件{}", filename);
		}
	}
	
	
	/**
	 * 根据协议生成Ack 
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genAckbuilder(TableEntity entity) throws Exception
	{
		//模板
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityAckBuilder.ftl");

		List<ProtocolStructure> protoStructList = Lists.newArrayList();
		ProtocolObject protocolObj = ProtocolParser.protoMap.get(entity.getEntityName());
		if (protocolObj == null) {
			Info info = Config.genTbMap.get(entity.getEntityName());
			if (info == null) {
				return;
			}
			protocolObj = ProtocolParser.protoMap.get(info.getRelation());
			if (protocolObj == null) {
				return;
			}
		}
		Map<String, ProtocolStructure> structureMap = protocolObj.getStructures();
		for (String key : structureMap.keySet()) {
			if (key.startsWith("Ack")) {
				protoStructList.add(structureMap.get(key));
			}
		}
		
		//生成路径
		String subDir = "/proto";
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase()+subDir;
		File file = new File(path);
		file.mkdirs();
		/*
		 * 生成收集到的PB所有文件, 开始循环生成 
		 */
		for (ProtocolStructure struct : protoStructList) {
			
			String protoClassName = struct.getName() + "Resp";
			
			String filename = path +  File.separator + protoClassName + Config.suffix;
			file = new File(filename);
			
			//模板信息创建
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("protoClassName", protoClassName);
			dataMap.put("entityName", entity.getEntityName());
			dataMap.put("struct", struct);
			dataMap.put("protoFields", struct.getFields());
			dataMap.put("protocolObj", protocolObj);
			
	        FileOutputStream fos = new FileOutputStream(file);
	        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
	        template.process(dataMap, out);
	        log.info("成功生成文件{}", filename);
		}
	}
	
	/**
	 * 根据协议生成Req
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genReqbuilder(TableEntity entity) throws Exception
	{
		//模板
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityReqBuilder.ftl");

		List<ProtocolStructure> protoStructList = Lists.newArrayList();
		ProtocolObject protocolObj = ProtocolParser.protoMap.get(entity.getEntityName());
		if (protocolObj == null) {
			Info info = Config.genTbMap.get(entity.getEntityName());
			if (info == null) {
				return;
			}
			protocolObj = ProtocolParser.protoMap.get(info.getRelation());
			if (protocolObj == null) {
				return;
			}
		}
		Map<String, ProtocolStructure> structureMap = protocolObj.getStructures();
		for (String key : structureMap.keySet()) {
			if (key.startsWith("Req")) {
				protoStructList.add(structureMap.get(key));
			}
		}
		
		//生成路径
		String subDir = "/proto";
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase()+subDir;
		File file = new File(path);
		file.mkdirs();
		/*
		 * 生成收集到的PB所有文件, 开始循环生成 
		 */
		for (ProtocolStructure struct : protoStructList) {
			String protoClassName = struct.getName() + "Resp";
			String filename = path +  File.separator + protoClassName + Config.suffix;
			file = new File(filename);
			
			//模板信息创建
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("protoClassName", protoClassName);
			dataMap.put("entityName", entity.getEntityName());
			dataMap.put("struct", struct);
			dataMap.put("protoFields", struct.getFields());
			dataMap.put("protocolObj", protocolObj);
			
	        FileOutputStream fos = new FileOutputStream(file);
	        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
	        template.process(dataMap, out);
	        log.info("成功生成文件{}", filename);
		}
	}
	
	/**
	 * 生成任务类型
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genMissionType(TableEntity entity) throws Exception
	{
		Info info = Config.genTbMap.get(entity.getEntityName());
		if (!info.isMission()) {
			return;
		}
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityMissionType.ftl");
		//生成路径
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase();
		File file = new File(path);
		file.mkdirs();
		
		String filename = path + File.separator + entity.getEntityName() +"MissionType" + Config.suffix;
		file = new File(filename);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entityName", entity.getEntityName());
		
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
        log.info("成功生成文件{}", filename);
	}
	
	/**
	 * 生成任务处理器
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genMissionHandler(TableEntity entity) throws Exception
	{
		Info info = Config.genTbMap.get(entity.getEntityName());
		if (!info.isMission()) {
			return;
		}
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityMissionHandler.ftl");
		//生成路径
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase();
		File file = new File(path);
		file.mkdirs();
		
		String filename = path + File.separator + entity.getEntityName() +"MissionHandler" + Config.suffix;
		file = new File(filename);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entityName", entity.getEntityName());
		
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
        log.info("成功生成文件{}", filename);
	}
	
	/**
	 * 生成资源类型
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genResourceDomain(TableEntity entity) throws Exception
	{
		Info info = Config.genTbMap.get(entity.getEntityName());
		if (!info.isMission()) {
			return;
		}
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityResourceDomain.ftl");
		//生成路径
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase();
		File file = new File(path);
		file.mkdirs();
		
		String filename = path + File.separator + entity.getEntityName() +"ResourceDomain" + Config.suffix;
		file = new File(filename);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entityName", entity.getEntityName());
		
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
        log.info("成功生成文件{}", filename);
	}
	
	/**
	 * 根据ExcelEntity生成EntityBase
	 * @param entity
	 * @throws Exception  
	 * @return void  
	 * @date 2019年9月16日上午11:33:16
	 */
	public static void genObserver(TableEntity entity) throws Exception
	{
		Template template = FreeMarkerTemplateUtils.getTemplate("EntityObserver.ftl");
		//生成路径
		String subDir = "/event";
		String path = Config.outputPath+"/"+entity.getEntityName().toLowerCase()+subDir;
		File file = new File(path);
		file.mkdirs();
		
		String filename = path + File.separator + entity.getEntityName() +"Observer" + Config.suffix;
		file = new File(filename);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entityName", entity.getEntityName());
		
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
        log.info("成功生成文件{}", filename);
	}
	
}
