package com.cat.server.game.module.artifact.domain;

import com.cat.server.core.server.AbstractModuleMultiDomain;
//import com.cat.server.game.module.mission.handler.ArtifactMissionHandler;
//import com.cat.server.game.module.mission.handler.IMissionHandler;
//import com.cat.server.game.module.mission.type.IMission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 	每个神器不用的任务, 所以一个任务处理类需要跟神器本身绑定
 * @author Jeremy
 *
 */
public class ArtifactDomain extends AbstractModuleMultiDomain<Long, Integer, Artifact> {
	
	private final Logger log = LoggerFactory.getLogger(ArtifactDomain.class);
	
//	/**
//	 * key: 神器id
//	 * value: 任务处理类
//	 */
//	private Map<Integer, IMissionHandler> missionHandlerMap;
	
	public ArtifactDomain() {
//		this.missionHandlerMap = new ConcurrentHashMap<>();
	}
	/**
	 * 第一次创建对象, 需要创建所有神兵对象
	 */
	@Override
	public void afterCreate() {
		//TODO	伪代码,创建神兵对象,i表示configId
		Artifact artifact = null;
		for (int i = 0; i < 5; i++) {
			artifact = Artifact.create(id, i);
			getBeanMap().put(artifact.getConfigId(), artifact);
		}
	}
	
	@Override
	public void afterInit() {
		beanMap.forEach((configId, artifact) -> {
//			//	伪代码,初始化任务处理器,不同的任务处理器代理的不同神器内的任务
//			IMissionHandler handler = ArtifactMissionHandler.Builder.newBuilder()
//					.playerId(id)
//					.missionData(artifact.getMissionData())
//					.build();
//			missionHandlerMap.put(configId, handler);
		});
	}
	
	/**
	 * 激活神器对象
	 *   
	 * @return void  
	 * @date 2022年3月27日下午9:59:19
	 */
	public void active(int artifactId) {
		Artifact artifact = Artifact.create(getId(), artifactId);
		this.putBean(artifact.getConfigId(), artifact);
	}
	
//	@Override
//	public ErrorCode onProcess(IEvent event) {
//		ErrorCode code = ErrorCode.SUCCESS;
//		for (Integer configId : missionHandlerMap.keySet()) {
//			IMissionHandler handler = missionHandlerMap.get(configId);
//			ErrorCode cur = handler.onProcess(event);
//			if (cur.isSuccess()) {//	如果处理成功, 则保存对应的神兵对象
//				getBean(configId).update();
//			}else {
//				code = cur;
//			}
//		}
//		return code;
//	}

//	@Override
//	public ErrorCode onReward(int configId, NatureEnum nenum) {
//		IMissionHandler handler = missionHandlerMap.get(configId);
//		return handler.onReward(configId, nenum);
//	}
//
//	@Override
//	public Collection<PBMission.PBMissionInfo> toProto() {
//		Collection<PBMission.PBMissionInfo> ret = new ArrayList<>();
//		for (IMissionHandler handler : missionHandlerMap.values()) {
//			ret.addAll(handler.toProto());
//		}
//		return ret;
//	}
//
//	@Override
//	public List<IMission> getUpdateList() {
//		List<IMission> ret = new ArrayList<>();
//		for (IMissionHandler handler : missionHandlerMap.values()) {
//			ret.addAll(handler.getUpdateList());
//		}
//		return ret;
//	}
//	@Override
//	public long getPlayerId() {
//		return getId();
//	}

}
