package com.cat.server.game.module.home.domain;

import java.util.HashMap;
import java.util.Map;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.module.home.proto.PBHomeInfoBuilder; 

/**
 * 家园对象, 整个系统对象, 包含别院, 资源, 方案等信息
* @author Jeremy
*/
@PO(name = "home")
public class Home extends HomePo implements IPersistence{
	
	/**
	 * 资源map, 用于缓存家具资源
	 */
	@Column(PROP_RESOURCESTR)
	private Map<Integer, Integer> propertieMap = new HashMap<>();
	
	/**
	 * 方案所有别院共享
	 */
	@Column(PROP_LAYOUTSCHEMESTR)
	private Map<Integer, LayoutScheme> layoutSchemeMap = new HashMap<>();
	
	/**
	 * 家园内所有的别院<br>
	 */
	@Column(PROP_BETSUINSTR)
	private Map<Integer, Betsuin> betsuinMap = new HashMap<>();
	
	/**
	 * 家园内生成的家具<br>
	 * 当失去所有引用时, 从家具资源内移除掉
	 */
	@Column(PROP_FURNITURESTR)
	private Map<Long, Furniture> furnitureMap = new HashMap<>();
	
	public Home() {

	}
	
	public Home(long playerId) {
		this.playerId = playerId;
	}
	
	public Map<Integer, Integer> getPropertieMap() {
		return propertieMap;
	}

	public Map<Integer, LayoutScheme> getLayoutSchemeMap() {
		return layoutSchemeMap;
	}

	public Map<Integer, Betsuin> getBetsuinMap() {
		return betsuinMap;
	}
	
	public Map<Long, Furniture> getFurnitureMap() {
		return furnitureMap;
	}
	
	//================一些逻辑===================
	
	/**
	 * 检查家具是否足够
	 * @param value
	 * @return void
	 * @date 2020年8月20日下午5:35:35
	 */
	public boolean checkProperties(int configId, int value) {
		return getProperties(configId) >= value;
	}

	/**
	 * 获取家具数量
	 * @param configId 家具id
	 * @return int 数量
	 * @date 2020年12月20日下午5:26:37
	 */
	public int getProperties(int configId) {
		return propertieMap.getOrDefault(configId, 0);
	}

	/**
	 * 家具增加
	 * @param configId 家具id
	 * @param added 增加的数量
	 */
	public void addProperties(int configId, int added) {
		int val = getProperties(configId);
		if (added < 0)
			return;
		val += added;
		val = val > Integer.MAX_VALUE ? Integer.MAX_VALUE : val;
		propertieMap.put(configId, val);
		this.update();
	}

	/**
	 * 家具减少值，数值只能为正，有函数自己实现减少操作
	 * @param configId 家具id
	 * @param added 减少的数量
	 */
	public void reduceProperties(int configId, int added) {
		int val = getProperties(configId);
		if (added < 0)
			return;
		val -= added;
		val = val < 0 ? 0 : val;
		propertieMap.put(configId, val);
		this.update();
	}
	
	/**
	 * 家园对象转协议对象
	 * @return
	 */
	public PBHomeInfoBuilder toProto() {
		PBHomeInfoBuilder builder = PBHomeInfoBuilder.newInstance();  
		builder.setName(this.getName());
		builder.setExp(this.getExp());
		builder.setLevel(this.getLevel());
		builder.setLastRenameTime(this.getLastRenameTime());
		
		return builder;
	}
}
