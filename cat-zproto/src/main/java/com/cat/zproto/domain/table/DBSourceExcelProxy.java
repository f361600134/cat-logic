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

import com.alibaba.fastjson.JSONObject;
import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.table.po.TableEntity;

@Repository
public class DBSourceExcelProxy implements IDBSource{
	
	private static final Logger log = LoggerFactory.getLogger(DBSourceExcelProxy.class);
	
	@Autowired private SettingConfig setting;
	
	/**
	 * key: <br>
	 * value: <br>
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
	
	private boolean loadOne(String tableEntityName) throws IOException {
		String dbsPath = setting.getDbInfo().getModuleDbsPath();
		String filePath = dbsPath.concat(File.separator).concat(tableEntityName).concat(CommonConstant.JSON_SUBFIX);
		File file = new File(filePath);
		if (!file.exists()) {
			return false;
		}
		if (!file.isFile()) {
			return false;
		}
		String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		TableEntity entity = JSONObject.parseObject(content, TableEntity.class);
		this.tableEntitieMap.put(entity.getEntityName(), entity);
		return true;
	}

	@Override
	public TableEntity getTableEntity(String tableEntityName) {
		TableEntity tableEntity = tableEntitieMap.get(tableEntityName);
		if (tableEntity == null) {
			try {
				if (this.loadOne(tableEntityName)) {
					tableEntity = tableEntitieMap.get(tableEntityName);
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error("getTableEntity error", e);
			}
		}
		return tableEntity;
	}

	@Override
	public int dbType() {
		return excel;
	}
	

}
