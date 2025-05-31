package com.iflytek.servelt;

import com.iflytek.model.Goods;
import com.iflytek.model.Order;
import com.iflytek.service.GoodsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;



/**
 * Servlet implementation class GoodsBuyServlet
 */
@WebServlet("/goods_buy")
public class GoodsBuyServlet extends HttpServlet {
	
	private GoodsService gService = new GoodsService();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Map<String, String[]> map =request.getParameterMap();
//		for(String key : map.keySet()) {
//			System.out.println(key+":"+map.get(key));
//		}
		Order o = null;
		if(request.getSession().getAttribute("order") != null) {
			o = (Order) request.getSession().getAttribute("order");
		}else {
			o = new Order();
			request.getSession().setAttribute("order", o);
		}
		int goodsid = Integer.parseInt(request.getParameter("goodsid"));
		Goods goods = gService.getById(goodsid);
		if(goods.getStock()>0) {
			o.addGoods(goods);
			response.getWriter().print("ok");
		}else {
			response.getWriter().print("fail");
		}
	}

}
