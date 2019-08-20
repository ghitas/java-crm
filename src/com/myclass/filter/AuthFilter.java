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

public class AuthFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//phia ben tren chain kiem tra request
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String action = req.getServletPath();
		if(action.equals("/login")) {
			chain.doFilter(request, response);
			return;
		}
		if(action.equals("/logout")) {
			chain.doFilter(request, response);
			return;
		}
		if(session.getAttribute("USER_LOGIN") == null) {
			resp.sendRedirect(req.getContextPath() + "/login?error=");
			return;
		}
//		Phan quyen
		UserDto user = (UserDto)session.getAttribute("USER_LOGIN");
		if(action.startsWith("/admin")) {
			if(user.getRoleName().equals("ROLE_ADMIN")) {
				chain.doFilter(request, response);
			}else {
				resp.sendRedirect(req.getContextPath() + "/error");
				return;
			}
		}else if(action.startsWith("/manager")) {
			if(user.getRoleName().equals("ROLE_MANAGER") || user.getRoleName().equals("ROLE_ADMIN")) {
				chain.doFilter(request, response);
			}else {
				resp.sendRedirect(req.getContextPath() + "/error");
				return;
			}
		}
		chain.doFilter(request, response);
	}

}
