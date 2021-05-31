package com.cat.generator.core.param;

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

import org.apache.commons.beanutils.ConvertUtils;

import com.cat.generator.core.param.annotation.Value;
import com.cat.generator.util.StringUtils;
import com.google.common.collect.Multimap;

public enum ParamEnum {
	
	Primitive(new Class<?>[] {Boolean.TYPE, Boolean.class, 
		Integer.TYPE, Integer.class, Long.TYPE, Long.class, Byte.TYPE, Byte.class,
		Double.TYPE, Double.class, Short.TYPE, Short.class, Float.TYPE, Float.class,
		String.class }){//基础类型
		
		@Override
		public boolean isAssignableFrom(Class<?> cla) {
			Class<?>[] classes = getClasses();
			for (Class<?> clazz : classes) {
				if (clazz == cla) {
					return true;
				}
			}
			return false;
		}
		
		public void doInject(String prefix, PropertiesBase base, Field field) {
			Value v = field.getAnnotation(Value.class);
			String prefixName = v == null ? (prefix):(prefix + "." + v.name());
			// 基础参数
			Multimap<String, ParamField> paramFieldMap = base.getValue(prefixName);
			// 基础类型和String类型
			Collection<ParamField> colls = paramFieldMap.get(field.getName());
			for (ParamField param: colls) {
				try {
					setValue(field, param.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		
		/**
		 * 基础属性直接数据赋值
		 * @param field
		 * @param value
		 * @return
		 * @throws Exception
		 * @return boolean
		 * @date 2019年2月13日下午6:22:16
		 */
		private boolean setValue(Field field, Object value) throws Exception {
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
				//logger.info("不支持的类型:{}", clz);
			}
			return false;
		}
	},
	Map(new Class<?>[] {Map.class}){ //Map类型
		@Override
		public boolean isAssignableFrom(Class<?> cla) {
			Class<?>[] classes = getClasses();
			for (Class<?> clazz : classes) {
				return clazz.isAssignableFrom(cla);
			}
			return false;
		}

		@Override
		public void doInject(String prefix, PropertiesBase base, Field field) throws Exception {
			String prefixName = prefix + "." + field.getName();
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
			Multimap<String, ParamField> paramFieldMap = base.getValue(prefixName);
			Class<?> keyClazz = Class.forName(keyActualType.getTypeName());
			Class<?> valueClazz = Class.forName(valueActualType.getTypeName());
			Method method = obj.getClass().getMethod("put", Object.class, Object.class);
			//value 为简单对象
			if (Primitive.isAssignableFrom(valueClazz)) {
				for (ParamField paramField : paramFieldMap.values()) {
					method.invoke(obj, ConvertUtils.convert(paramField.getKeyIndex(), keyClazz), ConvertUtils.convert(paramField.getValue(), valueClazz));
				}
			}else {
				for (String key: paramFieldMap.keySet()) {
					Collection<ParamField> colls = paramFieldMap.get(key);
					Object obj_ = valueClazz.newInstance();
					for (ParamField paramField : colls) {
						//只处理value
						Constructor<?>[] constructors_ = valueClazz.getConstructors();
						// 构造函数数量为0, 表示不是对象, 是接口或者抽象类
						if (constructors_.length == 0) {
							return;
						}
						//获取类型
						String methodGetName = "get" + StringUtils.firstCharUpper(paramField.getMainWord());
						Class<?> valueClazz_ = null;
						try {
							Method methodGet = obj_.getClass().getMethod(methodGetName);
							valueClazz_ = methodGet.getReturnType();
						} catch (Exception e) {
							methodGetName = "is" + StringUtils.firstCharUpper(paramField.getMainWord());
							Method methodGet = obj_.getClass().getMethod(methodGetName);
							valueClazz_ = methodGet.getReturnType();
						}
						//设置值
						String methodSetName = "set" + StringUtils.firstCharUpper(paramField.getMainWord());
						Method methodSet = obj_.getClass().getMethod(methodSetName, valueClazz_);
						methodSet.invoke(obj_, ConvertUtils.convert(paramField.getValue(), valueClazz_));
					}
					method.invoke(obj, ConvertUtils.convert(key, keyClazz), obj_);
				}
			}
			field.set(null, obj);
		}
	},
	Collection(new Class<?>[] {Collection.class}){ //Collection类型
		@Override
		public boolean isAssignableFrom(Class<?> cla) {
			Class<?>[] classes = getClasses();
			for (Class<?> clazz : classes) {
				return clazz.isAssignableFrom(cla);
			}
			return false;
		}
		
		@Override
		public void doInject(String prefix, PropertiesBase base, Field field) throws Exception {
			String prefixName = prefix + "." + field.getName();
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
			Multimap<String, ParamField> paramFieldMap = base.getValue(prefixName);
			Method method = obj.getClass().getMethod("add", Object.class);
			// 这里的clazz区分基本类型和对象类型
			Class<?> valueClazz = Class.forName(actualType.getTypeName());
			if (Primitive.isAssignableFrom(valueClazz)) {
				// 如果是基础类型,则直接调用add方法添加数据
				for (ParamField paramField : paramFieldMap.values()) {
					method.invoke(obj, ConvertUtils.convert(paramField.getValue(), valueClazz));
				}
			}else {
				// 如果是基础类型,则直接调用add方法添加数据
				for (String key: paramFieldMap.keySet()) {
					Collection<ParamField> colls = paramFieldMap.get(key);
					Object obj_ = valueClazz.newInstance();
					for (ParamField paramField : colls) {
						Constructor<?>[] constructors_ = valueClazz.getConstructors();
						// 构造函数数量为0, 表示不是对象, 是接口或者抽象类
						if (constructors_.length == 0) {
							return;
						}
						//获取类型
						String methodGetName = "get" + StringUtils.firstCharUpper(paramField.getMainWord());
						Method methodGet = obj_.getClass().getMethod(methodGetName);
						Class<?> valueClazz_ = methodGet.getReturnType();
						//设置值
						String methodSetName = "set" + StringUtils.firstCharUpper(paramField.getMainWord());
						Method methodSet = obj_.getClass().getMethod(methodSetName, valueClazz_);
						methodSet.invoke(obj_, ConvertUtils.convert(paramField.getValue(), valueClazz_));
					}
					method.invoke(obj, obj_);
				}
			}
			field.set(null, obj);
		}
	},
	MultiMap(){ //看需要增加MultiMap的实现, 不过
		@Override
		public boolean isAssignableFrom(Class<?> cla){
			return false;
		}
		
		@Override
		public void doInject(String prefix, PropertiesBase base, Field field) throws Exception {
			//TODO 
		}
		
	},
	Custom(){//自定义类型
		@Override
		public boolean isAssignableFrom(Class<?> cla) {
			return true;
		}
		
		@Override
		public void doInject(String prefix, PropertiesBase base, Field field) throws Exception {
			// 获取list的泛型的类型
			String prefixName = prefix + "." + field.getName();
			Constructor<?>[] constructors = field.getType().getConstructors();
			// 构造函数数量为0, 表示不是对象, 是接口或者抽象类
			if (constructors.length == 0) {
				return;
			}
			Object obj = field.getType().newInstance();
			Multimap<String, ParamField> paramFieldMap = base.getValue(prefixName);
			for (ParamField paramField : paramFieldMap.values()) {
				//获取类型
				String methodGetName = "get" + StringUtils.firstCharUpper(paramField.getMainWord());
				Method methodGet = obj.getClass().getMethod(methodGetName);
				Class<?> valueClazz = methodGet.getReturnType();
				//设置值
				String methodSetName = "set" + StringUtils.firstCharUpper(paramField.getMainWord());
				Method methodSet = obj.getClass().getMethod(methodSetName, valueClazz);
				methodSet.invoke(obj, ConvertUtils.convert(paramField.getValue(), valueClazz));
			}
			field.set(null, obj);
		}
	},
	;
	
	private Class<?>[] classes;
	
	private ParamEnum() {
	}
	private ParamEnum(Class<?>[] classes) {
		this.classes = classes;
	}
	
	public Class<?>[] getClasses() {
		return classes;
	}
	public void setClasses(Class<?>[] classes) {
		this.classes = classes;
	}
	
	public abstract boolean isAssignableFrom(Class<?> clazz);
	
	public abstract void doInject(String prefix, PropertiesBase base, Field field) throws Exception;
		
	public static ParamEnum getEnum(Class<?> clazz) {
		for (ParamEnum pEnum : values()) {
			if (pEnum.isAssignableFrom(clazz)) {
				return pEnum;
			}
		}
		return null;
	}
	
}
