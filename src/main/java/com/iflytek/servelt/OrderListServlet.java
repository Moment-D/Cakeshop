package com.iflytek.servelt;

import com.iflytek.model.Order;
import com.iflytek.model.User;
import com.iflytek.model.Page;
import com.iflytek.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Dream
 */
@WebServlet("/order_list")
public class OrderListServlet extends HttpServlet {
	
	private OrderService oService = new OrderService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("user");
		if(u == null) {
			response.sendRedirect("index");
			return;
		}

		int pageNo = 1;
		if(request.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		
		Page<Order> p = oService.getOrderPageByUser(u.getId(), pageNo);
		request.setAttribute("p", p);
		request.setAttribute("orderList", p.getList());
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}
}
