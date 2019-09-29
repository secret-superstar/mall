package com.kuaizi.etl.common.util;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**  
 * @Desc: 字符串辅助类<br/>
 * @Date: 2018年5月15日 下午7:30:59 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved   
 */
public class StringUtils {
    public static final char UNDERLINE='_';
    
    /**
     * 将驼峰命名转换为下划线命名。
     * 例：javaBeanTest -> java_bean_test
     * @param param
     * @return 
     *String
     * @exception 
     * @since  1.0.0
     */
    public static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    /**
     * 将下划线命名转换为驼峰命名。
     * 例：java_bean_test -> javaBeanTest
     * @param param
     * @return 
     *String
     * @exception 
     * @since  1.0.0
     */
    public static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (c==UNDERLINE){
               if (++i<len){
                   sb.append(Character.toUpperCase(param.charAt(i)));
               }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    /**
     * 将下划线命名转换为驼峰命名。
     * 例：java_bean_test -> javaBeanTest
     * @param param
     * @return 
     *String
     * @exception 
     * @since  1.0.0
     */
    public static String underlineToCamel2(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        StringBuilder sb=new StringBuilder(param);
        Matcher mc= Pattern.compile("_").matcher(param);
        int i=0;
        while (mc.find()){
            int position=mc.end()-(i++);
            //String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());
        }
        return sb.toString();
    }
	public static void main(String[] args) {
	}

	
	/**
	 * 
	* captureName(首字母大写)  
	* @param name
	* @return   
	* String  
	* @exception   
	* @since  1.0.0
	 */
	public static String firstCharUpperCase(String name) {
		return process(name, str -> {
			if (str.length() == 1){
				 return str.toUpperCase();
			} else {
				 String join = str.substring(0, 1).toUpperCase() + str.substring(1);
				 return join;
			}
		});
	}
	
	/**
	 * 
	* 利用function处理str字符串，返回处理后的字符串
	* @param str
	* @param function
	* @return   
	* String  
	* @exception   
	* @since  1.0.0
	 */
	public static String process(String str,Function<String, String> function){
		String result = str;
		if(str != null){
			result = function.apply(str);
		}
		return result;
	}
	
	/**
	 * 
	* 生成字符的缩略字符。提取首字符、大写字母、下划线后的第一个字符。
	* 如strStrLogogram,则返回ssl；
	* 如str_str_logogram,则返回ssl；
	* @param str
	* @return   
	* String  
	* @exception   
	* @since  1.0.0
	 */
	public static String logogramStr(String str){
		return process(str, s -> {
			String reuslt = null;
			if(s.length() > 1){
				StringBuilder builder = new StringBuilder();
				builder.append(s.substring(0, 1));
				
				Pattern p = Pattern.compile("_.|[A-Z]");
				Matcher m = p.matcher(s.substring(1));
				String match = null;
				while(m.find()){
					match = m.group().replace("_", "");
					builder.append(match);
				}
				reuslt = builder.toString().toLowerCase();
			} else {
				reuslt = s.toLowerCase();
			}
			return reuslt;
		});
		
	}
	
	
	/**
	 * 
	* 对字符进行前缀填充
	* @param fillingStr 待填充的字符
	* @param length 返回的数字加填充字符后的总长度
	* @param filledWith 填充的字符
	* @return   
	* String  
	* @exception   
	* @since  1.0.0
	 */
	public static String fillPrefix(String fillingStr,int length,char filledWith) {
		return fill(fillingStr, length, filledWith, true);
	}
	
	/**
	 * 
	* 对字符进行后缀填充
	* @param fillingStr 待填充的字符
	* @param length 返回的数字加填充字符后的总长度
	* @param filledWith 填充的字符
	* @return   
	* String  
	* @exception   
	* @since  1.0.0
	 */
	public static String fillSuffix(String fillingStr,int length,char filledWith) {
		return fill(fillingStr, length, filledWith, false);
	}
	
	/**
	 * 
	* 对字符进行填充
	* @param fillingStr 待填充的字符
	* @param length 返回的数字加填充字符后的总长度
	* @param filledWith 填充的字符
	* @param fillPrefix 是否前缀填充字符。true：是；false:后缀填充。
	* @return   
	* String  
	* @exception   
	* @since  1.0.0
	 */
	private static String fill(String fillingStr,int length,char filledWith,boolean fillPrefix) {
		if(fillingStr == null) {
			return null;
		}
		fillingStr = fillingStr.trim();
		if(fillingStr.length() >= length) {
			return fillingStr;
		}
		int fillLen = length - fillingStr.length();
		StringBuilder strBuilder = new StringBuilder();
		if(!fillPrefix) {
			strBuilder.append(fillingStr);
		}
		for (int i = 0; i < fillLen; i++) {
			strBuilder.append(filledWith);
		}
		if(fillPrefix) {
			strBuilder.append(fillingStr);
		}
		return strBuilder.toString();
	}
	
	/**
	 * Check that the given CharSequence is neither {@code null} nor of length 0.
	 * Note: Will return {@code true} for a CharSequence that purely consists of whitespace.
	 * <p><pre class="code">
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param str the CharSequence to check (may be {@code null})
	 * @return {@code true} if the CharSequence is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * Check that the given String is neither {@code null} nor of length 0.
	 * Note: Will return {@code true} for a String that purely consists of whitespace.
	 * @param str the String to check (may be {@code null})
	 * @return {@code true} if the String is not null and has length
	 * @see #hasLength(CharSequence)
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}
	/**
	 * Check whether the given CharSequence has actual text.
	 * More specifically, returns {@code true} if the string not {@code null},
	 * its length is greater than 0, and it contains at least one non-whitespace character.
	 * <p><pre class="code">
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * @param str the CharSequence to check (may be {@code null})
	 * @return {@code true} if the CharSequence is not {@code null},
	 * its length is greater than 0, and it does not contain whitespace only
	 * @see Character#isWhitespace
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}
