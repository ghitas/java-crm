package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.myclass.connection.JDBConnection;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;

public class LoginDao {
	private String query= "SELECT u.id, u.email,u.password,u.avatar,u.role_id, u.fullname, r.name "
			+ "FROM users u JOIN roles r ON u.role_id = r.id WHERE u.email=?";;
	public UserDto findByEmail(String email) {
		UserDto user = null;
		try (Connection conn = JDBConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				user = new UserDto();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setFullname(rs.getString("fullname"));
				user.setAvatar(rs.getString("avatar"));
				user.setRole_id(rs.getInt("role_id"));
				user.setRoleName(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
