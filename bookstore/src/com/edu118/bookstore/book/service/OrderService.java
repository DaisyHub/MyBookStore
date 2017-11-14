package com.edu118.bookstore.book.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.edu118.bookstore.book.dao.OrderDao;
import com.edu118.bookstore.book.domain.Cart;
import com.edu118.bookstore.book.domain.Order;
import com.edu118.util.JdbcUtils;

public class OrderService {
	private OrderDao orderDao = new OrderDao();

	public List<Order> showOrders(String uid) {
		List<Order> orders = orderDao.selectOrders(uid);
		/*
		 * 1.通过uid找到该用户的所有订单 2.通过oid找到所有的orderitem
		 */
		return orderDao.findOrderItem(orders);
	}

	public void addOrder(String uid, String oid) {
		/*
		 * 1.通过uid找到这个人的购物车 2.生成订单编号，订单时间,总价，uid，地址要在desc页面加载后再添加
		 * 3.将购物车里的信息整合，添加到orderitem表中去
		 */
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			List<Cart> cartInfo = new CartService().showCart(uid);
			int total = 0;
			for (Cart c : cartInfo) {
				total += c.getSubtotal();
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ordertime = format.format(new Date());
			orderDao.insertOrder(conn, oid, ordertime, total, uid);
			orderDao.insertOrderItem(conn, cartInfo, oid);
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	public Order findOrderByOid(String oid) {
		return orderDao.selectOrderByOid(oid);
	}

	public void payOrder(String oid, String address) {
		orderDao.payOrder(oid, address);

	}

	public void confirmReceipt(String oid) {
		orderDao.confirmReceipt(oid);

	}
}
