package com.iflytek.servelt;

import com.iflytek.model.Page;
import com.iflytek.model.Type;
import com.iflytek.service.GoodsService;
import com.iflytek.service.TypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



/**
 * Servlet implementation class GoodsListServlet
 */
@WebServlet("/goods_list")
public class GoodsListServlet extends HttpServlet {
	private GoodsService gService = new GoodsService();
	private TypeService tService = new TypeService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = 0;
		if(request.getParameter("id") != null) {
			id=Integer.parseInt(request.getParameter("id") ) ;
		}
		int pageNo = 1;
		if(request.getParameter("pageNo") != null) {
			pageNo=Integer.parseInt(request.getParameter("pageNo") ) ;
		}
		
//		List<Goods> list = gService.selectGoods(id, pageNo, 8);
//		request.setAttribute("list", list);
		
		Page p = gService.getGoodsPage(id, pageNo);
		request.setAttribute("p", p);
		request.setAttribute("id", id);
		Type t = null;
		if(id!=0) {
			t=tService.select(id);
		}
		request.setAttribute("t", t);
		request.getRequestDispatcher("/goods_list.jsp").forward(request, response);
	}

}
