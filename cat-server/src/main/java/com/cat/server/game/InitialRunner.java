package com.cat.server.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cat.api.dto.Tester;
import com.cat.api.service.ITestService;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.module.user.service.TestConService;

//FSC
@Component
public class InitialRunner  {
	
	private static final Logger log = LoggerFactory.getLogger(InitialRunner.class);
	
//	@Autowired 
//	private RankManager rankManager;
	
//	@Autowired 
//	private ConfigManager configManager;
	
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
			
			TestConService testService = SpringContextHolder.getBean(TestConService.class);
			if (testService != null) {
				testService.print();
			}
			ITestService testService1 = SpringContextHolder.getBean(ITestService.class);
			if (testService1 != null) {
				System.out.println(testService1.test(new Tester("Lora")));
			}
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
//	
	
}
