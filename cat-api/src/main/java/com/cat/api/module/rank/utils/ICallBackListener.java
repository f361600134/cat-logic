package com.cat.api.module.rank.utils;

import java.util.Collection;

/**
 * 排行榜 回调监听接口
 * @auth Jeremy
 * @date 2021年2月28日下午9:45:37
 */
@FunctionalInterface
public interface ICallBackListener<V> {
	
	/**
	 * 回调方法
	 * @param updateSet
	 * @param deleteSet
	 */
	public void call(Collection<V> updateSet, Collection<V> deleteSet);

}
