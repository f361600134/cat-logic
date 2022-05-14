package com.cat.server.game.module.home.domain;

import java.util.List;

/**
 * 布局方案<br>
 * 玩家自定义后保存的方案<br>
 * 所有的家具位置信息生成一个列表<br>
 * @author Jeremy
 */
public class LayoutScheme {
	
	/**
	 * 方案id
	 */
	private int planId;
	
	/**
	 * 家具列表计算后的方位信息<br>
	 */
	private List<Long> locationList;
	
	public LayoutScheme() {}
	
	public LayoutScheme(int planId, List<Long> locationList) {
		this.planId = planId;
		this.locationList = locationList;
	}
	
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	
	public List<Long> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Long> locationList) {
		this.locationList = locationList;
	}

	/**
	 * 创建一个布局方案
	 * @return 布局方案
	 */
	public static LayoutScheme create(int planId, List<Long> locationList) {
		return new LayoutScheme(planId, locationList);
	}
	
	/**
	 * 更新布局方案<br>
	 * 当保存布局方案时, 直接把布局方案替换成最新的方案
	 */
	public void update(List<Long> locationList) {
		this.locationList.clear();
		this.locationList.addAll(locationList);
	}
	
}
