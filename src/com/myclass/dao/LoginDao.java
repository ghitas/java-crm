package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.myclass.connection.JDBCConnection;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;

public class LoginDAO {

	private static final String FIND_BY_EMAIL = "SELECT u.id, u.email, u.password, u.fullname, u.avatar, r.name " + 
			"FROM public.users u " + 
			"JOIN public.roles r " + 
			"ON u.role_id = r.id " + 
			"WHERE email = ?;";
	 
	public UserDto findByEmail(String email) {
		
		UserDto user = null;
		try (Connection conn = JDBCConnection.getConnection()) {
			// Bước 2: Gửi câu truy vấn
			// Tạo ra câu truy vấn phù hợp với hệ quản trị CSDL mysql
			PreparedStatement statement = conn.prepareStatement(FIND_BY_EMAIL);
			statement.setString(1, email);

			// Thực thi truy vấn lấy dữu liệu
			ResultSet rs = statement.executeQuery();

			// Bước 3: Xử ký kết quả trả về
			while (rs.next()) {
				// Set thuộc tính cho User entity
				user = new UserDto();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setFullname(rs.getString("fullname"));
				user.setAvatar(rs.getString("avatar"));
				user.setRoleName(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
