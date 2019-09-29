package com.kuaizi.etl.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @Desc: util for MD5 encryption<br/>
 * @Date: 2018年5月10日 下午2:54:23 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved   
 */
public class MD5Util {
	private static final Logger log = LoggerFactory.getLogger(MD5Util.class); 
	private static MessageDigest md5;

	static{
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			log.error("", e);
		}
	}
	
	private MD5Util() {}

	/**
	 * 
	* digest16
	* @param text
	* @return   
	* String  
	* @exception   
	* @since  1.0.0
	 */
	public static String digest16(String text) {
		return digest(text, 16);
	}

	/**
	 * 
	* digest32
	* @param text
	* @return   
	* String  
	* @exception   
	* @since  1.0.0
	 */
	public static String digest32(String text) {
		return digest(text, 32);
	}

	/**
	 * 
	 * @param text text for encryption
	 * @param bit 16 or 32
	 * @return
	 */
	private synchronized static String digest(String text, int bit) {
		if (bit != 16 && bit != 32) {
			throw new IllegalArgumentException("bit should be 16 or 32");
		}
		md5.update(text.getBytes());
		byte[] b = md5.digest();

		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			int i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		if (bit == 32) {
			// 32 bit encryption
			return buf.toString();
		} else if (bit == 16) {
			// 16 bit encryption
			return buf.toString().substring(8, 24);
		} else {
			return null;
		}
	}
}
