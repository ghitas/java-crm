package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.JDBCConnection;
import com.myclass.entity.Role;

public class RoleDAO {

	private final String ROLE_FIND_ALL = "SELECT * FROM roles";
	private final String ROLE_FIND_BY_ID = "SELECT * FROM roles WHERE id = ?";
	
	public List<Role> findAll(){
		
		List<Role> roles = new ArrayList<Role>();

		try (Connection conn = JDBCConnection.getConnection()) {
			// Bước 2: Gửi câu truy vấn
			// Tạo ra câu truy vấn phù hợp với hệ quản trị CSDL mysql
			PreparedStatement statement = conn.prepareStatement(ROLE_FIND_ALL);
			// Thực thi truy vấn lấy dữu liệu
			ResultSet rs = statement.executeQuery();

			// Bước 3: Xử ký kết quả trả về
			while (rs.next()) {
				// Tạo entity Role hứng dữ liệu mỗi dòng trả về từ database
				Role role = new Role();
				// Set thuộc tính cho role entity
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
				role.setDescription(rs.getString("description"));
				// Add role vào danh sách để trả về cho jsp
				roles.add(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}
	
	public Role findById(int id) {
		
		Role role = new Role();
		
		try (Connection conn = JDBCConnection.getConnection()) {
			// Bước 2: Gửi câu truy vấn
			// Tạo ra câu truy vấn phù hợp với hệ quản trị CSDL mysql
			PreparedStatement statement = conn.prepareStatement(ROLE_FIND_BY_ID);
			statement.setInt(1, id);

			// Thực thi truy vấn lấy dữu liệu
			ResultSet rs = statement.executeQuery();
			
			// Bước 3: Xử ký kết quả trả về
			while (rs.next()) {
				// Set thuộc tính cho role entity
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
				role.setDescription(rs.getString("description"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return role;
	}

	public int add(Role role) {
		
		String query = "INSERT INTO roles(name, description) VALUES (?, ?)";
		try (Connection conn = JDBCConnection.getConnection()) {
			// Bước 2: Gửi câu truy vấn
			// Tạo ra câu truy vấn phù hợp với hệ quản trị CSDL mysql
			PreparedStatement statement = conn.prepareStatement(query);
			// Set dữ liệu cho câu truy vấn
			statement.setString(1, role.getName());
			statement.setString(2, role.getDescription());

			// Trả về kết quả truy vấn
			return statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int update(Role role) {
		String query = "UPDATE roles SET name = ?, description = ? WHERE id = ?";
		try (Connection conn = JDBCConnection.getConnection()) {

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, role.getName());
			statement.setString(2, role.getDescription());
			statement.setInt(3, role.getId());

			// Thực thi truy vấn lấy dữu liệu
			int result = statement.executeUpdate();

			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return - 1;
	}
	
	public int delete(int id) {
		String query = "DELETE FROM roles WHERE id = ?";
		try (Connection conn = JDBCConnection.getConnection()) {

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);

			// Thực thi truy vấn lấy dữu liệu
			int result = statement.executeUpdate();

			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return - 1;
	}
}
