package com.cat.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cat.robot.common.config.ServerConstant;
import com.cat.robot.util.ClassManager;
import com.cat.robot.util.param.ParamAnalysis;

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
    	
    	//解析配置
    	try {
			ParamAnalysis.analysis(ServerConstant.scanPath);
			//扫描配置环境
	    	ClassManager.instance().loadClasses(ServerConstant.scanPath);
	    	
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("xml/spring-context.xml");
		ctx.registerShutdownHook();
		ctx.start();
    	logger.info("Start server successful, cost time:{}ms", (System.currentTimeMillis() - startTime));
    }
}
