package com.kuaizi.etl.common.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
/**
 * 
 * @Desc: 基于jackson的json工具类<br/>
 * @Date: 2018年5月24日 下午5:56:41 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved
 */
public class JsonParser {
	private static final Logger LOG = LoggerFactory.getLogger(JsonParser.class);
	private static ObjectMapper OBJECTMAPPER = new ObjectMapper();
	private static JsonFactory JSONFACTORY = new JsonFactory();
	
	private JsonParser() {
		super();
	}

	/**
	 * Method to deserialize JSON content from given JSON content String.
	 * @param type
	 * @param jasonString
	 * @return 
	 *T
	 * @exception 
	 * @since  1.0.0
	 */
	public static <T> T toObject(String jasonString,Class<T> type) {
		T obj = null;
		try {
			obj = OBJECTMAPPER.readValue(jasonString, type);
		} catch (Exception e) {
			LOG.error("Can't parse the json.", e);
		} 
		return obj;
	}

	/**
	 * Method to deserialize JSON content from given JSON content byte.
	 * @param jasonBytes
	 * @param type
	 * @return 
	 *T
	 * @exception 
	 * @since  1.0.0
	 */
	public static <T> T toObject(byte[] jasonBytes,Class<T> type) {
		T obj = null;
		try {
			obj = OBJECTMAPPER.readValue(jasonBytes, type);
		} catch (Exception e) {
			LOG.error("Can't parse the json.", e);
		} 
		return obj;
	}

	/**
	 * Method to deserialize JSON content from given JSON content file.
	 * @param jsonFile
	 * @param type
	 * @return 
	 *T
	 * @exception 
	 * @since  1.0.0
	 */
	public static <T> T toObject(File jsonFile,Class<T> type) {
		T obj = null;
		try {
			obj = OBJECTMAPPER.readValue(jsonFile, type);
		} catch (Exception e) {
			LOG.error("Can't parse the json.", e);
		} 

		return obj;
	}

	/**
	 * Method to serialize JSON content from given bean.
	 * @param bean
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String toJsonString(Object bean) {
		StringWriter sw = new StringWriter();
		JsonGenerator gen = null;
		String jsonStr = null;
		try {
			gen = JSONFACTORY.createGenerator(sw);
			OBJECTMAPPER.writeValue(gen, bean);
			jsonStr = sw.toString();
		} catch (IOException e) {
			LOG.error("Can't convert bean to json string.", e);
		} finally {
			try {
				if(sw != null) {
					sw.close();
				}
			} catch (IOException e) {
				LOG.error("", e);
			}
			try {
				if(sw != null) {
					gen.close();
				}
			} catch (IOException e) {
				LOG.error("", e);
			}
		}
		return jsonStr;
	}

}
