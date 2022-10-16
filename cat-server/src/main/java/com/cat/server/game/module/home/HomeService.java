package com.cat.server.game.module.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.game.data.proto.PBHome.PBHomeFurniture;
import com.cat.server.game.data.proto.PBHome.PBHomeRectangle;
import com.cat.server.game.data.proto.PBHome.ReqHomeBetsuinInfo;
import com.cat.server.game.data.proto.PBHome.ReqHomeFurnitureCreate;
import com.cat.server.game.data.proto.PBHome.ReqHomeFurnitureRemove;
import com.cat.server.game.data.proto.PBHome.ReqHomeFurnitureUpdate;
import com.cat.server.game.data.proto.PBHome.ReqHomeInfo;
import com.cat.server.game.data.proto.PBHome.ReqHomeSchemeLayoutInfo;
import com.cat.server.game.data.proto.PBHome.ReqHomeSchemeLayoutSave;
import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.activityitem.domain.ActivityItem;
import com.cat.server.game.module.activityitem.domain.ActivityItemDomain;
import com.cat.server.game.module.home.define.HomeConstant;
import com.cat.server.game.module.home.domain.Betsuin;
import com.cat.server.game.module.home.domain.Furniture;
import com.cat.server.game.module.home.domain.FurnitureLocation;
import com.cat.server.game.module.home.domain.Home;
import com.cat.server.game.module.home.domain.HomeDomain;
import com.cat.server.game.module.home.proto.PBHomeBetsuinBuilder;
import com.cat.server.game.module.home.proto.PBHomeFurnitureGeometryBuilder;
import com.cat.server.game.module.home.proto.PBHomeInfoBuilder;
import com.cat.server.game.module.home.proto.RespHomeBetsuinInfoBuilder;
import com.cat.server.game.module.home.proto.RespHomeFurnitureCreateBuilder;
import com.cat.server.game.module.home.proto.RespHomeFurnitureRemoveBuilder;
import com.cat.server.game.module.home.proto.RespHomeFurnitureUpdateBuilder;
import com.cat.server.game.module.home.proto.RespHomeInfoBuilder;
import com.cat.server.game.module.home.proto.RespHomeSchemeLayoutInfoBuilder;
import com.cat.server.game.module.home.proto.RespHomeSchemeLayoutSaveBuilder;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.IResource;
import com.cat.server.game.module.resource.IResourceService;
import com.cat.server.game.module.resource.helper.ResourceHelper;


/**
 * Home控制器
 * @author Jeremy
 */
@Service
public class HomeService implements IHomeService, IResourceService{
	
	private static final Logger log = LoggerFactory.getLogger(HomeService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private HomeManager homeManager;
 
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
//		HomeDomain domain = homeManager.getDomain(playerId);
//		Collection<Home> beans = domain.getBeans();
		//FSC todo somthing...
		//Codes for proto
		//playerService.sendMessage(playerId, ack);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		homeManager.remove(playerId);
	}
  
	
	/**
	 * 更新指定的别院信息
	 */
	public void responseHomeInfo(HomeDomain domain) {
		Home home = domain.getBean();
		if (home == null) {
			return;
		}
		PBHomeInfoBuilder builder = PBHomeInfoBuilder.newInstance();  
		builder.setName(home.getName());
		builder.setExp(home.getExp());
		builder.setLevel(home.getLevel());
		builder.setLastRenameTime(home.getLastRenameTime());
		builder.addAllFurnitureRes(ResourceHelper.toPairProto(home.getPropertieMap()));
		
		//家园别院信息
		Betsuin betsuin = home.getBetsuinMap().get(HomeConstant.MAIN_BETSUIN);
		builder.setMainBetsuin(this.toHomeBetsuin(betsuin).build());
		
		playerService.sendMessage(domain.getId(), builder);
	}
	
	/**
	 * 转家园别院对象
	 * @return
	 */
	public PBHomeBetsuinBuilder toHomeBetsuin(Betsuin betsuin) {
		PBHomeBetsuinBuilder betsuinBuilder = PBHomeBetsuinBuilder.newInstance();
		betsuinBuilder.setBetsuinId(betsuin.getId());
		for (Long uniqueId : betsuin.getLocationMap().keySet()) {
			//检测是否存在这个布局家具
			Furniture furniture = this.getFurniture(uniqueId, uniqueId);
			if (furniture == null) {
				continue;
			}
			//存在家具,家具方位.构建返回消息
			PBHomeFurnitureGeometryBuilder builder = PBHomeFurnitureGeometryBuilder.newInstance();
			builder.setFurniture(furniture.toProto().build());
			
			FurnitureLocation localtion = betsuin.getLocationMap().get(uniqueId);
			builder.setRectangle(localtion.toProto().build());
			betsuinBuilder.addFurnitureGeometries(builder.build());
		}
		return betsuinBuilder;
	}
	
	/////////////业务逻辑//////////////////
	
	/**
	* 请求家园信息
	* @param long playerId
	* @param ReqHomeInfo req
	* @param RespHomeInfoResp ack
	*/
	public ErrorCode reqHomeInfo(long playerId, ReqHomeInfo req, RespHomeInfoBuilder ack){
		try {
			HomeDomain domain = homeManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			final int mapId = req.getMapId();
			Home home = domain.getBean();
			Betsuin betsuin = home.getBetsuinMap().get(mapId);
			if (betsuin == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			this.responseHomeInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqHomeInfo error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求移除布局家具
	* @param long playerId 玩家id
	* @param ReqHomeFurnitureRemove req
	* @param RespHomeFurnitureRemoveResp ack
	*/
	public ErrorCode reqHomeFurnitureRemove(long playerId, ReqHomeFurnitureRemove req, RespHomeFurnitureRemoveBuilder ack){
		try {
			HomeDomain domain = homeManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			final long uniqueId = req.getUniqueId();
			//final int mapId = req.getMap();
			final int mapId = 0;
			domain.removeFurnitureFromMap(mapId, uniqueId);
			this.responseHomeInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqHomeFurnitureRemove error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求布局方案保存
	* 保存当前布局, 把当前地图所有上布局家具, 存储为方案
	* @param long playerId
	* @param ReqHomeSchemeLayoutSave req
	* @param RespHomeSchemeLayoutSaveResp ack
	*/
	public ErrorCode reqHomeSchemeLayoutSave(long playerId, ReqHomeSchemeLayoutSave req, RespHomeSchemeLayoutSaveBuilder ack){
		try {
			HomeDomain domain = homeManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			final int mapId = req.getMapId();
			final int planId = req.getPlanId();
			domain.saveLayoutScheme(mapId, planId);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqHomeSchemeLayoutSave error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求创建布局家具(家具资源转布局家具)
	* 布局家具返回给客户端, 客户端
	* @param long playerId
	* @param ReqHomeFurnitureCreate req
	* @param RespHomeFurnitureCreateResp ack
	*/
	public ErrorCode reqHomeFurnitureCreate(long playerId, ReqHomeFurnitureCreate req, RespHomeFurnitureCreateBuilder ack){
		try {
			HomeDomain domain = homeManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			final int configId = req.getConfigId();
			ResultCodeData<Furniture> resultData = domain.createFurniture(configId);
			if (!resultData.isSuccess()) {
				return resultData.getErrorCode();
			}
			PBHomeFurnitureGeometryBuilder builder = PBHomeFurnitureGeometryBuilder.newInstance();
			builder.setFurniture(resultData.getData().toProto().build());
			ack.setFurnitureGeometry(builder.build());
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqHomeFurnitureCreate error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求家园家具信息更新
	* @param long playerId
	* @param ReqHomeFurnitureUpdate req
	* @param RespHomeFurnitureUpdateResp ack
	*/
	public ErrorCode reqHomeFurnitureUpdate(long playerId, ReqHomeFurnitureUpdate req, RespHomeFurnitureUpdateBuilder ack){
		try {
			HomeDomain domain = homeManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//地图id
			final int mapId = 0;
			Betsuin betsuin = domain.getBean().getBetsuinMap().get(mapId);
			if (betsuin == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			
			PBHomeFurniture pbFurniture = req.getFurnitureGeometry().getFurniture();
			final long uniqueId = pbFurniture.getUniqueId();
			//final int configId = pbFurniture.getConfigId();
			
			PBHomeRectangle pbRectangle = req.getFurnitureGeometry().getRectangle();
			final int x = pbRectangle.getX();
			final int y = pbRectangle.getY();
			final boolean reverse = pbRectangle.getReverse();
			
			//设置家园家具的方位信息
			FurnitureLocation location = FurnitureLocation.create(x, y, reverse);
			betsuin.getLocationMap().put(uniqueId, location);
			
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqHomeFurnitureUpdate error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求布局方案信息
	* @param long playerId
	* @param ReqHomeSchemeLayoutInfo req
	* @param RespHomeSchemeLayoutInfoResp ack
	*/
	public ErrorCode reqHomeSchemeLayoutInfo(long playerId, ReqHomeSchemeLayoutInfo req, RespHomeSchemeLayoutInfoBuilder ack){
		try {
			HomeDomain domain = homeManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.responseHomeInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqHomeSchemeLayoutInfo error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	
	/**
	* 请求家园别院信息
	* @param long playerId
	* @param ReqHomeBetsuinInfo req
	* @param RespHomeBetsuinInfoResp ack
	*/
	public ErrorCode reqHomeBetsuinInfo(long playerId, ReqHomeBetsuinInfo req, RespHomeBetsuinInfoBuilder ack){
		try {
			HomeDomain domain = homeManager.getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			final int betsuinId = req.getBetsuinId();
			Betsuin betsuin = domain.getBean().getBetsuinMap().get(betsuinId);
			if (betsuin == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			ack.setBetsuin(this.toHomeBetsuin(betsuin).build());
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			log.error("reqHomeBetsuinInfo error, playerId:{}", playerId, e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
  
	
	/////////////接口方法////////////////////////
	
	@Override
	public int resType() {
		return ResourceType.Home.getType();
	}
	
	@Override
	public boolean checkAdd(long playerId, Integer configId, Integer value) {
		return true;
	}
	
	@Override
	public boolean checkEnough(long playerId, Integer configId, Integer value) {
		HomeDomain domain = homeManager.getDomain(playerId);
		if (domain == null) return false;
		return domain.getBean().getProperties(configId) >= value;
	}

	@Override
	public void reward(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		HomeDomain domain = homeManager.getDomain(playerId);
		if (domain == null) return;
		domain.getBean().addProperties(configId, value);
	}

	@Override
	public void cost(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		HomeDomain domain = homeManager.getDomain(playerId);
		if (domain == null) return;
		domain.getBean().reduceProperties(configId, value);
	}
	
	@Override
	public void cost(long playerId, Long id, NatureEnum nEnum) {
		throw new UnsupportedOperationException("家园家具不支持该操作");
	}

	@Override
	public int getCount(long playerId, Integer configId) {
		HomeDomain domain = homeManager.getDomain(playerId);
		if (domain == null) {
			return 0;
		}
		return domain.getBean().getProperties(configId);
	}
	
	/**
	 * 获取布局家具对象
	 * @param playerId
	 * @param uniqueId
	 * @return
	 */
	public Furniture getFurniture(long playerId, long uniqueId) {
		HomeDomain domain = homeManager.getDomain(playerId);
		if (domain == null) return null;
		return domain.getFurniture(uniqueId);
	}
	
	/**
	 * 新增一个布局家具对象
	 */
	public void addFuniture(long playerId, int configId) {
		HomeDomain domain = homeManager.getDomain(playerId);
		if (domain == null) return;
		Furniture furniture = Furniture.create(configId);
		domain.getBean().getFurnitureMap().put(furniture.getUniqueId(), furniture);
	}
	
	/**
	 * 移除一个布局家具对象, 只能通过唯一Id移除
	 */
	public void reduceFuniture(long playerId, long uniqueId) {
		HomeDomain domain = homeManager.getDomain(playerId);
		if (domain == null) return;
		domain.getBean().getFurnitureMap().remove(uniqueId);
	}
	
//	@Override
//	public void addResource(long playerId, IResource res, NatureEnum nEnum) {
//		HomeDomain domain = homeManager.getDomain(playerId);
//		if (domain == null) return ;
//		if (!(res instanceof Furniture)) {
//			return;
//		}
//		//FIXME
//		Furniture item = (Furniture)res;
////		domain.addReource(item.getUniqueId(), item);
//	}
	
}
 
 
