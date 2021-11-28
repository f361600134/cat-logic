package com.cat.server.core.server;

import com.cat.orm.core.base.IBasePo;
import com.cat.orm.core.db.process.IDataProcess;
import com.cat.server.core.context.SpringContextHolder;

/**
 * 	常用持久化接口
 */
public interface IPersistence extends IBasePo{
	
	/**
	 * 保存至数据库
	 * @param po
	 */
	default public void save() {
		IDataProcess processor =  SpringContextHolder.getBean(IDataProcess.class);
		this.beforeSave();
		processor.insert(this);
	}
	
	/**
	 * 修改至数据库
	 * @param po
	 */
	default public void update() {
		IDataProcess processor =  SpringContextHolder.getBean(IDataProcess.class);
		this.beforeSave();
		processor.update(this);
	}
	
	/**
	 * 替换或修改至数据库
	 * @param po
	 */
	default public void replace() {
		IDataProcess processor =  SpringContextHolder.getBean(IDataProcess.class);
		this.beforeSave();
		processor.replace(this);
	}
	
	/**
	 *  从数据库内删除
	 */
	default public void delete() {
		IDataProcess processor =  SpringContextHolder.getBean(IDataProcess.class);
		processor.delete(this);
	}

}
