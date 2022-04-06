package com.cat.server.game.helper.condition.define;

import com.cat.server.game.helper.condition.ICondition;
import com.cat.server.game.helper.condition.impl.LevelCondition;
import com.cat.server.game.helper.condition.impl.StageCondition;

public enum ConditionEnum {
	
	Level(1) {
		@Override
		public ICondition newCondition(int conditionValue) {
			return new LevelCondition(conditionValue);
		}
	},
	
	Stage(2) {
		@Override
		public ICondition newCondition(int conditionValue) {
			return new StageCondition(conditionValue);
		}
	},
	
	;
	
	private int conditionId;
	
	private ConditionEnum(int conditionId) {
		this.conditionId = conditionId;
	}
	
	public int getConditionId() {
		return conditionId;
	}
	
	public abstract ICondition newCondition(int conditionValue);
	
	public static ConditionEnum valueOf(int conditionId) {
		for (ConditionEnum conditionEnum : values()) {
			if (conditionEnum.getConditionId() == conditionId) {
				return conditionEnum;
			}
		}
		return null;
	}
}


















