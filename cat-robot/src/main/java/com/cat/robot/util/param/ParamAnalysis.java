package com.cat.robot.util.param;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cat.robot.util.ClassPathScanner;

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
	public static void analysis(String path) throws Exception {
		ClassPathScanner scan = new ClassPathScanner(false, true, null);
		Set<Class<?>> classes = scan.getPackageAllClasses(path, true);
		for (Class<?> clazz : classes) {
			ParamMapping params = clazz.getAnnotation(ParamMapping.class);
			if (params == null) {
				continue;
			}
			//加载配置
			PropertiesBase base = PropertiesBase.instance(params.value());
			
			//解析配置
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isStatic(field.getModifiers())) {
					if (isPrimitive(field.getType())) {
						// 基础参数
						TreeMap<String, ParamField> paramFieldMap = base.getValue(field.getName());
						// 基础类型和String类型
						setValue(field, paramFieldMap.firstEntry().getValue().getValue());
					} else if (Collection.class.isAssignableFrom(field.getType())) {
						// 获取list的泛型的类型
						ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
						Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
						Type actualType = actualTypeArguments[actualTypeArguments.length - 1];
						// 获取构造函数
						Constructor<?>[] constructors = field.getType().getConstructors();
						// 构造函数数量为0, 表示不是对象, 是接口或者抽象类
						Object obj = null;
						if (constructors.length != 0) {
							obj = field.getType().newInstance();
						} else {
							// 实例化一个list
							obj = new ArrayList<Object>();
						}
						TreeMap<String, ParamField> paramFieldMap = base.getValue(field.getName());
						Method method = obj.getClass().getMethod("add", Object.class);
						// 这里的clazz区分基本类型和对象类型
						Class<?> valueClazz = Class.forName(actualType.getTypeName());
						logger.info("analysis, valueClazz:{}", valueClazz);
						// 如果是基础类型,则直接调用add方法添加数据
						for (ParamField paramField : paramFieldMap.values()) {
							method.invoke(obj, convert(paramField.getValue(isPrimitive(valueClazz)), valueClazz));
						}
						field.set(null, obj);
					} else if (Map.class.isAssignableFrom(field.getType())) { //
						ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
						Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
						Type keyActualType = actualTypeArguments[0];
						Type valueActualType = actualTypeArguments[1];
						// 获取构造函数
						Constructor<?>[] constructors = field.getType().getConstructors();
						// 构造函数数量为0, 表示不是对象, 是接口或者抽象类
						Object obj = null;
						if (constructors.length != 0) {
							obj = field.getType().newInstance();
						} else {
							obj = new HashMap<Object, Object>();
						}
						// 实例化一个list
						TreeMap<String, ParamField> paramFieldMap = base.getValue(field.getName());
						Class<?> keyClazz = Class.forName(keyActualType.getTypeName());
						Class<?> valueClazz = Class.forName(valueActualType.getTypeName());
						Method method = obj.getClass().getMethod("put", Object.class, Object.class);
						for (ParamField paramField : paramFieldMap.values()) {
							method.invoke(obj, convert(paramField.getKeyIndex(), keyClazz), convert(paramField.getValue(isPrimitive(valueClazz)), valueClazz));
						}
						field.set(null, obj);
					}
				}
			}
		}
	}

	public static Object convert(Object obj, Class<?> clazz) {
		if (isPrimitive(clazz)) {
			return ConvertUtils.convert(obj, clazz);
		} else {
			return JSON.parseObject(JSON.toJSONString(obj), clazz);
		}
	}

//	/**
//	 * 0. 解析配置文件<br>
//	 * 1. 找出所有Params注解的Javabean <br>
//	 * 2. 把注解内容添加到JavaBean中
//	 * 
//	 * @return void
//	 * @throws IllegalAccessException
//	 * @throws IllegalArgumentException
//	 * @date 2019年2月12日下午2:43:14
//	 */
//	public static void analysis1() throws Exception {
//		Set<Class<?>> classes = PackageBase.getAllClassByPackage("com.fatiny.combine.business");
//		for (Class<?> clazz : classes) {
//			ParamMapping params = clazz.getAnnotation(ParamMapping.class);
//			if (params == null) {
//				continue;
//			}
//			convert(clazz);
//		}
//	}

	/**
	 * 基础属性直接数据赋值
	 * 
	 * @param field
	 * @param value
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @date 2019年2月13日下午6:22:16
	 */
	public static boolean setValue(Field field, Object value) throws Exception {
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		Class<?> clz = field.getType();
		field.setAccessible(true);
		modifiersField.setAccessible(true);
		modifiersField.set(field, field.getModifiers() & ~Modifier.FINAL);
		if (clz == Boolean.TYPE || clz == Boolean.class) {
			field.set(null, Boolean.valueOf(value.toString()));
		} else if (clz == Integer.TYPE || clz == Integer.class) {
			field.set(null, Integer.valueOf(value.toString()));
		} else if (clz == Long.TYPE || clz == Long.class) {
			field.set(null, Long.valueOf(value.toString()));
		} else if (clz == Byte.TYPE || clz == Byte.class) {
			field.set(null, Byte.valueOf(value.toString()));
		} else if (clz == Double.TYPE || clz == Double.class) {
			field.set(null, Double.valueOf(value.toString()));
		} else if (clz == Short.TYPE || clz == Short.class) {
			field.set(null, Short.valueOf(value.toString()));
		} else if (clz == Float.TYPE || clz == Float.class) {
			field.set(null, Float.valueOf(value.toString()));
		} else if (clz == String.class) {
			field.set(null, value);
		} else {
			logger.info("不支持的类型:{}", clz);
		}
		return false;
	}

	/**
	 * 判断是否为基础类型以及基础类型的封装类型
	 *
	 * @see java.lang.Boolean#TYPE
	 * @see java.lang.Character#TYPE
	 * @see java.lang.Byte#TYPE
	 * @see java.lang.Short#TYPE
	 * @see java.lang.Integer#TYPE
	 * @see java.lang.Long#TYPE
	 * @see java.lang.Float#TYPE
	 * @see java.lang.Double#TYPE
	 */
	public static boolean isPrimitive(Class<?> clz) {
		if ((clz == Boolean.TYPE || clz == Boolean.class)//
				|| (clz == Character.TYPE || clz == Character.class)//
				|| (clz == Integer.TYPE || clz == Integer.class)//
				|| (clz == Long.TYPE || clz == Long.class)//
				|| (clz == Byte.TYPE || clz == Byte.class)//
				|| (clz == Double.TYPE || clz == Double.class)//
				|| (clz == Short.TYPE || clz == Short.class) //
				|| (clz == Float.TYPE || clz == Float.class)//
				|| clz == String.class) {
			return true;
		}
		return false;
	}

//	/**
//	 * 配置文件反射注入到javabean
//	 * 
//	 * @param clazz
//	 * @param map
//	 * @return
//	 * @throws Exception
//	 * @return Object
//	 * @date 2019年1月9日上午10:23:03
//	 */
//	@SuppressWarnings("unchecked")
//	public static <T> T convert(Class<T> clazz) throws Exception {
//		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
//		Object obj = clazz.newInstance();
//		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//		for (PropertyDescriptor descriptor : propertyDescriptors) {
//			String propertyName = descriptor.getName();
//			Class<?> clazzType = descriptor.getPropertyType();
//
//			if (PropertiesBase.containsKey(propertyName)) {
//				TreeMap<String, ParamField> paramFieldMap = PropertiesBase.getValue(propertyName);
//				// 基础类型和String类型
//				if (isPrimitive(clazzType)) {
//					logger.info("====isPrimitive====");
//					ParamField paramField = paramFieldMap.firstEntry().getValue();
//					if (paramField != null) {
//						descriptor.getWriteMethod().invoke(obj, ConvertUtils.convert(paramField.getValue(), clazzType));
//					}
//				} else if (Collection.class.isAssignableFrom(clazzType)) {
//					logger.info("====Collection====");
//					// 获取list的泛型的类型
//					ParameterizedType parameterizedType = (ParameterizedType) descriptor.getReadMethod().getGenericReturnType();
//					Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//					Type actualType = actualTypeArguments[actualTypeArguments.length - 1];
//					// 实例化一个list
//					Object list = null;
//					if (clazzType.getConstructors().length == 0) {
//						list = new ArrayList<Object>();
//					} else {
//						list = clazzType.newInstance();
//					}
//					// 如果是基础类型，则没问题。如果是对象类型，实例化对象，然后赋值
//					Class<?> valueClazz = Class.forName(actualType.getTypeName());
//					Method addMethod = clazzType.getDeclaredMethod("add", Object.class);
//					if (addMethod != null) {
//						for (ParamField paramField : paramFieldMap.values()) {
//							// 值设置进去list
//							addMethod.invoke(list, ConvertUtils.convert(paramField.getValue(), valueClazz));
//						}
//						descriptor.getWriteMethod().invoke(obj, list);
//					}
//				} else if (Map.class.isAssignableFrom(clazzType)) {
//					logger.info("====Map====");
//					// 获取list的泛型的类型
//					ParameterizedType parameterizedType = (ParameterizedType) descriptor.getReadMethod().getGenericReturnType();
//					Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//					Type keyActualType = actualTypeArguments[0];
//					Type valueActualType = actualTypeArguments[1];
//					// 实例化一个Map
//					Object map = null;
//					if (clazzType.getConstructors().length == 0) {
//						map = new HashMap<Object, Object>();
//					} else {
//						map = clazzType.newInstance();
//					}
//					Class<?> keyClazz = Class.forName(keyActualType.getTypeName());
//					Class<?> valueClazz = Class.forName(valueActualType.getTypeName());
//					Method addMethod = clazzType.getDeclaredMethod("put", Object.class, Object.class);
//					if (addMethod != null) {
//						for (ParamField paramField : paramFieldMap.values()) {
//							// 值设置进去list
//							addMethod.invoke(map, ConvertUtils.convert(paramField.getKeyIndex(), keyClazz), ConvertUtils.convert(paramField.getValue(), valueClazz));
//						}
//						descriptor.getWriteMethod().invoke(obj, map);
//					}
//				} else {
//					logger.info("propertyName:{} 找不到类型, clazzType:{}", propertyName, clazzType);
//				}
//			}
//		}
//		return (T) obj;
//	}

}
