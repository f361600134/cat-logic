package com.cat.robot.module.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.network.annotation.Cmd;
import com.cat.net.network.base.ISession;
import com.cat.net.network.controller.IController;
import com.cat.robot.module.chat.proto.ReqChat;
import com.cat.robot.module.robot.RobotManager;
import com.cat.server.game.data.proto.PBDefine;
import com.cat.server.game.data.proto.PBLogin;

@Controller
public class LoginController implements IController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired private RobotManager robotManager;
	/**
	 * 响应登录, 开始获取随机名
	 * 
	 * @param robotContext
	 * @param ack
	 * @return void
	 * @date 2019年7月4日上午11:45:13
	 */
	@Cmd(PBDefine.PBProtocol.RespPlayerLogin_VALUE)
	public void ackLogin(ISession gameSession, PBLogin.AckLogin ack) {
		int code = ack.getCode();
		int staus = ack.getStatus();
		logger.info("ackLogin code:{}, staus:{}", code, staus);
		// 0 表示登陆成功
		if (code == 0) {
			if (staus == 0) {
				// 不创建主公, 进入游戏
			} else {
				// 取随机名
//				PBLogin.ReqRandName.Builder newBuilder = PBLogin.ReqRandName.newBuilder();
//				context.send(PBDefine.PBProtocol.ReqRandName_VALUE, newBuilder.build());
			}
		}
		robotManager.getRobotMap().forEach((key, robot)->{
			ReqChat req = ReqChat.create();
			req.setChannel(0);
			//req.setContent("@mail sendMail,1");
			req.setContent("@resource 600001,1,600002,1");
			req.setRecvId(-1);
			robot.send(req);
		});
		
	}

	/**
	 * 返回名字列表, 创建角色
	 * 
	 * @param robotContext
	 * @param ack
	 * @return void
	 * @date 2019年7月4日上午11:45:02
	 */
	@Cmd(PBDefine.PBProtocol.RespPlayerRandName_VALUE)
	public void ackLogin(ISession gameSession, PBLogin.AckRandName ack) {
//		String names = ack.getNames();
//		if (!names.isEmpty()) {
//			int cmd = PBDefine.PBProtocol.ReqCreateHost_VALUE;
//			PBLogin.ReqCreateHost.Builder newBuilder = PBLogin.ReqCreateHost.newBuilder();
//			newBuilder.setNickName(names.get(0));//取第一个
//			int roleType = (int)(Math.random()*100%2)+108;
//			newBuilder.setRoleType(roleType);
//			newBuilder.setNickName(robotContext.getName());//取第一个
//			robotContext.send(cmd, newBuilder.build());
//		}
	}
//
//	/**
//	 * 创建角色返回
//	 * @param robotContext
//	 * @param res
//	 * @return void  
//	 * @date 2019年4月15日下午1:37:09
//	 */
//	@Cmd(id = PBDefine.PBProtocol.AckCreateHost_VALUE)
//	public void ackCreateHost(RobotContext robotContext, PBLogin.AckCreateHost res) {
//		if (res.getCode() == 0) {
//			//请求阵营
//			PBLogin.ReqJoinCamp.Builder msg = PBLogin.ReqJoinCamp.newBuilder();
//			msg.setCamp(0);
//			robotContext.send(PBDefine.PBProtocol.ReqJoinCamp_VALUE, msg.build());
//		}else {
//			logger.info("CreateHost error, code: ", res.getCode());
//		}
//	}

}
