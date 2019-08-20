package com.myclass.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.myclass.dao.LoginDAO;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;

@WebServlet(name = "AuthServlet", urlPatterns = {"/login", "/logout"})
public class AuthControler extends HttpServlet{

	private LoginDAO loginDAO = null;
	
	public AuthControler() {
		loginDAO = new LoginDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getServletPath();
		
		switch (action) {
		case "/login":
			String error = req.getParameter("error");
			// Kiểm tra tham số error xem có null hoặc rỗng ko?
			if(error == null || error.isEmpty()) {
				req.getRequestDispatcher("/views/login/index.jsp").forward(req, resp);
			}
			else {
				req.setAttribute("message", "Vui lòng đăng nhập trước khi truy cập vào hệ thống!");
				req.getRequestDispatcher("/views/login/index.jsp").forward(req, resp);
			}
			break;

		case "/logout": 
			// Xoá Session
			HttpSession session = req.getSession();
			if(session != null && session.getAttribute("USER_LOGIN") != null) {
				session.removeAttribute("USER_LOGIN");
			}
			// Chuyển hướng về trang login
			resp.sendRedirect(req.getContextPath() + "/login");
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		// Lấy thông tin đăng nhập từ form
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		// Lấy thông tin user từ db dựa theo email
		UserDto user = loginDAO.findByEmail(email);
		
		// Nếu hàm findByEmail trả về null (không tìm thấy tài khoản)
		if(user == null) {
			req.setAttribute("message", "Tài khoản không tồn tại!");
			req.getRequestDispatcher("/views/login/index.jsp").forward(req, resp);
			return;
		}
		
		// Kiểm tra mật khẩu 
		// Nếu mật khẩu không khớp
		if(!BCrypt.checkpw(password, user.getPassword())) {
			req.setAttribute("message", "Mật khẩu không khớp!");
			req.getRequestDispatcher("/views/login/index.jsp").forward(req, resp);
			return;
		}
		// Lưu thông tin đăng nhập vào Session
		HttpSession session = req.getSession(); // Khởi tạo đối tượng session
		session.setAttribute("USER_LOGIN", user); // Lưu user vào session có tên USER_LOGIN
		
		// Đăng nhập thành công
		resp.sendRedirect(req.getContextPath() + "/home");
	}
}
