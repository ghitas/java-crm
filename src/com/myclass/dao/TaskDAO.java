package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.JDBCConnection;
import com.myclass.dto.TaskDto;
import com.myclass.entity.Task;

public class TaskDAO {
	public List<Task> findAll() {
		List<Task> tasks = new ArrayList<Task>();
		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("select t.id, t.name, t.start_date, t.end_date from tasks as t");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Task task = new Task();
				task.setId(rs.getInt("id"));
				task.setName(rs.getString("name"));
				task.setStartDate(rs.getDate("start_date"));
				task.setEndDate(rs.getDate("end_date"));
				tasks.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tasks;
	}
	
	public List<Task> findAllById(int id){
		List<Task> tasks = new ArrayList<Task>();
		return tasks;
	}

	public List<Task> findByJobId(int id) {
		List<Task> tasks = new ArrayList<Task>();
		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("select t.id, t.name from tasks as t where t.job_id=?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Task task = new Task();
				task.setId(rs.getInt("id"));
				task.setName(rs.getString("name"));
				tasks.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tasks;
	}

	public List<TaskDto> findAllByUserId(int id) {
		List<TaskDto> tasks = new ArrayList<TaskDto>();
		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("select j.name as job_name, t.id, t.name, t.start_date, t.end_date, s.id as status_id, s.name as status_name from tasks as t inner join status as s on t.status_id = s.id inner join jobs as j on j.id = t.job_id where t.user_id=?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				TaskDto task = new TaskDto();
				task.setId(rs.getInt("id"));
				task.setName(rs.getString("name"));
				task.setStartDate(rs.getDate("start_date"));
				task.setEndDate(rs.getDate("end_date"));
				task.setJobName(rs.getString("job_name"));
				task.setStatusId(rs.getInt("status_id"));
				tasks.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tasks;
	}
}
