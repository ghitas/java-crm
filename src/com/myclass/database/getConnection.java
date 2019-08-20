package com.myclass.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface getConnection {
	Connection connectDB() throws ClassNotFoundException, SQLException;
}
