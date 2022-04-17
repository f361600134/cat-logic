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
 * 退出应用程序
 * @author Jeremy
 */
@Console("pet")
public class ConsolePet implements IConsole{
	
	private static final Logger log = LoggerFactory.getLogger(ConsolePet.class);

	@Autowired private RobotManager robotManager;
	
	/**
	 * pet,reqPetLevelup
	 * pet,reqPetIdentify
	 * pet,reqPetActive
	 */
	@Override
	public void process(String ...content) {
		log.info("====> 收到控制台宠物命令:{}", Arrays.toString(content));
		String head = content[1];
		String context = null;
		if (head.equals("reqPetLevelup")) {
			context = "@pet reqPetLevelup,871493974018232320";
		}
		else if (head.equals("reqPetIdentify")) {
			context = "@pet reqPetIdentify,871493974018232320";
		}
		else if (head.equals("reqPetActive")) {
			context = "@pet reqPetActive,871493974018232320";
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
