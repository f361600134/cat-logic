package com.cat.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cat.server.common.ClassManager;
import com.cat.server.common.ServerConstant;

/**
 * Hello, game world!
 */
public class App 
{
	private static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args)
    {
    	long startTime = System.currentTimeMillis();
    	//扫描配置环境
    	ClassManager.instance().loadClasses(ServerConstant.scanPath);
    	AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("xml/spring-context.xml");
		ctx.registerShutdownHook();
		ctx.start();
    	logger.info("Start server successful， cost time:{}ms", (System.currentTimeMillis() - startTime));
    }
}
