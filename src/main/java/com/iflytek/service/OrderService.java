package com.iflytek.service;

import com.iflytek.dao.OrderDao;
import com.iflytek.model.Order;
import com.iflytek.model.OrderItem;
import com.iflytek.model.Page;
import com.iflytek.model.Order;
import com.iflytek.utils.DBUtil;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


public class OrderService {
	private OrderDao oDao = new OrderDao();

	public void addOrder(Order order) {
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);

			oDao.insertOrder(con, order);
			int id = oDao.getLastInsertId(con);
			order.setId(id);
			for (OrderItem item : order.getItemMap().values()) {
				oDao.insertOrderItem(con, item);
			}

			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (con != null)
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}

	public List<Order> selectAll(int userid) {
		List<Order> list = null;
		try {
			list = oDao.selectAll(userid);
			for (Order o : list) {
				List<OrderItem> l = oDao.selectAllItem(o.getId());
				o.setItemList(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Page<Order> getOrderPage(int status, int pageNo) {
		Page<Order> p = new Page<>();
		p.setPageNo(pageNo);
		int pageSize = 10;
		//int totalCount = 0;
		try {
			int totalCount = oDao.getOrderCount(status);
			p.setTotalCount(totalCount);
			p.setTotalPage((totalCount + pageSize - 1) / pageSize);

			// 确保页码在合理范围内
			if (pageNo < 1) pageNo = 1;
			if (pageNo > p.getTotalPage()) pageNo = p.getTotalPage();

			int offset = (pageNo - 1) * pageSize;
			List<Order> list = oDao.selectOrderList(status, offset, pageSize);

			// 获取每个订单的订单项
			for (Order o : list) {
				List<OrderItem> items = oDao.selectAllItem(o.getId());
				o.setItemList(items);
			}

			p.setList(list);
		} catch (SQLException e) {
			e.printStackTrace();
			p.setList(Collections.emptyList());
		}
		return p;

	}

	public Page<Order> getOrderPageByUser(int userid, int pageNo) {
    Page<Order> p = new Page<>();
    p.setPageNo(pageNo);
    int pageSize = 5; // 每页显示5条订单
    int totalCount = 0;
    try {
        totalCount = oDao.getOrderCountByUser(userid);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    p.setPageSizeAndTotalCount(pageSize, totalCount);
    List<Order> list = null;
    try {
        list = oDao.selectOrderListByUser(userid, pageNo, pageSize);
        for(Order o : list) {
            List<OrderItem> l = oDao.selectAllItem(o.getId());
            o.setItemList(l);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    p.setList(list); // 直接设置订单列表，不需要额外的包装
    return p;
	}
	public void updateStatus(int id,int status) {
		try {
			oDao.updateStatus(id, status);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void delete(int id) {
		Connection con = null;
		try {
			con = DBUtil.getDataSource().getConnection();
			con.setAutoCommit(false);
			
			oDao.deleteOrderItem(con, id);
			oDao.deleteOrder(con, id);
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(con!=null)
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
		
	}
}
