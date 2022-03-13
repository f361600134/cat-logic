package com.cat.server.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
public @interface ModuleController {

	@AliasFor(annotation = Controller.class)
	String value() default "";
	
	/**
	 * 功能id
	 * @return  
	 * @return int  
	 * @date 2022年3月13日下午9:51:15
	 */
	int factionId();

}
