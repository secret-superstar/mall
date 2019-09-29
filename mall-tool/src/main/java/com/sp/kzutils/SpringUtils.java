package com.sp.kzutils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**  
 * @Desc: spring util<br/>
 * @Date: 2018年5月23日 下午7:18:53 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved   
 */
public class SpringUtils {

	/**
	 * 加载classpath:jooq-spring.xml文件
	 * @return 
	 *ClassPathXmlApplicationContext
	 * @exception 
	 * @since  1.0.0
	 */
	public static ClassPathXmlApplicationContext getJooqClassPathXmlApplicationContext() {
		return new ClassPathXmlApplicationContext("classpath:jooq-spring.xml");
	}

	/**
	 * 加载classpath:jooq-update-spring.xml文件
	 * @return 
	 *ClassPathXmlApplicationContext
	 * @exception 
	 * @since  1.0.0
	 */
	public static ClassPathXmlApplicationContext getJooqUpdateClassPathXmlApplicationContext() {
		return new ClassPathXmlApplicationContext("classpath:jooq-update-spring.xml");
	}
}
