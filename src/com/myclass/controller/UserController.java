package com.myclass.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.myclass.constants.UrlConstants;
import com.myclass.dao.RoleDAO;
import com.myclass.dao.TaskDAO;
import com.myclass.dao.UserDAO;
import com.myclass.dto.TaskDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;

@WebServlet(name = "UserServlet", 
	urlPatterns = {UrlConstants.USER_LIST, UrlConstants.USER_ADD, UrlConstants.USER_EDIT, UrlConstants.USER_DELETE, UrlConstants.USER_DETAIL })
public class UserController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO = null;
	private RoleDAO roleDAO = null;
	private TaskDAO taskDAO = null;
	
	public UserController() {
		userDAO = new UserDAO();
		roleDAO = new RoleDAO();
		taskDAO = new TaskDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.USER_LIST:
			req.setAttribute("users", userDAO.findAllWithRole());
			req.getRequestDispatcher("/views/user/index.jsp").forward(req, resp);
			break;
		case UrlConstants.USER_ADD:
			req.setAttribute("roles", roleDAO.findAll());
			req.getRequestDispatcher("/views/user/add.jsp").forward(req, resp);
			break;
		case UrlConstants.USER_EDIT:
			req.getRequestDispatcher("/views/user/edit.jsp").forward(req, resp);
			break;
		case UrlConstants.USER_DETAIL:
			getDetails(req, resp);
			break;
		case UrlConstants.USER_DELETE:
			break;
		default:
			break;
		}
	}
	
	private void getDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		UserDto user = userDAO.findByIdWithRole(id);
		req.setAttribute("user", user);
		List<TaskDto> tasks = taskDAO.findAllByUserId(id);
		List<TaskDto> openTasks = new ArrayList<TaskDto>();
		List<TaskDto> processTasks = new ArrayList<TaskDto>();
		List<TaskDto> finishTasks = new ArrayList<TaskDto>();
		for(TaskDto task: tasks) {
			switch (task.getStatusId()) {
			case 1:
				openTasks.add(task);
				break;
			case 2:
				processTasks.add(task);
				break;
			case 3:
				finishTasks.add(task);
				break;
			default:
				break;
			}
		}
		int open =  (int)(openTasks.size()*100.0f)/tasks.size();
		req.setAttribute("open", open);
		int process = (int)(processTasks.size()*100.0f)/tasks.size();
		req.setAttribute("process", process);
		req.setAttribute("finish", 100-open-process);
		req.setAttribute("openTasks", openTasks);
		req.setAttribute("processTasks", processTasks);
		req.setAttribute("finishTasks", finishTasks);
		req.getRequestDispatcher("/views/user/details.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.USER_ADD:
			postAdd(req, resp);
			break;
		case UrlConstants.USER_EDIT:
			req.getRequestDispatcher("/views/user/edit.jsp").forward(req, resp);
			break;
		default:
			break;
		}
	}
	
	private void postAdd(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		// Lấy thông tin từ form
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String fullname = req.getParameter("fullname");
		String avatar = req.getParameter("avatar");
		int roleId = Integer.parseInt(req.getParameter("roleId"));
		
		// Mã hóa mật khẩu
		password = BCrypt.hashpw(password, BCrypt.gensalt(12));
		
		// Khởi tạo đối tượng user
		User user = new User(email, password, fullname, avatar, roleId);
		
		if(userDAO.add(user) != -1) {
			resp.sendRedirect(req.getContextPath() + "/user");
			return;
		}
		req.setAttribute("message", "Thêm mới thất bại!");
		req.getRequestDispatcher("/views/user/add.jsp").forward(req, resp);
	}
}
