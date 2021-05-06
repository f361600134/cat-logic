package com.cat.server.core.server;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.orm.core.base.IBasePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jeremy
 * @param <I> 持有者id类型
 * @param <K> bean的id类型
 * @param <T> bean对象
 */
public abstract class AbstractModuleMultiDomain<I, K, T extends IBasePo> implements IModuleDomain<I, T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	public I id;
	protected Map<K, T> beanMap;
	
	private Class<T> basePoClazz;

	@SuppressWarnings("unchecked")
	public AbstractModuleMultiDomain() {
		this.beanMap = new HashMap<>();
		try {
			Type superClass = getClass().getGenericSuperclass();
			this.basePoClazz = (Class<T>)(((ParameterizedType) superClass).getActualTypeArguments()[2]);
		} catch (Exception e) {
			logger.error("AbstractModuleDomain error", e);
		}
	}
	
	/**
	 * 获取域内的实体map
	 * 
	 * @return
	 */
	public void putBean(K key, T value) {
		beanMap.put(key, value);
	}
	
	/**
	 * 获取域内的实体map
	 * 
	 * @return
	 */
	public Map<K, T> getBeanMap() {
		return beanMap;
	}

	/**
	 * 获取域内的实体map
	 * 
	 * @return
	 */
	public T getBean(K key) {
		return beanMap.get(key);
	}

	/**
	 * 获取域内的实体map
	 * 
	 * @return
	 */
	public Collection<T> getBeans() {
		return beanMap.values();
	}
	
	/**
	 * 初始化数据
	 * 
	 * @param itemList
	 */
	public void initData(I id,List<T> itemList) {
		this.id = id;
		itemList.forEach(e -> {
			e.afterLoad();
			beanMap.put((K) e.key(), e);
		});
		//this.afterCreate();
		//this.afterInit();
	}
	
	@Override
	public void initData(I id) {
		this.id = id;
		this.afterCreate();
	}
	
	@Override
	public Class<T> getBasePoClazz() {
		return basePoClazz;
	}
	
	@Override
	public I getId() {
		return id;
	}
	
	public void afterCreate() {}
	
}
