package com.kuaizi.etl.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @Desc: Collection utils<br/>
 * @Date: 2018年5月26日 下午6:00:19 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved   
 */
public class CollectionUtils {
	private static Logger log = LoggerFactory.getLogger(CollectionUtils.class);
	
	/**
	 * 将实体bean各属性及属性值转换为Map。
	 * @param bean
	 * @return 
	 *Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	public static <T> Map<String,Object> toMap(T bean){
		if(bean == null) {
			return null;
		}
		Class clazz = bean.getClass();
		Field [] fields = clazz.getDeclaredFields();
		Map<String,Object> map = new HashMap<>();
		for (Field field : fields) {
			String fieldName = field.getName();
			Method getMethod = ClassGetSetMethodUtils.getGetMethod(clazz, fieldName);
			if(getMethod != null) {
				try {
					Object value = getMethod.invoke(bean);
					if(value != null) {
						map.put(fieldName, value);
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					log.error("field {}",fieldName, e);
				}
			}
		}
		return map;
	}
}
