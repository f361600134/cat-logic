package com.cat.server.core.server;

import com.cat.orm.core.base.IBasePo;
import com.cat.orm.core.db.process.IDataProcess;
import com.cat.server.core.context.SpringContextHolder;

/**
 * 	常用持久化接口
 */
public interface IPersistence extends IBasePo{
	
	/**
	 * 保存
	 * @param po
	 */
	default public void save() {
		IDataProcess processor =  SpringContextHolder.getBean(IDataProcess.class);
		this.beforeSave();
		processor.insert(this);
	}
	
	/**
	 * 修改
	 * @param po
	 */
	default public void update() {
		IDataProcess processor =  SpringContextHolder.getBean(IDataProcess.class);
		this.beforeSave();
		processor.update(this);
	}
	
	/**
	 * 替换或修改
	 * @param po
	 */
	default public void replace() {
		IDataProcess processor =  SpringContextHolder.getBean(IDataProcess.class);
		this.beforeSave();
		processor.replace(this);
	}
	
	/**
	 * 删除
	 */
	default public void delete() {
		IDataProcess processor =  SpringContextHolder.getBean(IDataProcess.class);
		processor.delete(this);
	}

}
