package com.iflytek.servelt;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.time.LocalDateTime;


import com.iflytek.model.Order;
import com.iflytek.model.User;
import com.iflytek.service.OrderService;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class OrderConfirmServlet
 */
@WebServlet("/order_confirm")
public class OrderConfirmServlet extends HttpServlet {
	
	private OrderService oService = new OrderService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Order o = (Order) request.getSession().getAttribute("order");
		try {
			BeanUtils.copyProperties(o, request.getParameterMap());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		o.setDatetime(LocalDateTime.now());
		o.setStatus(2);
		o.setUser((User) request.getSession().getAttribute("user"));
		System.out.println(o.getAddress());
		oService.addOrder(o);
		request.getSession().removeAttribute("order");
		
		request.setAttribute("msg", "订单支付成功！");
		request.getRequestDispatcher("/order_success.jsp").forward(request, response);
	}

}
