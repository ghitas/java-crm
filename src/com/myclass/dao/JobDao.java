package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.JDBCConnection;
import com.myclass.entity.Job;

public class JobDAO {
	public List<Job> findAll() {

		List<Job> jobs = new ArrayList<Job>();

		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM jobs");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Job job = new Job();
				job.setId(rs.getInt("id"));
				job.setName(rs.getString("name"));
				job.setStartDate(rs.getDate("start_date"));
				job.setEndDate(rs.getDate("end_date"));
				jobs.add(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobs;
	}

//	public List<UserDto> findAllWithRole() {
//
//		List<UserDto> users = new ArrayList<UserDto>();
//		String query = "SELECT u.id, u.email, u.fullname, r.description FROM users u " + 
//				"JOIN roles r ON u.role_id = r.id";
//		try (Connection conn = JDBCConnection.getConnection()) {
//			// Bước 2: Gửi câu truy vấn
//			// Tạo ra câu truy vấn phù hợp với hệ quản trị CSDL mysql
//			PreparedStatement statement = conn.prepareStatement(query);
//			// Thực thi truy vấn lấy dữu liệu
//			ResultSet rs = statement.executeQuery();
//
//			// Bước 3: Xử ký kết quả trả về
//			while (rs.next()) {
//				// Tạo User DTO hứng dữ liệu mỗi dòng trả về từ database
//				UserDto user = new UserDto();
//				// Set thuộc tính cho User DTO
//				user.setId(rs.getInt("id"));
//				user.setEmail(rs.getString("email"));
//				user.setFullname(rs.getString("fullname"));
//				user.setRoleName(rs.getString("description"));
//				
//				users.add(user);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return users;
//	}
//	
	public Job findById(int id) {

		Job job = new Job();

		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM jobs WHERE id = ?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				job.setId(rs.getInt("id"));
				job.setName(rs.getString("name"));
				job.setStartDate(rs.getDate("start_date"));
				job.setEndDate(rs.getDate("end_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return job;
	}

	public int add(Job job) {
		String query = "INSERT INTO Jobs(name, start_date, end_date) VALUES (?, ?, ?)";
		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, job.getName());
			statement.setDate(2, job.getStartDate());
			statement.setDate(3, job.getEndDate());
			return statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int update(Job job) {
		String query = "UPDATE jobs SET name=?, start_date=?, end_date=? WHERE id = ?";
		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, job.getName());
			statement.setDate(2, job.getStartDate());
			statement.setDate(3, job.getEndDate());
			statement.setInt(4, job.getId());
			int result = statement.executeUpdate();
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return - 1;
	}
	
//	public List<JobDto> findTaskByJobId(int id){
//		List<JobDto> tasks = new ArrayList<JobDto>();
//		try (Connection conn = JDBCConnection.getConnection()) {
//			PreparedStatement statement = conn.prepareStatement("select j.id, j.name, j.start_date, j.end_date,"
//					+ "t.id as task_id, t.name as task_name from jobs as j inner join tasks as t on j.id = t.job_id where j.id=?");
//			statement.setInt(1, id);
//			ResultSet rs = statement.executeQuery();
//			while (rs.next()) {
//				JobDto jobDto = new JobDto();
//				jobDto.setId(rs.getInt("id"));
//				jobDto.setName(rs.getString("name"));
//				jobDto.setStartDate(rs.getDate("start_date"));
//				jobDto.setEndDate(rs.getDate("end_date"));
//				jobDto.setTaskId(rs.getInt("task_id"));
//				jobDto.setTaskName(rs.getString("task_name"));
//				tasks.add(jobDto);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return tasks;
//	}
//	
//	public int delete(int id) {
//		String query = "DELETE FROM users WHERE id = ?";
//		try (Connection conn = JDBCConnection.getConnection()) {
//
//			PreparedStatement statement = conn.prepareStatement(query);
//			statement.setInt(1, id);
//
//			// Thực thi truy vấn lấy dữu liệu
//			int result = statement.executeUpdate();
//
//			return result;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return - 1;
//	}
}
