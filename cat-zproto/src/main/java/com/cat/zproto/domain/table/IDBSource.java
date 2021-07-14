package com.cat.zproto.domain.table;

import java.io.IOException;

import com.cat.zproto.domain.table.po.TableEntity;

/**
 * 数据源接口
 * @author Administrator
 *
 */
public interface IDBSource {
	
	public int mysql = 1;
	public int excel = 2;
	
	public int dbType();
	
	/**
	 * 获取所有tableEntity, 先从本地缓存获取, 没有再查库
	 */
	public TableEntity getTableEntity(String tableEntityName);
	
	/**
	 * 获取所有tableEntity, 先从本地缓存获取, 没有再查库
	 */
	public void init() throws IOException;
	
	/**
	 * 存储数据源
	 */
	default public void save(String tableEntityName) {}
	
	/**
	 * 新增数据源
	 */
	default public void addNew(TableEntity tableEntity) {}
	
}
