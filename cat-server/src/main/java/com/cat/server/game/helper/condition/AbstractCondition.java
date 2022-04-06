package com.cat.server.game.helper.condition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractCondition implements ICondition {
	
	/**
	 * 条件列表
	 */
	protected final List<ICondition> conditions;
	
	public AbstractCondition() {
		this.conditions = new ArrayList<>();
	}
	
	public AbstractCondition(List<ICondition> conditions) {
		this.conditions = new ArrayList<>(conditions);
	}

	public Collection<ICondition> getConditions() {
		return Collections.unmodifiableCollection(conditions);
	}
	
    public void addCondition(final ICondition condition) {
        this.conditions.add(condition);
    }

    public boolean removeCondition(final ICondition condition) {
        return this.conditions.remove(condition);
    }
	
}
