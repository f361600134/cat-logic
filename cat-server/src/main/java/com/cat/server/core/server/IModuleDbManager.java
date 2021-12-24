package com.cat.server.core.server;

import com.cat.orm.core.base.IBasePo;

/**
 * 模块DbManager接口, 提供对数据库操作的结构
 * @author Jeremy
 * @param <T>
 * @deprecated 不愿意用反射的方式加载数据
 */
public interface IModuleDbManager<I, T extends IModuleDomain<I, ? extends IBasePo>> {
	
	/**
	 * 先从缓存获取, 获取不到从数据库获取
	 * @param id
	 * @return
	 */
	public T getFromDb(I id);
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	default public T get(I id) {
//		Type superClass = getClass().getGenericSuperclass();
//		Class<T> clazzDomain = (Class<T>)(((ParameterizedType) superClass).getActualTypeArguments()[1]);
//		
//		Type superBasePoClass = clazzDomain.getGenericSuperclass();
//		Class<? extends IBasePo> clazzBasePo = (Class<? extends IBasePo>)(((ParameterizedType) superBasePoClass).getActualTypeArguments()[1]);
//		
//		List list = processor().selectByIndex(clazzBasePo, new Object[] {id});
//		if (list.isEmpty()) {
//			return null;
//		}else {
//			try {
//				T domain = clazzDomain.newInstance();
//				domain.initData(id, list);
//				return domain;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
}
