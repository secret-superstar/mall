package com.kuaizi.etl.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**  
 * @Desc: json utils<br/>
 * @Date: 2018年5月15日 下午7:40:52 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved   
 */
public class JsonUtils {
	private final static Logger log = LoggerFactory.getLogger(JsonUtils.class);

	/**
	 * 
	* 将实体转换为JSON字符串
	* @param obj
	* @return   
	* JSONObject  
	* @exception   
	* @since  1.0.0
	 */
	public static<T> String toJsonStr(T obj){
		if(obj == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		Class clazz = obj.getClass();
		Field [] fields = clazz.getDeclaredFields();
		boolean first = true;
		for (Field field : fields) {
			String fieldName = field.getName();
			try {
				Class type = field.getType();
				if(fieldName.equals("serialVersionUID")) {
					continue;
				}
				String getName = "get" + StringUtils.firstCharUpperCase(fieldName);
				Method method = clazz.getMethod(getName);
				Object value = method.invoke(obj);
				if(value != null){
					if(first) {
						first = false;
					} else {
						builder.append(",");
					}
					builder.append("\"").append(fieldName).append("\":").append(value);
				}
			} catch (Exception e) {
				log.error("invoke failed,field: {}",fieldName, e);
			}
		}
		builder.append("}");
		return builder.toString();
	}
	
	/**
	 * 
	* 将实体转换为JSONObject
	* @param obj
	* @return   
	* JSONObject  
	* @exception   
	* @since  1.0.0
	 */
	public static<T> JSONObject toJSONObject(T obj){
		JSONObject jo = new JSONObject();
		Class clazz = obj.getClass();
		Field [] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			try {
				Class type = field.getType();
				if(fieldName.equals("serialVersionUID")) {
					continue;
				}
				String getName = "get" + StringUtils.firstCharUpperCase(fieldName);
				Method method = clazz.getMethod(getName);
				Object value = method.invoke(obj);
				if(value != null){
					jo.put(fieldName, value);
				}
			} catch (Exception e) {
				log.error("invoke failed,field: {}",fieldName, e);
			}
		}
		
		return jo;
	}
}
