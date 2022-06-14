package com.cat.robot.module.console.type.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.robot.module.chat.proto.ReqChat;
import com.cat.robot.module.console.annotation.Console;
import com.cat.robot.module.console.type.IConsole;
import com.cat.robot.module.robot.RobotManager;

/**
 * @author Jeremy
 */
@Console("equip")
public class ConsoleEquip implements IConsole{
	
	private static final Logger log = LoggerFactory.getLogger(ConsoleEquip.class);

	@Autowired private RobotManager robotManager;
	
	/**
	 * equip,addEquip
	 * equip,reqEquipUpgrade,913872464830795776
	 * equip,reqEquipPunching,913872464830795776
	 */
	@Override
	public void process(String ...content) {
		log.info("====> 收到控制台装备命令:{}", Arrays.toString(content));
		String head = content[1];
		String context = null;
		if (head.equals("addEquip")) {
			context = "@resource 310001,1";
		}
		else if (head.equals("reqEquipUpgrade")) {
			context = "@equip reqEquipUpgrade,913872464830795776";
		}
		else if (head.equals("reqEquipPunching")) {
			context = "@equip reqEquipPunching,913872464830795776";
		}
		
		ReqChat req = ReqChat.create();
		req.setChannel(0);
		req.setContent(context);
		req.setRecvId(-1);
		robotManager.getRobotMap().forEach((key, robot)->{
			robot.send(req);
		});
	}
	
}
