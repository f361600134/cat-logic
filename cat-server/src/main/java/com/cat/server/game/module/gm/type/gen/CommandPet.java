package com.cat.server.game.module.gm.type.gen;

import org.springframework.beans.factory.annotation.Autowired;
import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBPet.ReqPetActive;
import com.cat.server.game.data.proto.PBPet.ReqPetIdentify;
import com.cat.server.game.data.proto.PBPet.ReqPetLevelup;
import com.cat.server.game.module.gm.annotation.Command;
import com.cat.server.game.module.gm.type.AbstractModuleCommand;
import com.cat.server.game.module.pet.PetController;

/**
 * 资源GMService, 正数表示增加, 负数表示减少
 * @resource id1,num1,id2,num2
 * @resource 1001,10,1002,-10
 * @author Administrator
 */
@Command("@pet")
public class CommandPet extends AbstractModuleCommand{
	
	@Autowired
	private PetController petController;
	
	/**
	 * 请求宠物升级, @pet reqPetLevelup,100001
	 * @param session 会话
	 * @param data  数据,从0开始
	 * 
	 */
	public void reqPetLevelup(ISession session, String data[]) {
		ReqPetLevelup.Builder req = ReqPetLevelup.newBuilder();
		req.setUniqueId(Long.valueOf(data[1]));
		petController.reqPetLevelup(session, req.build());
	}
	
	/**
	 * 请求宠物激活
	 * @param session 会话
	 * @param data  数据,从0开始
	 * @return void  
	 * @date 2022年4月15日下午10:25:56
	 */
	public void reqPetActive(ISession session, String data[]) {
		ReqPetActive.Builder req = ReqPetActive.newBuilder();
		req.setUniqueId(Long.valueOf(data[1]));
		petController.reqPetActive(session, req.build());
	}
	
	/**
	 * 请求宠物鉴定
	 * @param session 会话
	 * @param data  数据,从0开始
	 * @return void  
	 * @date 2022年4月15日下午10:25:56
	 */
	public void reqPetIdentify(ISession session, String data[]) {
		ReqPetIdentify.Builder req = ReqPetIdentify.newBuilder();
		req.setUniqueId(Long.valueOf(data[1]));
		petController.reqPetIdentify(session, req.build());
	}
	
	
	
	
	
	
	
	
}
