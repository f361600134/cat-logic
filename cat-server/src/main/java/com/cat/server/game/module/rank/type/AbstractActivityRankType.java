package com.cat.server.game.module.rank.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import com.cat.server.game.module.activity.IActivityService;
import com.cat.server.game.module.activity.type.IActivityType;
import com.cat.server.game.module.rank.domain.Rank;

/**
 * 抽象的活动玩家个人排行榜类型<br>
 * 活动排行,活动进入领奖期, 封榜, 即:排行榜不能再变化<br>
 * 活动进入领奖期时,玩家可以领取排行榜奖励, 这个应当由活动模块实现. 活动排名奖励应配置在活动排行奖励公用一张表<br>
 * 活动关闭后, 需清空排行榜, 可以由活动结束事件通知排行模块清除排行数据, 也可以由活动结束时直接调用.
 * @author Jeremy
 */
public abstract class AbstractActivityRankType<A extends IActivityType> extends AbstractRankType {
	
	@Autowired protected IActivityService activityService;
	
	protected Class<A> activityClazz;
	

	@SuppressWarnings("unchecked")
    public AbstractActivityRankType() {
		super();
    	Type superClass = getClass().getGenericSuperclass();
		this.activityClazz = (Class<A>) (((ParameterizedType) superClass).getActualTypeArguments()[0]);
    }
	/**
     * 获取活动
     * @return IActivityType 活动对象
     */
    public A getActivityType() {
    	return activityService.getActivityType(activityClazz);
    }
    
    @Override
    public void onRefresh(Rank rank) {
    	A activity = getActivityType();
    	if (activity.isBegin()) {
    		super.onRefresh(rank);
		}
    }
    
    
}
