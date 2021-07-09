package com.cat.zproto.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.table.DBDomain;
import com.cat.zproto.domain.table.po.TableEntity;
import com.cat.zproto.manager.DBManager;

/**
 * 读取数据库信息, 读取指定表的表结构
 * @auth Jeremy
 * @date 2021年6月6日下午6:11:36
 */
@Service
public class DbService implements InitializingBean{
	
	@Autowired private SettingConfig setting;
	@Autowired private DBManager dbManager;
	
	private static final Logger log = LoggerFactory.getLogger(DbService.class);

	public TableEntity getTableEntity(String tableEntityName) {
		int dbType = setting.getDbInfo().getDbType();
		DBDomain domain = dbManager.getOrCreateDomain(dbType);
		if (domain == null) {
			return null;
		}
		return domain.getTableEntity(tableEntityName);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		dbManager.getOrCreateDomain(1);
		dbManager.getOrCreateDomain(2);
	}
	
}
