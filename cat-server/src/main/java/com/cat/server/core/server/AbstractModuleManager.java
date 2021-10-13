package com.cat.server.core.server;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.orm.core.base.BasePo;
import com.cat.orm.core.db.process.IDataProcess;

/**
 * 	抽象类Manager
 * @author Jeremy
 * @param <T> IModuleDomain的派生类
 */
public abstract class AbstractModuleManager<I, T extends IModuleDomain<I, ? extends BasePo>> implements IModuleManager<I, T>{
	
	public Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired protected IDataProcess process;
	
	/**域缓存*/
	protected final Map<I, T> domains = new ConcurrentHashMap<>();
	protected Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public AbstractModuleManager() {
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
	
	@Override
	public Collection<T> getAllDomain() {
		return domains.values();
	}
	
	/**
	 * 获取数据, 获取不到从数据库获取
	 */
	@Override
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
	 * 这里的getFromDB方法, 可以通过获取到domain反射获取pojo对象
	 * @param id 持有者id
	 * @return T domain对象
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
			return domain;
		}catch (Exception e) {
			logger.error("getFromDb error", e);
		}
		return null;
	}

}
