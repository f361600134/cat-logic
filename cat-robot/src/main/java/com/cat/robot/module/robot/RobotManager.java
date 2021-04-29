package com.cat.robot.module.robot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RobotManager {

	private final Logger logger = LoggerFactory.getLogger(RobotManager.class.getName());

//	public static RobotManager instance = new RobotManager();

	/**
	 * key: name value: RobotContext
	 */
	private Map<String, RobotContext> robotMap = new ConcurrentHashMap<>();

//	private ScheduledExecutorService executor;

	public RobotManager() {
//		executor = Executors.newScheduledThreadPool(1);//1条线程,阻塞执行,任务交替运行
	}

//	/**
//	 * 创建机器人上下文对象
//	 */
//	public RobotContext createContext() {
//		Robot robot = Robot.create();
//		RobotContext robotContext = RobotContext.create(robot);
//		robotMap.put(robotContext.getName(), robotContext);
//		return robotContext;
//	}

	/**
	 * 登录服验证
	 */
	public void start() {
//		/*
//		 * 生产机器人线程池,定时检测,如果机器人数量为0,则移除掉机器人
//		 * 定时线程池并发量最大5,5个机器人同时登陆游戏
//		 * 
//		 * 机器人执行任务线程池,100毫秒执行一次, 单线程执行
//		 */
//		RobotProduce robotProduce = new RobotProduce();
//		executor.scheduleWithFixedDelay(robotProduce, 0, Config.loginDelay, TimeUnit.SECONDS);
//
//		RobotAssembly robotAssembly = new RobotAssembly();
//		executor.scheduleWithFixedDelay(robotAssembly, 10 * 1000, Config.scriptDelay, TimeUnit.MICROSECONDS);
//
//		logger.info(Config.getConfigInfo());
	}

	/**
	 * 机器人连接游戏服
	 * 
	 * @return
	 * @return boolean
	 * @date 2019年4月24日下午3:11:12
	 */
	private boolean robotConnectServer() {
//		// 连接游戏服
//		for (RobotContext context : robotMap.values()) {
//			if (context.isSuccess()) {
//				try {
//					// 100ms登录一个机器人
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				// logger.info("登陆账号服成功, 账号服信息:{}",
//				// context.getRobot().getRobotLogin());
//				// 切换服务器
//				context.getRobot().getRobotLogin().setId(Config.serverId);
//				context.getRobot().getRobotLogin().setIp(Config.serverIP);
//				context.getRobot().getRobotLogin().setPort(Config.serverPort);
//				// logger.info("切换服务器, 账号服信息:{}",
//				// context.getRobot().getRobotLogin());
//				boolean bool = context.gameServerConnection();
//				if (!bool) {
//					logger.info("Unable  to connect to game server, robotName:{}", context.getRobot().getRobotLogin());
//					return false;
//				} else {
//					context.serverLogin();// 登陆游戏服
//				}
//			} else {
//				logger.info("Unable  to connect to account server, robotName:{}", context.getRobot().getRobotLogin());
//			}
//		}
		return true;
	}

	/**
	 * 机器人连接登录服
	 * 
	 * @return
	 * @return boolean
	 * @date 2019年4月24日下午3:11:12
	 */
	private void robotConnectLogin() {
		// 账号服登录
		for (RobotContext context : robotMap.values()) {
			context.login();
		}
		// logger.info("预备的机器人共有:{}", robotMap.size());
	}

//	/**
//	 * 移除已经执行完脚本, 并且完成任务的机器人
//	 * @param completes
//	 * @return void
//	 * @date 2019年4月24日下午3:11:30
//	 * @change 修复actor使用后未关闭, 导致机器actor实例无法创建问题
//	 */
//	public void remove(Set<RobotContext> completes) {
//		for (RobotContext robotContext : completes) {
//			if (robotContext.isFinish()) {
//				AkkaContext.stopTypedActor(robotContext.getRobotAcotr());
//				robotMap.remove(robotContext.getRobot().getName());
//				//AkkaContext.printActors();
//				GameEventBus.instance().post(LeaveGameEvent.create(robotContext));
//				//logger.info("Robot [{}] has completed work, and then disconnect.", robotContext.getRobot().getName());
//			}
//		}
//		//销毁机器人
//		for (RobotContext robotContext : completes) {
//			if (robotContext.isFinish()) {
//				robotContext.destory();
//			}
//		}
//		logger.info("Number of robots that have completed work:{}", completes.size());
//		completes.clear();
//		System.gc();
//	}
	
//	/**
//	 * 机器人生产线程, 数量小于等于0时生成
//	 * TODO 当前是阻塞执行, 改成并发执行
//	 * 
//	 * @auth Jeremy
//	 * @date 2019年6月12日下午4:49:43
//	 */
//	class RobotProduce implements Runnable {
//		@Override
//		public void run() {
//			int size = robotMap.size();
//			if (size <= 0) {
//				final RobotNet net = new RobotNet();
//				RobotContext robotContext = null;
//				for (int i = 0; i < Config.robotGroupCount; i++) {
//					robotContext = createContext();
//					robotContext.setRobotNet(net);
////					try {
////						Thread.sleep(100);
////					} catch (Exception e) {
////						logger.error("e", e);
////					}
//				}
//				logger.info("the last name of robot is {}, the number of robot:{}...", robotContext.getName(), robotMap.size());
//				//顺序执行, 所以能保证机器人登陆成功切换到业务执行线程
//				robotConnectLogin();
//				robotConnectServer();
//			}
//			try {
//				Thread.sleep(1000); // 1秒检测一次
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			//System.gc();
//		}
//	
//	}

//	/**
//	 * 机器人, 按部就班执行脚本
//	 * 
//	 * @auth Jeremy
//	 * @date 2019年6月13日上午12:23:49
//	 */
//	class RobotAssembly implements Runnable {
//		@Override
//		public void run() {
//			Set<RobotContext> errors = new HashSet<>(Config.robotGroupCount/10);
//			Set<RobotContext> completes = new HashSet<>(Config.robotGroupCount);
//			try {
//				for (RobotContext rc : robotMap.values()) {
//					if (!rc.isEntryGame()) {
//						errors.add(rc);
//					}
//					if (!rc.isComplete()) {
//						rc.getRobotAcotr().requestGame(rc);
//					}
//					if (rc.isFinish()){
//						completes.add(rc);
//					}
//				}
//				if (!errors.isEmpty()) {
//					remove(errors);
//					logger.info("Number of robots that could't work:{}", errors.size());
//				}
//				if (!completes.isEmpty()) {
//					remove(completes);
//				}
//			} catch (Exception e) {
//				logger.error(e.getMessage(), e);
//			}
//		}
//	}
	//
}