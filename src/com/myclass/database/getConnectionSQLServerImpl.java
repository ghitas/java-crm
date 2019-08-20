package com.myclass.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class getConnectionSQLServerImpl implements getConnection{
	@Override
	public Connection connectDB() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String hostName = "localhost";
		String sqlInstanceName = "201809COM0598\\SQLEXPRESS";
		String database = "DWQueue";
		String userName = "sa";
		String password = "12345!Fra";
		String connectionURL = "jdbc:jtds:sqlserver://" + hostName + ":1433/" + database + ";instance=" + sqlInstanceName;
 
     return DriverManager.getConnection(connectionURL, userName, password);
	}
}
