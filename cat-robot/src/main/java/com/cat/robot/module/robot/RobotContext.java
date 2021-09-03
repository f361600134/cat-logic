package com.cat.robot.module.robot;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cat.net.network.base.IProtocol;
import com.cat.net.network.base.ISession;
import com.cat.net.network.process.CommanderProtocolMapper;
import com.cat.robot.actor.IRobotActor;
import com.cat.robot.actor.RobotActor;
import com.cat.robot.common.akka.AkkaContext;
import com.cat.robot.common.config.Config;
import com.cat.robot.common.context.SpringContextHolder;
import com.cat.robot.module.login.proto.ReqLogin;
import com.cat.robot.net.RobotNet;
import com.cat.robot.util.HttpClientUtil;
import com.cat.server.game.data.proto.PBPlayer.AckInitPlayerInfo;

public class RobotContext {
	
	private final Logger logger = LoggerFactory.getLogger(RobotContext.class.getName());
	
	private Robot robot;
	private IRobotActor robotAcotr;
	
	private AckInitPlayerInfo robotInfo;
	
	private ISession gameSession;
	private RobotNet robotNet = new RobotNet(this);
	
	private boolean entryGame;
	//控制相关,脚本相关
	/**
	 * 发送命令时间. 如果超过10秒未发送命令, 则表示机器人完成任务
	 */
//	private long sendMsgTime;
//	private int index = 0;
//	private LinkedList<RecordingReadUtil.SavePacket> scriptRecords;
	
	public RobotContext() {
		robotAcotr = AkkaContext.createTypedActor(IRobotActor.class, RobotActor.class);
//		scriptRecords = RobotConfig.get().getScriptRecords();
	}
	

	
	public static RobotContext create(Robot robot) {
		RobotContext context = new RobotContext();
		context.setRobot(robot);
		context.getRobotAcotr().setContext(context);
		CommanderProtocolMapper dispacher =  SpringContextHolder.getBean(CommanderProtocolMapper.class);
		context.getRobotAcotr().registDispatcher(dispacher);
		return context;
	}
	
	public Robot getRobot() {
		return robot;
	}
	public void setRobot(Robot robot) {
		this.robot = robot;
	}
	public IRobotActor getRobotAcotr() {
		return robotAcotr;
	}
	public void setRobotAcotr(IRobotActor robotAcotr) {
		this.robotAcotr = robotAcotr;
	}
	public String getGameServerIp() {
		return robot.getRobotLogin().getIp();
	}
	
	public int getGameServerPort() {
		return robot.getRobotLogin().getPort();
	}
	
	public ISession getGameSession() {
		return gameSession;
	}

	public void setGameSession(ISession gameSession) {
		this.gameSession = gameSession;
	}

	public int getPlayerId() {
		return robot.getPlayerId();
	}
	
	public String getName() {
		return robot.getName();
	}
	
	public AckInitPlayerInfo getRobotInfo() {
		return robotInfo;
	}

	public void setRobotInfo(AckInitPlayerInfo robotInfo) {
		this.robotInfo = robotInfo;
	}

	public boolean isSuccess() {
		if (robot.getRobotLogin() != null) {
			return robot.getRobotLogin().isSuccess();
		}
		return false;
	}

	/**
	 * 账号服验证
//	 * TODO 可以改成异步的回调的方式, 账号服登陆后不需要等待是否登陆成功, 直接进行下一步操作.
	 */
	public void login() {
		List<NameValuePair> params = new ArrayList<NameValuePair>(); 
		params.add(new BasicNameValuePair("channelUId", robot.getName()));
		params.add(new BasicNameValuePair("inputUname", robot.getName()));
		params.add(new BasicNameValuePair("channelUname", robot.getName()));
		params.add(new BasicNameValuePair("pwd", robot.getPwd()));
		params.add(new BasicNameValuePair("os", "pc"));
		params.add(new BasicNameValuePair("ch", String.valueOf(robot.getChannal())));
		try {
			String result = HttpClientUtil.doGet(Config.loginUrl, params);
			RobotLogin robotLogin = JSON.parseObject(result, RobotLogin.class);
			robot.setRobotLogin(robotLogin);
		} catch (Exception e) {
			logger.error("robot login error", e);
		}
	}
	
	public void entryGame() {
		entryGame = true;
	}
	
	public boolean isEntryGame() {
		return entryGame;
	}
	
	/**
	 * 游戏服登陆验证
	 * @param time
	 */
	public void disconnect(){
		if (gameSession != null) {
			gameSession.disConnect();
		}else {
			logger.info("Failed to disconnect, channel is null, robotId:{}", robot.getPlayerId());
		}
	}
	
	/**
	 * 连接游戏服
	 */
	public boolean gameServerConnection(){
		return this.robotNet.connectToServer();
	}
	
	/**
	 * 发送测试消息
	 * @param time
	 */
	public void serverLogin(){
		send(ReqLogin.create("aaa"));
	}
	
	public void send(IProtocol protocol) {
		if (isOnline()) {
			gameSession.push(protocol);
			logger.info("send message:[{}], playerId:{}, size={}B, data:{}", protocol.protocol(), gameSession.getUserData(),
					protocol, protocol.toBytes().length);
		}
	}
	
	public boolean isOnline() {
		return gameSession != null && gameSession.isConnect();
	}
	
//	/**
//	 * 游戏服登陆验证
//	 * @param time
//	 */
//	public void serverLogin(){
//		int cmd = PBDefine.PBProtocol.ReqLogin_VALUE;
//		PBLogin.ReqLogin.Builder platLogin = PBLogin.ReqLogin.newBuilder();
//		platLogin.setUserName(robot.getRobotLogin().getUsername());
//		platLogin.setSessionKey(robot.getRobotLogin().getSessionKey());
//		platLogin.setChannel(robot.getChannal());
//		platLogin.setServerId(robot.getServerId());
//		platLogin.setLoginSid(robot.getRobotLogin().getLoginSid());
//		send(cmd, platLogin.build());
//	}
	
//	/***
//	 * 发公共通用消息到游戏服
//	 * @param cmd
//	 * @param msg
//	 * @return
//	 */
//	public boolean requestGame(){
//		if (index >= scriptRecords.size()) {
//			return false;
//		}
//		SavePacket savePacket = scriptRecords.get(index);
//		send(savePacket.packet.cmd(), savePacket.packet.lite());
//		index += 1;
//		return true;
//	}
	
//	/**
//	 * 销毁机器人
//	 *   
//	 * @return void  
//	 * @date 2019年6月18日下午10:34:08
//	 */
//	public void destory() {
//		disconnect();
////		robotNet = null;
//		robotInfo = null;
//		//scriptRecords = null;
//	}
	
//	/**
//	 * 发送消息
//	 */
//	public boolean send(int cmd, GeneratedMessageLite msg) {
//		if (channel != null) {
//			channel.writeAndFlush(Packet.encode(cmd,msg.toByteArray()));
//			sendMsgTime = System.currentTimeMillis();
//			//logger.info("Send message ID:[{}], account={}, size:{}, params={}",cmd, getName(), msg.getSerializedSize(), MessageOutput.create(msg));
//			return true;
//		}else {
//			logger.info("Failed to send message, channal is null");
//		}
//		return false;
//	}
	
//	/**
//	 * index >= 命令数量, 则表示执行脚本完成
//	 * @return  
//	 * @return boolean  
//	 * @date 2019年6月17日上午11:19:03
//	 */
//	public boolean isComplete(){
//		return index >= scriptRecords.size();
//	}
	
//	/**
//	 * 机器人完成任务
//	 * @return  
//	 * @return boolean  
//	 * @date 2019年6月17日上午11:30:01
//	 */
//	public boolean isFinish() {
//		return isComplete() && System.currentTimeMillis() >= sendMsgTime + (10 * 1000);
//	}
	
}
