package com.sp.utils;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库的工具
 * 1. 对数据库进行封装，增删改查的操作
 * 2. 对于数据库进行检测操作
 * 3. 对于数据库进行封装的处理
 * 4. 构建一个数据库连接池的处理
 */
public class DBUtils {


    private Connection connection = null;

    public Connection getConnection(String url, String username, String password) {

//        String url = "jdbc:mysql://localhost:3306/user";
//        String username = "root";
//        String password = "123456";

        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载驱动
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<String> getResultSet(String sql) throws SQLException {

        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.execute(sql);
        ResultSet resultSet = pstmt.getResultSet();
        while (resultSet.next()) {
            list.add(String.valueOf(resultSet.getRow()));
        }
        return list;
    }

}
