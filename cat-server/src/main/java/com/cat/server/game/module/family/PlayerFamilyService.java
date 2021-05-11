package com.cat.server.game.module.family;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.family.domain.PlayerFamily;
import com.cat.server.game.module.family.domain.PlayerFamilyDomain;
import com.cat.server.game.module.player.IPlayerService;


/**
 * PlayerFamily控制器
 */
@Service
public class PlayerFamilyService implements IPlayerFamilyService {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerFamilyService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private FamilyService familyService;
	
	@Autowired private PlayerFamilyManager playerFamilyManager;
	
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
//		PlayerFamilyDomain domain = playerFamilyManager.getDomain(playerId);
//		Collection<PlayerFamily> beans = domain.getBeans();
		//FSC todo somthing...
		//Codes for proto
		//playerService.sendMessage(playerId, ack);
	}
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		playerFamilyManager.remove(playerId);
	}
	
	/**
	 * 更新信息
	 */
	public void responsePlayerFamilyInfo(long playerId, Family family) {
		try {
//			for (PlayerFamily playerFamily : beans) {
//				resp.addArtifactlist(playerFamily.toProto());
//			}
			//playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("responsePlayerFamilyInfo error, familyId:{}", family.getId());
			log.error("responsePlayerFamilyInfo error, e:", e);
		}
	}
	
	/////////////业务逻辑//////////////////
	
	/**
	 * 创建家族
	 * @param playerId 创建者id
	 * @param name 家族名称
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode createFamily(long playerId, String name) {
		if (StringUtils.isBlank(name)) {
			return ErrorCode.CONTENT_INVALID_NAME;
		}
		//TODO 敏感字判断,特殊字符,长度判断
		try {
			Family family = familyService.createFamily(playerId, name);
			//	下发家族信息
			this.responsePlayerFamilyInfo(playerId, family);
			//	返回创建家族成功信息
		} catch (Exception e) {
			e.printStackTrace();
			log.error("createFamily error");
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 修改家族名
	 * @param playerId 修改者id
	 * @param name 修改的新名字
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode editFamilyName(long playerId, String name) {
		if (StringUtils.isBlank(name)) {
			return ErrorCode.CONTENT_INVALID_NAME;
		}
		//TODO 敏感字判断,特殊字符,长度判断
		PlayerFamilyDomain domain = playerFamilyManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.DOMAIN_IS_NULL;
		}
		final long familyId = familyService.getPlayerFamilyId(playerId);
		if (familyId == 0) {
			return ErrorCode.FAMILY_NO_FAMILY;
		}
		//	权限校验
		//	权限校验,放在FamilyService
		try {
			familyService.editFamilyName(playerId, name);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("editFamilyName error");
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 查询家族
	 * @param playerId
	 * @param name  
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode searchFamily(long playerId, String name) {
//		searchFamily
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 申请进入家族
	 * @param playerId
	 * @param name  
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode applyJoinFamily(long playerId, String name) {
		PlayerFamilyDomain domain = playerFamilyManager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.DOMAIN_IS_NULL;
		}
		if (!domain.isMeetTime()) {
			return ErrorCode.FAMILY_JOIN_TIME_LIMIT;
		}
		
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 退出家族
	 * @param playerId
	 * @param name  
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode exitFamily(long playerId, String name) {
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 查看家族申请列表
	 * @param playerId
	 * @param name  
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode showFamilyApplyInfo(long playerId, String name) {
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 管理家族,任命
	 * @param playerId
	 * @param name  
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode managerFamily(long playerId, String name) {
		return ErrorCode.SUCCESS;
	}
	
	
	
	/////////////接口方法////////////////////////
	
}