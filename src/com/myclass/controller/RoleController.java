package com.myclass.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myclass.constants.UrlConstants;
import com.myclass.dao.RoleDAO;
import com.myclass.entity.Role;

@WebServlet(name = "RoleServlet", urlPatterns = { UrlConstants.ROLE_LIST, 
		UrlConstants.ROLE_ADD, UrlConstants.ROLE_EDIT, UrlConstants.ROLE_DELETE })
public class RoleController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private RoleDAO roleDAO = null;

	public RoleController() {
		roleDAO = new RoleDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getServletPath();
		
		Cookie cookie = new Cookie("name", "Hoang");
		resp.addCookie(cookie);
		
		switch (action) {
		case UrlConstants.ROLE_LIST:
			getList(req, resp);
			break;
		case UrlConstants.ROLE_ADD:
			getAdd(req, resp);
			break;
		case UrlConstants.ROLE_EDIT:
			getEdit(req, resp);
			break;
		case UrlConstants.ROLE_DELETE:
			break;
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.ROLE_ADD:
			postAdd(req, resp);
			break;
		case UrlConstants.ROLE_DELETE:
			postEdit(req, resp);
			break;
		default:
			break;
		}
	}

	private void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Gọi hàm findAll() để lấy danh sách role từ db
		List<Role> roles = roleDAO.findAll();
		req.setAttribute("roles", roles);
		req.getRequestDispatcher("/views/role/index.jsp").forward(req, resp);
	}
	
	private void getAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/role/add.jsp").forward(req, resp);
	}

	private void getEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Bước 1: Lấy ra id từ url
		int id = Integer.parseInt(req.getParameter("id"));

		Role role = roleDAO.findById(id);

		// Bước 3: Chuyển role vưa lấy từ db về cho jsp để show thông tin lên form
		req.setAttribute("role", role);
		req.getRequestDispatcher("/views/role/edit.jsp").forward(req, resp);
	}

	private void postAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		String name = req.getParameter("name");
		String description = req.getParameter("description");

		// KIỂM TRA DỮ LIỆU
		if (name == null || name.isEmpty()) {
			req.setAttribute("message", "Vui lòng nhập tên!");
			req.getRequestDispatcher("/views/role/add.jsp").forward(req, resp);
			return;
		}

		// Tạo mới 1 thể hiện của Role entity
		// Đẩy dữ liệu vào để gửi lên hàm add của RoleDao
		Role role = new Role();
		role.setName(name);
		role.setDescription(description);

		// Gọi hàm add của RoleDAO để thêm mới
		int result = roleDAO.add(role);

		// Trường hợp thêm mới thất bại
		if (result < 1) {
			req.setAttribute("message", "Thêm mới thất bại!");
			req.getRequestDispatcher("/views/role/add.jsp").forward(req, resp);
			return;
		} else {
			// Chuyển hướng về trang danh sách sản phẩm
			resp.sendRedirect(req.getContextPath() + "/role");
			return;
		}
	}

	private void postEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String description = req.getParameter("description");

		Role role = new Role(id, name, description);

		if (roleDAO.update(role) == -1) {
			req.setAttribute("message", "Cập nhật thất bại!");
			req.getRequestDispatcher("/views/role/edit.jsp").forward(req, resp);
			return;
		}

		// Chuyển hướng về trang danh sách quyền
		resp.sendRedirect(req.getContextPath() + "/role");
		return;
	}
}
