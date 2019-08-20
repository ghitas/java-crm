package com.myclass.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myclass.connection.JDBConnection;
import com.myclass.constants.UrlConstants;
import com.myclass.dao.RoleDao;
import com.myclass.entity.Role;

@WebServlet(name = "RoleServlet", 
	urlPatterns = {UrlConstants.ROLE_LIST, UrlConstants.ROLE_ADD, UrlConstants.ROLE_EDIT, UrlConstants.ROLE_DELETE })
public class RoleController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private RoleDao roleDao = null;
	
	public RoleController() {
		roleDao = new RoleDao();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		req.setAttribute("action", action);// try auto close connection
		switch (action) {
		case UrlConstants.ROLE_LIST:
			getList(req, resp);
			break;
		case UrlConstants.ROLE_ADD:
			req.getRequestDispatcher(UrlConstants.ROLE_ADD_PATH).forward(req, resp);
			break;
		case UrlConstants.ROLE_EDIT:
			getRole(req, resp);
			break;
		case UrlConstants.ROLE_DELETE:
			deleteRole(req, resp);
			break;
		default:
			break;
		}
	}
	public void deleteRole(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("id"));
		int row = roleDao.deleteById(id, req, resp);
		if(row == -1) {
			req.setAttribute("message", "Delete fail!");
			req.getRequestDispatcher(UrlConstants.ROLE_LIST_PATH).forward(req, resp);
			return;
		}
		resp.sendRedirect(req.getContextPath()+"/admin/role");
	}
	public void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Role> roles = roleDao.findAll();
		req.setAttribute("roles", roles);
		req.getRequestDispatcher(UrlConstants.ROLE_LIST_PATH).forward(req, resp);
	}
	public void getRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Role role = roleDao.findById(id);
		req.setAttribute("role", role);
		req.getRequestDispatcher(UrlConstants.ROLE_EDIT_PATH).forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.ROLE_ADD:
			postAdd(req, resp);
			break;
		case UrlConstants.ROLE_EDIT:
			postEdit(req, resp);
			break;
		default:
			break;
		}
	}
	private void postAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		// validation
		if(name == null || name.trim().isEmpty()) {
			req.setAttribute("message", "please insert name");
			req.getRequestDispatcher(UrlConstants.ROLE_ADD_PATH).forward(req, resp);
			return;
		}
		Role role = new Role(-1, name, description);
		int rs = roleDao.add(role);
		if(rs > 0) {
			resp.sendRedirect(req.getContextPath()+"/admin/role");
		}else {
			req.setAttribute("message", "Adding fail!");
			req.getRequestDispatcher(UrlConstants.ROLE_LIST_PATH).forward(req, resp);
			return;
		}
	}
	private void postEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Role role = new Role();
		role.setId(Integer.parseInt(req.getParameter("id")));
		role.setName(req.getParameter("name"));
		role.setDescription(req.getParameter("description"));
		int rs = roleDao.update(role);
		if(rs == -1) {
			req.setAttribute("message", "can't edit role");
			req.getRequestDispatcher(UrlConstants.ROLE_EDIT_PATH).forward(req, resp);
			return;
		}
		resp.sendRedirect(req.getContextPath()+"/admin/role");
	}
}
