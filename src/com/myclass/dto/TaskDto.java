package com.myclass.dto;

import java.sql.Date;

public class TaskDto {
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;
	private String jobName;
	private int statusId;
	private String statusName;
	
	public TaskDto() {}
	
	public TaskDto(int id, String name, Date startDate, Date endDate, String jobName, int statusId,
			String statusName) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.jobName = jobName;
		this.statusId = statusId;
		this.statusName = statusName;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
