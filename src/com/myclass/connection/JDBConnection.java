package com.myclass.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBConnection {
	private final static String url = "jdbc:mysql://localhost:3306/crm";
	private final static String user = "root";
	private final static String password = "@Pcn2104";
	public static Connection getConnection() {
		try {
			// Sử dụng class com.msql.cj.jdbc.Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// gọi hàm getConnection của Drivermanager để thực hiện kết nối
			// hàm trả về đối tượng Connection
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
