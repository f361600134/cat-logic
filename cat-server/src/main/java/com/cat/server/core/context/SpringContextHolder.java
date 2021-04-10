package com.cat.server.core.context;

import java.lang.annotation.Annotation;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring上下文获取, 
 * @author Administrator
 *
 */
@Component
public class SpringContextHolder implements ApplicationContextAware{
	
	private static final Logger log = LoggerFactory.getLogger(SpringContextHolder.class);

	private static ApplicationContext springContext;
	
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		log.info("注册[ApplicationContext]服务");
		SpringContextHolder.springContext = context;
	}
		
	public ApplicationContext getSpringContext() {
		return springContext;
	}
	
	public static <T> T getBean(Class<T> tClass) {
		return springContext.getBean(tClass);
	}
	
	/**
	 * 根据注解类型获取实体集合
	 * 
	 * @param classType
	 * @return
	 */
	public Collection<Object> getBeansWithAnnotation(Class<? extends Annotation> classType) {
		return springContext.getBeansWithAnnotation(classType).values();
	}
}
