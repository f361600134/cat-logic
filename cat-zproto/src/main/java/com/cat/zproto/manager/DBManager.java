package com.cat.zproto.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.zproto.domain.table.DBDomain;
import com.cat.zproto.domain.table.IDBSource;

@Component
public class DBManager {
	
	
	@Autowired private List<IDBSource> dbSources;
	
	/**
	 * key: DbType <br>
	 * value: module domain <br>
	 */
	private final Map<Integer, DBDomain> domainMap = new HashMap<>();
	
	
	/**
	 * 获取Moduledomain
	 * @param version
	 * @return
	 */
	public DBDomain getDomain(Integer dbType) {
		return domainMap.get(dbType);
	}

	/**
	 * 获取或创建domain
	 * @param version
	 * @return
	 */
	public DBDomain getOrCreateDomain(Integer dbType) {
		DBDomain domain = getDomain(dbType);
		if (domain == null) {
			IDBSource db = getDbsource(dbType);
			if (db != null) {
				domain = new DBDomain(db);
				try {
					domain.init();
				} catch (IOException e) {
					e.printStackTrace();
				}
				domainMap.put(dbType, domain);
			}
		}
		return domain;
	}

	/**
	 * 根据数据源类型, 获取数据源代理类
	 * @param dbType
	 * @return
	 */
	private IDBSource getDbsource(Integer dbType) {
		for (IDBSource db : dbSources) {
			if (db.dbType() == dbType) {
				return db;
			}
		}
		return null;
	}
	
}
