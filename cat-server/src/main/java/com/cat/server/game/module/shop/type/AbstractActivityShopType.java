package com.cat.server.game.module.shop.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ext.IConfigShop;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.activity.IActivityService;
import com.cat.server.game.module.activity.type.IActivityType;
import com.cat.server.game.module.shop.domain.ShopDomain;

/**
 * 活动类型抽象商店
 * @auth Jeremy
 * @date 2022年3月13日上午11:20:54
 */
public abstract class AbstractActivityShopType<T extends IConfigShop, A extends IActivityType> extends AbstractShopType<T> {
	
	protected Class<A> activityClazz;
	
	@Autowired protected IActivityService activityService;
	
    @SuppressWarnings("unchecked")
    public AbstractActivityShopType() {
    	Type superClass = getClass().getGenericSuperclass();
		this.shopConfigClazz = (Class<T>) (((ParameterizedType) superClass).getActualTypeArguments()[0]);
		this.activityClazz = (Class<A>) (((ParameterizedType) superClass).getActualTypeArguments()[1]);
    }
    
    @Override
    public ErrorCode buy(ShopDomain domain, int configId, int number) {
    	//先检查活动状态
    	A activity = this.getActivityType();
    	if (!activity.isOpen()) {
			return ErrorCode.SHOP_ACTIVITY_NOT_OPEN;
		}
    	return super.buy(domain, configId, number);
    }
    
    @Override
    public ErrorCode quickBuy(ShopDomain domain) {
    	//先检查活动状态
    	A activity = this.getActivityType();
    	if (!activity.isOpen()) {
			return ErrorCode.SHOP_ACTIVITY_NOT_OPEN;
		}
    	return super.quickBuy(domain);
    }
    
    @Override
    public Map<Integer, T> getConfigs() {
    	A activity = this.getActivityType();
    	if (activity == null) {
			return Collections.emptyMap();
		}
    	return ConfigManager.getInstance().getConfigs(shopConfigClazz, (c)->this.getConfigActivityId(c) == activity.getConfigId());
    }
	
    /**
     * 获取活动
     * @return  
     * @return IActivityType  
     * @date 2022年3月13日上午11:27:41
     */
    public A getActivityType() {
    	return activityService.getActivityType(this.getActivityTypeId(), activityClazz);
    }
    
    /**
     * 获取当前活动商店对应的活动类型
     * @return  活动商店类型
     * @date 2022年3月13日上午11:35:20
     */
    public abstract int getActivityTypeId();
    
    /**
     * 获取当前商店对应的活动id
     * @return  活动商店类型
     * @date 2022年3月13日上午11:35:20
     */
    public abstract int getConfigActivityId(T config);
    
}
