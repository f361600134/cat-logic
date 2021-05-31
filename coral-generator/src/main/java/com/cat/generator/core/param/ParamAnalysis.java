package com.cat.generator.core.param;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.generator.core.param.annotation.ParamMapping;
import com.cat.generator.util.ClassPathScanner;


/**
 * 1. 解析文件,配置和类绑定， 并且解析城支持LIST， MAP数据。便于赋值 <br>
 * 2. 循环类文件里面的成员变量，获取类型， 根据类型赋值。
 * 
 * @auth Jeremy
 * @date 2019年2月16日上午12:19:03
 */
public class ParamAnalysis {

	public static final Logger logger = LoggerFactory.getLogger(ParamAnalysis.class);
	
	/**
	 * 0. 解析配置文件<br>
	 * 1. 找出所有Params注解的Javabean <br>
	 * 2. 把注解内容添加到JavaBean中
	 *
	 * @return void
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @date 2019年2月12日下午2:43:14
	 */
	public static void analysis() throws Exception {
		long startTime = System.currentTimeMillis();
//		Set<Class<?>> classes = PackageBase.getAllClassByPackage("com.game.robot");
		ClassPathScanner scan = new ClassPathScanner(false, true, null);
		Set<Class<?>> classes = scan.getPackageAllClasses("com.cat.generator.core.common", true);
		for (Class<?> clazz : classes) {
			ParamMapping params = clazz.getAnnotation(ParamMapping.class);
			if (params == null) {
				continue;
			}
			String prefix = params.prefix();
			//加载配置
			PropertiesBase base = PropertiesBase.instance(params.filePath());
			//解析配置
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isStatic(field.getModifiers())) {
					ParamEnum.getEnum(field.getType()).doInject(prefix, base, field);
				}
			}
		}
		logger.info("inject properties successful, cost time:{}ms", (System.currentTimeMillis() - startTime));
	}

}
