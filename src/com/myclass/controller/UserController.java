package com.myclass.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.myclass.connection.JDBConnection;
import com.myclass.constants.UrlConstants;
import com.myclass.dao.RoleDao;
import com.myclass.dao.UserDao;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;
import com.mysql.cj.Session;

@WebServlet(name = "UserServlet", urlPatterns = {UrlConstants.USER_ADD, UrlConstants.USER_DETAIL, UrlConstants.USER_DELETE, UrlConstants.USER_EDIT, UrlConstants.USER_LIST})
public class UserController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private RoleDao roleDao;
	
	public UserController() {
		userDao = new UserDao();
		roleDao = new RoleDao();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.USER_LIST:
			getList(req, resp);
			break;
		case UrlConstants.USER_ADD:
			req.setAttribute("roles", roleDao.findAll());
			req.getRequestDispatcher(UrlConstants.USER_ADD_PATH).forward(req, resp);
			break;
		case UrlConstants.USER_EDIT:
			req.getRequestDispatcher(UrlConstants.USER_EDIT_PATH).forward(req, resp);
			break;
		case UrlConstants.USER_DETAIL:
			getUserDetail(req, resp);
			break;
		case UrlConstants.USER_DELETE:
			deleteById(req, resp);
			break;
		default:
			break;
		}
	}
	private void getUserDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(UrlConstants.USER_DETAIL_PATH).forward(req, resp);
		int id = Integer.parseInt(req.getParameter("id"));
		UserDto user = new UserDto();
	}
	private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		int id=Integer.parseInt(req.getParameter("id"));
		int rs = userDao.delete(id);
		if(rs == -1) {
			req.setAttribute("message", "Delete fail");
			req.getRequestDispatcher(UrlConstants.USER_LIST_PATH).forward(req, resp);
			return;
		}else {
			resp.sendRedirect(req.getContextPath() + "/manager/user");
		}
	}
	private void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UserDto> users = userDao.findAllWithRole();
		req.setAttribute("users", users);
		req.getRequestDispatcher(UrlConstants.USER_LIST_PATH).forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.USER_ADD:
			postAdd(req, resp);
			break;
		case UrlConstants.USER_EDIT:
			req.getRequestDispatcher(UrlConstants.USER_EDIT_PATH).forward(req, resp);
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
		User user = new User(-1,email, password, fullname, avatar, roleId);
		
		if(userDao.add(user) != -1) {
			resp.sendRedirect(req.getContextPath() + "/manager/user");
			return;
		}
		req.setAttribute("message", "Thêm mới thất bại!");
		req.getRequestDispatcher(UrlConstants.USER_ADD_PATH).forward(req, resp);
	}
}
