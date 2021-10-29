package com.cat.rank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

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
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        ctx.registerShutdownHook();
        ctx.start();
        logger.info("Start server successful， cost time:{}ms", (System.currentTimeMillis() - startTime));
        try {
            TimeUnit.SECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
