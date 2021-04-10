package com.cat.server.core.server;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.cat.orm.core.base.BasePo;
import com.cat.orm.core.db.process.DataProcessorAsyn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;

/**
 * 	抽象类Manager
 * @author Jeremy
 * @param <T> IModuleDomain的派生类
 */
public abstract class AbstractModuleManager<I, T extends IModuleDomain<I, ? extends BasePo>> implements IModuleManager<I, T>{
	
	public Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired protected DataProcessorAsyn process;
	
	/**域缓存*/
	protected final Map<I, T> domains;
	protected Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public AbstractModuleManager() {
		this.domains = Maps.newConcurrentMap();
		try {
			Type superClass = getClass().getGenericSuperclass();
			this.clazz = (Class<T>)(((ParameterizedType) superClass).getActualTypeArguments()[1]);
			//throw a new Exception if the clazz is not a normal class.
			if (this.clazz.isInterface() || Modifier.isAbstract(this.clazz.getModifiers())) {
				throw new IllegalArgumentException("AbstractModuleManager error, the Arguments is not a normal class");
			}
		} catch (Exception e) {
			logger.error("AbstractModuleDomain error", e);
		}
	}
	
	/**
	 * 获取数据, 获取不到从数据库获取
	 */
	public T getDomain(I id) {
		T domain = domains.get(id);
		if (domain == null) {
			domain = getFromDb(id);
			domains.put(id, domain);
		}
		return domain;
	}
	
	@Override
	public void remove(I id) {
		domains.remove(id);
	}
	
	/**
	 * 这里的getFromDB方法, 可以通过获取到domain反射获取pojo对象, 然后set到domain内
	 * 所以
	 * @param playerId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public T getFromDb(I id) {
		try {
			T domain = clazz.newInstance();
			List list = process.selectByIndex(domain.getBasePoClazz(), new Object[] {id});
			if (list.isEmpty()) {
				//	无数据创建
				domain.initData(id);
			}else {
				//	有数据初始化
				domain.initData(id, list);
			}
			domain.afterInit();
			return domain;
		}catch (Exception e) {
			logger.error("getFromDb error", e);
		}
		return null;
	}

}
