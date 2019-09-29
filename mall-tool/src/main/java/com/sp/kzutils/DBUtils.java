package com.kuaizi.etl.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @Desc: utils for database<br/>
 * @Date: 2018年5月10日 下午5:31:33 <br/>
 * @Author: kinwu <br/>
 * @Version:  <br/>  
 * Copyright (c) 2018 @kuaizi.co. All Rights Reserved   
 */
public class DBUtils {
	private static final Logger log = LoggerFactory.getLogger(DBUtils.class);
	
	/**
	 * 获取数据库连接
	 * @param url
	 * @param user
	 * @param password
	 * @param driverClassName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 *Connection
	 * @exception 
	 * @since  1.0.0
	 */
	public static Connection getConnection(String url,String user,String password,String driverClassName) 
			throws ClassNotFoundException, SQLException {
		// 1.加载驱动程序
		Class.forName(driverClassName);
		// 2.获得数据库链接
		return DriverManager.getConnection(url, user, password);
	}
	
	/**
	 * 
	* 
	* @param rs ResultSet
	* @param ps PreparedStatement
	* @param con   Connection
	* void  
	* @exception   
	* @since  1.0.0
	 */
    public static void closeConn(ResultSet rs,Statement ps,Connection conn){
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				log.error("", e);
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
				log.error("", e);
			}
		}
		closeConn(conn);
    }
    
	/**
	 * close connection
	 * @param conn closing connection
	 */
	public static void closeConn(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
			log.error("close connection failed：" + e);
		}
	}

}
