package com.kuaizi.etl.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @Desc: 实体工具类<br/>
 * @Date: 2018年5月2日 下午5:05:46 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved   
 */
public class BeanUtils {
	private static Logger log = LoggerFactory.getLogger(BeanUtils.class);
	
	/**
     * 对象转字节数组
     * @param obj
     * @return
     */
    public static byte[] ObjectToBytes(Object obj){
        byte[] bytes = null;
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;
        try {
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (IOException e) {
            log.error("", e);
        }finally {
            try {
                if(bo!=null){
                    bo.close();
                }
                if(oo!=null){
                    oo.close();
                }
            } catch (IOException e) {
            	log.error("", e);
            }
        }
        return bytes;
    }
    
    /**
     * 字节数组转对象
     * @param bytes
     * @return
     */
    public static Object BytesToObject(byte[] bytes){
        Object obj = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            bi =new ByteArrayInputStream(bytes);
            oi =new ObjectInputStream(bi);
            obj = oi.readObject();
        } catch (Exception e) {
        	log.error("", e);
        }finally {
            try {
                if(bi!=null){
                    bi.close();
                }
                if(oi!=null){
                    oi.close();
                }
            } catch (IOException e) {
            	log.error("", e);
            }
        }

        return obj;
    }
    
    /**
     * 组合计划id、广告id、等生成效果表主键
     * @param campaignId
     * @param adId
     * @param creativeId
     * @param sizeId
     * @param adSetId
     * @param statisticsDate
     * @param md5Hash
     * @return 
     *String
     * @exception 
     * @since  1.0.0
     */
    public static String genStatisticsDynamicCreativePrimaryKey(Integer campaignId,Integer adId,
    		Integer creativeId,Integer sizeId,Integer adSetId,Integer statisticsDate,String md5Hash) {
    	StringBuilder builder = new StringBuilder();
    	builder.append(campaignId)
    	.append(adId)
    	.append(creativeId)
    	.append(sizeId)
    	.append(adSetId)
    	.append(statisticsDate)
    	.append(md5Hash);
    	return MD5Util.digest16(builder.toString());
    }
    
   /**
    * 根据statistics_dynamic_creative_hour主键生成rowKey
    * @param projectId
    * @param campaignId
    * @param adId
    * @param adSetId
    * @param creativeId
    * @param sizeId
    * @param dynamicId
    * @param statisticsDate
    * @param hour
    * @return 
    *String
    * @exception 
    * @since  1.0.0
    */
    public static String genKey(Integer projectId,Integer campaignId,Integer adId,
    		Integer adSetId,Integer creativeId,Integer sizeId,Integer dynamicId,Integer statisticsDate,Integer hour) {
    	StringBuilder builder = new StringBuilder();
    	builder
    	.append(projectId)
    	.append(campaignId)
    	.append(adId)
    	.append(adSetId)
    	.append(creativeId)
    	.append(sizeId)
    	.append(dynamicId)
    	.append(statisticsDate)
    	.append(hour);
    	return MD5Util.digest16(builder.toString());
    }
    
    /**
     * 连接各id
     * @param id
     * @param projectId
     * @param campaignId
     * @param adId
     * @param adSetId
     * @param creativeId
     * @param sizeId
     * @param dynamicId
     * @param statisticsDate
     * @param hour
     * @param msgType
     * @return 
     *String
     * @exception 
     * @since  1.0.0
     */
    public static String join(String id,Integer projectId,Integer campaignId,Integer adId,
    		Integer adSetId,Integer creativeId,Integer sizeId,Integer dynamicId,Integer statisticsDate,Integer hour,Integer msgType) {
        StringBuilder sb = new StringBuilder("(");

        sb.append(id);
        sb.append(",").append(projectId);
        sb.append(",").append(campaignId);
        sb.append(",").append(adId);
        sb.append(",").append(adSetId);
        sb.append(",").append(creativeId);
        sb.append(",").append(sizeId);
        sb.append(",").append(dynamicId);
        sb.append(",").append(statisticsDate);
        sb.append(",").append(hour);
        sb.append(",").append(msgType);

        sb.append(")");
        return sb.toString();
    }
}
