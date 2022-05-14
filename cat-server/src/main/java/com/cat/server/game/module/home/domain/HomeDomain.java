package com.cat.server.game.module.home.domain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleDomain;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.home.define.HomeConstant;
import com.cat.server.game.module.home.utils.HomeUtil;

/**
* HomeDomain
* @author Jeremy
*/
public class HomeDomain extends AbstractModuleDomain<Long, Home> {

	private static final Logger log = LoggerFactory.getLogger(HomeDomain.class);
	
	
	@Override
	public void afterCreate() {
		Home home = this.getBean();
		//TODO something... after create home object.
		//初始化别院
		//初始化区域
		//初始化家具
		Betsuin betsuin = Betsuin.create(HomeConstant.MAIN_BETSUIN);
		home.getBetsuinMap().put(betsuin.getId(), betsuin);
		home.update();
	}
	
	////////////业务代码////////////////////
	
	/**
	 * 获取布局家具对象
	 * @param playerId
	 * @param uniqueId
	 * @return
	 */
	public Furniture getFurniture(long uniqueId) {
		return this.getBean().getFurnitureMap().get(uniqueId);
	}
	
	/**
	 * 保存本地图的方案信息
	 * @param mapId 地图id
	 * @param planId 方案id
	 */
	public void saveLayoutScheme(int mapId, int planId) {
		final Home home = this.getBean();
		Betsuin betsuin = home.getBetsuinMap().get(mapId);
		List<Long> locationList = new ArrayList<>();
		for (Long uniqueId: betsuin.getLocationMap().keySet()) {
			Furniture furniture = this.getFurniture(uniqueId);
			if (furniture == null) {
				continue;
			}
			FurnitureLocation location = betsuin.getLocationMap().get(uniqueId);
			if (location == null) {
				continue;
			}
			long locationId = HomeUtil.buildUniqueId(furniture.getConfigId(), location.getX(), location.getY(), location.getReverse());
			locationList.add(locationId);
		}
		LayoutScheme layout = home.getLayoutSchemeMap().get(planId);
		layout.setLocationList(locationList);
		home.save();
	}
	
	/**
	 * 地图中移除掉<br>
	 * 先从地图中移除掉, 然后检索所有的房间是否有引用, 无引用则删掉此家具,加入到背包
	 */
	public void removeFurnitureFromMap(int mapId, long uniqueId) {
		final Home home = this.getBean();
		final Betsuin betsuin = home.getBetsuinMap().get(mapId);
		//从别院中移除掉家具的方位
		betsuin.getLocationMap().remove(uniqueId);
		//当前地图只要使用了该家具, 就不能继续使用家具了, 从家具列表中移除
		Furniture furniture = home.getFurnitureMap().remove(uniqueId);
		if (furniture == null) {
			return;
		}
		//加入家具资源到背包
		home.addProperties(furniture.getConfigId(), 1);
	}
	
	/**
	 * 创建布局家具<br>
	 * 判断家具资源是否充足, 如果充足则扣除资源, 创建布局家具
	 * @param configId 家具配置id
	 * @return
	 */
	public ResultCodeData<Furniture> createFurniture(int configId) {
		final Home home = this.getBean();
		if (home.getProperties(configId) <= 0) {
			return ResultCodeData.of(ErrorCode.AMOUNT_NOT_ENOUGH);
		}
		//扣除资源, 扣除成功后创建家具
		home.reduceProperties(configId, 1);
		Furniture furniture = Furniture.create(configId);
		return ResultCodeData.of(furniture);
	}

	
}

