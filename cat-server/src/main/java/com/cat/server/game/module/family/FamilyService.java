package com.cat.server.game.module.family;

import java.util.List;
import java.util.Map;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.net.core.executor.DisruptorExecutorGroup;
import com.cat.net.core.executor.DisruptorStrategy;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.family.domain.FamilyDomain;


/**
 * Family控制器
 */
@Service
public class FamilyService implements IFamilyService {
	
	private static final Logger log = LoggerFactory.getLogger(FamilyService.class);
	
//	@Autowired private IPlayerService playerService;
	
	@Autowired private FamilyManager familyManager;
	
//	/**
//	 * 登陆
//	 */
//	public void onLogin(long playerId) {
////		FamilyDomain domain = familyManager.getDomain(playerId);
////		Collection<Family> beans = domain.getBeans();
//		//FSC todo somthing...
//		//Codes for proto
//		//playerService.sendMessage(playerId, ack);
//	}
	
//	/**
//	 * 当玩家离线,移除掉道具模块数据
//	 * @param playerId
//	 */
//	public void onLogout(long playerId) {
//		familyManager.remove(playerId);
//	}
	
	/**
	 * 更新信息
	 */
	public void responseFamilyInfo(long playerId, Family family) {
		try {
			//resp.addArtifactlist(family.toProto());
			//playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("responseFamilyInfo error, familyId:{}", family.getId());
			log.error("responseFamilyInfo error, e:", e);
		}
	}
	
	/////////////业务逻辑//////////////////
	/**
	 * 创建家族
	 * @param playerId
	 * @param name  
	 * @return void  
	 * @date 2021年5月10日下午11:19:24
	 */
	public ErrorCode createFamily(long playerId, String name) {
		if (StringUtils.isBlank(name)) {
			return ErrorCode.CONTENT_INVALID_NAME;
		}
		//TODO 敏感字判断,特殊字符,长度判断
		
		//	重复名字判断
		if (familyManager.getFamilyNameMap().containsKey(name)) {
			return ErrorCode.FAMILY_NAME_EXIST;
		}
		//	重复进入家族判断
		long familyId = familyManager.getFamilyIdByPlayerId(playerId);
		if (familyId > 0) {
			return ErrorCode.FAMILY_NAME_EXIST;
		}
		FamilyDomain domain = familyManager.getDomain();
		Family family = domain.createFamily(name);
		this.responseFamilyInfo(playerId, family);
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