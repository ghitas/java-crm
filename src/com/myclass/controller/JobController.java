package com.myclass.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myclass.constants.UrlConstants;
import com.myclass.dao.JobDao;
import com.myclass.entity.Job;

@WebServlet(name = "JobServlet", urlPatterns = {UrlConstants.JOB_DETAILS, UrlConstants.JOB_ADD, UrlConstants.JOB_LIST, UrlConstants.JOB_DELETE, UrlConstants.JOB_EDIT})
public class JobController extends HttpServlet{
	private JobDao jobDao;
	
	public JobController() {
		jobDao = new JobDao();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.JOB_LIST:
			getJobs(req, resp);
			break;
		case UrlConstants.JOB_EDIT:
			editJob(req, resp);
			break;

		case UrlConstants.JOB_ADD:
			
			break;

		case UrlConstants.JOB_DELETE:
			
			break;
		default:
			break;
		}
	}
	
	private void editJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Job job = jobDao.findJobById(id);
		req.setAttribute("job", job);
		req.getRequestDispatcher("/views/job/edit.jsp").forward(req, resp);
	}
	private void getJobs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Job> jobs = jobDao.getList();
		req.setAttribute("jobs", jobs);
		req.getRequestDispatcher("/views/job/index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.JOB_ADD:
//			postAdd(req, resp);
			break;
		case UrlConstants.JOB_EDIT:
			try {
				postEdit(req, resp);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	private void postEdit(HttpServletRequest req, HttpServletResponse resp) throws ParseException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		Date startDate = Date.valueOf(req.getParameter("startDate"));
		Date endDate = Date.valueOf(req.getParameter("endDate"));
		Job job = new Job(id, name, startDate, endDate);
		int row = jobDao.updateJob(job);
		if(row == -1) {
			req.setAttribute("message", "Update faild");
		}else {			
			resp.sendRedirect(req.getContextPath() + "/job");
		}
	}
}
