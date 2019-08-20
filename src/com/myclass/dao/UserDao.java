package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.JDBConnection;
import com.myclass.dto.TaskDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;

public class UserDao {
	public List<User> findAll() {

		List<User> users = new ArrayList<User>();

		try (Connection conn = JDBConnection.getConnection()) {
			// Bước 2: Gửi câu truy vấn
			// Tạo ra câu truy vấn phù hợp với hệ quản trị CSDL mysql
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM users");
			// Thực thi truy vấn lấy dữu liệu
			ResultSet rs = statement.executeQuery();

			// Bước 3: Xử ký kết quả trả về
			while (rs.next()) {
				// Tạo entity User hứng dữ liệu mỗi dòng trả về từ database
				User user = new User();
				// Set thuộc tính cho User entity
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setFullname(rs.getString("fullname"));
				user.setAvatar(rs.getString("avatar"));
				user.setRoleId(rs.getInt("role_id"));
				// Add User vào danh sách để trả về cho jsp
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public List<UserDto> findAllWithRole() {

		List<UserDto> users = new ArrayList<UserDto>();
		String query = "SELECT u.id, u.email, u.fullname, r.description FROM users u " + 
				"JOIN roles r ON u.role_id = r.id";
		try (Connection conn = JDBConnection.getConnection()) {
			// Bước 2: Gửi câu truy vấn
			// Tạo ra câu truy vấn phù hợp với hệ quản trị CSDL mysql
			PreparedStatement statement = conn.prepareStatement(query);
			// Thực thi truy vấn lấy dữu liệu
			ResultSet rs = statement.executeQuery();

			// Bước 3: Xử ký kết quả trả về
			while (rs.next()) {
				// Tạo User DTO hứng dữ liệu mỗi dòng trả về từ database
				UserDto user = new UserDto();
				// Set thuộc tính cho User DTO
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setFullname(rs.getString("fullname"));
				user.setRoleName(rs.getString("description"));
				
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public User findById(int id) {

		User user = new User();

		try (Connection conn = JDBConnection.getConnection()) {
			// Bước 2: Gửi câu truy vấn
			// Tạo ra câu truy vấn phù hợp với hệ quản trị CSDL mysql
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
			statement.setInt(1, id);

			// Thực thi truy vấn lấy dữu liệu
			ResultSet rs = statement.executeQuery();

			// Bước 3: Xử ký kết quả trả về
			while (rs.next()) {
				// Set thuộc tính cho User entity
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setFullname(rs.getString("fullname"));
				user.setAvatar(rs.getString("avatar"));
				user.setRoleId(rs.getInt("role_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public int add(User user) {

		String query = "INSERT INTO Users(email, password, fullname, avatar, role_id) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = JDBConnection.getConnection()) {
			// Bước 2: Gửi câu truy vấn
			// Tạo ra câu truy vấn phù hợp với hệ quản trị CSDL mysql
			PreparedStatement statement = conn.prepareStatement(query);
			// Set dữ liệu cho câu truy vấn
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFullname());
			statement.setString(4, user.getAvatar());
			statement.setInt(5, user.getRoleId());

			// Trả về kết quả truy vấn
			return statement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int update(User user) {
		String query = "UPDATE users SET email = ?, password = ?, fullname = ?, avatar = ?, role_id = ? WHERE id = ?";
		try (Connection conn = JDBConnection.getConnection()) {

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFullname());
			statement.setString(4, user.getAvatar());
			statement.setInt(5, user.getRoleId());
			statement.setInt(6, user.getId());

			// Thực thi truy vấn lấy dữu liệu
			int result = statement.executeUpdate();

			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return - 1;
	}
	
	public int delete(int id) {
		String query = "DELETE FROM users WHERE id = ?";
		try (Connection conn = JDBConnection.getConnection()) {

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
	public UserDto getUserDetail(int id) {
		UserDto userDto = null;
		String query = "SELECT u.id, u.email, u.fullname,"
				+ "t.id as 'taskId', t.name as 'taskName', t.start_date as 'taskStartDate,"
				+ "t.end_date as 'taskEndDate', t.job_id as 'jobId', t.status_id as 'statusId"
				+ "s.name as 'statusName',"
				+ "j.name as 'jobName',"
				+ "r.description as 'roleName'"
				+ "FROM users u "
				+ "INNER JOIN tasks t ON t.user_id = u.id"
				+ "INNER JOIN jobs j ON t.job_id = j.id"
				+ "INNER JOIN status s ON t.status_id = s.id"
				+ "INNER JOIN roles r ON r.id = u.role_id"
				+ "WHERE u.id = ?";
		try (Connection conn = JDBConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				UserDto user = new UserDto();
				TaskDto taskDto = new TaskDto();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setFullname(rs.getString("fullname"));
				user.setRoleName(rs.getString("roleName"));
				taskDto.setId(rs.getInt("taskId"));
				taskDto.setName(rs.getString("taskName"));
				taskDto.setStartDate(rs.getDate("taskStartDate"));
				taskDto.setEndDate(rs.getDate("taskEndDate"));
				taskDto.setJobId(rs.getInt("jobId"));
				taskDto.setJobName(rs.getString("jobName"));
				taskDto.setStatusId(rs.getInt("statusId"));
				taskDto.setStatusName(rs.getString("statusName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDto;
	}
}
