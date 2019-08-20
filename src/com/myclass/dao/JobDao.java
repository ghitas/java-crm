package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import com.myclass.connection.JDBConnection;
import com.myclass.constants.UrlConstants;
import com.myclass.entity.Job;

public class JobDao {
	public final String SELECT_JOB = "select j.id, j.name, j.start_date, j.end_date from jobs as j";
	public final String SELECT_JOB_BY_ID = "select j.id, j.name, j.start_date, j.end_date from jobs as j where id=?";
	public final String UPDATE_JOB = "UPDATE jobs SET name=?, start_date=?, end_date=? WHERE id=?";
	
	public List<Job> getList() {
		List<Job> jobs = null;
		try(Connection conn = JDBConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(SELECT_JOB);
			ResultSet rSet = statement.executeQuery();
			jobs = new ArrayList<Job>();
			while (rSet.next()) {
				Job job = new Job();
				job.setId(rSet.getInt("id"));
				job.setName(rSet.getString("name"));
				job.setStartDate(rSet.getDate("start_date"));
				job.setEndDate(rSet.getDate("end_date"));
				jobs.add(job);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobs;
	}
	public Job findJobById(int id) {
		Job job = new Job();
		try(Connection conn = JDBConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(SELECT_JOB_BY_ID);
			statement.setInt(1, id);
			ResultSet rSet = statement.executeQuery();
			while (rSet.next()) {
				job.setId(rSet.getInt("id"));
				job.setName(rSet.getString("name"));
				job.setStartDate(rSet.getDate("start_date"));
				job.setEndDate(rSet.getDate("end_date"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return job;
	}
	
	public int updateJob(Job job) {
		try(Connection conn = JDBConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(UPDATE_JOB);
			statement.setString(1, job.getName());
			statement.setDate(2, job.getStartDate());
			statement.setDate(3, job.getEndDate());
			statement.setInt(4, job.getId());
			int row = statement.executeUpdate();
			return row;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
