package com.cat.server.game.module.activity.type;

import java.util.EnumMap;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.type.impl.StudyClubActivityType;

/**
 * 所有活动id, 初始化加载所有活动
 * @author Jeremy
 */
public enum ActivityTypeEnum {

    /**
     * 研习社活动
     */
    STUDYCLUB(101) {
		@Override
		public IActivityType newActivityType(Activity activity) {
			return new StudyClubActivityType(activity);
		}
	},

    ;
    private final int value;

    ActivityTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public abstract IActivityType newActivityType(Activity activity);

    //使用缓存, 避免values内存复制
    private static EnumMap<ActivityTypeEnum, Integer> activityTypeMap = new EnumMap<>(ActivityTypeEnum.class);
    static {
    	for (ActivityTypeEnum activityType : ActivityTypeEnum.values()) {
    		activityTypeMap.put(activityType, activityType.getValue());
		}
    }
    
    public static ActivityTypeEnum valueOf(int activityType) {
    	for (ActivityTypeEnum type : activityTypeMap.keySet()) {
    		if (type.getValue() == activityType) {
				return type;
			}
		}
    	return null;
    }

}
