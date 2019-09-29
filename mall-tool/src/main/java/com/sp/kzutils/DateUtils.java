package com.kuaizi.etl.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @Desc: 日期工具类<br/>
 * @Date: 2018年5月2日 下午3:28:52 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved   
 */
public class DateUtils {
	private final static Logger log = LoggerFactory.getLogger(DateUtils.class);
	
	private DateUtils(){}
	
	private static Map<String ,ThreadLocal<SimpleDateFormat>> DATAFORMATMAP = null;
  
	/**
	 * 
	 * 通过指定的日期格式，获取线程独立的SimpleDateFormat实例。
	 * 因为SimpleDateFormat不是线程安全，所以利用ThreadLocal将SimpleDateFormat绑定到对应的线程中，
	 * 即每个线程一个SimpleDateFormat实例，实现线程安全。
	* @param pattern 日期格式
	* @return   
	* SimpleDateFormat  
	* @exception   
	* @since  1.0.0
	 */
    public synchronized static SimpleDateFormat getDateFormat(final String pattern){
    	if(DATAFORMATMAP == null){
    		 DATAFORMATMAP =
    					new HashMap<String ,ThreadLocal<SimpleDateFormat>>();
    	}
		ThreadLocal<SimpleDateFormat> threadLocal = DATAFORMATMAP.get(pattern);
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<SimpleDateFormat>(){
				protected SimpleDateFormat initialValue() {
					return new SimpleDateFormat(pattern);
				}
			};
			DATAFORMATMAP.put(pattern, threadLocal);
		}
		return threadLocal.get();
    } 
    
    /**
	 * 
	 * @param pattern date pattern
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getSimpleDateFormat(String pattern){
		if(pattern==null){
			return null;
		}
		return new SimpleDateFormat(pattern);
	}
	
	/**
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		return getDateFormat(pattern).format(date);
	}

	/**
	 * 
	* 根据指定的pattern格式化时间戳
	* @param timestamp 时间戳，秒或毫秒
	* @param pattern
	* @return   
	* String  
	* @exception   
	* @since  1.0.0
	 */
	public static String formatDate(long timestamp, String pattern) {
		if(timestamp < 10000000000l ){
			//将秒转换为毫秒
			timestamp *= 1000;
		}
		return formatDate(new Date(timestamp), pattern);
	}
	
	/**
	 * 
	 * @param time
	 * @param pattern
	 * @return null if error
	 */
	public static Date parse(String time,String pattern){
		try {
			return getDateFormat(pattern).parse(time);
		} catch (ParseException e) {
			log.error("{} parse to pattern {} failed!" ,time,pattern, e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param timestamp 时间戳，秒或毫秒
	 * @param unit time pattern,such as yyyy MM dd HH mm ss WW.
	 * @param interval time interval
	 * @return long time add by unit and interval.
	 */
	public static long addTime(long timestamp,String unit,int interval){
		Calendar c=Calendar.getInstance();
		if(timestamp < 10000000000l ){
			//将秒转换为毫秒
			timestamp *= 1000;
		}
		c.setTimeInMillis(timestamp);
		if ("yyyy".equals(unit)) {
			 c.add(Calendar.YEAR,interval);
		} else if ("MM".equals(unit)) {
			 c.add(Calendar.MONTH,interval);
		} else if ("ww".equals(unit)) {
			 c.add(Calendar.WEEK_OF_YEAR,interval);
		} else if ("WW".equals(unit)) {
			 c.add(Calendar.WEEK_OF_MONTH,interval);
		} else if ("DD".equals(unit)) {
			 c.add(Calendar.DAY_OF_YEAR,interval);
		} else if ("dd".equals(unit)) {
			 c.add(Calendar.DAY_OF_MONTH,interval);
		} else if ("HH".equals(unit)) {
			 c.add(Calendar.HOUR_OF_DAY,interval);
		} else if ("mm".equals(unit)) {
			 c.add(Calendar.MINUTE,interval);
		} else if ("ss".equals(unit)) {
			 c.add(Calendar.SECOND,interval);
		} else if ("SSS".equals(unit)) {
			 c.add(Calendar.MILLISECOND,interval);
		}
		return c.getTime().getTime();
	}
	
	/**
	 * 
	 * @param time
	 *            long time
	 * @param unit
	 *            time pattern,such as yyyy MM dd HH mm ss WW.
	 * @return time unit,such as 2012
	 */
	public static long getTimeUnit(long time, String unit) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return getTimeUnit(c, unit);
	}
	
	/**
	 * 
	 * @param time
	 *            long time millisecond
	 * @param unit
	 *            time pattern,such as yyyy MM dd HH mm ss WW.
	 * @return time unit,such as 2012
	 */
	public static long getTimeUnit(Calendar c, String unit) {
		if ("yyyy".equals(unit)) {
			return c.get(Calendar.YEAR);
		} else if ("MM".equals(unit)) {
			return c.get(Calendar.MONTH);
		} else if ("ww".equals(unit)) {
			return c.get(Calendar.WEEK_OF_YEAR);
		} else if ("WW".equals(unit)) {
			return c.get(Calendar.WEEK_OF_MONTH);
		} else if ("DD".equals(unit)) {
			return c.get(Calendar.DAY_OF_YEAR);
		} else if ("dd".equals(unit)) {
			return c.get(Calendar.DAY_OF_MONTH);
		} else if ("HH".equals(unit)) {
			return c.get(Calendar.HOUR_OF_DAY);
		} else if ("mm".equals(unit)) {
			return c.get(Calendar.MINUTE);
		} else if ("ss".equals(unit)) {
			return c.get(Calendar.SECOND);
		} else if ("SSS".equals(unit)) {
			return c.get(Calendar.MILLISECOND);
		} else {
			return c.getTimeInMillis();
		}
	}
	
	/**
	 * 
	* 将时间戳转换为java.sql.Date 
	* @param timestamp
	* @return   
	* java.sql.Date  
	* @exception   
	* @since  1.0.0
	 */
	public static java.sql.Date toSqlDate(long timestamp){
		if(timestamp < 10000000000l ){
			//将秒转换为毫秒
			timestamp *= 1000;
		}
		return new java.sql.Date(timestamp);
	}
	
	/**
	 * 
	* 将时间戳转换为java.util.Date 
	* @param timestamp
	* @return   
	* java.util.Date  
	* @exception   
	* @since  1.0.0
	 */
	public static Date toDate(long timestamp){
		if(timestamp < 10000000000l ){
			//将秒转换为毫秒
			timestamp *= 1000;
		}
		return new Date(timestamp);
	}
	
	
	/**
	 * get Date at time zone GMT+8:00
	 * @return
	 */
	public static Date getCurrentDateGMT8(){
		return getCurrentCalendarGMT8().getTime();
	}
	
	/**
	 * 返回单位为秒的时间戳
	 * @return
	 */
	public static Long getCurrentTimestampSecond(){
		return getCurrentCalendarGMT8().getTime().getTime()/1000;
	}
	
	/**
	 * get Calendar at time zone GMT+8:00
	 * @return
	 */
	public static Calendar getCurrentCalendarGMT8(){
		return Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
	}
	
	/**
	 * 将当前时间转换为指定pattern的long类型。
	 * @param pattern 日期格式，必须能转换为long
	 * @return 
	 *long
	 * @exception 
	 * @since  1.0.0
	 */
	public static long getCurrentTimeLong(String pattern) {
		return Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern)));
	}
	
	/**
	 * 
	* current time
	* @return   
	* java.sql.Date  
	* @exception   
	* @since  1.0.0
	 */
	public static java.sql.Date getCurrentSqlDate(){
		return new java.sql.Date(getCurrentCalendarGMT8().getTimeInMillis());
	}
	
	/**
	 * get Calendar at time zone GMT+8:00,current date dawn
	 * @return
	 */
	public static Calendar getCurrenDateDawnGMT8(){
		Calendar now = getCurrentCalendarGMT8();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		return now;
	}
	
	/**
	 * 返回当前整点时间，忽略分钟和秒。如当前实际时间为2016-12-30 12:50:50，则返回 2016-12-30 12:00:00
	 * @return
	 */
	public static Calendar getCurrenHourDawnGMT8(){
		Calendar now = getCurrentCalendarGMT8();
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		return now;
	}
	
	/**
	 * 
	* 将指定时间戳设置为凌晨时间
	* @param timestamp 时间戳，秒或毫秒
	* @return   
	* Date  
	* @exception   
	* @since  1.0.0
	 */
	public static Date toDateDawn(long timestamp){
		if(timestamp < 10000000000l ){
			//将秒转换为毫秒
			timestamp *= 1000;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 
	* 将指定date日期的时间设置为凌晨时间
	* @param date
	* @return   
	* Date  
	* @exception   
	* @since  1.0.0
	 */
	public static Date getDateDawn(Date date){
		return toDateDawn(date.getTime());
	}
	
	/**
	 * 
	* @return   yesterday
	* Date  
	* @exception   
	* @since  1.0.0
	 */
	public static Date getYesterday(){
		Calendar now = getCurrentCalendarGMT8();
		now.add(Calendar.DAY_OF_YEAR, -1);
		return now.getTime();
	}
	
	/**
	 * 
	* convert java.util.Date to java.sql.Date
	* @param utilDate
	* @return   
	* java.sql.Date  
	* @exception   
	* @since  1.0.0
	 */
	public static java.sql.Date utilDate2SqlDate(Date utilDate){
		return new java.sql.Date(utilDate.getTime());
	}
	
	
	/**
	 * 
	* now
	* @return   
	* Timestamp  
	* @exception   
	* @since  1.0.0
	 */
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(getCurrentDateGMT8().getTime());
	}
	
	/**
	 * 
	* 获取当前时间至当天23:59:59的秒数
	* @return   
	* long  
	* @exception   
	* @since  1.0.0
	 */
	public static long getTodayRemainSecond(){
		Calendar now = getCurrentCalendarGMT8();
		long nowSec = now.getTimeInMillis() / 1000;
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		
		long endSec = now.getTimeInMillis() / 1000;
		long sec = endSec - nowSec;
		return sec;
	}
	
	/**
	 * 
	* 获取date时间从date当天凌晨00:00开始的秒数。
	* 如:2017-12-01 01:00:00，则返回3600，
	* 2017-12-01 00:00:10，则返回10。
	* @param date
	* @return   
	* long  
	* @exception   
	* @since  1.0.0
	 */
	public static long getDateSecondFromDawn(Date date) {
		return getDateSecondFromDawn(date.getTime());
	}
	
	/**
	 * 
	* 获取millis时间，从millis当天凌晨00:00开始的秒数。
	* 如:2017-12-01 01:00:00，则返回3600，
	* 2017-12-01 00:00:10，则返回10。
	* @param timestamp 时间戳，秒或毫秒
	* @return   
	* long  
	* @exception   
	* @since  1.0.0
	 */
	public static long getDateSecondFromDawn(long timestamp) {
		if(timestamp < 10000000000l ){
			//将秒转换为毫秒
			timestamp *= 1000;
		}
		return (timestamp - toDateDawn(timestamp).getTime())/1000;
	}
	
	/**
	 * 
	* 获取当前时间当天23:59:59的时间戳
	* @return   
	* long  
	* @exception   
	* @since  1.0.0
	 */
	public static long getTodayLastTimeSecond(){
		Calendar now = getCurrentCalendarGMT8();
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		
		long endSec = now.getTimeInMillis() / 1000;
		return endSec;
	}
	
	/**
	 * 
	* getLastDayOfWeek
	* 获取当前周最后一天（周六为一周的最后一天）
	* @return   
	* Date  
	* @exception   
	* @since  1.0.0
	 */
	public static Date getLastDayOfWeek(){
		Calendar now = getCurrentCalendarGMT8();
		now.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime();
	}
	
	/**
	 * 
	* getLastDayOfWeek
	* 获取当前月最后一天
	* @return   
	* Date  
	* @exception   
	* @since  1.0.0
	 */
	public static Date getLastDayOfMonth(){
		Calendar now = getCurrentCalendarGMT8();
		now.add(Calendar.MONTH, 1);
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.add(Calendar.DAY_OF_MONTH, -1);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime();
	}
	
	/**
	 * 
	* 获取指定date日期的第一天
	* @param date
	* @return   
	* Date  
	* @exception   
	* @since  1.0.0
	 */
	public static Date getLastDayOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	/**
	 * 
	* 获取指定date日期的第一天
	* @param date
	* @return   
	* Date  
	* @exception   
	* @since  1.0.0
	 */
	public static Date getFirstDayOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 
	* 获取指定date日期下月的第一天
	* @param date
	* @return   
	* Date  
	* @exception   
	* @since  1.0.0
	 */
	public static Date getFirstDayOfNextMonth(Date date){
		return getFirstDayOfNextMonth(date.getTime());
	}
	
	/**
	 * 
	* 获取指定date日期下月的第一天
	* @param timestamp 时间戳，秒或毫秒
	* @return   
	* Date  
	* @exception   
	* @since  1.0.0
	 */
	public static Date getFirstDayOfNextMonth(long timestamp){
		if(timestamp < 10000000000l ){
			//将秒转换为毫秒
			timestamp *= 1000;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 
	* 计算2个日期相差的天数
	* @param date1
	* @param date2
	* @return   
	* int  
	* @exception   
	* @since  1.0.0
	 */
	public static long betweenDay(Date date1,Date date2,boolean include) {
		Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
		
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
		long between = (cal1.getTimeInMillis() - cal2.getTimeInMillis())/86400000;
		between = Math.abs(between);
		if(include) {
			between += 1;
		}
		return between;
	}
	
	
	/**
	 * 
	* 将秒数转换为*d*h*m*s即，多少天多少小时多少分多少秒
	* @param second
	* @return   
	* String  *d*h*m*s
	* @exception   
	* @since  1.0.0
	 */
	public static String formatSecond(int seconds) {
		if(seconds == 0) {
			return "0s";
		}
		int mod = seconds;
		int day = mod/86400;
		mod = mod%86400;
		
		int hour = mod/3600;
		mod = mod%3600;
		
		int minute = mod/60;
		mod = mod%60;
		
		int second = mod;
		
		StringBuilder sb = new StringBuilder();
		if(day > 0) {
			sb.append(day).append("d");
		}
		if(hour > 0) {
			sb.append(hour).append("h");
		}
		if(minute > 0) {
			sb.append(minute).append("m");
		}
		if(second > 0) {
			sb.append(second).append("s");
		}
		
		return sb.toString();
	}
}
