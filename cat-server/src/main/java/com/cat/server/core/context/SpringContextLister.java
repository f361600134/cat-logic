package com.cat.server.core.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Service;

import com.cat.server.core.lifecycle.LifecycleManager;


@Service
public class SpringContextLister implements ApplicationListener<ApplicationEvent>{

	@Autowired 
	private LifecycleManager lifecycleManager;
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextStartedEvent) {
			lifecycleManager.start();
		}else if (event instanceof ContextRefreshedEvent) {
		}else if(event instanceof ContextClosedEvent){
			lifecycleManager.stop();
		}
	}

}
