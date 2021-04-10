package com.cat.server.game.module.mainmission.domain;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.module.mission.type.MainMissionType;
import com.cat.server.game.module.mission.type.MissionTypeData;

/**
 * 主线任务对象, 所有主线任务存储在此
* @author Jeremy
*/
@PO(name = "main_mission")
public class MainMission extends MainMissionPo implements IPersistence{
	
	/**   
	 * serialVersionUID:变量描述.   
	 */ 
	private static final long serialVersionUID = 1L;
	
	/**
	 * MissionTypeData 的定义, 是为了实现所有任务的统一处理, 以及保存
	 * 但是, 也带来了不好的方面, 在MainMission内缓存的任务对象的生命周期, 由外部任务处理器去控制
	 * 破坏了内聚性, 
	 * 	任务对象, 此对象不应该提供接口给外部修改.
	 * 	所有的外部修改, 都必须在此对象内提供方法, 修改对象后存储
	 */
	@Column(PROP_MISSIONSTR)
	private MissionTypeData<MainMissionType> missionData;
	
	public MainMission() {
		this.missionData = new MissionTypeData<>();
	}
	
	public MainMission(long playerId) {
		this.playerId = playerId;
	}
	
	public static MainMission create(long playerId) {
		return new MainMission(playerId);
	}
	

	public MissionTypeData<MainMissionType> getMissionData() {
		return missionData;
	}
	
	@Override
	public String toString() {
		return "MainMission [missionData=" + missionData + "]";
	}
}
