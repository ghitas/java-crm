package com.myclass.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.myclass.database.getConnectionPostgestImpl;

public class JDBCConnection {

	private static final String url = "jdbc:mysql://localhost:3306/crm_app";
	private static final String username = "root";
	private static final String password = "123456";
	
	public static Connection getConnection() {
		try {
			getConnectionPostgestImpl conn = new getConnectionPostgestImpl();
			// Gọi hàm getConnection của DriverManager để thực hiện kết nối db
			// Hàm này trả về đối tượng Connection
			return conn.connectDB();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
