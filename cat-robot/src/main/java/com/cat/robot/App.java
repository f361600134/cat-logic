package com.cat.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cat.net.network.controller.DefaultServerController;
import com.cat.net.network.tcp.TcpClientStarter;
import com.cat.robot.common.config.ServerConstant;
import com.cat.robot.util.ClassManager;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger logger = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	long startTime = System.currentTimeMillis();
    	
		//扫描配置环境
//    	ClassManager.instance().loadClasses(ServerConstant.scanPath);
//    	AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("xml/spring-context.xml");
//		ctx.registerShutdownHook();
//		ctx.start();
    	
    	TcpClientStarter starter = new TcpClientStarter("127.0.0.1", 5001, new DefaultServerController());
    	try {
			starter.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	logger.info("Start server successful, cost time:{}ms", (System.currentTimeMillis() - startTime));
    }
}
