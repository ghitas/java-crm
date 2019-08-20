package com.myclass.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myclass.constants.UrlConstants;
import com.myclass.dao.TaskDAO;
import com.myclass.entity.Task;

@WebServlet(name = "TaskServlet", urlPatterns = {UrlConstants.TASK_LIST, UrlConstants.TASK_ADD, UrlConstants.TASK_EDIT, UrlConstants.TASK_DETAIL, UrlConstants.TASK_DELETE})
public class TaskController extends HttpServlet{
	private TaskDAO taskDao = null;
	
	public TaskController() {
		taskDao = new TaskDAO();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.TASK_LIST:
			getList(req, resp);
			break;
		case UrlConstants.TASK_ADD:
			break;
		case UrlConstants.TASK_EDIT:
			break;
		case UrlConstants.TASK_DETAIL:
			break;
		case UrlConstants.TASK_DELETE:
			break;
		default:
			break;
		}
	}
	private void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Task> tasks =  taskDao.findAll();
		req.setAttribute("tasks", tasks);
		req.getRequestDispatcher("/views/task/index.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
