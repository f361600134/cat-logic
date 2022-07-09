package com.cat.server.game.module.stall.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.interfaces.IConfigResource;
import com.cat.server.game.module.resource.IResource;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * 抽象货架
 * @author Jeremy
 */
public abstract class AbstractCommodityShelf<T extends IResource, C extends IConfigResource> implements IComodityContainer {
	
	protected Class<T> CommodityClazz;
	protected Class<C> configClazz;
	
	@SuppressWarnings("unchecked")
    public AbstractCommodityShelf() {
    	Type superClass = getClass().getGenericSuperclass();
		this.CommodityClazz = (Class<T>) (((ParameterizedType) superClass).getActualTypeArguments()[0]);
		this.configClazz = (Class<C>) (((ParameterizedType) superClass).getActualTypeArguments()[1]);
    }
	
	/**
	 * 该分类下的商品列表
	 * key: 商品唯一id values: 商品
	 */
	private Map<Long, T> commodities = new HashMap<>();

	/**
	 * 适用于查询的缓存数据<br>
	 * key: 商品名字
	 * value: 商品id列表
	 */
	private Multimap<String, Long> cache = ArrayListMultimap.create();

	public Collection<T> search(String keyword) {
		List<Long> temp = new ArrayList<>();
		if (StringUtils.isBlank(keyword)) {
			//关键字为空, 查询全部
			temp.addAll(commodities.keySet());
		}else {
			//关键字不为空, 按关键字检索
			for (String name : this.cache.keySet()) {
				if (name.contains(keyword)) {
					temp.addAll(this.cache.get(name));
				}
			}
		}
		if (temp.isEmpty()) return Collections.emptyList();
		
		List<T> ret = new ArrayList<>();
		temp.forEach(k->ret.add(this.commodities.get(k)));
		return ret;
	}

	@Override
	public void add(int configId, long uniqueId, int price) {
		T t = this.getReource(uniqueId);
		C config = this.getConfig(t.getConfigId());
		this.cache.put(config.getName(), t.getUniqueId());
	}
	
	@Override
	public void remove(int configId, long uniqueId) {
		T t = this.getReource(uniqueId);
		this.commodities.remove(t.getUniqueId(), t);
		C config = this.getConfig(t.getConfigId());
		this.cache.remove(config.getName(), t.getUniqueId());
	}
	
	/**
	 * 从配置里获取到商品的名字
	 * @return
	 */
	public C getConfig(int configId) {
		return ConfigManager.getInstance().getConfig(configClazz, configId);
	}
	
	/**
	 * 从配置里获取到商品的名字
	 * @return
	 */
	public abstract T getReource(long uniqueId);
	
}
