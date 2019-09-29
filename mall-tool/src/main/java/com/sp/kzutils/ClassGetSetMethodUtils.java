package com.sp.kzutils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Desc: 通过反射获取实体类的字段setter、getter方法Method实例工具类。<br/>
 * @Date: 2018年5月22日 下午5:11:43 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved
 */
public class ClassGetSetMethodUtils {
	private static Logger log = LoggerFactory.getLogger(ClassGetSetMethodUtils.class);
	private static Map<Class,Map<String,Method>> CLASSFIELDMETHODMAP;
	
	/**
	 * 
	* 获取field对应的get Method
	* @param field 字段名，对应数据库中的列
	* @param parameterTypes 方法参数，set方法时需要，get方法为null
	* @return   
	* Method  
	* @exception   
	* @since  1.0.0
	 */
	public static Method getGetMethod(Class clazz,String field,Class<?>... parameterTypes){
		return getMethod(clazz, field, MethodPrefix.GET, parameterTypes);
	}
	
	/**
	 * 
	* 获取field对应的set方法Method
	* @param field 字段名，对应数据库中的列
	* @param parameterTypes 方法参数，set方法时需要，get方法为null
	* @return   
	* Method  
	* @exception   
	* @since  1.0.0
	 */
	public static Method getSetMethod(Class clazz,String field,Class<?>... parameterTypes){
		return getMethod(clazz, field, MethodPrefix.SET, parameterTypes);
	}
	
	/**
	 * 
	* 获取field对应的get或set方法Method
	* @param field 字段名，对应数据库中的列
	* @param methodPrefix get or set
	* @param parameterTypes 方法参数，set方法时需要，get方法为null
	* @return   
	* Method  
	* @exception   
	* @since  1.0.0
	 */
	private static Method getMethod(Class clazz,String field,MethodPrefix methodPrefix,Class<?>... parameterTypes){
		if(CLASSFIELDMETHODMAP == null){
			synchronized (ClassGetSetMethodUtils.class) {
				if(CLASSFIELDMETHODMAP == null){
					CLASSFIELDMETHODMAP = new HashMap<Class,Map<String,Method>>();
				}
			}
		}
		Map<String,Method> fieldMethodMap = CLASSFIELDMETHODMAP.get(clazz);
		if(fieldMethodMap == null) {
			synchronized (ClassGetSetMethodUtils.class) {
				if(fieldMethodMap == null){
					fieldMethodMap = new HashMap<>();
					CLASSFIELDMETHODMAP.put(clazz, fieldMethodMap);
				}
			}
		}
		String methodName = null;
		switch(methodPrefix){
			case GET:
				methodName = "get" + StringUtils.firstCharUpperCase(field);
				break;
			case SET:
				methodName = "set" + StringUtils.firstCharUpperCase(field);
			break;
		}
		Method method =  null;
		try {
			method = fieldMethodMap.get(methodName);
			if(method == null){
				method = clazz.getMethod(methodName, parameterTypes);
				fieldMethodMap.put(methodName, method);
			}
		} catch (NoSuchMethodException | SecurityException e) {
			log.error("get field {} method {} failed!",field,methodName,e);
		}
		return method;
	}
	
	private enum MethodPrefix {
		/**
		 * getter method
		 */
		GET,
		/**
		 * setter method
		 */
		SET
	}
}
