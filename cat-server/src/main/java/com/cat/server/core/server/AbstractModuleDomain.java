package com.cat.server.core.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.cat.orm.core.base.IBasePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这里的ModuleDomain, 封装Pojo对象, 那么所有玩家的Pojo对象, 就只有一条数据.
 * 这样好处便是, 对于系统内的所有模块数据, 统一获取.
 *  但是带来的问题便是, 对于超复杂对象, 一条数据,过于庞大
 * @author Jeremy
 * @param <T>
 */
public abstract class AbstractModuleDomain<I, T extends IBasePo> implements IModuleDomain<I, T>{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	protected I id;
	protected T bean;
	
	private Class<T> basePoClazz;
	
	@SuppressWarnings("unchecked")
	public AbstractModuleDomain() {
		try {
			Type superClass = getClass().getGenericSuperclass();
			this.basePoClazz = (Class<T>)(((ParameterizedType) superClass).getActualTypeArguments()[0]);
		} catch (Exception e) {
			logger.error("AbstractModuleDomain error", e);
		}
	}
	
	@Override
	public Class<T> getBasePoClazz() {
		return basePoClazz;
	}
	
	/**
	 * 子类实现完成Domain数据的初始化内容
	 * @param v
	 */
	public void initData(I id) {
		try {
			this.id = id;
			//this.bean = basePoClazz.newInstance();
			//	通过带参数构造方法, 创建playerDomain
			Constructor<T> constructor = this.basePoClazz.getDeclaredConstructor(Long.class);
			if (constructor == null) {
				//FIXME? 这样强制BasePO提供带有long类型的参数好不好? 如果long类型其他含义的参数这里会有问题
				//先不改,如果出了问题,可以在aftercreate内,通过传入id,在内部去set持有者的id
				throw new NullPointerException("未提供带有long类型的构造函数");
			}
			this.bean = constructor.newInstance(id);
			this.afterCreate();
			//this.afterInit();
		} catch (Exception e) {
			logger.error("AbstractModuleDomain initData error", e);
		} 
	}

	/**
	 * 初始化数据, 对于单条数据, 直接以此条数据作为bean对象
	 * 如果数据为空, 则初始化新的一条数据
	 */
	@Override
	public void initData(I id,List<T> v) {
		this.id = id;
		if (v.size() == 1) {
			this.bean = v.get(0);
			this.bean.afterLoad();
			//this.afterInit();
		}
		else {
			logger.info("AbstractModuleDomain initData has an error, the v.size != 1");
		}
	}
	@Override
	public I getId() {
		return id;
	}
	/**
	 * 创建后的操作, 由子类实现, 初始化模块初始数据等操作
	 */
	public void afterCreate() {}
	
}
