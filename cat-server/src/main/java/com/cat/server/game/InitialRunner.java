package com.cat.server.game;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.api.request.ReqIdentityAuthenticate;
import com.cat.net.common.NetConfig;
import com.cat.net.network.client.RpcClientStarter;
import com.cat.server.common.ServerConfig;
import com.cat.server.common.ServerConstant;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.data.config.local.ConfigActivityScheduleTime;
import com.cat.server.game.module.activity.IActivityService;
import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.type.ActivityTypeEnum;
import com.cat.server.game.module.activity.type.IActivityType;
import com.cat.server.game.module.player.rpc.ReqIdentityAuthenticateCallback;
import com.rpc.common.RpcConfig;
import com.rpc.core.client.RequesterManager;
import com.zaxxer.hikari.HikariDataSource;

//FSC
@Component
public class InitialRunner implements ILifecycle{
	
	private static final Logger log = LoggerFactory.getLogger(InitialRunner.class);
	
	@Autowired private RpcConfig rpcConfig;
	@Autowired private NetConfig netConfig;
	@Autowired private ServerConfig config;
	@Autowired private IActivityService activityService;
	
	@Autowired 
	private RequesterManager requesterManager;
	
//	@Autowired 
//	private RankManager rankManager;
	
	@Autowired 
	private ConfigManager configManager;
	
//	@Autowired
//	private com.coral.api.service.ITestService testService;
	
	public InitialRunner() {
	}
	
	
//	@Autowired private TestConService conService;
//	@Autowired private GameEventBus eventBus;
	
//	@Autowired 
//	private SnowflakeGenerator generater;
	
//	@Autowired 
//	private IResourceGroupService resourceService;
	
//	private Processor processor;
//	
////	@Autowired private DataSource ds;
//	
////	@Autowired private UserDao userDao;
	
//	@Autowired private DataProcessorAsyn processor;
	
//	@Autowired private TestConService service; 
	
	public void run() throws Exception {
		try {
//			TestConService testService = SpringContextHolder.getBean(TestConService.class);
//			if (testService != null) {
//				testService.print();
//			}
//			ITestService testService1 = SpringContextHolder.getBean(ITestService.class);
//			if (testService1 != null) {
//				System.out.println(testService1.test(new Tester("Lora")));
//			}
//			conService.print();
//			configManager.onInitialize();
//			rank();
//			System.out.println(testService1.test(new Tester("Harry")));
//			service.print();
//			log.info("generater:{}", generater.nextId());
//			Map<Integer, Integer> rewardMap = Maps.newHashMap(); 
//			rewardMap.put(90001, 1);
//			resourceService.reward(1, rewardMap, NatureEnum.GM);
			
////			dataSourceFactory.druidDataSource();
////			System.out.println(dataSourceFactory.druidDataSource());
////			System.out.println();
////			processor.print();
////			testInsertBatchDiff();
////			processor.select(User.class);
//				testInsert();
////			testInsertBatch();
////			testSelect();
////			System.out.println(userDao.getById(5));
			ConfigActivityScheduleTime config = configManager.getConfig(ConfigActivityScheduleTime.class, 150101);
//			System.out.println("======11=======>"+config.getTime().getUniqueTime()+", "+config.getStartTime());
			
			Collection<Activity> activitys = activityService.getAllActivitys();
			System.out.println(activitys.size());
//			IActivityType activityType = activityService.getActivityType(ActivityTypeEnum.LEARN_COMMUNITY.getValue());
//			System.out.println(activityType);
			this.consoleListener();
			log.info(getSystemInfo());
		} catch (Exception e) {
			log.error("服务器初始化过程出现异常, 启动失败", e);
		}
	}
	
//	public void rank() {
//		RankDomain domain = rankManager.getDomain(1);
//		try {
//			int totalFooCount = 1000000; 
//			long startTime = System.currentTimeMillis();
//			for (int i = totalFooCount; i > 0 ; i--) {
//				Rank rank = new Rank(1, 1, i, i);
//				domain.put(rank.getUniqueId(), rank);
//			}
//			long cost = (System.currentTimeMillis()-startTime);
//			long avg = totalFooCount / cost;
//			log.info("totalCount:{}, cost time:{}[ms], avg:{}[/ms], domain:{}", totalFooCount, cost, avg, domain);
//			domain.onSave();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	
//	public void testInsert() {
//		for (int i = 1; i < 1000; i++) {
//			User user = new User();
//			user.setId(i);
//			user.setName("ccc");
//			user.setAge(25);
//			user.setBirthday(new Date().toString());
//			try {
//				processor.insert(user);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		try {//10后,新数据
//			System.out.println("开始进入等待中....");
//			Thread.sleep(50000);
//			System.out.println("等待结束中....");
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} 
//		for (int i = 2001; i < 3000; i++) {
//			User user = new User();
//			user.setId(i);
//			user.setName("ccc");
//			user.setAge(25);
//			user.setBirthday(new Date().toString());
//			try {
//				processor.insert(user);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	public void testInsertBatch() {
//		List<BasePo> list = Lists.newArrayList();
//		for (int i = 0; i < 1000; i++) {
//			User user = new User();
//			user.setId(i);
//			user.setName("ccc");
//			user.setAge(25);
//			user.setBirthday(new Date().toString());
//			list.add(user);
//		}
//		try {
//			processor.insertBatch(list);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	//测试批量插入
//	public void testInsertBatchDiff() {
//		List<BasePo> list = Lists.newArrayList();
//		
//		Stu stu = new Stu();
//		stu.setId(1);
//		stu.setName("ccc");
//		stu.setAge(25);
//		stu.setBirthday(new Date().toString());
//		list.add(stu);
//		
//		User user = new User();
//		user.setId(2);
//		user.setName("ccc");
//		user.setAge(25);
//		user.setBirthday(new Date().toString());
//		list.add(user);
//		
//		processor.insertBatch(list);
//	}
//	
////	public void testSelect() {
//////		User user = new User();
//////		user.setId(1);
//////		user.setName("aaa");
//////		user.setAge(25);
//////		user.setBirthday(new Date().toString());
//////		User user = processor.select(User.class, 4);
//////		System.out.println(user);
////		Dao<BasePo> dao = processor.getDao(User.class.getSimpleName().toLowerCase());
////		for (int i = 0; i < 1000; i++) {
////			try {
////				System.out.println(dao.select(ds, i));
//////				User user = processor.select(User.class, i);
//////				System.out.println(user);
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
////		}
////	}
	
	public String getSystemInfo() {
		Mbeans.obtain();
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = group;
		// 遍历线程组树，获取根线程组
		while (group != null) {
			topGroup = group;
			group = group.getParent();
		}
		Runtime runtime = Runtime.getRuntime();
		long usedMemory = runtime.totalMemory() - runtime.freeMemory();
		StringBuilder builder = new StringBuilder();
		builder.append("\n\n").append("====================================================").append("\n");
		builder.append("虚拟机可用处理器:").append(runtime.availableProcessors()).append("\n");
		builder.append("初始活跃线程组总数:").append(topGroup.activeGroupCount()).append("\n");
		builder.append("初始活跃线程总数:").append(topGroup.activeCount()).append("\n");
		builder.append("虚拟机可用最大内存:").append(runtime.maxMemory() / 1024 / 1024).append("M").append("\n");
		builder.append("虚拟机占用总内存:").append(runtime.totalMemory() / 1024 / 1024).append("M").append("\n");
		builder.append("虚拟机空闲内存:").append(runtime.freeMemory() / 1024 / 1024).append("M").append("\n");
		builder.append("当前使用内存:").append(usedMemory / 1024 / 1024).append("M").append("\n");
		builder.append("ObjectPendingFinalizationCount:").append(Mbeans.currObtain.getFinallzationCount).append("\n");
		builder.append("heapMemoryUsage:").append(Mbeans.currObtain.heapMemoryUsage).append("\n");
		builder.append("nonHeapMemoryUsage:").append(Mbeans.currObtain.nonHeapMemoryUsage).append("\n");
		
		builder.append("----------------------------------------------------").append("\n");
		
		HikariDataSource dataSource = SpringContextHolder.getBean(HikariDataSource.class);
		//LettuceConnectionFactory lettuceFactory = SpringContextHolder.getBean(LettuceConnectionFactory.class);
		//RegistryConfig registryConfig = SpringContextHolder.getBean(RegistryConfig.class);
		builder.append("开服时间:").append(config.getOpenDate()).append("\n");
		builder.append("当前服务器id:").append(config.getServerId()).append("\n");
		builder.append("TCP连接地址:").append(netConfig.getServerIp()).append(":").append(netConfig.getTcpPort()).append("\n");
		builder.append("WEBSOCKET连接地址:").append(netConfig.getServerIp()).append(":").append(netConfig.getWebscoketPort()).append("\n");
		builder.append("HTTP连接地址:").append(netConfig.getServerIp()).append(":").append(netConfig.getHttpPort()).append("\n");
//		if (lettuceFactory != null) {
//			builder.append("redis连接地址:").append(lettuceFactory.getHostName()).append(":").append(lettuceFactory.getPort()).append("\n");
//		}
//		if (registryConfig != null) {
//			builder.append("Dubbo注册中心:").append(registryConfig.getAddress()).append(":").append(registryConfig.getPort()).append("\n");
//		}
		builder.append("数据库连接地址:").append(dataSource.getJdbcUrl()).append("\n");
		builder.append("====================================================").append("\n\n");
		return builder.toString();
	} 
	
	public static class Mbeans {
		public static Mbeans lastObtain = null;
		public static Mbeans currObtain = null;

		public long obtainTime = 0;
		public long getFinallzationCount;
		public String heapMemoryUsage;
		public String nonHeapMemoryUsage;

		public static Mbeans obtain() {
			lastObtain = currObtain;
			currObtain = new Mbeans();
			if (lastObtain == null)
				lastObtain = currObtain;

			currObtain.obtainTime = System.currentTimeMillis();

			MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
			currObtain.getFinallzationCount = memoryMXBean.getObjectPendingFinalizationCount();
			currObtain.heapMemoryUsage = memoryMXBean.getHeapMemoryUsage().toString();
			currObtain.nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage().toString();

			return currObtain;
		}
	}
	
	public void consoleListener() {
		if (!System.getProperty("os.name").toLowerCase(Locale.US).contains("windows")) {
            // 只监听 windows 系统
            return;
        }
		Thread consoleThread = new Thread(() -> {
            try (Scanner sc = new Scanner(System.in)) {
            	while (true) {
            		 String cmd = sc.next();
                     if(cmd.equals("close")) {
                     	break;
                     }else if(cmd.equals("rpc")) {
                    	 try {
                    		//rpc请求
                 			RpcClientStarter client = requesterManager.getClient(ServerConstant.NODE_TYPE_LOGIN);
                 			if(client == null) {
                 				log.info("没有找到合适的节点, 节点类型{}", ServerConstant.NODE_TYPE_LOGIN);
                 				continue;
                 			}
                 			ReqIdentityAuthenticate req = ReqIdentityAuthenticate.create(rpcConfig.getServerId(), rpcConfig.getNodeType());
                 			log.info("目标节点类型: {}, 发送验证参数:[{}|{}], 客户端:{}", ServerConstant.NODE_TYPE_LOGIN, rpcConfig.getServerId(), rpcConfig.getNodeType(), client);
                 			client.ask(req, 300L, new ReqIdentityAuthenticateCallback());
     					} catch (Exception e) {
     						e.printStackTrace();
     					}
                     }
                     try {
						Thread.sleep(100L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                }
            }finally {
            	System.exit(0);
            }
        });
        consoleThread.setName("os_console_thread");
        consoleThread.setDaemon(true);
        consoleThread.start();
	}
	
	
	@Override
	public void start() throws Throwable {
		this.run();
	}
	
	public int priority() {
		return Priority.LOWEST.getPriority();
	}
	
}
