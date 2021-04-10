package com.cat.server.core.server;

import com.cat.orm.core.base.IBasePo;
import com.cat.orm.core.db.process.DataProcessorAsyn;
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
		DataProcessorAsyn processorAsyn =  SpringContextHolder.getBean(DataProcessorAsyn.class);
		this.beforeSave();
		processorAsyn.insert(this);
	}
	
	/**
	 * 修改
	 * @param po
	 */
	default public void update() {
		DataProcessorAsyn processorAsyn =  SpringContextHolder.getBean(DataProcessorAsyn.class);
		this.beforeSave();
		processorAsyn.update(this);
	}
	
	/**
	 * 替换或修改
	 * @param po
	 */
	default public void replace() {
		DataProcessorAsyn processorAsyn =  SpringContextHolder.getBean(DataProcessorAsyn.class);
		this.beforeSave();
		processorAsyn.replace(this);
	}
	
	/**
	 * 删除
	 */
	default public void delete() {
		DataProcessorAsyn processorAsyn =  SpringContextHolder.getBean(DataProcessorAsyn.class);
		processorAsyn.delete(this);
	}

}
