package com.cat.server.game.module.function.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.shaded.com.google.common.collect.Lists;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;

/**
 * @author Jeremy
 */
@PO(name = "function")
public class Function extends FunctionPo implements IPersistence {

	@Column(PROP_FUNCTIONSTR)
	private Map<Integer, FunctionData> functionDataMap = new HashMap<>();

	@Column(PROP_REDDOTSTR)
	private Map<Integer, FunctionReddot> reddotMap = new HashMap<>();

	public Function() {

	}

	public Function(long playerId) {
		this.playerId = playerId;
	}

	public Map<Integer, FunctionReddot> getReddotMap() {
		return reddotMap;
	}
	
	public Map<Integer, FunctionData> getFunctionDataMap() {
		return functionDataMap;
	}

	/**
	 * 获取指定条件的红点
	 * 
	 * @param conditionId 条件id
	 * @return 红点数据列表
	 */
	public List<Integer> getReddot(int reddotId) {
		FunctionReddot reddot = reddotMap.get(reddotId);
		return reddot == null ? Collections.emptyList() : reddot.getReddotValues();
	}

	/**
	 * 获取指定条件的红点
	 * 
	 * @param conditionId 条件id
	 * @return 红点数量, 大于0表示有红点
	 */
	public boolean replaceReddot(int reddotId, List<Integer> newValues) {
		if (CollectionUtils.isEmpty(newValues)) 
			return false;

		FunctionReddot reddot = reddotMap.computeIfAbsent(reddotId, v -> FunctionReddot.create(reddotId));
		List<Integer> oldValues = reddot.getReddotValues();
		// 如果新旧红点数据一致, 表示没有变化, 返回false
		if (CollectionUtils.containsAll(oldValues, newValues)) {
			return false;
		}
		reddot.getReddotValues().clear();
		reddot.getReddotValues().addAll(newValues);
		return true;
	}

	/**
	 * 获取功能模块数据
	 * 
	 * @return
	 */
	public FunctionData getFunctionData(int functionId) {
		return functionDataMap.computeIfAbsent(functionId, v -> FunctionData.create(functionId));
	}
}
