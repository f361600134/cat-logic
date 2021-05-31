package com.cat.generator.core.param.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamMapping {

	// 指定配置文件目录
	String filePath() default "src/main/resource/application.properties";

	// 指定配置文件前缀
	String prefix() default "";

}
