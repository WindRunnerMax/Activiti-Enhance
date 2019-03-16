package com.pressanykeytoac.base;
import java.sql.*;

public class connMysql {
	public static Connection connectMsql()
	{
		Connection conn = null;
		String url="jdbc:mysql://127.0.0.1:3306/activiti?useUnicode=true&characterEncoding=utf8&useSSL=true";
		String USER = "admin";
		String PASS = "147258369";
		try {
			Class.forName("com.mysql.jdbc.Driver");
					} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection(url,USER,PASS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
	    return conn;	
}
}
