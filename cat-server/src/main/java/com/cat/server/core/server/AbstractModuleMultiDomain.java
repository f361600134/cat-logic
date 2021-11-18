package com.cat.server.core.server;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.orm.core.base.IBasePo;

/**
 * 模块多Domain域抽象方法<p>
 * 如果持有者为玩家, 则id为玩家id, beanMap视为玩家此模块的基础数据.<p>
 * 以Hero模块{@link com.cat.server.game.module.hero.domain.HeroDomain}为例, I 持有者id, 即玩家id, beanMap的key则为武将id, value则为武将对象<p>
 * 以Family模块{@link com.cat.server.game.module.family.domain.FamilyDomain}为例, I 持有id为serverId, 表示本区的区服id, beanMap的key则为家族id, value则为家族对象<p>
 * 其他模块亦如此
 *
 * @param <I> 持有者id类型
 * @param <K> bean的id类型
 * @param <T> bean对象
 * @author Jeremy
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
            this.basePoClazz = (Class<T>) (((ParameterizedType) superClass).getActualTypeArguments()[2]);
        } catch (Exception e) {
            logger.error("AbstractModuleDomain error", e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public AbstractModuleMultiDomain(Map<K, T> beanMap) {
        this.beanMap = beanMap;
        try {
            Type superClass = getClass().getGenericSuperclass();
            this.basePoClazz = (Class<T>) (((ParameterizedType) superClass).getActualTypeArguments()[2]);
        } catch (Exception e) {
            logger.error("AbstractModuleDomain error", e);
        }
    }

    /**
     * 新增域内实体
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
    @SuppressWarnings("unchecked")
	@Override
    public void initData(I id, List<T> itemList) {
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

    /**
     * 无数据创建数据时调用
     */
    public void afterCreate() {
    }

}
