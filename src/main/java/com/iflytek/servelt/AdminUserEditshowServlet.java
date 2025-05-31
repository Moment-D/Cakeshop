package com.iflytek.servelt;

import com.iflytek.model.User;
import com.iflytek.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Servlet implementation class AdminUserEditshowServlet
 */
@WebServlet("/admin/user_editshow")
public class AdminUserEditshowServlet extends HttpServlet {
	
	private UserService uService = new UserService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User user = uService.selectById(id);
		request.setAttribute("u", user);
		request.getRequestDispatcher("/admin/user_edit.jsp").forward(request, response);
	}


}
