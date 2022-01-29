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
	
	/**
	 * 域缓存
	 * 之前使用ConcurrentHashMap作为显示调用缓存, 玩家个人模块的缓存数据绑定玩家声明周期<br>
	 * 后续为了支持, 玩家不在线情况, 依旧可以操作器数据, 所以修改为Cache作为缓存, 玩家不在线也可以load出模块数据进行修改<br>
	 * 修改后在指定时间内没有读写, 则被从缓存中移除掉.<br>
	 * */
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
	
	@Override
	public T getOrLoadDomain(I id) {
 		T domain = domains.get(id);
		if (domain == null) {
			domain = getFromDb(id);
		}
		return domain;
	}
	
	@Override
	public T getDomain(I id) {
		return domains.get(id);
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
	@Override
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
			domains.put(id, domain);
			return domain;
		}catch (Exception e) {
			logger.error("getFromDb error", e);
		}
		return null;
	}
	
}
