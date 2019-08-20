package com.myclass.dto;

import java.util.List;

public class UserDto{
	private int id;
	private String email;
	private String fullname;
	private String roleName;
	private String password;
	private String avatar;
	private int role_id;
	private List<TaskDto> tasks;
	public List<TaskDto> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskDto> tasks) {
		this.tasks = tasks;
	}
	public UserDto(int id, String email, String fullname, String roleName, String password, String avatar,
			int role_id, List<TaskDto> tasks) {
		super();
		this.id = id;
		this.email = email;
		this.fullname = fullname;
		this.roleName = roleName;
		this.password = password;
		this.avatar = avatar;
		this.role_id = role_id;
		this.tasks = tasks;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public UserDto() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String rolename) {
		this.roleName = rolename;
	}
}
