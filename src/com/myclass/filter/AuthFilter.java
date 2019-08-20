package com.myclass.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myclass.dto.UserDto;

public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		String action = req.getServletPath();
		
		// kiem tra loi bat dang nhap lai
		if(action.equals("/login") || action.startsWith("/error")) {
			chain.doFilter(request, response);
			return;
		}
		
		// kiem tra nguoi dung dang nhap
		HttpSession session = req.getSession();
		if (session.getAttribute("USER_LOGIN") == null) { 
			// chuyen trang neu user chua dang nhap
			resp.sendRedirect(req.getContextPath() + "/login?error=");
			return;
		}
		// ========--------- Role separate ---------============
		
		// check role admin
		if(action.startsWith("/role")) {
			UserDto user = (UserDto)session.getAttribute("USER_LOGIN");
			if(user.getRoleName().equals("ROLE_ADMIN")) {
				chain.doFilter(request, response);
			}
			else {
				resp.sendRedirect(req.getContextPath() + "/error/403");
			}
		}
		
		// check role manager
		else if(action.startsWith("/user")) {
			UserDto user = (UserDto)session.getAttribute("USER_LOGIN");
			if(user.getRoleName().equals("ROLE_MANAGER") || user.getRoleName().equals("ROLE_ADMIN")) {
				chain.doFilter(request, response);
			}
			else {
				resp.sendRedirect(req.getContextPath() + "/error/403");
			}
		}
		else {
			chain.doFilter(request, response);
		}
		

	}

}
