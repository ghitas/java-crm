package com.myclass.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.myclass.dao.LoginDao;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;

@WebServlet(name = "AuthController", urlPatterns = {"/login", "/logout"})
public class AuthController extends HttpServlet{
	private LoginDao loginDao;
	public AuthController() {
		loginDao = new LoginDao();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		String action = req.getServletPath();
		switch (action) {
		case "/login":
			try {
				String error = req.getParameter("error");
				if(error != null) {
					req.setAttribute("message", "Vui long dang nhap truoc");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			req.getRequestDispatcher("/views/login/index.jsp").forward(req, resp);
			break;
		case "/logout":
			HttpSession session = req.getSession();
			if(session != null && session.getAttribute("USER_LOGIN") != null) {
				session.removeAttribute("USER_LOGIN");
			}
			resp.sendRedirect(req.getContextPath() + "/login");
			break;
		default:
			break;
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		UserDto user = loginDao.findByEmail(email);
		if(user == null) {
			req.setAttribute("message", "Tai khoan khong ton tai");
			req.getRequestDispatcher("/views/login/index.jsp").forward(req, resp);
			return;
		}
		if(!BCrypt.checkpw(password, user.getPassword())) {
			req.setAttribute("message", "Mat khau khong khop");
			req.getRequestDispatcher("/views/login/index.jsp").forward(req, resp);
			return;
		};
		//luu thong tin xuong section
		HttpSession session = req.getSession();//khoi tao section
		session.setAttribute("USER_LOGIN", user);//luu user vao session
		
		resp.sendRedirect(req.getContextPath() + "/home");
		
	}
}
