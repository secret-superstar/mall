package com.sp.kzutils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @Desc: HTTP请求工具类<br/>
 * @Date: 2018年7月13日 上午11:59:09 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved   
 */
public class HttpUtils {
	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);
	
	/**
	 *  利用GET方法请求，按UTF-8解码请求结果并返回
	 * @param url
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String doGet(String url) {
		return doGet(url, "UTF-8");
	}
	
	/**
	 * 利用GET方法请求，按指定编码解码请求结果并返回
	 * @param url
	 * @param responseCharsetName 返回内容编码
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String doGet(String url,String responseCharsetName) {
		HttpGet request = new HttpGet(url);
		try (CloseableHttpClient client = HttpClients.createDefault();
				CloseableHttpResponse response = client.execute(request)){
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(),responseCharsetName);
			} else {
				log.warn("GET {} code {}", url, response.getStatusLine().getStatusCode());
				return null;
			}
		} catch (Exception e) {
        	log.error("", e);
        }
        return null;
	}
	
	/**
	 * 利用GET方法请求，结果以byte[]返回
	 * @param url
	 * @return 
	 *byte[]
	 * @exception 
	 * @since  1.0.0
	 */
	public static byte[] doGetInputStream(String url) {
		HttpGet request = new HttpGet(url);
		try (CloseableHttpClient client = HttpClients.createDefault();
				CloseableHttpResponse response = client.execute(request)){
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toByteArray(response.getEntity());
			} else {
				log.warn("GET {} code {}", url, response.getStatusLine().getStatusCode());
				return null;
			}
		} catch (Exception e) {
        	log.error("", e);
        }
        return null;
	}
	
	/**
	 * 利用POST方法请求，指定params参数，按UTF-8解码请求结果并返回
	 * @param url
	 * @param params POST参数，不能为空
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String doPost(String url, Map<String, String> params) {
		return doPost(url, params, "UTF-8");
	}
	
	/**
	 * 利用POST方法请求，指定params参数，按指定编码解码请求结果并返回
	 * @param url url
	 * @param params POST参数，不能为空
	 * @param responseCharsetName 返回内容编码
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String doPost(String url, Map<String, String> params,String responseCharsetName) {
		if(params == null || params.size() == 0) {
			log.warn("POST params can't be null");
			return null;
		}
		// 实例化HTTP方法
		HttpPost request = new HttpPost(url);

		// 设置参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String value = params.get(name);
			nvps.add(new BasicNameValuePair(name, value));
		}
		request.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		
		try (CloseableHttpClient client = HttpClients.createDefault();
				CloseableHttpResponse response = client.execute(request)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(),responseCharsetName);
			} else {
				log.warn("POST {} code {},params {}", url, response.getStatusLine().getStatusCode(), nvps);
				return null;
			}
		} catch (Exception e) {
			log.error("post {} failed,params {}", url, nvps, e);
			return null;
		}
	}
	
	/**
	 * 使用HttpClient发送带有JSON正文的POST请求，按UTF-8编码解码请求结果并返回
	 * @param url
	 * @param jsonParams JSON正文字符串
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String doPostJSONParam(String url, String jsonParams) {
		return doPostJSONParam(url, jsonParams, "UTF-8");
	}
	
	/**
	 * 使用HttpClient发送带有JSON正文的POST请求，按指定编码解码请求结果并返回
	 * @param url
	 * @param jsonParams JSON正文字符串
	 * @param responseCharsetName 返回内容编码
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String doPostJSONParam(String url, String jsonParams,String responseCharsetName) {
		StringEntity entity = new StringEntity(jsonParams,ContentType.APPLICATION_JSON);
		return doPost(url, entity, responseCharsetName);
	}
	
	/**
	 * 
	 * @param url
	 * @param httpEntity
	 * @param responseCharsetName
	 * @return 
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	private static String doPost(String url, HttpEntity httpEntity,String responseCharsetName) {
		// 实例化HTTP方法
		HttpPost request = new HttpPost(url);
		request.setEntity(httpEntity);
		
		try (CloseableHttpClient client = HttpClients.createDefault();
				CloseableHttpResponse response = client.execute(request)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(),responseCharsetName);
			} else {
				log.warn("POST {} code {},HttpEntity {}", url, response.getStatusLine().getStatusCode(), httpEntity);
				return null;
			}
		} catch (Exception e) {
			log.error("post {} failed,HttpEntity {}", url, httpEntity, e);
			return null;
		}
	}
}
