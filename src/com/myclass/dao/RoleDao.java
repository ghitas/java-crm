package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myclass.connection.JDBConnection;
import com.myclass.entity.Role;

public class RoleDao {
	
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		List<Role> roles = new ArrayList<Role>();
		// try auto close connection
		try(Connection conn = JDBConnection.getConnection()) {
			// B2: send query collection
			// create query statement
			String query = "SELECT * FROM roles";
			PreparedStatement statement = conn.prepareStatement(query);
			// execute 
			ResultSet rs = statement.executeQuery();
			// B3: response processing
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String desc = rs.getString("description");
				roles.add(new Role(id, name, desc));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roles;
	}
	
	public Role findById(int id) {
		Role role = new Role();
		try(Connection conn = JDBConnection.getConnection()) {
			String query = "SELECT * FROM roles WHERE id=?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
				role.setDescription(rs.getString("description"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return role;
	}
	
	public int add(Role role) {
		try(Connection conn = JDBConnection.getConnection()) {	
			// B2: send query collection
			// create query statement
			String query = "INSERT INTO roles (name, description)  VALUES(?,?)";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, role.getName());
			statement.setString(2, role.getDescription());
			// execute 
			return statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public int update(Role role) {
		String query = "UPDATE roles SET name = ?, description = ? WHERE id = ?";
		try (Connection conn = JDBConnection.getConnection()) {

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
	public int deleteById(int id, HttpServletRequest req, HttpServletResponse resp) {
		try(Connection conn = JDBConnection.getConnection()) {
			String query = "DELETE FROM roles WHERE id=?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			return statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
