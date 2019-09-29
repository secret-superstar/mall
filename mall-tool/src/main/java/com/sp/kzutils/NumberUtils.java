package com.sp.kzutils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @Desc: 数值数据工具类<br/>
 * @Date: 2018年6月12日 上午11:24:53 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved   
 */
public class NumberUtils {
	private final static Logger log = LoggerFactory.getLogger(NumberUtils.class);
	
	private NumberUtils(){}
	
	private static Map<String ,ThreadLocal<NumberFormat>> numberFormatMap = null;
	private static ReadWriteLock numberFormatMapLock = null;
  
	/**
	 * 获取线程安全的指定小数位的NumberFormat实例
	 * @param minFraction 最小小数位
	 * @param maxFraction 最大小数位
	 * @return 
	 *NumberFormat
	 * @exception 
	 * @since  1.0.0
	 */
    public static NumberFormat getFractionNumberFormat(final int minFraction,final int maxFraction){
    	if(numberFormatMapLock == null) {
    		synchronized(NumberUtils.class) {
    			if(numberFormatMapLock == null) {
    				numberFormatMapLock = new ReentrantReadWriteLock();
    			}
    		}
    	}
    	if(numberFormatMap == null){
    		Lock writeLock = numberFormatMapLock.writeLock();
    		try {
    			writeLock.lock();
    			if(numberFormatMap == null){
    				log.info("new NumberFormat Map");
    	    		numberFormatMap = new ConcurrentHashMap<String ,ThreadLocal<NumberFormat>>();
    			} else {
    				log.info("NumberFormat Map is conctructed");
    			}
    		} finally {
    			writeLock.unlock();
    		}
    	}
    	String key = "fraction-" + minFraction + "-" + maxFraction;
		ThreadLocal<NumberFormat> threadLocal = numberFormatMap.get(key);
		if (threadLocal == null) {
			Lock writeLock = numberFormatMapLock.writeLock();
    		try {
    			writeLock.lock();
    			threadLocal = numberFormatMap.get(key);
    			if(threadLocal == null){
    				log.info("new ThreadLocal {} NumberFormat",key);
    				threadLocal = new ThreadLocal<NumberFormat>(){
    					protected NumberFormat initialValue() {
    						NumberFormat nf = NumberFormat.getNumberInstance();
    						nf.setMinimumFractionDigits(minFraction);
    						nf.setMaximumFractionDigits(maxFraction);
    						//如果想输出的格式用逗号隔开，可以设置成true
    						 nf.setGroupingUsed(false);
    						return nf;
    					}
    				};
    				numberFormatMap.put(key, threadLocal);
    			} else {
    				log.info("ThreadLocal {} NumberFormat is conctructed",key);
    			}
    		} finally {
    			writeLock.unlock();
    		}
		}
		return threadLocal.get();
    } 
    
    /**
     * 格式化
     * @param nf
     * @param rawValue
     * @return 
     *float
     * @exception 
     * @since  1.0.0
     */
    public static float format(float rawValue,NumberFormat nf) {
		return Float.parseFloat(nf.format(rawValue));
	}
    
    /**
     * 格式化float数值，指定保留最小小数和最大小数 
     * @param minFraction 最小小数
     * @param maxFraction 最大小数
     * @param rawValue
     * @return 
     *float
     * @exception 
     * @since  1.0.0
     */
    public static float format(float rawValue,int minFraction,int maxFraction){
    	return Float.parseFloat(getFractionNumberFormat(minFraction, maxFraction).format(rawValue));
    }
    
    /**
     * 将数值型对象转换为Integer对象
     * @param valueObj
     * @return 
     *Integer
     * @exception 
     * @since  1.0.0
     */
    public static Integer toInteger(Object valueObj) {
    	if(valueObj == null) {
			return null;
		}
		Integer result = null;
		if (valueObj instanceof Integer) {
			result = (Integer) valueObj;
		} else if (valueObj instanceof Long) {
			result = ((Long) valueObj).intValue();
		} else if (valueObj instanceof Float) {
			result = ((Float) valueObj).intValue();
		} else if (valueObj instanceof Double) {
			result = ((Double) valueObj).intValue();
		} else if (valueObj instanceof BigDecimal) {
			result = ((BigDecimal) valueObj).intValue();
		}  else if (valueObj instanceof String && ((String)valueObj).matches("\\d+")) {
			result = Integer.parseInt((String) valueObj);
		} else {
			throw new IllegalArgumentException("value " + valueObj + " unknown type " + valueObj.getClass().getName());
		}

		return result;
	}
	
    /**
     * 将数值类型的对象转换为Long对象
     * @param valueObj
     * @return 
     *Long
     * @exception 
     * @since  1.0.0
     */
	public static Long toLong(Object valueObj) {
		if(valueObj == null) {
			return null;
		}
		Long result = null;
		if (valueObj instanceof Long) {
			result = (Long)valueObj;
		} else if (valueObj instanceof Integer) {
			result = ((Integer)valueObj).longValue();
		} else if (valueObj instanceof Float) {
			result = ((Float) valueObj).longValue();
		} else if (valueObj instanceof Double) {
			result = ((Double) valueObj).longValue();
		} else if (valueObj instanceof BigDecimal) {
			result = ((BigDecimal) valueObj).longValue();
		} else if (valueObj instanceof String && ((String)valueObj).matches("\\d+")) {
			result = Long.parseLong((String) valueObj);
		} else {
			throw new IllegalArgumentException("value " + valueObj + " unknown type " + valueObj.getClass().getName());
		}

		return result;
	}
	
	/**
	 * 将数值类型的对象转换为Float对象
	 * @param valueObj
	 * @return 
	 *Float
	 * @exception 
	 * @since  1.0.0
	 */
	public static Float toFloat(Object valueObj) {
		if(valueObj == null) {
			return null;
		}
		Float result = null;
		if (valueObj instanceof Float) {
			result = (Float)valueObj;
		} else if (valueObj instanceof Integer) {
			result = ((Integer)valueObj).floatValue();
		} else if (valueObj instanceof Long) {
			result = ((Long) valueObj).floatValue();
		} else if (valueObj instanceof Double) {
			result = ((Double) valueObj).floatValue();
		}  else if (valueObj instanceof BigDecimal) {
			result = ((BigDecimal) valueObj).floatValue();
		} else if (valueObj instanceof String && ((String)valueObj).matches("[.\\d]+")) {
			result = Float.parseFloat((String) valueObj);
		} else {
			throw new IllegalArgumentException("value " + valueObj + " unknown type " + valueObj.getClass().getName());
		}

		return result;
	}
}
