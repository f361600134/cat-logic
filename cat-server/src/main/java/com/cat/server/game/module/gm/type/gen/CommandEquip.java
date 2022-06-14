package com.cat.server.game.module.gm.type.gen;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.net.network.base.ISession;
import com.cat.server.game.data.proto.PBEquip.ReqEquipInfo;
import com.cat.server.game.data.proto.PBEquip.ReqEquipPunching;
import com.cat.server.game.data.proto.PBEquip.ReqEquipUpgrade;
import com.cat.server.game.module.equip.EquipController;
import com.cat.server.game.module.gm.annotation.Command;
import com.cat.server.game.module.gm.type.AbstractModuleCommand;

/**
 * 装备GM
 * @author Jeremy
 */
@Command("@equip")
public class CommandEquip extends AbstractModuleCommand{
	
	@Autowired
	private EquipController equipController;
	
	
	/**
	 * 请求装备信息, @equip reqEquipInfo
	 * @param session 会话
	 * @param data  数据,从0开始
	 */
	public void reqEquipInfo(ISession session, String data[]) {
		ReqEquipInfo.Builder req = ReqEquipInfo.newBuilder();
		equipController.reqEquipInfo(session, req.build());
	}
	
	/**
	 * 请求装备加工, @equip reqEquipUpgrade
	 * @param session 会话
	 * @param data  数据,从0开始
	 */
	public void reqEquipUpgrade(ISession session, String data[]) {
		ReqEquipUpgrade.Builder req = ReqEquipUpgrade.newBuilder();
		req.setEquipId(Long.valueOf(data[1]));
		equipController.reqEquipUpgrade(session, req.build());
	}
	
	/**
	 * 请求装备打孔, @equip reqEquipPunching
	 * @param session 会话
	 * @param data  数据,从0开始
	 */
	public void reqEquipPunching(ISession session, String data[]) {
		ReqEquipPunching.Builder req = ReqEquipPunching.newBuilder();
		req.setEquipId(Long.valueOf(data[1]));
		equipController.reqEquipPunching(session, req.build());
	}
	
}
