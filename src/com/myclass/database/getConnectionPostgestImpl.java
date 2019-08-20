package com.myclass.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class getConnectionPostgestImpl implements getConnection{
	@Override
	public Connection connectDB() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/crm", "postgres", "12345!fra");
	}
}
