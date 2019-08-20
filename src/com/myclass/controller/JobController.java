package com.myclass.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.myclass.constants.UrlConstants;
import com.myclass.dao.JobDAO;
import com.myclass.dao.TaskDAO;
import com.myclass.entity.Job;
import com.myclass.entity.Task;
import com.myclass.entity.User;

@WebServlet(name = "JobServlet", 
	urlPatterns = {UrlConstants.JOB_LIST, UrlConstants.JOB_ADD, UrlConstants.JOB_EDIT, UrlConstants.JOB_DETAIL, UrlConstants.JOB_DELETE })
public class JobController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private JobDAO jobDAO = null;
	private TaskDAO taskDAO = null;
	
	public JobController() {
		jobDAO = new JobDAO();
		taskDAO = new TaskDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.JOB_LIST:
			req.setAttribute("jobs", jobDAO.findAll());
			req.getRequestDispatcher("/views/job/index.jsp").forward(req, resp);
			break;
		case UrlConstants.JOB_ADD:
			req.getRequestDispatcher("/views/job/add.jsp").forward(req, resp);
			break;
		case UrlConstants.JOB_EDIT:
			getEdit(req, resp);
			break;
		case UrlConstants.JOB_DETAIL:
			getJobDetails(req, resp);
			break;
		case UrlConstants.JOB_DELETE:
			break;
		default:
			break;
		}
	}

	private void getJobDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Job job = jobDAO.findById(id);
		req.setAttribute("job", job);
		List<Task> tasks = taskDAO.findByJobId(id);
		req.setAttribute("tasks", tasks);
		select j.name as job_name,
		t.name as task_name, t.start_date as job_start_date,
		u.fullname as user_name,
		s.name as status_name
		 from jobs as j inner join tasks as t on t.job_id = j.id 
		 inner join users as u on t.user_id = u.id 
		 inner join status as s on t.status_id = s.id
		 where j.id = 1;
		req.getRequestDispatcher("/views/job/details.jsp").forward(req, resp);
	}

	private void getEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Job job = jobDAO.findById(id);
		req.setAttribute("job", job);
		req.getRequestDispatcher("/views/job/edit.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.JOB_ADD:
			postAdd(req, resp);
			break;
		case UrlConstants.JOB_EDIT:
			postEdit(req, resp);
			break;
		default:
			break;
		}
	}
	
	private void postEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		Date startDate = Date.valueOf(req.getParameter("startDate"));
		Date endDate= Date.valueOf(req.getParameter("endDate"));
		Job job = new Job(id, name, startDate, endDate);
		int result = jobDAO.update(job);
		if(result == -1) {
			req.setAttribute("message", "Can't update job");
		}else {
			resp.sendRedirect(req.getContextPath() + UrlConstants.JOB_LIST);
		}
	}

	private void postAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Job job = new Job();
		job.setName(req.getParameter("name"));
		job.setStartDate((Date)Date.valueOf(req.getParameter("startDate")));
		job.setEndDate(Date.valueOf(req.getParameter("endDate")));
		int result = jobDAO.add(job);
		if(result == -1) {
			req.setAttribute("message", "Can't add job");
		}else {			
			resp.sendRedirect(req.getContextPath() + UrlConstants.JOB_LIST);
		}
	}
}
