package com.cat.zproto.domain.table;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cat.zproto.common.SpringContextHolder;
import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.system.SettingVersion;
import com.cat.zproto.domain.table.po.TableEntity;

@Repository
public class DBSourceExcelProxy implements IDBSource{
	
	private static final Logger log = LoggerFactory.getLogger(DBSourceExcelProxy.class);
	
	@Autowired private SettingConfig setting;
	
	/**
	 * key: entityName<br>
	 * value: 数据库数据源实体<br>
	 */
	private final Map<String, TableEntity> tableEntitieMap = new HashMap<>();

	@Override
	public void init() throws IOException {
		//从本地加载指定配置进入缓存
		String dbsPath = setting.getDbInfo().getModuleDbsPath();
		//加载结构目录, 初始化模板结构信息
		File file = new File(dbsPath);
		if (!file.exists()) {
			FileUtils.forceMkdir(file);
		}
		File[] files = file.listFiles();
		for (File f : files) {
			String content = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
			TableEntity entity = JSONObject.parseObject(content, TableEntity.class);
			this.tableEntitieMap.put(entity.getEntityName(), entity);
		}
	}
	
	private TableEntity loadOne(String tableEntityName) throws IOException {
		String dbsPath = setting.getDbInfo().getModuleDbsPath();
		String filePath = dbsPath.concat(File.separator).concat(tableEntityName).concat(CommonConstant.JSON_SUBFIX);
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		if (!file.isFile()) {
			return null;
		}
		String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		TableEntity entity = JSONObject.parseObject(content, TableEntity.class);
		return entity;
	}

	/**
	 * 如果是excel表内获取数据源, 如果根据名字获取不到, 那么生成一个新的
	 */
	@Override
	public TableEntity getTableEntity(String tableEntityName) {
		TableEntity tableEntity = tableEntitieMap.get(tableEntityName);
		if (tableEntity == null) {
			try {
				tableEntity = this.loadOne(tableEntityName);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("getTableEntity error", e);
			}
			//如果最后还是获取不到,则表示没有这个模块的数据源, 实例化新的
			if (tableEntity == null) {
				tableEntity = new TableEntity(tableEntityName);
			}
			this.tableEntitieMap.put(tableEntity.getEntityName(), tableEntity);
		}
		return tableEntity;
	}

	@Override
	public int dbType() {
		return excel;
	}

	@Override
	public void save(String tableEntityName) {
		String dbsPath = setting.getDbInfo().getModuleDbsPath();
		TableEntity tableEntity = tableEntitieMap.get(tableEntityName);
		//先存储协议
		String data = JSON.toJSONString(tableEntity, SerializerFeature.PrettyFormat);
		String filePath = dbsPath.concat(File.separator).concat(tableEntityName).concat(CommonConstant.JSON_SUBFIX);
		try {
			FileUtils.writeStringToFile(new File(filePath), data, StandardCharsets.UTF_8, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
